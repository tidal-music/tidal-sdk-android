package com.tidal.sdk.auth

import android.net.Uri
import com.tidal.sdk.auth.login.LoginRepository
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.model.DeviceAuthorizationResponse
import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.auth.model.TokenResponseError
import com.tidal.sdk.common.NetworkError
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger

/**
 * Main entry point for authentication and authorization operations. This class provides functions
 * to initialize and finalize login processes, handle device logins, and manage user credentials.
 */
class Auth internal constructor(
    private val loginRepository: LoginRepository,
) {

    /**
     * Checks whether a user is currently logged in.
     * @return True if a user is logged in, false otherwise.
     */
    fun isLoggedIn(): Boolean {
        return loginRepository.isLoggedIn()
    }

    /**
     * Begins the login process by generating a URI to the login service.
     * @param redirectUri The URI to redirect to after successful login.
     * @param loginConfig Optional configuration for customizing the login process.
     * @return A URI to direct the user to for login.
     */
    fun initializeLogin(redirectUri: String, loginConfig: LoginConfig?): Uri {
        val uriString = loginRepository.getLoginUri(redirectUri, loginConfig)
        return Uri.parse(uriString).also {
            logger.d {
                "initializeLogin: redirectUri: $redirectUri, " +
                    "loginConfig: $loginConfig, returned uri: $it"
            }
        }
    }

    /**
     * Initializes a device login flow, providing the necessary information for user verification.
     * @return A response containing device and user verification information.
     * @throws NetworkError If a network error occurs during the process.
     */
    suspend fun finalizeLogin(loginResponseUri: String) {
        loginRepository.getCredentialsFromLoginCode(loginResponseUri).also {
            logger.d {
                "finalizeLogin: loginResponseUri: $loginResponseUri, result: $it"
            }
        }
    }

    suspend fun setCredentials(credentials: Credentials, refreshToken: String? = null) {
        logger.d {
            "setCredentials: credentials: $credentials, refreshToken: $refreshToken"
        }
        loginRepository.setCredentials(credentials, refreshToken)
    }

    suspend fun logout() {
        logger.d { "logout" }
        loginRepository.logout()
    }

    /**
     * Initializes a device login flow, providing the necessary information for user verification.
     * @return [AuthResult] containing either a [DeviceAuthorizationResponse], or, if the operation
     * was unsuccessful, a [NetworkError].
     */
    suspend fun initializeDeviceLogin(): AuthResult<DeviceAuthorizationResponse> {
        return loginRepository.initializeDeviceLogin().also {
            logger.d { "initializeDeviceLogin: result: $it" }
        }
    }

    /**
     * Finalizes the device login flow. Polls until either a valid access token is received,
     * or an unrecoverable error occurs.
     *
     * @returns [AuthResult] containing either [Nothing] when successful or a
     * [TokenResponseError] in case the operation failed due to an unrecoverable error occurring
     * when requesting the access token.
     */
    suspend fun finalizeDeviceLogin(deviceCode: String): AuthResult<Nothing> {
        return loginRepository.pollForDeviceLoginResponse(deviceCode)
    }
}
