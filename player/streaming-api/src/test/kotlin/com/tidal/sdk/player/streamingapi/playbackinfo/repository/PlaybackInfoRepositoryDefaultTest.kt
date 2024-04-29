package com.tidal.sdk.player.streamingapi.playbackinfo.repository

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.BroadcastPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.TrackPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.VideoPlaybackInfoFactory
import com.tidal.sdk.player.streamingapi.offline.OfflinePlaybackInfoProviderStub
import com.tidal.sdk.player.streamingapi.playbackinfo.api.PlaybackInfoServiceStub
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackMode
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

/**
 * Test that the [PlaybackInfoRepository] returns correct [PlaybackInfo] in various
 * situations.
 */
internal class PlaybackInfoRepositoryDefaultTest {

    private val offlinePlaybackInfoProvider = OfflinePlaybackInfoProviderStub()
    private val playbackInfoService = PlaybackInfoServiceStub()
    private val apiErrorMapperLazy = { mock<ApiErrorMapper>() }
    private val playbackInfoRepository = PlaybackInfoRepositoryDefault(
        offlinePlaybackInfoProvider,
        playbackInfoService,
        apiErrorMapperLazy,
    )

    @Test
    fun getTrackPlaybackInfoShouldThrowWhenUncaughtExceptionIsThrown() {
        assertFailure {
            runBlocking {
                getTrackPlaybackInfo(
                    PlaybackInfoServiceStub.PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION,
                )
            }
        }.hasClass(NullPointerException::class)
    }

    @Test
    fun getTrackPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val playbackInfo = getTrackPlaybackInfo(PlaybackInfoServiceStub.PLAYBACK_INFO_ID_SUCCESS)

        assertThat(playbackInfo).isEqualTo(TrackPlaybackInfoFactory.DEFAULT)
    }

    private suspend fun getTrackPlaybackInfo(trackId: Int) =
        playbackInfoRepository.getTrackPlaybackInfo(
            trackId,
            AudioQuality.LOW,
            PlaybackMode.STREAM,
            "streamingSessionId",
            null,
        )

    @Test
    fun getVideoPlaybackInfoShouldThrowWhenUncaughtExceptionIsThrown() {
        assertFailure {
            runBlocking {
                getVideoPlaybackInfo(
                    PlaybackInfoServiceStub.PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION,
                )
            }
        }.hasClass(NullPointerException::class)
    }

    @Test
    fun getVideoPlaybackInfoShouldReturnCorrectWhenPlaybackInfoIsReturned() = runBlocking {
        val playbackInfo = getVideoPlaybackInfo(PlaybackInfoServiceStub.PLAYBACK_INFO_ID_SUCCESS)

        assertThat(playbackInfo).isEqualTo(VideoPlaybackInfoFactory.DEFAULT)
    }

    private suspend fun getVideoPlaybackInfo(videoId: Int) =
        playbackInfoRepository.getVideoPlaybackInfo(
            videoId,
            VideoQuality.LOW,
            PlaybackMode.STREAM,
            "streamingSessionId",
            null,
        )

    @Test
    fun getBroadcastPlaybackInfoShouldThrowWhenUncaughtExceptionIsThrown() {
        assertFailure {
            runBlocking {
                getBroadcastPlaybackInfo(
                    PlaybackInfoServiceStub.PLAYBACK_INFO_ID_FOR_UNCAUGHT_EXCEPTION.toString(),
                )
            }
        }.hasClass(NullPointerException::class)
    }

    @Test
    fun getBroadcastPlaybackInfoShouldReturnCorrectAndFillMissingStreamingSessionIdWhenPlaybackInfoIsReturned() =
        runBlocking {
            val playbackInfo = getBroadcastPlaybackInfo(
                PlaybackInfoServiceStub.PLAYBACK_INFO_ID_SUCCESS.toString(),
            )

            assertThat(playbackInfo).isEqualTo(BroadcastPlaybackInfoFactory.DEFAULT)
        }

    private suspend fun getBroadcastPlaybackInfo(djSessionId: String) =
        playbackInfoRepository
            .getBroadcastPlaybackInfo(
                djSessionId,
                BroadcastPlaybackInfoFactory.DEFAULT.streamingSessionId,
                AudioQuality.LOW,
            )

    @Test
    fun getOfflineTrackPlaybackInfo() = runBlocking {
        val playbackInfo =
            playbackInfoRepository.getOfflineTrackPlaybackInfo(123, "streamingSessionId")

        assertThat(playbackInfo).isEqualTo(TrackPlaybackInfoFactory.OFFLINE_PLAY)
    }

    @Test
    fun getOfflineVideoPlaybackInfo() = runBlocking {
        val playbackInfo =
            playbackInfoRepository.getOfflineVideoPlaybackInfo(123, "streamingSessionId")

        assertThat(playbackInfo).isEqualTo(VideoPlaybackInfoFactory.OFFLINE_PLAY)
    }
}
