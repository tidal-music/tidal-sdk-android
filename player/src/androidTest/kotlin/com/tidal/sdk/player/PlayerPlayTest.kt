package com.tidal.sdk.player

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
import com.tidal.sdk.auth.model.Scopes
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.player.MockWebServerExtensions.mockResponse
import com.tidal.sdk.player.MockWebServerExtensions.mockResponseAsBuffer
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.model.Event.MediaProductEnded
import com.tidal.sdk.player.repeatableflakytest.RepeatableFlakyTest
import com.tidal.sdk.player.repeatableflakytest.RepeatableFlakyTestRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class PlayerPlayTest {

    @get:Rule
    val server = MockWebServer()

    @get:Rule
    val repeatableFlakyTestRule = RepeatableFlakyTestRule()

    private val gson = Gson()
    private val eventSender = mock<EventSender>()
    private lateinit var player: Player

    private val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return if (request.requestUrl?.encodedPath?.startsWith("/v1/tracks") == true) {
                server.mockResponse("playbackinfo/tracks/get_1_bts.json")
            } else if (request.requestUrl?.encodedPath?.endsWith("test.m4a") == true) {
                server.mockResponseAsBuffer("test_5sec.m4a")
            } else {
                MockResponse()
            }
        }
    }

    @Before
    fun setUp() {
        player = Player(
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
                as Application,
            object : CredentialsProvider {
                private val CREDENTIALS = Credentials(
                    clientId = "a client id",
                    requestedScopes = Scopes(emptySet()),
                    clientUniqueKey = null,
                    grantedScopes = Scopes(emptySet()),
                    userId = null,
                    expires = null,
                    token = null,
                )

                override val bus: Flow<TidalMessage> = emptyFlow()

                override suspend fun getCredentials(apiErrorSubStatus: String?) =
                    AuthResult.Success(CREDENTIALS)

                override fun getLatestCredentials() = CREDENTIALS
            },
            eventSender,
            version = "PLAYLOG_TESTS_ANDROID",
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request()
                    val newRequest = request.newBuilder()
                        .url(server.url(request.url.encodedPath))
                        .build()
                    it.proceed(newRequest)
                }.build(),
        )
        server.dispatcher = dispatcher
    }

    @After
    fun afterEach() = runBlocking {
        val job = launch { player.playbackEngine.events.first { it is Event.Release } }
        player.release()
        job.join()
    }

    @RepeatableFlakyTest
    @Ignore("Asset positions are not accurate and we do not yet know why")
    @Test
    fun loadAndPlaySendsCorrectPlaylogEvent() {
        val mediaProduct = MediaProduct(ProductType.TRACK, "1", "TEST", "456")

        player.playbackEngine.load(mediaProduct)
        player.playbackEngine.play()
        runBlocking {
            withTimeout(8000) {
                player.playbackEngine.events.filter { it is MediaProductEnded }.first()
            }
        }

        verify(eventSender).sendEvent(
            eq("playback_session"),
            eq(ConsentCategory.NECESSARY),
            argThat {
                with(gson.fromJson(this, JsonObject::class.java)["payload"].asJsonObject) {
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
