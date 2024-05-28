package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.ApiErrorSubStatus
import com.tidal.sdk.auth.model.DeviceAuthorizationResponse
import com.tidal.sdk.auth.model.ErrorResponse
import com.tidal.sdk.auth.model.LoginResponse
import com.tidal.sdk.auth.network.LoginService
import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.auth.util.buildTestHttpException
import com.tidal.sdk.util.CoroutineTestTimeProvider
import java.net.UnknownHostException

internal class FakeLoginService(
    private val errorResponse: ErrorResponse? = null,
    private val shouldThrowUnknownHostException: Boolean = false,
) : LoginService {

    val fakeLoginResponse = LoginResponse(
        "credentials",
        "clientName",
        0,
        "refreshToken",
        "tokenType",
        "",
        userId = 999,
    )

    /**
     * Set the login behaviour before testing the device login flow
     */
    lateinit var deviceLoginBehaviour: DeviceLoginBehaviour

    private var deviceLoginPendingHelper: DeviceLoginPendingHelper? = null
    var calls = mutableListOf<CallType>()

    private lateinit var deviceAuthorizationResponse: DeviceAuthorizationResponse

    override suspend fun getTokenWithCodeVerifier(
        code: String,
        clientId: String,
        grantType: String,
        redirectUri: String,
        scopes: String,
        codeVerifier: String,
        clientUniqueKey: String?,
    ): LoginResponse {
        calls.add(CallType.GET_TOKEN_WITH_CODE_VERIFIER)
        return returnLoginResponseOrThrow()
    }

    override suspend fun getDeviceAuthorization(
        clientId: String,
        scope: String,
    ): DeviceAuthorizationResponse {
        calls.add(CallType.GET_DEVICE_AUTHORIZATION)
        errorResponse?.let {
            throw buildTestHttpException(it)
        }
        return makeDeviceAuthorizationResponse().also {
            deviceAuthorizationResponse = it
        }
    }

    override suspend fun getTokenFromDeviceCode(
        clientId: String,
        clientSecret: String?,
        deviceCode: String,
        grantType: String,
        scopes: String,
        clientUniqueKey: String?,
    ): LoginResponse {
        calls.add(CallType.GET_TOKEN_FROM_DEVICE_CODE)
        if (shouldThrowUnknownHostException) {
            throw UnknownHostException()
        }
        deviceLoginPendingHelper?.let { helper ->
            if (helper.isPending()) {
                throw buildTestHttpException(deviceLoginBehaviour.errorResponseWhilePending)
            } else {
                deviceLoginBehaviour.errorResponseToFinishWith?.let {
                    throw buildTestHttpException(it)
                }
            }
        }

        return deviceLoginBehaviour.errorResponseToFinishWith?.let {
            throw buildTestHttpException(it)
        } ?: returnLoginResponseOrThrow()
    }

    private fun makeDeviceAuthorizationResponse(): DeviceAuthorizationResponse {
        deviceLoginPendingHelper = DeviceLoginPendingHelper(
            deviceLoginBehaviour.timeProvider,
            deviceLoginBehaviour.loginPendingSeconds,
        )
        return DeviceAuthorizationResponse(
            "deviceCode",
            "userCode",
            "verificationUri",
            "verificationUriComplete",
            deviceLoginBehaviour.authorizationResponseExpirationSeconds,
            2,
        )
    }

    private fun returnLoginResponseOrThrow(): LoginResponse {
        if (shouldThrowUnknownHostException) {
            throw UnknownHostException()
        }
        errorResponse?.let {
            throw buildTestHttpException(errorResponse)
        } ?: run {
            return fakeLoginResponse
        }
    }

    enum class CallType {
        GET_TOKEN_WITH_CODE_VERIFIER,
        GET_TOKEN_FROM_DEVICE_CODE,
        GET_DEVICE_AUTHORIZATION,
    }

    class DeviceLoginPendingHelper(
        private val timeProvider: TimeProvider,
        private val pendingForSeconds: Int,
    ) {

        private val startTime = timeProvider.now

        fun isPending(): Boolean {
            return startTime.epochSeconds + pendingForSeconds > timeProvider.now.epochSeconds
        }
    }
}

/**
 * Use this class to define [FakeLoginService]'s behaviour when using the
 * device login flow. Note that you need to supply a [CoroutineTestTimeProvider]
 * and start it before making calls.
 */
internal data class DeviceLoginBehaviour(
    val authorizationResponseExpirationSeconds: Int,
    val loginPendingSeconds: Int,
    val errorResponseWhilePending: ErrorResponse = ErrorResponse(
        400,
        "authorization_pending",
        ApiErrorSubStatus.AuthorizationPending.value.toInt(),
        "",
    ),
    val errorResponseToFinishWith: ErrorResponse? = null,
    val timeProvider: CoroutineTestTimeProvider,
)
