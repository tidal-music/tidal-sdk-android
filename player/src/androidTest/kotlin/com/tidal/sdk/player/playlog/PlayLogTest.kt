package com.tidal.sdk.player.playlog

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
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
import com.tidal.sdk.player.playbackengine.model.Event.MediaProductEnded
import com.tidal.sdk.player.repeatableflakytest.RepeatableFlakyTest
import com.tidal.sdk.player.repeatableflakytest.RepeatableFlakyTestRule
import com.tidal.sdk.player.setBodyFromFile
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
import org.mockito.Mockito.mock
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify

class PlayLogTest {

    @get:Rule
    val server = MockWebServer()

    @get:Rule
    val repeatableFlakyTestRule = RepeatableFlakyTestRule()

    private val eventReporterCoroutineScope =
        TestScope(StandardTestDispatcher(TestCoroutineScheduler()))
    private val responseDispatcher = PlayLogTestMockWebServerDispatcher(server)
    private val eventSender = mock<EventSender>()
    private lateinit var player: Player

    @Before
    fun setUp() {
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
                    userId = null,
                    expires = null,
                    token = null,
                )

                override val bus: Flow<TidalMessage> = emptyFlow()

                override suspend fun getCredentials(apiErrorSubStatus: String?) =
                    AuthResult.Success(CREDENTIALS)

                override fun isUserLoggedIn() = true
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
    fun afterEach() = runBlocking {
        val job = launch { player.playbackEngine.events.first { it is Event.Release } }
        player.release()
        job.join()
    }

    @RepeatableFlakyTest
    @Test
    fun loadAndPlayUntilEnd() = runTest {
        val mediaProduct = MediaProduct(ProductType.TRACK, "1", "TEST", "456")
        responseDispatcher[
            "https://api.tidal.com/v1/tracks/1/playbackinfo?playbackmode=STREAM&assetpresentation=FULL&audioquality=LOW".toHttpUrl(),
        ] = {
            MockResponse().setBodyFromFile(
                "api-responses/playbackinfo/tracks/playlogtest/get_1_bts.json"
            )
        }
        responseDispatcher["https://test.audio.tidal.com/1_bts.m4a".toHttpUrl()] = {
            MockResponse().setBodyFromFile("raw/playlogtest/1_bts.m4a")
        }

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(8_000) {
                player.playbackEngine.events.filter { it is MediaProductEnded }.first()
            }
        }

        eventReporterCoroutineScope.advanceUntilIdle()
        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(Gson().fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
                    assertThat(get("startAssetPosition").asDouble).isEqualTo(0.0)
                    assertThat(get("endAssetPosition").asDouble).isBetween(5.0, 5.1)
                    assertThat(get("actualProductId").asString).isEqualTo("1")
                    assertThat(get("sourceType").asString).isEqualTo(mediaProduct.sourceType)
                    assertThat(get("sourceId").asString).isEqualTo(mediaProduct.sourceId)
                    assertThat(get("actions").asJsonArray.size()).isEqualTo(0)
                }
                true
            },
            eq(emptyMap()),
        )
    }
}