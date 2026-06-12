package com.tidal.sdk.tidalapi.networking

import kotlin.math.min

/** The category of failure a retry attempt is reacting to. */
enum class ErrorCategory {
    /** A retryable HTTP status: response code 429 or any 5xx. */
    HTTP_STATUS,

    /** A read/connect timeout (`java.net.SocketTimeoutException`). */
    TIMEOUT,

    /** A non-timeout transport failure (other `IOException`: DNS, TLS, connection, …). */
    NETWORK,
}

/**
 * Per-category exponential-backoff configuration. Pure and OkHttp-free so it can be reasoned about
 * and tested in isolation.
 *
 * @param baseMillis the base delay `B`, applied before the first retry.
 * @param maxMillis the cap `M`, an upper bound on the capped (pre-jitter) delay.
 * @param maxRetries the number of retries `N` allowed after the first attempt, for this category.
 */
data class BackoffSpec(val baseMillis: Long, val maxMillis: Long, val maxRetries: Int) {

    /**
     * Computes the backoff delay, in milliseconds, before the next retry [attempt] (0-based).
     *
     * Follows `D = min(B·2ⁿ, M)` then `× j`, applying the cap **before** jitter to match the iOS
     * `JitteredBackoffPolicy` (a deliberate divergence from the literal jitter-then-cap ordering).
     * The jitter factor `j` falls in the band `[0.8, 1.0]` via `0.8 + 0.2 · random()`. [random] is
     * injected (defaulting to [Math.random] at the call site) for deterministic tests.
     */
    @Suppress("MagicNumber")
    fun computeDelayMillis(attempt: Int, random: () -> Double): Long {
        val capped = cappedDelayMillis(attempt)
        return (capped * (0.8 + 0.2 * random())).toLong()
    }

    /**
     * `min(baseMillis · 2^attempt, maxMillis)`, grown step-by-step and clamped to [maxMillis] as
     * soon as a further doubling would exceed it. Computing the growth iteratively (rather than via
     * `baseMillis * (1L shl attempt)`) avoids the `Long` shift wrapping at `attempt >= 64` and the
     * multiplication overflowing, so an oversized attempt index saturates at the cap instead of
     * producing a small or negative delay.
     */
    private fun cappedDelayMillis(attempt: Int): Long {
        var capped = min(baseMillis, maxMillis)
        repeat(attempt) {
            if (capped >= maxMillis / 2) return maxMillis
            capped *= 2
        }
        return capped
    }
}
