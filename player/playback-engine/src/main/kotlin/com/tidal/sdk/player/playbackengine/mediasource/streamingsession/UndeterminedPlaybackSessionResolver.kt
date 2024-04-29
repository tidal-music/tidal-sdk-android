package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

internal class UndeterminedPlaybackSessionResolver(
    private val versionedCdmCalculator: VersionedCdm.Calculator,
) {

    @Suppress("LongMethod")
    operator fun invoke(
        undetermined: PlaybackStatistics.Undetermined,
        playbackInfo: PlaybackInfo,
    ) = with(undetermined) {
        when (playbackInfo) {
            is PlaybackInfo.Track ->
                PlaybackStatistics.Success.Prepared.Audio(
                    streamingSessionId,
                    playbackInfo.trackId.toString(),
                    idealStartTimestampMs,
                    playbackInfo.assetPresentation,
                    versionedCdmCalculator(playbackInfo),
                    playbackInfo.audioQuality,
                    adaptations,
                    actualAudioMode = playbackInfo.audioMode,
                    MediaStorage.INTERNET,
                )

            is PlaybackInfo.Video ->
                PlaybackStatistics.Success.Prepared.Video(
                    streamingSessionId,
                    playbackInfo.videoId.toString(),
                    idealStartTimestampMs,
                    playbackInfo.assetPresentation,
                    versionedCdmCalculator(playbackInfo),
                    playbackInfo.videoQuality,
                    adaptations,
                    actualStreamType = playbackInfo.streamType,
                    MediaStorage.INTERNET,
                )

            is PlaybackInfo.Broadcast ->
                PlaybackStatistics.Success.Prepared.Broadcast(
                    streamingSessionId,
                    playbackInfo.id,
                    idealStartTimestampMs,
                    versionedCdmCalculator(playbackInfo),
                    playbackInfo.audioQuality,
                    adaptations,
                    MediaStorage.INTERNET,
                )

            is PlaybackInfo.Offline.Track ->
                PlaybackStatistics.Success.Prepared.Audio(
                    streamingSessionId,
                    playbackInfo.track.trackId.toString(),
                    idealStartTimestampMs,
                    playbackInfo.track.assetPresentation,
                    versionedCdmCalculator(playbackInfo),
                    playbackInfo.track.audioQuality,
                    adaptations,
                    actualAudioMode = playbackInfo.track.audioMode,
                    playbackInfo.mediaStorage,
                )

            is PlaybackInfo.Offline.Video ->
                PlaybackStatistics.Success.Prepared.Video(
                    streamingSessionId,
                    playbackInfo.video.videoId.toString(),
                    idealStartTimestampMs,
                    playbackInfo.video.assetPresentation,
                    versionedCdmCalculator(playbackInfo),
                    playbackInfo.video.videoQuality,
                    adaptations,
                    actualStreamType = playbackInfo.video.streamType,
                    playbackInfo.mediaStorage,
                )
        }
    }

    private val PlaybackInfo.Offline.mediaStorage: MediaStorage
        get() {
            return if (storage?.externalStorage == true) {
                MediaStorage.DEVICE_EXTERNAL
            } else {
                MediaStorage.DEVICE_INTERNAL
            }
        }
}
