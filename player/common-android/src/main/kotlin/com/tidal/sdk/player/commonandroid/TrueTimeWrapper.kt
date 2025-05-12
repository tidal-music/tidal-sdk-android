package com.tidal.sdk.player.commonandroid

import com.instacart.library.truetime.TrueTime

class TrueTimeWrapper {

    val currentTimeMillis: Long
        get() =
            try {
                TrueTime.now().time
            } catch (_: Throwable) {
                System.currentTimeMillis()
            }
}
