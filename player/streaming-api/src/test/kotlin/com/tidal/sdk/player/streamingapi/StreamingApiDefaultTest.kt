package com.tidal.sdk.player.streamingapi

import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.player.MockWebServerExtensions.enqueueResponse
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.di.DaggerStreamingApiComponent
import com.tidal.sdk.player.streamingapi.offline.OfflinePlaybackInfoProviderStub
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
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
    fun getVideoPlaybackInfoShouldReturnCorrectWhenNetworkError() {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        assertThrows<ConnectException> { getVideoPlaybackInfo() }
    }

    @Test
    fun getVideoPlaybackInfoShouldFailWhen4xx() {
        val expectedResponseCode = 401
        server.enqueueResponse("errors/401_4005.json", expectedResponseCode)

        val actual = assertThrows<ApiError> { getVideoPlaybackInfo() }

        assertThat(actual.status).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getVideoPlaybackInfoShouldFailWhen500() {
        val expectedResponseCode = 500
        server.enqueueResponse("errors/500_999.json", expectedResponseCode)

        val actual = assertThrows<ApiError> { getVideoPlaybackInfo() }

        assertThat(actual.status).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectPlaybackInfo() {
        server.enqueueResponse("${ApiConstants.VIDEOS_PATH}.json")

        val playbackInfo = getVideoPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(VideoPlaybackInfoFactory.DEFAULT)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectPlaybackInfoWhenStreamingSessionIdIsEmpty() {
        server.enqueueResponse("${ApiConstants.VIDEOS_PATH}_empty_streaming_session_id.json")

        val playbackInfo = getVideoPlaybackInfo()

        assertThat(playbackInfo)
            .isDataClassEqualTo(VideoPlaybackInfoFactory.EMPTY_STREAMING_SESSION_ID)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectPlaybackInfoWhenReplacementVideoId() {
        server.enqueueResponse("${ApiConstants.VIDEOS_PATH}_replacement_video_id.json")

        val playbackInfo = getVideoPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(VideoPlaybackInfoFactory.REPLACEMENT_VIDEO_ID)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectPlaybackInfoWhenReplacementVideoQuality() {
        server.enqueueResponse("${ApiConstants.VIDEOS_PATH}_replacement_video_quality.json")

        val playbackInfo = getVideoPlaybackInfo()

        assertThat(playbackInfo)
            .isDataClassEqualTo(VideoPlaybackInfoFactory.REPLACEMENT_VIDEO_QUALITY)
    }

    @Test
    fun getVideoPlaybackInfoShouldFailWhenInvalidMimeType() {
        server.enqueueResponse("${ApiConstants.VIDEOS_PATH}_unknown_mime_type.json")

        assertThrows<JsonParseException> { getVideoPlaybackInfo() }
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectPlaybackInfoWhenProtectedContent() {
        server.enqueueResponse("${ApiConstants.VIDEOS_PATH}_protected.json")

        val playbackInfo = getVideoPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(VideoPlaybackInfoFactory.PROTECTED)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectPlaybackInfoWhenOffline() {
        server.enqueueResponse("${ApiConstants.VIDEOS_PATH}_offline.json")

        val playbackInfo = getVideoPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(VideoPlaybackInfoFactory.OFFLINE)
    }

    private fun getVideoPlaybackInfo() = runBlocking {
        streamingApi.getVideoPlaybackInfo(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT,
            VideoQuality.LOW,
            PlaybackMode.STREAM,
            "streamingSessionId",
            null,
        )
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
