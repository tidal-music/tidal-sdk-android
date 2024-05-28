package com.tidal.sdk.auth.util

import com.tidal.sdk.auth.model.ApiErrorSubStatus
import com.tidal.sdk.auth.model.ErrorResponse
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException

private const val HTTP_CLIENT_ERROR_STATUS_START = 400
private const val HTTP_SERVER_ERROR_STATUS_START = 500
private const val HTTP_STATUS_END = 600
private const val MILLIS_MULTIPLIER = 1000

internal fun Int.toMilliseconds() = this * MILLIS_MULTIPLIER

internal fun HttpException.isClientError(): Boolean {
    return (HTTP_CLIENT_ERROR_STATUS_START until HTTP_SERVER_ERROR_STATUS_START).contains(
        this.code(),
    )
}

internal fun HttpException.isServerError(): Boolean {
    return (HTTP_SERVER_ERROR_STATUS_START until HTTP_STATUS_END).contains(
        this.code(),
    )
}

internal fun Int.isServerError(): Boolean {
    return (HTTP_SERVER_ERROR_STATUS_START until HTTP_STATUS_END).contains(
        this,
    )
}

@Suppress("SwallowedException")
internal fun HttpException.getErrorResponse(): ErrorResponse? {
    val response = response()

    return response?.errorBody()?.string()?.let {
        try {
            Json.decodeFromString<ErrorResponse>(it)
        } catch (e: SerializationException) {
            logger.d { "Error parsing error response from HttpException: $it" }
            null
        }
    }
}

internal fun String?.shouldRefreshToken(): Boolean {
    return ApiErrorSubStatus.entries.firstOrNull { it.value == this }?.shouldTriggerRefresh == true
}

internal fun String?.isSubStatus(status: ApiErrorSubStatus): Boolean {
    return this == status.value
}

internal fun Set<String>.toScopesString(): String {
    return joinToString(" ")
}
