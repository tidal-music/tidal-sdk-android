package com.tidal.sdk.tidalapi.networking

/**
 * Policy defining how a tidalapi network call should be retried, following a three-error-category
 * model. Tidalapi-local and free of any auth/common/OkHttp types so it can be reasoned about and
 * tested in isolation.
 *
 * Each category carries its own [BackoffSpec] (base/max/retries) and the categories' attempt counts
 * are tracked independently by the interceptor, so exhausting one category's retries does not
 * consume another's budget.
 */
interface RetryPolicy {
    /** Backoff for retryable HTTP statuses (429 or 5xx). */
    val httpStatus: BackoffSpec

    /** Backoff for read/connect timeouts (`java.net.SocketTimeoutException`). */
    val timeout: BackoffSpec

    /** Backoff for other transport failures (DNS, TLS, connection, …). */
    val network: BackoffSpec

    /** The [BackoffSpec] governing the given [category]. */
    fun specFor(category: ErrorCategory): BackoffSpec =
        when (category) {
            ErrorCategory.HTTP_STATUS -> httpStatus
            ErrorCategory.TIMEOUT -> timeout
            ErrorCategory.NETWORK -> network
        }

    /**
     * Whether a request is retry-eligible by method. Step 1 retries the idempotent verbs GET and
     * HEAD only; mutations are never retried. [hasIdempotencyKey] is accepted (so the signature is
     * stable into the keyed-write follow-up) but ignored here.
     */
    fun isRetryableRequest(method: String, hasIdempotencyKey: Boolean): Boolean =
        method in setOf("GET", "HEAD")
}

/**
 * Enabled-by-default policy with the per-category constants:
 * - HTTP status (429 / 5xx): base 500 ms, cap 16 s, 3 retries.
 * - Timeout: base 8 s, cap 32 s, 3 retries.
 * - Network: base 1 s, cap 16 s, 10 retries.
 */
@Suppress("MagicNumber")
class DefaultRetryPolicy : RetryPolicy {
    override val httpStatus = BackoffSpec(baseMillis = 500L, maxMillis = 16_000L, maxRetries = 3)
    override val timeout = BackoffSpec(baseMillis = 8_000L, maxMillis = 32_000L, maxRetries = 3)
    override val network = BackoffSpec(baseMillis = 1_000L, maxMillis = 16_000L, maxRetries = 10)
}
