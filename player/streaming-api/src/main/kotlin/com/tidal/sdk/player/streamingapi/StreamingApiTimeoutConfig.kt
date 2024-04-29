package com.tidal.sdk.player.streamingapi

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * A parameter bag for creating configurations for streaming api(mainly pbi and drm licenses)
 * timeouts.
 */
data class StreamingApiTimeoutConfig(
    val connectTimeout: Duration = 15.seconds,
    val readTimeout: Duration = 30.seconds,
    val writeTimeout: Duration = 15.seconds,
)
