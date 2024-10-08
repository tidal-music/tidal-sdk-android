package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.events.model.PlaybackStatistics.Payload.Adaptation
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import java.util.UUID
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class UndeterminedPlaybackSessionResolverTest {

    private val versionedCdmCalculator = mock<VersionedCdm.Calculator>()
    private val undeterminedPlaybackSessionResolver =
        UndeterminedPlaybackSessionResolver(versionedCdmCalculator)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(versionedCdmCalculator)

    @Test
    fun preparePlaybackStatisticsTrack() {
        val id = mock<UUID>()
        val startTimestampMs = mock<PlaybackStatistics.IdealStartTimestampMs.Known>()
        val adaptations = mock<List<Adaptation>>()
        val undetermined = mock<PlaybackStatistics.Undetermined> {
            on { it.streamingSessionId } doReturn id
            on { it.idealStartTimestampMs } doReturn startTimestampMs
            on { it.adaptations } doReturn adaptations
        }
        val productId = "3"
        val assetPresentation = mock<AssetPresentation>()
        val audioQuality = mock<AudioQuality>()
        val audioMode = mock<AudioMode>()
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { it.trackId } doReturn productId.toInt()
            on { it.assetPresentation } doReturn assetPresentation
            on { it.audioQuality } doReturn audioQuality
            on { it.audioMode } doReturn audioMode
        }
        val versionedCdm = mock<VersionedCdm>()
        whenever(versionedCdmCalculator(playbackInfo)) doReturn versionedCdm

        val actual = undeterminedPlaybackSessionResolver(undetermined, playbackInfo, null)

        verify(undetermined).streamingSessionId
        verify(undetermined).idealStartTimestampMs
        verify(undetermined).adaptations
        verify(playbackInfo).trackId
        verify(playbackInfo).assetPresentation
        verify(versionedCdmCalculator)(playbackInfo)
        verify(playbackInfo).audioQuality
        verify(playbackInfo).audioMode
        assertThat(actual).isEqualTo(
            PlaybackStatistics.Success.Prepared.Audio(
                id,
                productId,
                startTimestampMs,
                assetPresentation,
                versionedCdm,
                audioQuality,
                adaptations,
                actualAudioMode = audioMode,
                MediaStorage.INTERNET,
                null,
            ),
        )
        verifyNoMoreInteractions(
            id,
            undetermined,
            assetPresentation,
            audioQuality,
            audioMode,
            playbackInfo,
            versionedCdm,
            startTimestampMs,
        )
    }

    @Test
    fun preparePlaybackStatisticsVideo() {
        val id = mock<UUID>()
        val startTimestampMs = mock<PlaybackStatistics.IdealStartTimestampMs.Known>()
        val adaptations = mock<List<Adaptation>>()
        val undetermined = mock<PlaybackStatistics.Undetermined> {
            on { it.streamingSessionId } doReturn id
            on { it.idealStartTimestampMs } doReturn startTimestampMs
            on { it.adaptations } doReturn adaptations
        }
        val productId = "${Int.MAX_VALUE}"
        val assetPresentation = mock<AssetPresentation>()
        val videoQuality = mock<VideoQuality>()
        val streamType = mock<StreamType>()
        val playbackInfo = mock<PlaybackInfo.Video> {
            on { it.videoId } doReturn productId.toInt()
            on { it.assetPresentation } doReturn assetPresentation
            on { it.videoQuality } doReturn videoQuality
            on { it.streamType } doReturn streamType
        }
        val versionedCdm = mock<VersionedCdm>()
        whenever(versionedCdmCalculator(playbackInfo)) doReturn versionedCdm

        val actual = undeterminedPlaybackSessionResolver(undetermined, playbackInfo, null)

        verify(undetermined).streamingSessionId
        verify(undetermined).idealStartTimestampMs
        verify(undetermined).adaptations
        verify(playbackInfo).videoId
        verify(playbackInfo).assetPresentation
        verify(versionedCdmCalculator)(playbackInfo)
        verify(playbackInfo).videoQuality
        verify(playbackInfo).streamType
        assertThat(actual).isEqualTo(
            PlaybackStatistics.Success.Prepared.Video(
                id,
                productId,
                startTimestampMs,
                assetPresentation,
                versionedCdm,
                videoQuality,
                adaptations,
                actualStreamType = streamType,
                MediaStorage.INTERNET,
                null,
            ),
        )
        verifyNoMoreInteractions(
            id,
            undetermined,
            assetPresentation,
            videoQuality,
            streamType,
            playbackInfo,
            versionedCdm,
            startTimestampMs,
        )
    }

    @Test
    fun preparePlaybackStatisticsBroadcast() {
        val id = mock<UUID>()
        val startTimestampMs = mock<PlaybackStatistics.IdealStartTimestampMs.Known>()
        val adaptations = mock<List<Adaptation>>()
        val undetermined = mock<PlaybackStatistics.Undetermined> {
            on { it.streamingSessionId } doReturn id
            on { it.idealStartTimestampMs } doReturn startTimestampMs
            on { it.adaptations } doReturn adaptations
        }
        val productId = "3"
        val audioQuality = mock<AudioQuality>()
        val playbackInfo = mock<PlaybackInfo.Broadcast> {
            on { it.id } doReturn productId
            on { it.audioQuality } doReturn audioQuality
        }
        val versionedCdm = mock<VersionedCdm>()
        whenever(versionedCdmCalculator(playbackInfo)) doReturn versionedCdm

        val actual = undeterminedPlaybackSessionResolver(undetermined, playbackInfo, null)

        verify(undetermined).streamingSessionId
        verify(undetermined).idealStartTimestampMs
        verify(undetermined).adaptations
        verify(playbackInfo).id
        verify(versionedCdmCalculator)(playbackInfo)
        verify(playbackInfo).audioQuality
        assertThat(actual).isEqualTo(
            PlaybackStatistics.Success.Prepared.Broadcast(
                id,
                productId,
                startTimestampMs,
                versionedCdm,
                audioQuality,
                adaptations,
                MediaStorage.INTERNET,
                null,
            ),
        )
        verifyNoMoreInteractions(
            id,
            undetermined,
            audioQuality,
            playbackInfo,
            versionedCdm,
            startTimestampMs,
        )
    }

    @Test
    fun preparePlaybackStatisticsUC() {
        val id = mock<UUID>()
        val startTimestampMs = mock<PlaybackStatistics.IdealStartTimestampMs.Known>()
        val adaptations = mock<List<Adaptation>>()
        val undetermined = mock<PlaybackStatistics.Undetermined> {
            on { it.streamingSessionId } doReturn id
            on { it.idealStartTimestampMs } doReturn startTimestampMs
            on { it.adaptations } doReturn adaptations
        }
        val productId = "3"
        val playbackInfo = mock<PlaybackInfo.UC> {
            on { it.id } doReturn productId
        }
        val versionedCdm = mock<VersionedCdm>()
        whenever(versionedCdmCalculator(playbackInfo)) doReturn versionedCdm

        val actual = undeterminedPlaybackSessionResolver(undetermined, playbackInfo, null)

        verify(undetermined).streamingSessionId
        verify(undetermined).idealStartTimestampMs
        verify(undetermined).adaptations
        verify(playbackInfo).id
        verify(versionedCdmCalculator)(playbackInfo)
        assertThat(actual).isEqualTo(
            PlaybackStatistics.Success.Prepared.UC(
                id,
                productId,
                startTimestampMs,
                versionedCdm,
                adaptations,
                MediaStorage.INTERNET,
                null,
            ),
        )
        verifyNoMoreInteractions(
            id,
            undetermined,
            playbackInfo,
            versionedCdm,
            startTimestampMs,
        )
    }

    @Suppress("LongMethod")
    @Test
    fun preparePlaybackStatisticsOfflineTrack() {
        val id = mock<UUID>()
        val startTimestampMs = mock<PlaybackStatistics.IdealStartTimestampMs.Known>()
        val adaptations = mock<List<Adaptation>>()
        val undetermined = mock<PlaybackStatistics.Undetermined> {
            on { it.streamingSessionId } doReturn id
            on { it.idealStartTimestampMs } doReturn startTimestampMs
            on { it.adaptations } doReturn adaptations
        }
        val productId = "3"
        val assetPresentation = mock<AssetPresentation>()
        val audioQuality = mock<AudioQuality>()
        val audioMode = mock<AudioMode>()
        val playbackInfoTrack = mock<PlaybackInfo.Track> {
            on { it.trackId } doReturn productId.toInt()
            on { it.assetPresentation } doReturn assetPresentation
            on { it.audioQuality } doReturn audioQuality
            on { it.audioMode } doReturn audioMode
        }
        val playbackInfoOfflineTrack = mock<PlaybackInfo.Offline.Track> {
            on { it.track } doReturn playbackInfoTrack
        }
        val versionedCdm = mock<VersionedCdm>()
        whenever(versionedCdmCalculator(playbackInfoOfflineTrack)) doReturn versionedCdm

        val actual =
            undeterminedPlaybackSessionResolver(undetermined, playbackInfoOfflineTrack, null)

        verify(undetermined).streamingSessionId
        verify(undetermined).idealStartTimestampMs
        verify(undetermined).adaptations
        verify(playbackInfoTrack).trackId
        verify(playbackInfoTrack).assetPresentation
        verify(versionedCdmCalculator)(playbackInfoOfflineTrack)
        verify(playbackInfoTrack).audioQuality
        verify(playbackInfoTrack).audioMode
        verify(playbackInfoOfflineTrack, times(4)).track
        verify(playbackInfoOfflineTrack).storage
        assertThat(actual).isEqualTo(
            PlaybackStatistics.Success.Prepared.Audio(
                id,
                productId,
                startTimestampMs,
                assetPresentation,
                versionedCdm,
                audioQuality,
                adaptations,
                actualAudioMode = audioMode,
                MediaStorage.DEVICE_INTERNAL,
                null,
            ),
        )
        verifyNoMoreInteractions(
            id,
            undetermined,
            assetPresentation,
            audioQuality,
            audioMode,
            playbackInfoOfflineTrack,
            versionedCdm,
            startTimestampMs,
        )
    }

    @Suppress("LongMethod")
    @Test
    fun preparePlaybackStatisticsOfflineVideo() {
        val id = mock<UUID>()
        val startTimestampMs = mock<PlaybackStatistics.IdealStartTimestampMs.Known>()
        val adaptations = mock<List<Adaptation>>()
        val undetermined = mock<PlaybackStatistics.Undetermined> {
            on { it.streamingSessionId } doReturn id
            on { it.idealStartTimestampMs } doReturn startTimestampMs
            on { it.adaptations } doReturn adaptations
        }
        val productId = "${Int.MAX_VALUE}"
        val assetPresentation = mock<AssetPresentation>()
        val videoQuality = mock<VideoQuality>()
        val streamType = mock<StreamType>()
        val playbackInfoVideo = mock<PlaybackInfo.Video> {
            on { it.videoId } doReturn productId.toInt()
            on { it.assetPresentation } doReturn assetPresentation
            on { it.videoQuality } doReturn videoQuality
            on { it.streamType } doReturn streamType
        }
        val playbackInfoOfflineVideo = mock<PlaybackInfo.Offline.Video> {
            on { it.video } doReturn playbackInfoVideo
        }
        val versionedCdm = mock<VersionedCdm>()
        whenever(versionedCdmCalculator(playbackInfoOfflineVideo)) doReturn versionedCdm

        val actual =
            undeterminedPlaybackSessionResolver(undetermined, playbackInfoOfflineVideo, null)

        verify(undetermined).streamingSessionId
        verify(undetermined).idealStartTimestampMs
        verify(undetermined).adaptations
        verify(playbackInfoVideo).videoId
        verify(playbackInfoVideo).assetPresentation
        verify(versionedCdmCalculator)(playbackInfoOfflineVideo)
        verify(playbackInfoVideo).videoQuality
        verify(playbackInfoVideo).streamType
        verify(playbackInfoOfflineVideo, times(4)).video
        assertThat(actual).isEqualTo(
            PlaybackStatistics.Success.Prepared.Video(
                id,
                productId,
                startTimestampMs,
                assetPresentation,
                versionedCdm,
                videoQuality,
                adaptations,
                actualStreamType = streamType,
                MediaStorage.DEVICE_INTERNAL,
                null,
            ),
        )
        verifyNoMoreInteractions(
            id,
            undetermined,
            assetPresentation,
            videoQuality,
            streamType,
            playbackInfoVideo,
            versionedCdm,
            startTimestampMs,
        )
    }
}
