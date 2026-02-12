package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.auth.model.AuthResult
import com.tidal.sdk.auth.model.Credentials
import com.tidal.sdk.common.TidalMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FakeCredentialsProvider(
    var token: String = "fake-token",
    private var shouldSucceed: Boolean = true,
) : CredentialsProvider {

    override val bus: Flow<TidalMessage> = emptyFlow()

    override suspend fun getCredentials(apiErrorSubStatus: String?): AuthResult<Credentials> =
        if (shouldSucceed) {
            AuthResult.Success(
                Credentials(
                    clientId = "test-client",
                    requestedScopes = emptySet(),
                    clientUniqueKey = null,
                    grantedScopes = emptySet(),
                    userId = null,
                    expires = Long.MAX_VALUE,
                    token = token,
                ),
            )
        } else {
            AuthResult.Failure(null)
        }

    override fun isUserLoggedIn() = true
}
