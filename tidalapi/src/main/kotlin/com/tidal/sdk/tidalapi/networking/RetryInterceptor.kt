package com.tidal.sdk.tidalapi.networking

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

private const val IDEMPOTENCY_KEY_HEADER = "Idempotency-Key"

/**
 * An [Interceptor] that transparently retries failed requests according to the three-error-category
 * [RetryPolicy]. It sits below Retrofit's type layer, so the `Response<T>` consumer contract is
 * untouched.
 *
 * Only retry-eligible requests are retried — Step 1 gates this to the idempotent verbs GET and HEAD
 * (see [RetryPolicy.isRetryableRequest]); mutations pass through once. Each failed attempt is
 * mapped to an [ErrorCategory] by [classify], and that category's [BackoffSpec] drives the wait.
 * Attempt counts are tracked per category, so a run of timeouts does not consume the network retry
 * budget. 401 is never retried here — it is left to the [DefaultAuthenticator].
 *
 * Backoff blocks the OkHttp dispatcher thread via [sleep]; this is consistent with the existing
 * [AuthInterceptor]/[DefaultAuthenticator], which already block on these threads. [sleep] and
 * [random] are injected (with production defaults) for deterministic tests.
 */
internal class RetryInterceptor(
    private val policy: RetryPolicy,
    private val random: () -> Double = Math::random,
    private val sleep: (Long) -> Unit = Thread::sleep,
) : Interceptor {

    @Suppress("ReturnCount")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val hasKey = !request.header(IDEMPOTENCY_KEY_HEADER).isNullOrBlank()
        if (!policy.isRetryableRequest(request.method, hasKey)) {
            return chain.proceed(request)
        }

        val attempts = IntArray(ErrorCategory.entries.size)
        while (true) {
            var response: Response? = null
            val category =
                try {
                    response = chain.proceed(request)
                    classify(response, error = null)
                } catch (e: IOException) {
                    // A non-retryable throwable (incl. non-IOException) propagates directly. A
                    // retryable transport failure backs off and loops, or rethrows once the
                    // category's budget is spent.
                    val errorCategory = classify(response = null, error = e) ?: throw e
                    if (hasBudget(attempts, errorCategory)) {
                        backOff(chain, attempts, errorCategory)
                        continue
                    }
                    throw e
                }
            // A non-retryable response (2xx/3xx, 401, non-429 4xx) or a spent budget surfaces the
            // response unchanged with its body intact.
            val nonNullResponse = requireNotNull(response)
            if (category == null || !hasBudget(attempts, category)) {
                return nonNullResponse
            }
            // Committed to retrying: free the body first so it is never leaked even if the backoff
            // is aborted by cancellation.
            nonNullResponse.close()
            backOff(chain, attempts, category)
        }
    }

    /** Whether [category] has any retry attempt left. */
    private fun hasBudget(attempts: IntArray, category: ErrorCategory): Boolean =
        attempts[category.ordinal] < policy.specFor(category).maxRetries

    /**
     * Waits the backoff for [category] and records the attempt. A cancelled call aborts retrying
     * promptly: [Thread.sleep] is not woken by [okhttp3.Call.cancel], so the call's cancelled state
     * is checked around the sleep and an [InterruptedException] also ends the wait. In every abort
     * case an [IOException] is thrown so the caller observes the cancellation rather than a stale
     * (un-retried) failure response. Call only when [hasBudget] is true.
     */
    private fun backOff(chain: Interceptor.Chain, attempts: IntArray, category: ErrorCategory) {
        if (chain.call().isCanceled()) throw IOException("Canceled")
        try {
            sleep(policy.specFor(category).computeDelayMillis(attempts[category.ordinal], random))
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw IOException("Retry backoff interrupted", e)
        }
        if (chain.call().isCanceled()) throw IOException("Canceled")
        attempts[category.ordinal]++
    }
}
