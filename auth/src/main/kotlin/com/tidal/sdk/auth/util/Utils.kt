package com.tidal.sdk.auth.util

import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.ErrorResponse
import com.tidal.sdk.auth.model.success
import com.tidal.sdk.common.NetworkError
import com.tidal.sdk.common.i
import com.tidal.sdk.common.logger
import java.io.IOException
import kotlinx.coroutines.delay
import retrofit2.HttpException

/**
 * Lets a suspend fun retry on [IOException], following a [RetryPolicy] to define the
 * number and frequency of retries.
 * This means, when returning network calls , they can be retried automatically using
 * this function.
 * This function returns an [AuthResult], this means no exceptions are thrown.
 * [HttpExceptions] are handled acoording to specs, all other potentially caught exceptions
 * will lead to a [NetworkError] at this point.
 */
@Suppress("TooGenericExceptionCaught")
internal suspend fun <T> retryWithPolicy(
    retryPolicy: RetryPolicy,
    authErrorPolicy: AuthErrorPolicy? = null,
    block: suspend () -> T,
): AuthResult<T> {
    var currentDelay = retryPolicy.delayMillis.toLong()
    var attempts = 0
    var throwable: Throwable? = null
    var errorResponse: ErrorResponse? = null
    while (retryPolicy.shouldRetry(errorResponse, throwable, attempts)) {
        try {
            return success(block())
        } catch (t: Throwable) {
            retryPolicy.logger.i {
                "Retrying network call. Attempt #$attempts, exception: ${t.message}"
            }

            throwable = t
            errorResponse = (throwable as? HttpException)?.getErrorResponse()
        }
        attempts += 1
        delay(currentDelay)
        currentDelay = (currentDelay * retryPolicy.delayFactor)
    }
    return (authErrorPolicy ?: DefaultAuthErrorPolicy()).handleError(errorResponse, throwable)
}

@Suppress("TooGenericExceptionCaught")
internal suspend fun <T> retryWithPolicyUnwrapped(
    retryPolicy: RetryPolicy,
    block: suspend () -> T,
): T {
    var currentDelay = retryPolicy.delayMillis.toLong()
    var attempts = 0
    var throwable: Throwable? = null
    while (retryPolicy.shouldRetry(null, throwable, attempts)) {
        try {
            return block()
        } catch (t: Throwable) {
            retryPolicy.logger.i {
                "Retrying network call. Attempt #$attempts, exception: ${t.message}"
            }
            throwable = t
        }
        attempts += 1
        delay(currentDelay)
        currentDelay = (currentDelay * retryPolicy.delayFactor)
    }
    throw (throwable!!)
}
