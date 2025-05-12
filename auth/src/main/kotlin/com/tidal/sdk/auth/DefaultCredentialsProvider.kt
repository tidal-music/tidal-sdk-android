package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.common.TidalMessage
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class DefaultCredentialsProvider
internal constructor(
    authBus: MutableSharedFlow<TidalMessage>,
    private val tokenRepository: TokenRepository,
) : CredentialsProvider {

    override val bus: SharedFlow<TidalMessage> = authBus.asSharedFlow()

    override suspend fun getCredentials(apiErrorSubStatus: String?): AuthResult<Credentials> {
        return tokenRepository.getCredentials(apiErrorSubStatus).also {
            logger.d { "getCredentials called, apiErrorSubStatus: $apiErrorSubStatus, result $it" }
        }
    }

    override fun isUserLoggedIn() = tokenRepository.getLatestTokens()?.credentials?.userId != null
}
