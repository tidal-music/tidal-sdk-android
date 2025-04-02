package com.tidal.sdk.player.streamingapi

import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.tidal.sdk.player.MockWebServerExtensions.enqueueResponse
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.di.DaggerStreamingApiComponent
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicense
import com.tidal.sdk.player.streamingapi.drm.model.DrmLicenseRequest
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

/**
 * Test that the [StreamingApi] returns correct in various situations, using real
 * dependencies(except api calls).
 */
internal class StreamingApiDefaultTest {

    @get:ExtendWith
    val server = MockWebServer()

    private lateinit var streamingApi: StreamingApi

    @BeforeEach
    fun setUp() {
        val gson = Gson()
        streamingApi = DaggerStreamingApiComponent.factory()
            .create(
                okHttpClient = OkHttpClient.Builder()
                    .addInterceptor {
                        val request = it.request()
                        val newRequest = request.newBuilder()
                            .url(server.url(request.url.encodedPath))
                            .build()
                        it.proceed(newRequest)
                    }
                    .build(),
                apiErrorFactory = ApiError.Factory(gson),
                streamingApiTimeoutConfig = StreamingApiTimeoutConfig(),
                gson = gson,
                offlinePlaybackInfoProvider = OfflinePlaybackInfoProviderStub(),
            ).streamingApi
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectWhenNetworkError() {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        assertThrows<ConnectException> { getTrackPlaybackInfo() }
    }

    @Test
    fun getTrackPlaybackInfoShouldFailWhen4xx() {
        val expectedResponseCode = 401
        server.enqueueResponse("errors/401_4005.json", expectedResponseCode)

        val actual = assertThrows<ApiError> { getTrackPlaybackInfo() }

        assertThat(actual.status).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getTrackPlaybackInfoShouldFailWhen500() {
        val expectedResponseCode = 500
        server.enqueueResponse("errors/500_999.json", expectedResponseCode)

        val actual = assertThrows<ApiError> { getTrackPlaybackInfo() }

        assertThat(actual.status).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectPlaybackInfo() {
        server.enqueueResponse("${ApiConstants.TRACKS_PATH}.json")

        val playbackInfo = getTrackPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(TrackPlaybackInfoFactory.DEFAULT)
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectPlaybackInfoWhenStreamingSessionIdIsEmpty() {
        server.enqueueResponse("${ApiConstants.TRACKS_PATH}_empty_streaming_session_id.json")

        val playbackInfo = getTrackPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(
            TrackPlaybackInfoFactory.EMPTY_STREAMING_SESSION_ID,
        )
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectPlaybackInfoWhenReplacementTrackId() {
        server.enqueueResponse("${ApiConstants.TRACKS_PATH}_replacement_track_id.json")

        val playbackInfo = getTrackPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(TrackPlaybackInfoFactory.REPLACEMENT_TRACK_ID)
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectPlaybackInfoWhenReplacementAudioQuality() {
        server.enqueueResponse("${ApiConstants.TRACKS_PATH}_replacement_audio_quality.json")

        val playbackInfo = getTrackPlaybackInfo()

        assertThat(playbackInfo)
            .isDataClassEqualTo(TrackPlaybackInfoFactory.REPLACEMENT_AUDIO_QUALITY)
    }

    @Test
    fun getTrackPlaybackInfoShouldFailWhenInvalidMimeType() {
        server.enqueueResponse("${ApiConstants.TRACKS_PATH}_unknown_mime_type.json")

        assertThrows<JsonParseException> { getTrackPlaybackInfo() }
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectPlaybackInfoWhenProtectedContent() {
        server.enqueueResponse("${ApiConstants.TRACKS_PATH}_protected.json")

        val playbackInfo = getTrackPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(TrackPlaybackInfoFactory.PROTECTED)
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectPlaybackInfoWhenOffline() {
        server.enqueueResponse("${ApiConstants.TRACKS_PATH}_offline.json")

        val playbackInfo = getTrackPlaybackInfo()

        assertThat(playbackInfo).isDataClassEqualTo(TrackPlaybackInfoFactory.OFFLINE)
    }

    private fun getTrackPlaybackInfo() = runBlocking {
        streamingApi.getTrackPlaybackInfo(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT,
            AudioQuality.LOW,
            PlaybackMode.STREAM,
            true,
            "streamingSessionId",
            null,
        )
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

        assertThat(playbackInfo).isDataClassEqualTo(
            VideoPlaybackInfoFactory.EMPTY_STREAMING_SESSION_ID,
        )
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

        assertThat(playbackInfo).isDataClassEqualTo(
            VideoPlaybackInfoFactory.REPLACEMENT_VIDEO_QUALITY,
        )
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
    fun getBroadcastPlaybackInfoShouldReturnCorrectWhenNetworkError() {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        assertThrows<ConnectException> { getBroadcastPlaybackInfo() }
    }

    @Test
    fun getBroadcastPlaybackInfoShouldFailWhen4xx() {
        val expectedResponseCode = 401
        server.enqueueResponse("errors/401_4005.json", expectedResponseCode)

        val actual = assertThrows<ApiError> { getBroadcastPlaybackInfo() }

        assertThat(actual.status).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getBroadcastPlaybackInfoShouldFailWhen500() {
        val expectedResponseCode = 500
        server.enqueueResponse("errors/500_999.json", expectedResponseCode)

        val actual = assertThrows<ApiError> { getBroadcastPlaybackInfo() }

        assertThat(actual.status).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getBroadcastPlaybackInfoShouldReturnCorrectPlaybackInfo() {
        server.enqueueResponse("${ApiConstants.BROADCASTS_PATH}.json")

        val playbackInfo = getBroadcastPlaybackInfo()

        assertThat(playbackInfo).isEqualToIgnoringGivenProperties(
            BroadcastPlaybackInfoFactory.DEFAULT,
            PlaybackInfo.Broadcast::streamingSessionId,
        )
    }

    @Test
    fun getBroadcastPlaybackInfoShouldReturnCorrectPlaybackInfoWhenReplacementId() {
        server.enqueueResponse("${ApiConstants.BROADCASTS_PATH}_replacement_id.json")

        val playbackInfo = getBroadcastPlaybackInfo()

        assertThat(playbackInfo).isEqualToIgnoringGivenProperties(
            BroadcastPlaybackInfoFactory.REPLACEMENT_ID,
            PlaybackInfo.Broadcast::streamingSessionId,
        )
    }

    @Test
    fun getBroadcastPlaybackInfoShouldReturnCorrectPlaybackInfoWhenReplacementAudioQuality() {
        server.enqueueResponse("${ApiConstants.BROADCASTS_PATH}_replacement_audio_quality.json")

        val playbackInfo = getBroadcastPlaybackInfo()

        assertThat(playbackInfo)
            .isEqualToIgnoringGivenProperties(
                BroadcastPlaybackInfoFactory.REPLACEMENT_AUDIO_QUALITY,
                PlaybackInfo.Broadcast::streamingSessionId,
            )
    }

    @Test
    fun getBroadcastPlaybackInfoShouldFailWhenInvalidMimeType() {
        server.enqueueResponse("${ApiConstants.BROADCASTS_PATH}_unknown_mime_type.json")

        assertThrows<JsonParseException> { getBroadcastPlaybackInfo() }
    }

    private fun getBroadcastPlaybackInfo() = runBlocking {
        streamingApi.getBroadcastPlaybackInfo(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT.toString(),
            "streamingSessionId",
            AudioQuality.LOW,
        ) as PlaybackInfo.Broadcast
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

        val actual = assertThrows<ApiError> { getDrmLicense() }

        assertThat(actual.status).isEqualTo(expectedResponseCode)
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectDrmLicense() {
        server.enqueueResponse("${ApiConstants.LICENSE_PATH}.json")

        val drmLicense = getDrmLicense()

        assertThat(drmLicense).isDataClassEqualTo(
            DrmLicense(ApiConstants.STREAMING_SESSION_ID, ApiConstants.DRM_PAYLOAD_RESPONSE),
        )
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectPlaybackInfoWhenStreamingSessionIdIsEmpty() {
        server.enqueueResponse("${ApiConstants.LICENSE_PATH}_empty_streaming_session_id.json")

        val drmLicense = getDrmLicense()

        assertThat(drmLicense).isDataClassEqualTo(DrmLicense("", ApiConstants.DRM_PAYLOAD_RESPONSE))
    }

    @Test
    fun getDrmLicenseShouldReturnCorrectPlaybackInfoWhenPayloadIsEmpty() {
        server.enqueueResponse("${ApiConstants.LICENSE_PATH}_empty_payload.json")

        val drmLicense = getDrmLicense()

        assertThat(drmLicense).isDataClassEqualTo(DrmLicense(ApiConstants.STREAMING_SESSION_ID, ""))
    }

    private fun getDrmLicense() = runBlocking {
        streamingApi.getDrmLicense(
            DrmLicenseRequest(
                ApiConstants.STREAMING_SESSION_ID,
                ApiConstants.LICENSE_SECURITY_TOKEN,
                ApiConstants.DRM_PAYLOAD_REQUEST,
            ),
        )
    }
}
