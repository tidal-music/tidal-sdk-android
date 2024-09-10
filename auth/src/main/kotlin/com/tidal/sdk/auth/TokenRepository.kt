package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.AuthConfig
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.auth.model.CredentialsUpdatedMessage
import com.tidal.sdk.auth.model.RefreshResponse
import com.tidal.sdk.auth.model.Tokens
import com.tidal.sdk.auth.model.failure
import com.tidal.sdk.auth.model.success
import com.tidal.sdk.auth.network.NetworkingJobHandler
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
import java.net.HttpURLConnection
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class TokenRepository(
    private val authConfig: AuthConfig,
    private val timeProvider: TimeProvider,
    private val tokensStore: TokensStore,
    private val tokenService: TokenService,
    private val defaultBackoffPolicy: RetryPolicy,
    private val upgradeBackoffPolicy: RetryPolicy,
    private val bus: MutableSharedFlow<TidalMessage>,
    private val networkingJobHandler: NetworkingJobHandler,
) {

    /**
     * Mutex to ensure that only one thread at a time can update/upgrade the token.
     */
    private val tokenMutex = Mutex()

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

    internal fun getLatestTokens(): Tokens? = tokensStore.getLatestTokens(authConfig.credentialsKey)

    @Suppress("UnusedPrivateMember")
    suspend fun getCredentials(apiErrorSubStatus: String?): AuthResult<Credentials> {
        logger.d { "Received subStatus: $apiErrorSubStatus" }

        lateinit var result: AuthResult<Credentials>
        val job = networkingJobHandler.scope.launch {
            try {
                tokenMutex.withLock {
                    logger.d { "getCredentials: ${Thread.currentThread().name}" }
                    var upgradedRefreshToken: String? = null
                    val credentials = getLatestTokens()

                    result = if (credentials != null && needsCredentialsUpgrade()) {
                        logger.d { "Upgrading credentials" }
                        val upgradeCredentials = upgradeTokens(credentials)
                        upgradeCredentials.successData?.let {
                            upgradedRefreshToken = it.refreshToken
                            success(it.credentials)
                        } ?: upgradeCredentials as AuthResult.Failure
                    } else {
                        logger.d { "Updating credentials" }
                        updateCredentials(credentials, apiErrorSubStatus)
                    }.also {
                        it.successData?.let { token ->
                            saveTokensAndNotify(token, upgradedRefreshToken, credentials)
                        }
                    }
                }
            } catch (e: CancellationException) {
                logger.d { "getCredentials execution was cancelled: ${e.message}" }
                result = failure(null)
            }
        }.also { networkingJobHandler.jobs.add(it) }
        job.join()
        return result
    }

    private suspend fun updateCredentials(
        storedTokens: Tokens?,
        apiErrorSubStatus: String?,
    ): AuthResult<Credentials> = when {
        storedTokens?.credentials?.isExpired(timeProvider) == false &&
            apiErrorSubStatus.shouldRefreshToken().not() -> {
            success(storedTokens.credentials)
        }
        // if a refreshToken is available, we'll use it
        storedTokens?.refreshToken != null -> {
            val refreshToken = storedTokens.refreshToken
            refreshCredentials { refreshUserCredentials(refreshToken) }
        }

        // if nothing is stored, we will try and refresh using a client secret
        authConfig.clientSecret != null -> {
            refreshCredentials { getClientCredentials(authConfig.clientSecret) }
        }

        // as a last resort we return a token-less Credentials, we're logged out
        else -> logout()
    }

    private suspend fun upgradeTokens(storedTokens: Tokens): AuthResult<Tokens> {
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
                expiresIn = it.expiresIn ?: 0,
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

    private suspend fun getClientCredentials(clientSecret: String): AuthResult<RefreshResponse> =
        retryWithPolicy(
            defaultBackoffPolicy,
        ) {
            tokenService.getTokenFromClientSecret(
                authConfig.clientId,
                clientSecret,
                GRANT_TYPE_CLIENT_CREDENTIALS,
                authConfig.scopes.toScopesString(),
            )
        }

    companion object {

        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
        private const val GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials"
        private const val GRANT_TYPE_UPGRADE = "update_client"
    }
}
