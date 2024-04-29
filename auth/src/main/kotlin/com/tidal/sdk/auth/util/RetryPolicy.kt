package com.tidal.sdk.auth.util

import com.tidal.sdk.auth.model.ErrorResponse
import retrofit2.HttpException

/**
 * Policy defining how a network call should be retried
 */
internal interface RetryPolicy {
    /**
     * The maximum number of attempts after the first call
     */
    val numberOfRetries: Int

    /**
     * The base delay to wait between retries
     */
    val delayMillis: Int

    /**
     * The factor by which [delayMillis] gets multiplied for each following attempt
     */
    val delayFactor: Int

    /**
     * The default implementation for retry-on-exception evaluation will return true only on
     * server errors (5xx)This is the default because it is the behaviour
     * needed for most calls in the authentication context.
     * Override for different behaviour.
     */
    fun shouldRetryOnException(throwable: Throwable): Boolean {
        return ((throwable as? HttpException)?.isServerError() ?: false)
    }

    fun shouldRetryOnErrorResponse(errorResponse: ErrorResponse?): Boolean {
        return errorResponse?.status?.isServerError() == true
    }

    /**
     * The default implementation for retry returns true if the submitted throwable is null
     * or [shouldRetryOnException] evaluates to true, and the attempt count submitted is
     * below the [numberOfRetries] threshold. Override for different behaviour.
     */
    fun shouldRetry(errorResponse: ErrorResponse?, throwable: Throwable?, attempt: Int): Boolean {
        return (
            throwable == null ||
                shouldRetryOnErrorResponse(errorResponse) ||
                shouldRetryOnException(
                    throwable,
                )
            ) &&
            attempt <= numberOfRetries
    }
}

@Suppress("MagicNumber")
internal class DefaultRetryPolicy : RetryPolicy {
    override val numberOfRetries = 5
    override val delayMillis = 1000
    override val delayFactor = 2
}

@Suppress("MagicNumber")
internal class UpgradeTokenRetryPolicy : RetryPolicy {
    override val numberOfRetries = 5
    override val delayMillis = 1000
    override val delayFactor = 2

    override fun shouldRetryOnException(throwable: Throwable) = true
    override fun shouldRetryOnErrorResponse(errorResponse: ErrorResponse?) = true
}
