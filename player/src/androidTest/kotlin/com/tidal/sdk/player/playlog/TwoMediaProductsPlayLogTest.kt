package com.tidal.sdk.player.playlog

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
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
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.events.EventReporterModuleRoot
import com.tidal.sdk.player.events.di.DefaultEventReporterComponent
import com.tidal.sdk.player.events.playlogtest.PlayLogTestDefaultEventReporterComponentFactory
import com.tidal.sdk.player.events.reflectionComponentFactoryF
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.setBodyFromFile
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
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
import org.junit.runners.Parameterized
import org.mockito.Mockito.atMost
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify

internal class TwoMediaProductsPlayLogTest {

    @get:Rule
    val server = MockWebServer()

    private val eventReporterCoroutineScope =
        TestScope(StandardTestDispatcher(TestCoroutineScheduler()))
    private val responseDispatcher = PlayLogTestMockWebServerDispatcher(server)
    private val eventSender = mock<EventSender>()
    private val mediaProduct1 = MediaProduct(ProductType.TRACK, "1", "TEST_1", "456")
    private val mediaProduct2 = MediaProduct(ProductType.TRACK, "2", "TEST_2", "789")
    private lateinit var player: Player

    @Before
    fun setUp() {
        responseDispatcher[
            "https://api.tidal.com/v1/tracks/${mediaProduct1.productId}/playbackinfo?playbackmode=STREAM&assetpresentation=FULL&audioquality=LOW&immersiveaudio=true".toHttpUrl(),
        ] = {
            MockResponse().setBodyFromFile(
                "api-responses/playbackinfo/tracks/playlogtest/get_1_bts.json",
            )
        }
        responseDispatcher["https://test.audio.tidal.com/1_bts.m4a".toHttpUrl()] = {
            MockResponse().setBodyFromFile("raw/playlogtest/1_bts.m4a")
        }
        responseDispatcher[
            "https://api.tidal.com/v1/tracks/${mediaProduct2.productId}/playbackinfo?playbackmode=STREAM&assetpresentation=FULL&audioquality=LOW&immersiveaudio=true".toHttpUrl(),
        ] = {
            MockResponse().setBodyFromFile(
                "api-responses/playbackinfo/tracks/playlogtest/get_2_bts.json",
            )
        }
        responseDispatcher["https://test.audio.tidal.com/test_1min.m4a".toHttpUrl()] = {
            MockResponse().setBodyFromFile("raw/playlogtest/test_1min.m4a")
        }
        EventReporterModuleRoot.reflectionComponentFactoryF = {
            PlayLogTestDefaultEventReporterComponentFactory(eventReporterCoroutineScope)
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
        private const val MEDIA_PRODUCT_1_DURATION_SECONDS = 5.055
        private const val MEDIA_PRODUCT_2_DURATION_SECONDS = 60.606667

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            originalEventReporterComponentFactoryF =
                EventReporterModuleRoot.reflectionComponentFactoryF
        }

        @JvmStatic
        @Parameterized.Parameters
        fun parameters(): List<Array<MediaProduct>> {
            val mediaProduct1s = setOf(
                MediaProduct(ProductType.TRACK, "1", "TESTA", "456"),
                MediaProduct(ProductType.TRACK, "1", null, "789"),
                MediaProduct(ProductType.TRACK, "1", "TESTB", null),
                MediaProduct(ProductType.TRACK, "1", null, null),
            )
            val mediaProduct2s = setOf(
                MediaProduct(ProductType.TRACK, "2", "TESTA", "456"),
                MediaProduct(ProductType.TRACK, "2", null, "789"),
                MediaProduct(ProductType.TRACK, "2", "TESTB", null),
                MediaProduct(ProductType.TRACK, "2", null, null),
            )
            return mediaProduct1s.flatMap { mediaProduct1 ->
                mediaProduct2s.map { mediaProduct2 ->
                    arrayOf(mediaProduct1, mediaProduct2)
                }
            }
        }
    }

    @After
    fun afterEach() {
        runBlocking {
            val job = launch { player.playbackEngine.events.first { it is Event.Release } }
            player.release()
            job.join()
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
    fun playSequentially() = runTest(timeout = 3.minutes) {
        val gson = Gson()

        player.playbackEngine.load(mediaProduct1)
        player.playbackEngine.setNext(mediaProduct2)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(2.minutes) {
                player.playbackEngine.events.filter { it is Event.MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    // https://github.com/androidx/media/issues/1252
                    get("startAssetPosition").asDouble.isAssetPositionEqualTo(0.0) &&
                        // https://github.com/androidx/media/issues/1253
                        get("endAssetPosition").asDouble
                            .isAssetPositionEqualTo(MEDIA_PRODUCT_1_DURATION_SECONDS) &&
                        get("actualProductId")?.asString.contentEquals(mediaProduct1.productId) &&
                        get("sourceType")?.asString.contentEquals(mediaProduct1.sourceType) &&
                        get("sourceId")?.asString.contentEquals(mediaProduct1.sourceId) &&
                        get("actions").asJsonArray.isEmpty
                }
            },
            eq(emptyMap()),
        )
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    // https://github.com/androidx/media/issues/1252
                    get("startAssetPosition").asDouble.isAssetPositionEqualTo(0.0) &&
                        // https://github.com/androidx/media/issues/1253
                        get("endAssetPosition").asDouble
                            .isAssetPositionEqualTo(MEDIA_PRODUCT_2_DURATION_SECONDS) &&
                        get("actualProductId")?.asString.contentEquals(mediaProduct2.productId) &&
                        get("sourceType")?.asString.contentEquals(mediaProduct2.sourceType) &&
                        get("sourceId")?.asString.contentEquals(mediaProduct2.sourceId) &&
                        get("actions").asJsonArray.isEmpty
                }
            },
            eq(emptyMap()),
        )
    }

    private fun Double.isAssetPositionEqualTo(targetPosition: Double) =
        (this - targetPosition).absoluteValue < 0.5
}
