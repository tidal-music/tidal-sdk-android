package com.tidal.sdk.auth.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

internal class DefaultTimeProvider : TimeProvider {
    override val now: Instant
        get() = Clock.System.now()
}
