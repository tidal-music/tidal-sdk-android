package com.tidal.sdk.tidalapi.networking

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Retries eligible requests per the [RetryPolicy], backing off per [ErrorCategory].
 *
 * Public so consumers of the TIDAL API can reuse the same retry logic in their own Retrofit/OkHttp
 * instances, e.g.
 * `OkHttpClient.Builder().addInterceptor(TidalApiRetryInterceptor(DefaultRetryPolicy()))`.
 */
class TidalApiRetryInterceptor(
    private val policy: RetryPolicy,
    private val random: () -> Double = Math::random,
    private val sleep: (Long) -> Unit = Thread::sleep,
    private val retryListener: TidalApiRetryListener = NoOpTidalApiRetryListener,
) : Interceptor {

    @Suppress("ReturnCount")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!policy.isRetryableRequest(request.method, request.header("Idempotency-Key"))) {
            return chain.proceed(request)
        }

        val attempts = IntArray(ErrorCategory.entries.size)
        while (true) {
            var response: Response? = null
            val category =
                try {
                    val proceeded = chain.proceed(request)
                    response = proceeded
                    proceeded.toRetryCategory()
                } catch (e: IOException) {
                    val errorCategory = e.toRetryCategory() ?: throw e
                    if (hasBudget(attempts, errorCategory)) {
                        backOff(chain, attempts, errorCategory)
                        continue
                    }
                    retryListener.onRetriesExhausted(errorCategory, attempts[errorCategory.ordinal])
                    throw e
                }
            val nonNullResponse = requireNotNull(response)
            if (category == null) {
                return nonNullResponse
            }
            if (!hasBudget(attempts, category)) {
                retryListener.onRetriesExhausted(category, attempts[category.ordinal])
                return nonNullResponse
            }
            nonNullResponse.close() // Free the body before retrying so it is not leaked.
            backOff(chain, attempts, category)
        }
    }

    private fun hasBudget(attempts: IntArray, category: ErrorCategory): Boolean =
        attempts[category.ordinal] < policy.getConfigurationFor(category).maxRetries

    /** Sleeps [category]'s backoff and records the attempt; throws [IOException] if cancelled. */
    private fun backOff(chain: Interceptor.Chain, attempts: IntArray, category: ErrorCategory) {
        if (chain.call().isCanceled()) throw IOException("Canceled")
        val attempt = attempts[category.ordinal]
        val delayMillis = policy.getConfigurationFor(category).computeDelayMillis(attempt, random)
        retryListener.onRetry(category, attempt, delayMillis)
        try {
            sleep(delayMillis)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw IOException("Retry backoff interrupted", e)
        }
        if (chain.call().isCanceled()) throw IOException("Canceled")
        attempts[category.ordinal]++
    }
}
