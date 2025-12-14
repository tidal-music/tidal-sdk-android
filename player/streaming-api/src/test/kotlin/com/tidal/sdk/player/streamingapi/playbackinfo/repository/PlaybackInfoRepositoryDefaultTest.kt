package com.tidal.sdk.player.streamingapi.playbackinfo.repository

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.player.streamingapi.TrackPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.UCPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.VideoPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.offline.OfflinePlaybackInfoProviderStub
import com.tidal.sdk.player.streamingapi.playbackinfo.api.PlaybackInfoServiceStub
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

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
    fun getTrackPlaybackInfoShouldThrowWhenUncaughtExceptionIsThrown() {
        assertFailure {
                runBlocking {
                    getTrackPlaybackInfo(
                        PlaybackInfoServiceStub.PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION
                    )
                }
            }
            .hasClass(NullPointerException::class)
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val playbackInfo = getTrackPlaybackInfo(PlaybackInfoServiceStub.PLAYBACK_INFO_ID_SUCCESS)

        assertThat(playbackInfo).isEqualTo(TrackPlaybackInfoFactory.DEFAULT)
    }

    private suspend fun getTrackPlaybackInfo(trackId: String) =
        playbackInfoRepository.getTrackPlaybackInfo(
            trackId,
            AudioQuality.LOW,
            PlaybackMode.STREAM,
            true,
            "streamingSessionId",
            null,
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
