package com.tidal.sdk.player.playbackengine

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/**
 * A helper for creating [PlaybackContext]s.
 */
internal class PlaybackContextFactory {

    @Suppress("LongMethod")
    fun create(playbackInfo: PlaybackInfo, referenceId: String?): PlaybackContext {
        return when (playbackInfo) {
            is PlaybackInfo.Track -> {
                PlaybackContext.Track(
                    playbackInfo.audioMode,
                    playbackInfo.audioQuality,
                    playbackInfo.audioQuality.toBitRate(),
                    playbackInfo.bitDepth,
                    getCodec(playbackInfo.audioMode, playbackInfo.audioQuality),
                    playbackInfo.sampleRate,
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
                    playbackInfo.audioQuality.toBitRate(),
                    null,
                    getCodec(AudioMode.STEREO, playbackInfo.audioQuality),
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
                    playbackInfo.id,
                    AssetPresentation.FULL,
                    0F,
                    AssetSource.ONLINE,
                    playbackInfo.streamingSessionId,
                    referenceId
                )
            }

            is PlaybackInfo.Offline.Track -> PlaybackContext.Track(
                playbackInfo.track.audioMode,
                playbackInfo.track.audioQuality,
                playbackInfo.track.audioQuality.toBitRate(),
                playbackInfo.track.bitDepth,
                getCodec(playbackInfo.track.audioMode, playbackInfo.track.audioQuality),
                playbackInfo.track.sampleRate,
                playbackInfo.track.trackId.toString(),
                playbackInfo.track.assetPresentation,
                0F,
                AssetSource.OFFLINE,
                playbackInfo.streamingSessionId,
                referenceId,
            )

            is PlaybackInfo.Offline.Video -> PlaybackContext.Video(
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

    private fun AudioQuality.toBitRate(): Int? {
        return when (this) {
            AudioQuality.LOW -> BIT_RATE_LOW
            AudioQuality.HIGH -> BIT_RATE_HIGH
            else -> null
        }
    }

    private fun getCodec(audioMode: AudioMode, audioQuality: AudioQuality): String? {
        if (audioMode != AudioMode.STEREO) return null
        return when (audioQuality) {
            AudioQuality.LOW, AudioQuality.HIGH -> CODEC_AAC
            AudioQuality.LOSSLESS, AudioQuality.HI_RES_LOSSLESS -> CODEC_FLAC
        }
    }

    private companion object {
        private const val BIT_RATE_LOW = 96_000
        private const val BIT_RATE_HIGH = 320_000

        private const val CODEC_AAC = "aac"
        private const val CODEC_FLAC = "flac"
    }
}
