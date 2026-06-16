package com.tidal.sdk.tidalapi.networking

import java.io.IOException
import java.net.SocketTimeoutException
import okhttp3.Response

private const val TOO_MANY_REQUESTS = 429
private const val SERVER_ERROR_MIN = 500
private const val SERVER_ERROR_MAX = 599

/**
 * The [ErrorCategory] this response represents for retry purposes, or `null` when it is not
 * retryable. Only code 429 or a 5xx is an [HTTP_STATUS][ErrorCategory.HTTP_STATUS] failure;
 * 2xx/3xx, 401 (left to [DefaultAuthenticator]) and every other 4xx are deliberately non-retryable.
 */
internal fun Response.toRetryCategory(): ErrorCategory? =
    if (code == TOO_MANY_REQUESTS || code in SERVER_ERROR_MIN..SERVER_ERROR_MAX) {
        ErrorCategory.HTTP_STATUS
    } else {
        null
    }

/**
 * The [ErrorCategory] this throwable represents for retry purposes, or `null` when it is not
 * retryable. A [SocketTimeoutException] is a [TIMEOUT][ErrorCategory.TIMEOUT]; any other
 * [IOException] is a [NETWORK][ErrorCategory.NETWORK] failure (DNS, TLS, connection, …). A
 * non-[IOException] throwable is never retried (it is rethrown by the caller).
 */
internal fun Throwable.toRetryCategory(): ErrorCategory? =
    when (this) {
        is SocketTimeoutException -> ErrorCategory.TIMEOUT
        is IOException -> ErrorCategory.NETWORK
        else -> null
    }
