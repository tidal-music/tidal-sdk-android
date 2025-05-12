package com.tidal.sdk.player.playbackengine.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/** A parameter bag for creating configurations for playback asset timeouts. */
data class AssetTimeoutConfig(
    val connectTimeout: Duration = 15.seconds,
    val readTimeout: Duration = 30.seconds,
    val writeTimeout: Duration = 15.seconds,
)
