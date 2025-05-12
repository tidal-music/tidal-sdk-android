package com.tidal.eventproducer.fakes

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.common.NetworkError
import com.tidal.sdk.common.TidalMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCredentialsProvider(
    private val isSuccessResult: Boolean = true,
    private val refreshToken: String = "",
    private val refreshClientId: String = "",
) : CredentialsProvider {

    var accessToken = "accessToken"
    var clientId = "clientId"

    override val bus: Flow<TidalMessage>
        get() = flowOf()

    override suspend fun getCredentials(apiErrorSubStatus: String?): AuthResult<Credentials> =
        if (isSuccessResult) {
            accessToken = refreshToken
            clientId = refreshClientId
            AuthResult.Success(getDefaultCredentials(accessToken, clientId))
        } else {
            AuthResult.Failure(NetworkError("404"))
        }

    override fun isUserLoggedIn() = true

    private fun getDefaultCredentials(accessToken: String, clientId: String) =
        Credentials(
            clientId = clientId,
            requestedScopes = emptySet(),
            clientUniqueKey = "",
            grantedScopes = emptySet(),
            userId = "",
            expires = Long.MAX_VALUE,
            token = accessToken,
        )
}
