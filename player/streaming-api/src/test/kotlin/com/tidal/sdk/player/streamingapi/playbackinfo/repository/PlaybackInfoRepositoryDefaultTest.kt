package com.tidal.sdk.player.streamingapi.playbackinfo.repository

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.player.streamingapi.TrackPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.UCPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.VideoPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.offline.OfflinePlaybackInfoProviderStub
import com.tidal.sdk.player.streamingapi.playbackinfo.api.PlaybackInfoServiceStub
import com.tidal.sdk.player.streamingapi.playbackinfo.api.TrackManifestsStub
import com.tidal.sdk.player.streamingapi.playbackinfo.api.VideoManifestsStub
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/** Test that the [PlaybackInfoRepository] returns correct [PlaybackInfo] in various situations. */
internal class PlaybackInfoRepositoryDefaultTest {

    private val offlinePlaybackInfoProvider = OfflinePlaybackInfoProviderStub()
    private val playbackInfoService = PlaybackInfoServiceStub()
    private val apiErrorMapper = ApiErrorMapper(ApiError.Factory(Gson()), Gson())
    private val trackManifests = TrackManifestsStub()
    private val videoManifests = VideoManifestsStub()
    private val playbackInfoRepository =
        PlaybackInfoRepositoryDefault(
            offlinePlaybackInfoProvider,
            playbackInfoService,
            { apiErrorMapper },
            trackManifests,
            videoManifests,
        )

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val trackId = ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT
        val streamingSessionId = "streamingSessionId"

        val playbackInfo = getTrackPlaybackInfo(trackId, streamingSessionId)

        val expected =
            PlaybackInfo.Track(
                trackId = trackId.toInt(),
                audioQuality = AudioQuality.LOW,
                assetPresentation = AssetPresentation.FULL,
                audioMode = AudioMode.STEREO,
                manifestHash = ApiConstants.MANIFEST_HASH,
                previewReason = null,
                streamingSessionId = streamingSessionId,
                manifestMimeType = ManifestMimeType.DASH,
                manifest = ApiConstants.MANIFEST,
                licenseUrl = null,
                albumReplayGain = -9.8f,
                albumPeakAmplitude = 0.999923f,
                trackReplayGain = -9.8f,
                trackPeakAmplitude = 0.999923f,
                offlineRevalidateAt = -1,
                offlineValidUntil = -1,
            )
        assertThat(playbackInfo).isEqualTo(expected)
    }

    private suspend fun getTrackPlaybackInfo(trackId: String, streamingSessionId: String) =
        playbackInfoRepository.getTrackPlaybackInfo(
            trackId,
            AudioQuality.LOW,
            PlaybackMode.STREAM,
            true,
            streamingSessionId,
            false,
        )

    @Test
    fun getVideoPlaybackInfoShouldThrowWhenUncaughtExceptionIsThrown() {
        assertFailure {
                runBlocking { getVideoPlaybackInfo(VideoManifestsStub.ID_FOR_UNCAUGHT_EXCEPTION) }
            }
            .hasClass(NullPointerException::class)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val videoId = ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT
        val streamingSessionId = "streamingSessionId"

        val playbackInfo = getVideoPlaybackInfo(videoId, streamingSessionId)

        assertThat(playbackInfo).isEqualTo(VideoPlaybackInfoFactory.DEFAULT)
    }

    @Test
    fun getVideoPlaybackInfoShouldThrowApiErrorWhenHttpError() {
        val apiError =
            assertThrows<ApiError> {
                runBlocking { getVideoPlaybackInfo(VideoManifestsStub.ID_FOR_HTTP_ERROR) }
            }

        assertThat(apiError.status).isEqualTo(403)
        assertThat(apiError.subStatus).isEqualTo(ApiError.SubStatus.GenericPlaybackError)
        assertThat(apiError.userMessage).isEqualTo("Not entitled")
    }

    private suspend fun getVideoPlaybackInfo(
        videoId: String,
        streamingSessionId: String = "streamingSessionId",
    ) =
        playbackInfoRepository.getVideoPlaybackInfo(
            videoId,
            VideoQuality.LOW,
            PlaybackMode.STREAM,
            streamingSessionId,
            null,
        )

    @Test
    fun getUCPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val playbackInfo = getUCPlaybackInfo(ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT)

        assertThat(playbackInfo).isEqualTo(UCPlaybackInfoFactory.DEFAULT)
    }

    private suspend fun getUCPlaybackInfo(itemId: String) =
        playbackInfoRepository.getUC(itemId, UCPlaybackInfoFactory.DEFAULT.streamingSessionId)

    @Test
    fun getOfflineTrackPlaybackInfo() = runBlocking {
        val playbackInfo =
            playbackInfoRepository.getOfflineTrackPlaybackInfo("123", "streamingSessionId")

        assertThat(playbackInfo).isEqualTo(TrackPlaybackInfoFactory.OFFLINE_PLAY)
    }

    @Test
    fun getOfflineVideoPlaybackInfo() = runBlocking {
        val playbackInfo =
            playbackInfoRepository.getOfflineVideoPlaybackInfo("123", "streamingSessionId")

        assertThat(playbackInfo).isEqualTo(VideoPlaybackInfoFactory.OFFLINE_PLAY)
    }
}
