package com.tidal.sdk.player.streamingapi.playbackinfo.model

/**
 * Playback mode of a given [PlaybackInfo].
 *
 * [STREAM] Indicating the content should be used for on-demand streaming.
 * [OFFLINE] Indicating the content should be downloaded for offline playback.
 */
enum class PlaybackMode {
    STREAM,
    OFFLINE,
}
