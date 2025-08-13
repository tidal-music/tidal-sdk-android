package com.tidal.sdk.player.playbackengine.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/** A parameter bag for creating configurations for playback buffer logic. */
data class BufferConfiguration(
    val backBufferDuration: Duration = 20.seconds,
    val minPlaybackBufferAudio: Duration = 2.minutes,
    val maxPlaybackBufferAudio: Duration = 2.minutes,
    val bufferForPlayback: Duration = 2.5.seconds,
    val bufferForPlaybackAfterRebuffer: Duration = 5.seconds,
    val audioTrackBuffer: Duration = 1.5.seconds,
)
