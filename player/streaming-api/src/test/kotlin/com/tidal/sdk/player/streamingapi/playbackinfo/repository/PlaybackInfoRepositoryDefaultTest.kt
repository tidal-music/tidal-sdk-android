package com.tidal.sdk.player.streamingapi.playbackinfo.repository

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
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
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests
import com.tidal.sdk.tidalapi.generated.models.AudioNormalizationData
import com.tidal.sdk.tidalapi.generated.models.Links
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsAttributes
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsResourceObject
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsSingleResourceDataDocument
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

/** Test that the [PlaybackInfoRepository] returns correct [PlaybackInfo] in various situations. */
internal class PlaybackInfoRepositoryDefaultTest {

    private val offlinePlaybackInfoProvider = OfflinePlaybackInfoProviderStub()
    private val playbackInfoService = PlaybackInfoServiceStub()
    private val apiErrorMapperLazy = { mock<ApiErrorMapper>() }
    private val trackManifests = mock<TrackManifests>()
    private val playbackInfoRepository =
        PlaybackInfoRepositoryDefault(
            offlinePlaybackInfoProvider,
            playbackInfoService,
            apiErrorMapperLazy,
            trackManifests,
        )

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val trackId = ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT
        val streamingSessionId = "streamingSessionId"

        // Create mock response data
        val trackManifestsAttributes =
            TrackManifestsAttributes(
                albumAudioNormalizationData =
                    AudioNormalizationData(replayGain = -9.8f, peakAmplitude = 0.999923f),
                trackAudioNormalizationData =
                    AudioNormalizationData(replayGain = -9.8f, peakAmplitude = 0.999923f),
                formats = listOf(TrackManifestsAttributes.Formats.HEAACV1),
                hash = ApiConstants.MANIFEST_HASH,
                trackPresentation = TrackManifestsAttributes.TrackPresentation.FULL,
                uri = "data:application/dash+xml;base64,${ApiConstants.MANIFEST}",
                drmData = null,
            )
        val trackManifestsResource =
            TrackManifestsResourceObject(
                id = trackId,
                type = "trackManifests",
                attributes = trackManifestsAttributes,
            )
        val responseDocument =
            TrackManifestsSingleResourceDataDocument(
                data = trackManifestsResource,
                links = Links(self = ""),
            )

        whenever(trackManifests.trackManifestsIdGet(any(), any(), any(), any(), any(), any()))
            .thenReturn(Response.success(responseDocument))

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
                runBlocking {
                    getVideoPlaybackInfo(
                        PlaybackInfoServiceStub.PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION
                    )
                }
            }
            .hasClass(NullPointerException::class)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val playbackInfo = getVideoPlaybackInfo(PlaybackInfoServiceStub.PLAYBACK_INFO_ID_SUCCESS)

        assertThat(playbackInfo).isEqualTo(VideoPlaybackInfoFactory.DEFAULT)
    }

    private suspend fun getVideoPlaybackInfo(videoId: String) =
        playbackInfoRepository.getVideoPlaybackInfo(
            videoId,
            VideoQuality.LOW,
            PlaybackMode.STREAM,
            "streamingSessionId",
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
