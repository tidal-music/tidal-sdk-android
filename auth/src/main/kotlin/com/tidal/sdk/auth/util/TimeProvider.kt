package com.tidal.sdk.auth.util

import kotlinx.datetime.Instant

internal interface TimeProvider {
    val now: Instant
}
