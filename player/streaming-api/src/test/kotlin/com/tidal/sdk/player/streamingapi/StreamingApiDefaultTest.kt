package com.tidal.sdk.player.streamingapi

import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.player.MockWebServerExtensions.enqueueResponse
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.streamingapi.di.DaggerStreamingApiComponent
import com.tidal.sdk.player.streamingapi.offline.OfflinePlaybackInfoProviderStub
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import java.net.ConnectException
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock

/**
 * Test that the [StreamingApi] returns correct in various situations, using real
 * dependencies(except api calls).
 */
internal class StreamingApiDefaultTest {

    @get:ExtendWith val server = MockWebServer()

    private lateinit var streamingApi: StreamingApi
    private lateinit var credentialsProviderMock: CredentialsProvider

    @BeforeEach
    fun setUp() {
        val gson = Gson()
        credentialsProviderMock = mock<CredentialsProvider>()

        streamingApi =
            DaggerStreamingApiComponent.factory()
                .create(
                    okHttpClient =
                        OkHttpClient.Builder()
                            .addInterceptor {
                                val request = it.request()
                                val newRequest =
                                    request
                                        .newBuilder()
                                        .url(server.url(request.url.encodedPath))
                                        .build()
                                it.proceed(newRequest)
                            }
                            .build(),
                    apiErrorFactory = ApiError.Factory(gson),
                    streamingApiTimeoutConfig = StreamingApiTimeoutConfig(),
                    gson = gson,
                    offlinePlaybackInfoProvider = OfflinePlaybackInfoProviderStub(),
                    credentialsProvider = credentialsProviderMock,
                    tidalApiCacheDir = null,
                )
                .streamingApi
    }

    @Test
    fun getUCPlaybackInfoShouldReturnCorrectWhenNetworkIssues() {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        val playbackInfo = getUCPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(UCPlaybackInfoFactory.DEFAULT)
    }

    @Test
    fun getUCPlaybackInfoShouldReturnCorrectPlaybackInfo() {
        val playbackInfo = getUCPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(UCPlaybackInfoFactory.DEFAULT)
    }

    private fun getUCPlaybackInfo() = runBlocking {
        streamingApi.getUCPlaybackInfo(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT.toString(),
            "streamingSessionId",
        ) as PlaybackInfo.UC
    }

    @Test
    fun getOfflineTrackPlaybackInfoShouldReturnCorrectPlaybackInfo() {
        val playbackInfo = runBlocking {
            streamingApi.getOfflineTrackPlaybackInfo("123", "streamingSessionId")
        }

        assertThat(playbackInfo).isDataClassEqualTo(TrackPlaybackInfoFactory.OFFLINE_PLAY)
    }

    @Test
    fun getOfflineVideoPlaybackInfoShouldReturnCorrectPlaybackInfo() {
        val playbackInfo = runBlocking {
            streamingApi.getOfflineVideoPlaybackInfo("123", "streamingSessionId")
        }

        assertThat(playbackInfo).isDataClassEqualTo(VideoPlaybackInfoFactory.OFFLINE_PLAY)
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectWhenNetworkError() {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        assertThrows<ConnectException> { getDrmLicense() }
    }

    @Test
    fun getDrmLicenseShouldFailWhen500() {
        val expectedResponseCode = 500
        server.enqueueResponse("errors/500_999.json", expectedResponseCode)

        val drmLicenseResponse = getDrmLicense()

        assertThat(drmLicenseResponse.isSuccessful).isEqualTo(false)
        assertThat(drmLicenseResponse.code()).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectDrmLicense() {
        server.enqueue(
            MockResponse().setResponseCode(200).setBody(ApiConstants.DRM_PAYLOAD_RESPONSE)
        )

        val drmLicenseResponse = getDrmLicense()

        assertThat(drmLicenseResponse.isSuccessful).isEqualTo(true)
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectPlaybackInfoWhenStreamingSessionIdIsEmpty() {
        server.enqueue(
            MockResponse().setResponseCode(200).setBody(ApiConstants.DRM_PAYLOAD_RESPONSE)
        )

        val drmLicenseResponse = getDrmLicense()

        assertThat(drmLicenseResponse.isSuccessful).isEqualTo(true)
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectPlaybackInfoWhenPayloadIsEmpty() {
        server.enqueue(MockResponse().setResponseCode(200).setBody(""))

        val drmLicenseResponse = getDrmLicense()

        assertThat(drmLicenseResponse.isSuccessful).isEqualTo(true)
    }

    private fun getDrmLicense() = runBlocking {
        streamingApi.getDrmLicense(ApiConstants.LICENSE_URL, ApiConstants.DRM_PAYLOAD_REQUEST)
    }
}
