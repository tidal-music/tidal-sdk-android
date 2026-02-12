package com.tidal.sdk.player.playbackengine

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/** A helper for creating [PlaybackContext]s. */
internal class PlaybackContextFactory {

    fun create(playbackInfo: PlaybackInfo, referenceId: String?): PlaybackContext {
        return when (playbackInfo) {
            is PlaybackInfo.Track -> {
                PlaybackContext.Track(
                    playbackInfo.audioMode,
                    playbackInfo.audioQuality,
                    null,
                    null,
                    null,
                    null,
                    playbackInfo.previewReason,
                    playbackInfo.trackId.toString(),
                    playbackInfo.assetPresentation,
                    0F,
                    AssetSource.ONLINE,
                    playbackInfo.streamingSessionId,
                    referenceId,
                )
            }

            is PlaybackInfo.Video -> {
                PlaybackContext.Video(
                    playbackInfo.streamType,
                    playbackInfo.videoQuality,
                    playbackInfo.videoId.toString(),
                    playbackInfo.assetPresentation,
                    0F,
                    AssetSource.ONLINE,
                    playbackInfo.streamingSessionId,
                    referenceId,
                )
            }

            is PlaybackInfo.Broadcast -> {
                PlaybackContext.Track(
                    AudioMode.STEREO,
                    playbackInfo.audioQuality,
                    null,
                    null,
                    null,
                    null,
                    null,
                    playbackInfo.id,
                    AssetPresentation.FULL,
                    0F,
                    AssetSource.ONLINE,
                    playbackInfo.streamingSessionId,
                    referenceId,
                )
            }

            is PlaybackInfo.UC -> {
                PlaybackContext.Track(
                    AudioMode.STEREO,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    playbackInfo.id,
                    AssetPresentation.FULL,
                    0F,
                    AssetSource.ONLINE,
                    playbackInfo.streamingSessionId,
                    referenceId,
                )
            }

            is PlaybackInfo.Offline.Track ->
                PlaybackContext.Track(
                    playbackInfo.track.audioMode,
                    playbackInfo.track.audioQuality,
                    null,
                    null,
                    null,
                    null,
                    null,
                    playbackInfo.track.trackId.toString(),
                    playbackInfo.track.assetPresentation,
                    0F,
                    AssetSource.OFFLINE,
                    playbackInfo.streamingSessionId,
                    referenceId,
                )

            is PlaybackInfo.Offline.Video ->
                PlaybackContext.Video(
                    playbackInfo.video.streamType,
                    playbackInfo.video.videoQuality,
                    playbackInfo.video.videoId.toString(),
                    playbackInfo.video.assetPresentation,
                    0F,
                    AssetSource.OFFLINE,
                    playbackInfo.streamingSessionId,
                    referenceId,
                )
        }
    }
}
