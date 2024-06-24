package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.auth.model.RefreshResponse
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.model.failure
import com.tidal.sdk.auth.model.success
import com.tidal.sdk.auth.storage.TokensStore
import com.tidal.sdk.auth.token.TokenService
import com.tidal.sdk.auth.util.RetryPolicy
import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.auth.util.retryWithPolicy
import com.tidal.sdk.auth.util.shouldRefreshToken
import com.tidal.sdk.auth.util.toScopesString
import com.tidal.sdk.common.RetryableError
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.common.UnexpectedError
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.util.concurrent.atomic.AtomicInteger

internal class TokenRepository(
    private val authConfig: AuthConfig,
    private val timeProvider: TimeProvider,
    private val tokensStore: TokensStore,
    private val tokenService: TokenService,
    private val defaultBackoffPolicy: RetryPolicy,
    private val upgradeBackoffPolicy: RetryPolicy,
    private val bus: MutableSharedFlow<TidalMessage>,
) {

    var getCredentialsCalls = AtomicInteger(0)
    var refreshesBranchSkipOrOuterSkip = AtomicInteger(0)
    var refreshesBranchToken = AtomicInteger(0)
    var refreshesBranchSecret = AtomicInteger(0)
    var refreshesBranchLogout = AtomicInteger(0)
    var upgrades = AtomicInteger(0)

    private fun needsCredentialsUpgrade(): Boolean {
        val storedCredentials = getLatestTokens()?.credentials
        return with(authConfig) {
            when {
                storedCredentials == null -> {
                    false
                }
                // level USER indicates we have a refreshToken, a precondition for this flow
                storedCredentials.level == Credentials.Level.USER -> {
                    clientId != storedCredentials.clientId
                }

                else -> {
                    false
                }
            }
        }
    }

    internal fun getLatestTokens(): Tokens? {
        return tokensStore.getLatestTokens(authConfig.credentialsKey)
    }

    @Suppress("UnusedPrivateMember")
    suspend fun getCredentials(apiErrorSubStatus: String?): AuthResult<Credentials> {
        getCredentialsCalls.incrementAndGet()
        logger.d { "Received subStatus: $apiErrorSubStatus" }
        val latestTokens = getLatestTokens()
        /**
         * Note the double if check. This is to avoid synchronized whenever possible (since it's
         * slow). It's the same reason why when you write a singleton you're supposed to do the
         * null check both outside and inside the synchronized call.
         */
        if ((latestTokens?.credentials?.isExpired(timeProvider) != false) ||
            needsCredentialsUpgrade()
        ) {
            return synchronized(this) {
                var upgradedRefreshToken: String? = null
                val latestTokens = getLatestTokens()
                if (latestTokens != null && needsCredentialsUpgrade()) {
                    val upgradeCredentials = runBlocking { upgradeTokens(latestTokens) }
                    upgradeCredentials.successData?.let {
                        upgradedRefreshToken = it.refreshToken
                        success(it.credentials)
                    } ?: upgradeCredentials as AuthResult.Failure
                } else {
                    updateCredentials(latestTokens, apiErrorSubStatus)
                }.also {
                    it.successData?.let { token ->
                        runBlocking {
                            saveTokensAndNotify(token, upgradedRefreshToken, latestTokens)
                        }
                    }
                }
            }
        }
        refreshesBranchSkipOrOuterSkip.incrementAndGet()
        return success(latestTokens.credentials)
    }

    private fun updateCredentials(
        storedTokens: Tokens?,
        apiErrorSubStatus: String?,
    ) = when {
        storedTokens?.credentials?.isExpired(timeProvider) == false &&
            apiErrorSubStatus.shouldRefreshToken().not() -> {
            logger.d { "Refresh skipped" }
            refreshesBranchSkipOrOuterSkip.incrementAndGet()
            success(storedTokens.credentials)
        }
        // if a refreshToken is available, we'll use it
        storedTokens?.refreshToken != null -> {
            val refreshToken = storedTokens.refreshToken
            logger.d { "Refreshing via refresh token" }
            refreshesBranchToken.incrementAndGet()
            runBlocking { refreshCredentials { refreshUserCredentials(refreshToken) } }
        }

        // if nothing is stored, we will try and refresh using a client secret
        authConfig.clientSecret != null -> {
            logger.d { "Refreshing via client secret" }
            refreshesBranchSecret.incrementAndGet()
            runBlocking { refreshCredentials { getClientCredentials(authConfig.clientSecret) } }
        }

        // as a last resort we return a token-less Credentials, we're logged out
        else -> {
            refreshesBranchLogout.incrementAndGet()
            logout()
        }
    }

    private suspend fun upgradeTokens(storedTokens: Tokens): AuthResult<Tokens> {
        upgrades.incrementAndGet()
        val response = retryWithPolicy(upgradeBackoffPolicy) {
            with(storedTokens) {
                tokenService.upgradeToken(
                    refreshToken = requireNotNull(this.refreshToken),
                    clientUniqueKey = requireNotNull(authConfig.clientUniqueKey),
                    clientId = authConfig.clientId,
                    clientSecret = authConfig.clientSecret,
                    scopes = authConfig.scopes.toScopesString(),
                    grantType = GRANT_TYPE_UPGRADE,
                )
            }
        }

        return response.successData?.let {
            val token = Credentials.create(
                authConfig,
                timeProvider,
                expiresIn = it.expiresIn,
                userId = it.userId.toString(),
                token = it.accessToken,
            )
            success(Tokens(token, it.refreshToken))
        } ?: failure(RetryableError("1"))
    }

    private fun logout(): AuthResult<Credentials> {
        logger.d { "Logging out" }
        return success(
            Credentials.create(
                authConfig,
                timeProvider,
                grantedScopes = setOf<String>(),
                expiresIn = 0,
                userId = null,
                token = null,
            ),
        )
    }

    private suspend fun refreshCredentials(
        apiCall: suspend () -> AuthResult<RefreshResponse>,
    ): AuthResult<Credentials> {
        return when (val result = apiCall()) {
            is AuthResult.Success -> {
                val response = result.data
                if (response != null) {
                    success(Credentials.create(authConfig, timeProvider, response))
                } else {
                    failure(null)
                }
            }

            is AuthResult.Failure -> {
                var refreshResult: AuthResult<Credentials> = failure(result.message)
                val errorCode = (result.message as? UnexpectedError)?.code
                errorCode?.let { code ->
                    if (code.toInt() <= HttpURLConnection.HTTP_UNAUTHORIZED) {
                        refreshResult = success(
                            // if code 400, 401, the user is effectively logged out
                            // and we return a lower level token
                            Credentials.createBasic(authConfig),
                        )
                    }
                }
                return refreshResult
            }
        }
    }

    private suspend fun saveTokensAndNotify(
        credentials: Credentials,
        refreshToken: String? = null,
        storedTokens: Tokens?,
    ) {
        if (credentials != storedTokens?.credentials) {
            val tokens = Tokens(
                credentials,
                refreshToken ?: storedTokens?.refreshToken,
            )
            tokensStore.saveTokens(tokens)
            logger.d { "Credentials updated and saved: $credentials" }
            bus.emit(CredentialsUpdatedMessage(tokens.credentials))
        }
    }

    private suspend fun refreshUserCredentials(refreshToken: String): AuthResult<RefreshResponse> {
        logger.d { "Refreshing user credentials, scopes: ${authConfig.scopes.toScopesString()}" }
        return retryWithPolicy(defaultBackoffPolicy) {
            tokenService.getTokenFromRefreshToken(
                authConfig.clientId,
                authConfig.clientSecret,
                refreshToken,
                GRANT_TYPE_REFRESH_TOKEN,
                authConfig.scopes.toScopesString(),
            )
        }
    }

    private suspend fun getClientCredentials(clientSecret: String): AuthResult<RefreshResponse> {
        return retryWithPolicy(defaultBackoffPolicy) {
            tokenService.getTokenFromClientSecret(
                authConfig.clientId,
                clientSecret,
                GRANT_TYPE_CLIENT_CREDENTIALS,
                authConfig.scopes.toScopesString(),
            )
        }
    }

    companion object {

        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
        private const val GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials"
        private const val GRANT_TYPE_UPGRADE = "update_client"
    }
}
