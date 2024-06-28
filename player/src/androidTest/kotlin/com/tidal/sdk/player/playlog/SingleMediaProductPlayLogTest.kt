package com.tidal.sdk.player.playlog

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.util.isLoggedIn
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.player.Player
import com.tidal.sdk.player.common.PlaybackEngineUsageAfterReleaseException
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.events.EventReporterModuleRoot
import com.tidal.sdk.player.events.di.DefaultEventReporterComponent
import com.tidal.sdk.player.events.model.PlaybackSession
import com.tidal.sdk.player.events.playlogtest.PlayLogTestDefaultEventReporterComponentFactory
import com.tidal.sdk.player.events.reflectionComponentFactoryF
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.setBodyFromFile
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.atMost
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify

internal class SingleMediaProductPlayLogTest {

    @get:Rule
    val server = MockWebServer()

    private val eventReporterCoroutineScope =
        TestScope(StandardTestDispatcher(TestCoroutineScheduler()))
    private val responseDispatcher = PlayLogTestMockWebServerDispatcher(server)
    private val eventSender = mock<EventSender>()
    private val mediaProduct = MediaProduct(ProductType.TRACK, "1", "TEST_1", "456")
    private lateinit var player: Player

    @Before
    fun setUp() {
        EventReporterModuleRoot.reflectionComponentFactoryF = {
            PlayLogTestDefaultEventReporterComponentFactory(eventReporterCoroutineScope)
        }
        responseDispatcher[
            "https://api.tidal.com/v1/tracks/${mediaProduct.productId}/playbackinfo?playbackmode=STREAM&assetpresentation=FULL&audioquality=LOW&immersiveaudio=true".toHttpUrl(),
        ] = {
            MockResponse().setBodyFromFile(
                "api-responses/playbackinfo/tracks/playlogtest/get_1_bts.json",
            )
        }
        responseDispatcher["https://test.audio.tidal.com/1_bts.m4a".toHttpUrl()] = {
            MockResponse().setBodyFromFile("raw/playlogtest/1_bts.m4a")
        }
        server.dispatcher = responseDispatcher

        player = Player(
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
                as Application,
            object : CredentialsProvider {
                private val CREDENTIALS = Credentials(
                    clientId = "a client id",
                    requestedScopes = emptySet(),
                    clientUniqueKey = null,
                    grantedScopes = emptySet(),
                    userId = "a non-null user id",
                    expires = null,
                    token = "a non-null token",
                )

                override val bus: Flow<TidalMessage> = emptyFlow()

                override suspend fun getCredentials(apiErrorSubStatus: String?) =
                    AuthResult.Success(CREDENTIALS)

                override fun isUserLoggedIn() = CREDENTIALS.isLoggedIn()
            },
            eventSender = eventSender,
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request()
                    val mockWebServerUrl = responseDispatcher.urlRecords[request.url]
                        ?: return@addInterceptor it.proceed(request)
                    it.proceed(
                        request.newBuilder()
                            .url(mockWebServerUrl)
                            .build(),
                    )
                }.build(),
        )
    }

    companion object {
        private lateinit var originalEventReporterComponentFactoryF:
            () -> DefaultEventReporterComponent.Factory

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            originalEventReporterComponentFactoryF =
                EventReporterModuleRoot.reflectionComponentFactoryF
        }
    }

    @After
    fun afterEach() {
        try {
            runBlocking {
                val job = launch { player.playbackEngine.events.first { it is Event.Release } }
                player.release()
                job.join()
            }
        } catch (throwable: Throwable) {
            if (throwable !is PlaybackEngineUsageAfterReleaseException) {
                throw throwable
            }
        }
        verify(eventSender, atMost(Int.MAX_VALUE))
            .sendEvent(
                argThat { !contentEquals("playback_session") },
                anyOrNull(),
                anyOrNull(),
                anyOrNull(),
            )
        verifyNoMoreInteractions(eventSender)
    }

    @Test
    fun loadAndPlayUntilEnd() = runTest {
        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(Gson().fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    // https://github.com/androidx/media/issues/1252
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    // https://github.com/androidx/media/issues/1253
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    assertThat(get("actions").asJsonArray.size()).isEqualTo(0)
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Suppress("LongMethod")
    @Test
    fun loadAndPlayThenPauseThenPlay() = runTest {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(4.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(2.seconds)
            while (player.playbackEngine.assetPosition < 2) {
                delay(10.milliseconds)
            }
            player.playbackEngine.pause()
            delay(1.seconds)
            player.playbackEngine.play()
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    with(get("actions").asJsonArray) {
                        val stopAction =
                            gson.fromJson(this[0], PlaybackSession.Payload.Action::class.java)
                        assertThat(stopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(stopAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val startAction =
                            gson.fromJson(this[1], PlaybackSession.Payload.Action::class.java)
                        assertThat(startAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(startAction.assetPositionSeconds)
                            .isAssetPositionEqualTo(stopAction.assetPositionSeconds)
                        assertThat(startAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val perfectResumeTimestamp = stopAction.timestamp +
                            1.seconds.inWholeMilliseconds
                        assertThat(startAction.timestamp)
                            .isBetween(perfectResumeTimestamp - 500, perfectResumeTimestamp + 500)
                    }
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Suppress("LongMethod")
    @Test
    fun loadAndPlayThenSeekForward() = runTest {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(4.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(2.seconds)
            while (player.playbackEngine.assetPosition < 2) {
                delay(10.milliseconds)
            }
            player.playbackEngine.seek(3000F)
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    with(get("actions").asJsonArray) {
                        val stopAction =
                            gson.fromJson(this[0], PlaybackSession.Payload.Action::class.java)
                        assertThat(stopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(stopAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val startAction =
                            gson.fromJson(this[1], PlaybackSession.Payload.Action::class.java)
                        assertThat(startAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(startAction.assetPositionSeconds).isAssetPositionEqualTo(3.0)
                        val perfectResumeTimestamp = stopAction.timestamp
                        assertThat(startAction.timestamp)
                            .isBetween(perfectResumeTimestamp - 500, perfectResumeTimestamp + 500)
                    }
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Test
    fun loadAndPlayThenSeekBack() = runTest {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(4.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(3.seconds)
            while (player.playbackEngine.assetPosition < 3) {
                delay(10.milliseconds)
            }
            player.playbackEngine.seek(2000F)
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    with(get("actions").asJsonArray) {
                        val stopAction =
                            gson.fromJson(this[0], PlaybackSession.Payload.Action::class.java)
                        assertThat(stopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(stopAction.assetPositionSeconds).isAssetPositionEqualTo(3.0)
                        val startAction =
                            gson.fromJson(this[1], PlaybackSession.Payload.Action::class.java)
                        assertThat(startAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(startAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val perfectResumeTimestamp = stopAction.timestamp
                        assertThat(startAction.timestamp)
                            .isBetween(perfectResumeTimestamp - 500, perfectResumeTimestamp + 500)
                    }
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Test
    fun loadAndPlayThenSeekForwardWhilePaused() = runTest {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(4.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(2.seconds)
            while (player.playbackEngine.assetPosition < 2) {
                delay(10.milliseconds)
            }
            player.playbackEngine.pause()
            player.playbackEngine.seek(3000F)
            player.playbackEngine.play()
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    with(get("actions").asJsonArray) {
                        val stopAction =
                            gson.fromJson(this[0], PlaybackSession.Payload.Action::class.java)
                        assertThat(stopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(stopAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val startAction =
                            gson.fromJson(this[1], PlaybackSession.Payload.Action::class.java)
                        assertThat(startAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(startAction.assetPositionSeconds).isAssetPositionEqualTo(3.0)
                        val perfectResumeTimestamp = stopAction.timestamp
                        assertThat(startAction.timestamp)
                            .isBetween(perfectResumeTimestamp - 500, perfectResumeTimestamp + 500)
                    }
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Test
    fun loadAndPlayThenSeekBackWhilePaused() = runTest {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(4.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(3.seconds)
            while (player.playbackEngine.assetPosition < 3) {
                delay(10.milliseconds)
            }
            player.playbackEngine.pause()
            player.playbackEngine.seek(2_000F)
            player.playbackEngine.play()
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    with(get("actions").asJsonArray) {
                        val stopAction =
                            gson.fromJson(this[0], PlaybackSession.Payload.Action::class.java)
                        assertThat(stopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(stopAction.assetPositionSeconds).isAssetPositionEqualTo(3.0)
                        val startAction =
                            gson.fromJson(this[1], PlaybackSession.Payload.Action::class.java)
                        assertThat(startAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(startAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val perfectResumeTimestamp = stopAction.timestamp
                        assertThat(startAction.timestamp)
                            .isBetween(perfectResumeTimestamp - 500, perfectResumeTimestamp + 500)
                    }
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Suppress("LongMethod")
    @Test
    fun playWithDoublePauseAndResume() = runTest {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(4.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(2.seconds)
            while (player.playbackEngine.assetPosition < 2) {
                delay(10.milliseconds)
            }
            player.playbackEngine.pause()
            player.playbackEngine.play()
            delay(1.seconds)
            while (player.playbackEngine.assetPosition < 3) {
                delay(10.milliseconds)
            }
            player.playbackEngine.pause()
            player.playbackEngine.play()
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    with(get("actions").asJsonArray) {
                        val firstStopAction =
                            gson.fromJson(this[0], PlaybackSession.Payload.Action::class.java)
                        assertThat(firstStopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(firstStopAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val firstStartAction =
                            gson.fromJson(this[1], PlaybackSession.Payload.Action::class.java)
                        assertThat(firstStartAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(firstStartAction.assetPositionSeconds)
                            .isAssetPositionEqualTo(2.0)
                        val perfectFirstResumeTimestamp = firstStopAction.timestamp
                        assertThat(firstStartAction.timestamp)
                            .isBetween(
                                perfectFirstResumeTimestamp - 500,
                                perfectFirstResumeTimestamp + 500,
                            )
                        val secondStopAction =
                            gson.fromJson(this[2], PlaybackSession.Payload.Action::class.java)
                        assertThat(secondStopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(secondStopAction.assetPositionSeconds)
                            .isAssetPositionEqualTo(3.0)
                        val secondStartAction =
                            gson.fromJson(this[3], PlaybackSession.Payload.Action::class.java)
                        assertThat(secondStartAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(secondStartAction.assetPositionSeconds)
                            .isAssetPositionEqualTo(3.0)
                        val perfectSecondResumeTimestamp = secondStopAction.timestamp
                        assertThat(secondStartAction.timestamp)
                            .isBetween(
                                perfectSecondResumeTimestamp - 500,
                                perfectSecondResumeTimestamp + 500,
                            )
                    }
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Suppress("LongMethod")
    @Test
    fun playWithDoubleSeek() = runTest {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(4.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(2.seconds)
            while (player.playbackEngine.assetPosition < 2) {
                delay(10.milliseconds)
            }
            player.playbackEngine.seek(3_000F)
            player.playbackEngine.seek(2_000F)
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    with(get("actions").asJsonArray) {
                        val firstStopAction =
                            gson.fromJson(this[0], PlaybackSession.Payload.Action::class.java)
                        assertThat(firstStopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(firstStopAction.assetPositionSeconds).isAssetPositionEqualTo(2.0)
                        val firstStartAction =
                            gson.fromJson(this[1], PlaybackSession.Payload.Action::class.java)
                        assertThat(firstStartAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(firstStartAction.assetPositionSeconds)
                            .isAssetPositionEqualTo(3.0)
                        val perfectFirstResumeTimestamp = firstStopAction.timestamp
                        assertThat(firstStartAction.timestamp)
                            .isBetween(
                                perfectFirstResumeTimestamp - 500,
                                perfectFirstResumeTimestamp + 500,
                            )
                        val secondStopAction =
                            gson.fromJson(this[2], PlaybackSession.Payload.Action::class.java)
                        assertThat(secondStopAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_STOP)
                        assertThat(secondStopAction.assetPositionSeconds)
                            .isAssetPositionEqualTo(3.0)
                        val secondStartAction =
                            gson.fromJson(this[3], PlaybackSession.Payload.Action::class.java)
                        assertThat(secondStartAction.actionType)
                            .isEqualTo(PlaybackSession.Payload.Action.Type.PLAYBACK_START)
                        assertThat(secondStartAction.assetPositionSeconds)
                            .isAssetPositionEqualTo(2.0)
                        val perfectSecondResumeTimestamp = secondStopAction.timestamp
                        assertThat(secondStartAction.timestamp)
                            .isBetween(
                                perfectSecondResumeTimestamp - 500,
                                perfectSecondResumeTimestamp + 500,
                            )
                    }
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Test
    fun playFromSeekPosition() = runTest {
        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.seek(2_000F)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(Gson().fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(2.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    assertThat(get("actions").asJsonArray).isEmpty()
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Test
    fun playWithRepeatOne() = runTest {
        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.setRepeatOne(true)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }
                    .take(2)
                    .collect()
            }
            player.playbackEngine.setRepeatOne(false)
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender, times(2)).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(Gson().fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble)
                        .isAssetPositionEqualTo(MEDIA_PRODUCT_DURATION_SECONDS)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    assertThat(get("actions").asJsonArray).isEmpty()
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Test
    fun playAndStop() = runTest {
        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(1.seconds)
            while (player.playbackEngine.assetPosition < 1) {
                delay(10.milliseconds)
            }
            player.playbackEngine.reset()
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(Gson().fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble).isAssetPositionEqualTo(1.0)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    assertThat(get("actions").asJsonArray).isEmpty()
                }
                true
            },
            eq(emptyMap()),
        )
    }

    @Test
    fun playWithRelease() = runTest {
        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(8.seconds) {
                player.playbackEngine.events.filter { it is Event.MediaProductTransition }.first()
            }
            delay(1.seconds)
            while (player.playbackEngine.assetPosition < 1) {
                delay(10.milliseconds)
            }
            player.playbackEngine.release()
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(Gson().fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isAssetPositionEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble).isAssetPositionEqualTo(1.0)
                    assertThat(get("actualProductId").asString).isEqualTo(mediaProduct.productId)
                    assertThat(get("sourceType")?.asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId")?.asString).isEqualTo(mediaProduct.sourceId)
                    assertThat(get("actions").asJsonArray).isEmpty()
                }
                true
            },
            eq(emptyMap()),
        )
    }
}

private const val MEDIA_PRODUCT_DURATION_SECONDS = 5.055
