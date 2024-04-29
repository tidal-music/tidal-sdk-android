package com.tidal.sdk.player.streamingprivileges.connection

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val ConnectRunnable.Companion.reflectionDELAY_BASE_MS: Int
    get() = reflectionGetInstanceMemberProperty("DELAY_BASE_MS")!!

internal val ConnectRunnable.Companion.reflectionJITTER_FACTOR_MAX: Float
    get() = reflectionGetInstanceMemberProperty("JITTER_FACTOR_MAX")!!
