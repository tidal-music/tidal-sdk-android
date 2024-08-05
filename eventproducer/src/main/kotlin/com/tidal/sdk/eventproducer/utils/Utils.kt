package com.tidal.sdk.eventproducer.utils

import com.tidal.networktime.SNTPClient
import com.tidal.sdk.eventproducer.model.Result
import com.tidal.sdk.eventproducer.model.failure
import com.tidal.sdk.eventproducer.model.success
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Suppress("TooGenericExceptionCaught", "SwallowedException")
internal suspend fun <T> safeRequest(block: suspend () -> T): Result<T> {
    return try {
        success(block())
    } catch (t: Throwable) {
        failure()
    }
}

internal val SNTPClient.ntpOrLocalClockTime: Duration
    get() = epochTime ?: System.currentTimeMillis().milliseconds
