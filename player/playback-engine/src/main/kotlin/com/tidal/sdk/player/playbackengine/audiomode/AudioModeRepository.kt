package com.tidal.sdk.player.playbackengine.audiomode

import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/**
 * Repository for getting the [immersiveAudio] to be used in a specific request for [PlaybackInfo].
 */
internal class AudioModeRepository(
    var immersiveAudio: Boolean = true,
)
