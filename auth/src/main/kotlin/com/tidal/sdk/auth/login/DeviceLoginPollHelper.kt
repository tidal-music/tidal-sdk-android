package com.tidal.sdk.auth.login

import com.tidal.sdk.auth.model.ApiErrorSubStatus
import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.ErrorResponse
import com.tidal.sdk.auth.model.LoginResponse
import com.tidal.sdk.auth.model.TokenResponseError
import com.tidal.sdk.auth.model.UnexpectedError
import com.tidal.sdk.auth.model.failure
import com.tidal.sdk.auth.network.LoginService
import com.tidal.sdk.auth.util.AuthErrorPolicy
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.isClientError
import com.tidal.sdk.auth.util.isServerError
import com.tidal.sdk.auth.util.isSubStatus
import com.tidal.sdk.auth.util.retryWithPolicy
import com.tidal.sdk.auth.util.retryWithPolicyUnwrapped
import com.tidal.sdk.auth.util.toMilliseconds
import com.tidal.sdk.common.NetworkError
import com.tidal.sdk.common.RetryableError
import com.tidal.sdk.common.TidalError
import java.net.HttpURLConnection
import retrofit2.HttpException

internal class DeviceLoginPollHelper(private val loginService: LoginService) {
    private lateinit var pollRetryPolicy: RetryPolicy

    fun prepareForPoll(interval: Int, maxDuration: Int) {
        pollRetryPolicy = object : RetryPolicy {
            override val numberOfRetries = maxDuration / interval
            override val delayMillis = interval.toMilliseconds()
            override val delayFactor = 1

            override fun shouldRetry(
                errorResponse: ErrorResponse?,
                throwable: Throwable?,
                attempt: Int,
            ): Boolean {
                val isTokenExpired = errorResponse?.subStatus
                    .toString()
                    .isSubStatus(ApiErrorSubStatus.ExpiredAccessToken)
                return attempt < numberOfRetries && !isTokenExpired
            }
        }
    }

    suspend fun poll(
        authConfig: AuthConfig,
        deviceCode: String,
        grantType: String,
        retryPolicy: RetryPolicy,
    ): AuthResult<LoginResponse> {
        return retryWithPolicy(pollRetryPolicy, PollErrorPolicy()) {
            retryWithPolicyUnwrapped(retryPolicy) {
                loginService.getTokenFromDeviceCode(
                    clientId = authConfig.clientId,
                    clientSecret = authConfig.clientSecret,
                    grantType = grantType,
                    deviceCode = deviceCode,
                    scopes = authConfig.scopes.toString(),
                    clientUniqueKey = authConfig.clientUniqueKey,
                )
            }
        }
    }

    private class PollErrorPolicy : AuthErrorPolicy {
        override fun <T> handleError(
            errorResponse: ErrorResponse?,
            throwable: Throwable?,
        ): AuthResult<T> {
            return when (throwable) {
                is HttpException -> {
                    val subStatus = ApiErrorSubStatus
                        .entries
                        .firstOrNull { it.value == errorResponse?.subStatus.toString() }
                    when {
                        throwable.isServerError() -> failure(
                            RetryableError(
                                throwable.code().toString(),
                                subStatus?.value?.toInt(),
                                throwable,
                            ),
                        )

                        throwable.isClientError() -> failure(
                            if (throwable.code() > HttpURLConnection.HTTP_UNAUTHORIZED) {
                                TokenResponseError(
                                    throwable.code().toString(),
                                    subStatus?.value?.toInt(),
                                    throwable,
                                )
                            } else {
                                handleSubStatus(subStatus, throwable)
                            },
                        )

                        else -> failure(NetworkError("1", throwable))
                    }
                }

                else -> failure(NetworkError("0", throwable))
            }
        }

        private fun handleSubStatus(
            subStatus: ApiErrorSubStatus?,
            exception: HttpException,
        ): TidalError {
            return when (subStatus) {
                ApiErrorSubStatus.ExpiredAccessToken -> {
                    TokenResponseError(
                        exception.code().toString(),
                        subStatus.value.toInt(),
                        exception,
                    )
                }

                ApiErrorSubStatus.AuthorizationPending -> {
                    RetryableError(
                        exception.code().toString(),
                        subStatus.value.toInt(),
                        exception,
                    )
                }

                else -> {
                    UnexpectedError(
                        exception.code().toString(),
                        subStatus?.value?.toInt(),
                        exception,
                    )
                }
            }
        }
    }
}
