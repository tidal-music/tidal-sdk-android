package com.tidal.sdk.tidalapi.networking

import java.io.IOException
import java.net.SocketTimeoutException
import okhttp3.Response

/**
 * Classifies the outcome of a request attempt into the [ErrorCategory] that governs its retry, or
 * `null` when the outcome is not retryable.
 *
 * A thrown [SocketTimeoutException] is a [TIMEOUT][ErrorCategory.TIMEOUT]; any other [IOException]
 * is a [NETWORK][ErrorCategory.NETWORK] failure (DNS, TLS, connection, …). A non-`IOException`
 * throwable is never retried (it is rethrown by the caller). For a returned response, only code 429
 * or a 5xx is an [HTTP_STATUS][ErrorCategory.HTTP_STATUS] failure; 2xx/3xx, 401 (left to
 * [DefaultAuthenticator]) and every other 4xx are deliberately non-retryable.
 */
@Suppress("MagicNumber")
internal fun classify(response: Response?, error: Throwable?): ErrorCategory? =
    when {
        error is SocketTimeoutException -> ErrorCategory.TIMEOUT
        error is IOException -> ErrorCategory.NETWORK
        error != null -> null
        response != null && (response.code == 429 || response.code in 500..599) ->
            ErrorCategory.HTTP_STATUS
        else -> null
    }
