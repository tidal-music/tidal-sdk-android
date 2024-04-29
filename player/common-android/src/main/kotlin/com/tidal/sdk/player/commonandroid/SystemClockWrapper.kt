package com.tidal.sdk.player.commonandroid

import android.os.SystemClock

class SystemClockWrapper {

    val uptimeMillis: Long
        get() = SystemClock.uptimeMillis()
}
