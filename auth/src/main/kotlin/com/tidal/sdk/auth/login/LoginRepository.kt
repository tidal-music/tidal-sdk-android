package com.tidal.sdk.auth.login

import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.AuthorizationError
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.auth.model.DeviceAuthorizationResponse
import com.tidal.sdk.auth.model.LoginConfig
import com.tidal.sdk.auth.model.LoginResponse
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.model.failure
import com.tidal.sdk.auth.network.LoginService
import com.tidal.sdk.auth.network.NetworkingJobHandler
import com.tidal.sdk.auth.storage.TokensStore
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.auth.util.retryWithPolicy
import com.tidal.sdk.auth.util.toScopesString
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import kotlinx.coroutines.flow.MutableSharedFlow

internal class LoginRepository constructor(
    private val authConfig: AuthConfig,
    private val timeProvider: TimeProvider,
    private val codeChallengeBuilder: CodeChallengeBuilder,
    private val loginUriBuilder: LoginUriBuilder,
    private val loginService: LoginService,
    private val tokensStore: TokensStore,
    private val exponentialBackoffPolicy: RetryPolicy,
    private val bus: MutableSharedFlow<TidalMessage>,
    private val networkingJobHandler: NetworkingJobHandler,
) {

    private var codeVerifier: String? = null
    private val deviceLoginPollHelper: DeviceLoginPollHelper by lazy {
        DeviceLoginPollHelper(loginService)
    }

    fun getLoginUri(
        redirectUri: String,
        loginConfig: LoginConfig?,
    ): String {
        val codeChallenge = with(codeChallengeBuilder) {
            codeVerifier = createCodeVerifier()
            createCodeChallenge(codeVerifier!!)
        }
        return loginUriBuilder.getLoginUri(
            redirectUri,
            loginConfig,
            codeChallenge,
        )
    }

    @Suppress("MagicNumber")
    suspend fun getCredentialsFromLoginCode(uri: String): AuthResult<LoginResponse> {
        val redirectUri = RedirectUri.fromUriString(uri)
        if (redirectUri is RedirectUri.Failure || codeVerifier == null) {
            return failure(AuthorizationError("0"))
        }
        with(redirectUri as RedirectUri.Success) {
            return retryWithPolicy(exponentialBackoffPolicy) {
                loginService.getTokenWithCodeVerifier(
                    code = code,
                    clientId = authConfig.clientId,
                    grantType = GRANT_TYPE_AUTHORIZATION_CODE,
                    redirectUri = url,
                    scopes = authConfig.scopes.toScopesString(),
                    codeVerifier = requireNotNull(codeVerifier),
                    clientUniqueKey = authConfig.clientUniqueKey,
                )
            }.also {
                it.successData?.let { response ->
                    saveTokensAndNotifyBus(response)
                }
            }
        }
    }

    suspend fun setCredentials(credentials: Credentials, refreshToken: String? = null) {
        networkingJobHandler.cancelAllJobs()
        val storedTokens = tokensStore.getLatestTokens(authConfig.credentialsKey)
        if (credentials != storedTokens?.credentials) {
            val tokens = Tokens(
                credentials,
                refreshToken ?: storedTokens?.refreshToken,
            )
            tokensStore.saveTokens(tokens)
            bus.emit(CredentialsUpdatedMessage(tokens.credentials))
        }
    }

    private suspend fun saveTokensAndNotifyBus(response: LoginResponse) {
        val tokens = Tokens(
            credentials = Credentials(
                authConfig.clientId,
                authConfig.scopes,
                authConfig.clientUniqueKey,
                response.scopesString.split(", ").toSet(),
                response.userId?.toString(),
                timeProvider.now + response.expiresIn.toLong(),
                response.accessToken,
            ),
            refreshToken = response.refreshToken,
        )
        tokensStore.saveTokens(tokens)
        bus.emit(CredentialsUpdatedMessage(tokens.credentials))
    }

    suspend fun logout() {
        tokensStore.eraseTokens()
        bus.emit(CredentialsUpdatedMessage())
    }

    suspend fun initializeDeviceLogin(): AuthResult<DeviceAuthorizationResponse> = retryWithPolicy(
        retryPolicy = exponentialBackoffPolicy
    ) {
        loginService.getDeviceAuthorization(
            authConfig.clientId,
            authConfig.scopes.toScopesString(),
        ).also {
            deviceLoginPollHelper.prepareForPoll(it.interval, it.expiresIn)
        }
    }

    suspend fun pollForDeviceLoginResponse(deviceCode: String): AuthResult<Nothing> {
        val pollResult = deviceLoginPollHelper.poll(
            authConfig,
            deviceCode,
            GRANT_TYPE_DEVICE_CODE,
            exponentialBackoffPolicy,
        ).also {
            it.successData?.let { response ->
                saveTokensAndNotifyBus(response)
            }
        }
        return when (pollResult) {
            is AuthResult.Success -> {
                AuthResult.Success(null)
            }

            is AuthResult.Failure -> {
                AuthResult.Failure(pollResult.message)
            }
        }.also {
            logger.d { "finalizeDeviceLogin: deviceCode: $deviceCode, result: $it" }
        }
    }

    companion object {

        private const val GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code"
        private const val GRANT_TYPE_DEVICE_CODE = "urn:ietf:params:oauth:grant-type:device_code"
    }
}
