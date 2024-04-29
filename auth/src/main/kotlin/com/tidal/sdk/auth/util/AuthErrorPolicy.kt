package com.tidal.sdk.auth.util

import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.ErrorResponse
import com.tidal.sdk.auth.model.UnexpectedError
import com.tidal.sdk.auth.model.failure
import com.tidal.sdk.common.NetworkError
import com.tidal.sdk.common.RetryableError
import retrofit2.HttpException

/**
 * Implement this interface to create a handler to transform [Throwable] or [ErrorResponse]
 * thrown while executing [retryWithPolicy] into an [AuthResult]
 */
internal interface AuthErrorPolicy {

    fun <T> handleError(
        errorResponse: ErrorResponse?,
        throwable: Throwable?,
    ): AuthResult<T>
}

internal class DefaultAuthErrorPolicy : AuthErrorPolicy {

    override fun <T> handleError(
        errorResponse: ErrorResponse?,
        throwable: Throwable?,
    ): AuthResult.Failure {
        with(throwable) {
            return when (this) {
                is HttpException -> {
                    val subStatus = getErrorResponse()?.subStatus
                    when {
                        isClientError() -> failure(
                            UnexpectedError(
                                code().toString(),
                                subStatus,
                                this,
                            ),
                        )

                        isServerError() -> failure(
                            RetryableError(
                                code().toString(),
                                subStatus,
                                this,
                            ),
                        )

                        else -> failure(NetworkError("1", this))
                    }
                }

                else -> failure(NetworkError("0", this))
            }
        }
    }
}
