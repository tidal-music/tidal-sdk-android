package com.tidal.sdk.player.streamingapi.playbackinfo.api

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isDataClassEqualTo
import assertk.assertions.isInstanceOf
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.tidal.sdk.player.MockWebServerExtensions.enqueueResponse
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.player.streamingapi.TrackPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.VideoPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Test that the [PlaybackInfoService] returns correct [PlaybackInfo] in various situations, or that
 * it fails with an exception.
 */
internal class PlaybackInfoServiceTest {

    @get:ExtendWith val server = MockWebServer()

    private lateinit var playbackInfoService: PlaybackInfoService

    private val gson =
        GsonBuilder()
            .registerTypeAdapter(
                ManifestMimeType::class.java,
                ManifestMimeType.Converter.Deserializer(),
            )
            .create()

    private val converterFactory = GsonConverterFactory.create(gson)

    @BeforeEach
    fun setUp() {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(converterFactory)
                .build()

        playbackInfoService = retrofit.create(PlaybackInfoService::class.java)
    }

    @Test
    fun getTrackPlaybackInfoShouldFailWhenNetworkError() {
        server.enqueue(MockResponse().throttleBody(1024, 1, TimeUnit.SECONDS))

        assertFailure { getTrackPlaybackInfo() }.isInstanceOf(IOException::class.java)
    }

    @Test
    fun getTrackPlaybackInfoShouldFailWhen404() {
        server.enqueue(MockResponse().setResponseCode(404))

        assertFailure { getTrackPlaybackInfo() }.isInstanceOf(HttpException::class.java)
    }

    @Test
    fun getTrackPlaybackInfoShouldFailWhen500() {
        server.enqueue(MockResponse().setResponseCode(500))

        assertFailure { getTrackPlaybackInfo() }.isInstanceOf(HttpException::class.java)
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

        assertThat(playbackInfo)
            .isDataClassEqualTo(TrackPlaybackInfoFactory.EMPTY_STREAMING_SESSION_ID)
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
        playbackInfoService.getTrackPlaybackInfo(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT,
            PlaybackMode.STREAM,
            AssetPresentation.FULL,
            AudioQuality.LOW,
            true,
            ApiConstants.STREAMING_SESSION_ID,
            null,
        )
    }

    @Test
    fun getVideoPlaybackInfoShouldFailWhenNetworkError() {
        server.enqueue(MockResponse().throttleBody(1024, 1, TimeUnit.SECONDS))

        assertFailure { getVideoPlaybackInfo() }.isInstanceOf(IOException::class.java)
    }

    @Test
    fun getVideoPlaybackInfoShouldFailWhen404() {
        server.enqueue(MockResponse().setResponseCode(404))

        assertFailure { getVideoPlaybackInfo() }.isInstanceOf(HttpException::class.java)
    }

    @Test
    fun getVideoPlaybackInfoShouldFailWhen500() {
        server.enqueue(MockResponse().setResponseCode(500))

        assertFailure { getVideoPlaybackInfo() }.isInstanceOf(HttpException::class.java)
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
        playbackInfoService.getVideoPlaybackInfo(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT,
            PlaybackMode.STREAM,
            AssetPresentation.FULL,
            VideoQuality.LOW,
            ApiConstants.STREAMING_SESSION_ID,
            null,
        )
    }
}
