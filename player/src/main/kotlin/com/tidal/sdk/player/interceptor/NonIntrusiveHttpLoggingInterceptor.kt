package com.tidal.sdk.player.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

/**
 * A http logger that delegates to two different interceptors based on the host address.
 *
 * The idea is that we want
 * - body level logging for api requests because these are quite small, but still useful.
 * - basic level logging for trakc and video assets because they are too intrusive to print the
 *   whole thing. The body would be omitted, but the header is big and not useful for us.
 */
internal class NonIntrusiveHttpLoggingInterceptor(
    private val basicLevelHttpLoggingInterceptor: HttpLoggingInterceptor,
    private val bodyLevelHttpLoggingInterceptor: HttpLoggingInterceptor,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        if (arrayOf("api", "et").any { it in chain.request().url.host }) {
            bodyLevelHttpLoggingInterceptor.intercept(chain)
        } else {
            basicLevelHttpLoggingInterceptor.intercept(chain)
        }
}
