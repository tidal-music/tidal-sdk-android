package com.tidal.sdk.flo.core.internal

import android.os.SystemClock

internal class DefaultBackoffPolicy : BackoffPolicy {

    private val firstAttemptFailedAtMs by lazy { SystemClock.uptimeMillis() }

    override fun onFailedAttempt(accumulatedFailedAttempts: Int) =
        if (firstAttemptFailedAtMs - SystemClock.uptimeMillis() > DROP_AFTER_MS) {
            null
        } else {
            ATTEMPT_DELAY_MS
        }

    companion object {
        private const val DROP_AFTER_MS = 5 * 60 * 1_000
        private const val ATTEMPT_DELAY_MS = 10 * 1_000L
    }
}
