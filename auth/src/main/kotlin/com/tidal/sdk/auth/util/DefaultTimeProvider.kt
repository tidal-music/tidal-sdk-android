package com.tidal.sdk.auth.util

internal class DefaultTimeProvider : TimeProvider {
    override val now: Long
        get() = System.currentTimeMillis() / 1000
}
