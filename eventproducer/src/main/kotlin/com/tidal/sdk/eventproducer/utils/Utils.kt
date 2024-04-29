package com.tidal.sdk.eventproducer.utils

import com.tidal.sdk.eventproducer.model.Result
import com.tidal.sdk.eventproducer.model.failure
import com.tidal.sdk.eventproducer.model.success

@Suppress("TooGenericExceptionCaught", "SwallowedException")
suspend fun <T> safeRequest(block: suspend () -> T): Result<T> {
    return try {
        success(block())
    } catch (t: Throwable) {
        failure()
    }
}
