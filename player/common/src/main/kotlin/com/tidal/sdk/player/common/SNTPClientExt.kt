package com.tidal.sdk.player.common

import com.tidal.networktime.SNTPClient
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

val SNTPClient.ntpOrLocalClockTime: Duration
    get() = epochTime ?: System.currentTimeMillis().milliseconds
