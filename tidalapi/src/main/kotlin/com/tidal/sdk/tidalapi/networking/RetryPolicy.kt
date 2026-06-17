package com.tidal.sdk.tidalapi.networking

/**
 * Policy defining how a tidalapi network call should be retried, following a three-error-category
 * model. Tidalapi-local and free of any auth/common/OkHttp types so it can be reasoned about and
 * tested in isolation.
 *
 * Each category carries its own [BackoffConfiguration] (base/max/retries) and the categories'
 * attempt counts are tracked independently by the interceptor, so exhausting one category's retries
 * does not consume another's budget.
 */
interface RetryPolicy {
    /** Backoff for retryable HTTP statuses (429 or 5xx). */
    val httpStatus: BackoffConfiguration

    /** Backoff for read/connect timeouts (`java.net.SocketTimeoutException`). */
    val timeout: BackoffConfiguration

    /** Backoff for other transport failures (DNS, TLS, connection, …). */
    val network: BackoffConfiguration

    /** The [BackoffConfiguration] governing the given [category]. */
    fun getConfigurationFor(category: ErrorCategory): BackoffConfiguration =
        when (category) {
            ErrorCategory.HTTP_STATUS -> httpStatus
            ErrorCategory.TIMEOUT -> timeout
            ErrorCategory.NETWORK -> network
        }

    /**
     * Whether a request is retry-eligible by method. Retries the safe methods GET, HEAD and OPTIONS
     * only; mutations are never retried.
     */
    fun isRetryableRequest(method: String): Boolean = method in setOf("GET", "HEAD", "OPTIONS")

    /**
     * Whether a request is retry-eligible. Retries the safe read methods GET, HEAD and OPTIONS (per
     * [isRetryableRequest]), and any mutation carrying an `Idempotency-Key` header (the backend
     * replays a duplicate keyed mutation, making it safe to retry). Un-keyed mutations are never
     * retried.
     *
     * @param[idempotencyKey] the request's `Idempotency-Key` header value, or `null` when absent.
     */
    fun isRetryableRequest(method: String, idempotencyKey: String?): Boolean =
        isRetryableRequest(method) || idempotencyKey != null
}

/**
 * Enabled-by-default policy with the per-category constants:
 * - HTTP status (429 / 5xx): base 500 ms, cap 16 s, 3 retries.
 * - Timeout: base 8 s, cap 32 s, 3 retries.
 * - Network: base 1 s, cap 16 s, 10 retries.
 */
class DefaultRetryPolicy : RetryPolicy {
    override val httpStatus =
        BackoffConfiguration(
            baseMillis = HTTP_STATUS_BASE_MILLIS,
            maxMillis = HTTP_STATUS_MAX_MILLIS,
            maxRetries = HTTP_STATUS_RETRIES,
        )
    override val timeout =
        BackoffConfiguration(
            baseMillis = TIMEOUT_BASE_MILLIS,
            maxMillis = TIMEOUT_MAX_MILLIS,
            maxRetries = TIMEOUT_RETRIES,
        )
    override val network =
        BackoffConfiguration(
            baseMillis = NETWORK_BASE_MILLIS,
            maxMillis = NETWORK_MAX_MILLIS,
            maxRetries = NETWORK_RETRIES,
        )

    private companion object {
        const val HTTP_STATUS_BASE_MILLIS = 500L
        const val HTTP_STATUS_MAX_MILLIS = 16_000L
        const val HTTP_STATUS_RETRIES = 3

        const val TIMEOUT_BASE_MILLIS = 8_000L
        const val TIMEOUT_MAX_MILLIS = 32_000L
        const val TIMEOUT_RETRIES = 3

        const val NETWORK_BASE_MILLIS = 1_000L
        const val NETWORK_MAX_MILLIS = 16_000L
        const val NETWORK_RETRIES = 10
    }
}
