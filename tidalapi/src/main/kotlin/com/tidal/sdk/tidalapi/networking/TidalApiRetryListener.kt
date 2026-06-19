package com.tidal.sdk.tidalapi.networking

/**
 * Observation seam for retry decisions made by [TidalApiRetryInterceptor]. Implementations MUST NOT
 * affect retry behaviour; they are notified only. The default is [NoOpTidalApiRetryListener].
 */
interface TidalApiRetryListener {

    /**
     * Called immediately before each backoff wait.
     *
     * @param category the failure category being retried.
     * @param attempt the 0-based retry index for [category].
     * @param delayMillis the backoff delay about to be applied.
     */
    fun onRetry(category: ErrorCategory, attempt: Int, delayMillis: Long)

    /**
     * Called when [category]'s retry budget is spent and the failing response/exception is
     * surfaced.
     *
     * @param attempts the total number of retries made for [category].
     */
    fun onRetriesExhausted(category: ErrorCategory, attempts: Int)
}

/**
 * A [TidalApiRetryListener] that does nothing. The default, so retry overhead/behaviour is
 * unchanged.
 */
object NoOpTidalApiRetryListener : TidalApiRetryListener {
    override fun onRetry(category: ErrorCategory, attempt: Int, delayMillis: Long) = Unit

    override fun onRetriesExhausted(category: ErrorCategory, attempts: Int) = Unit
}
