package com.tidal.sdk.auth

import com.tidal.sdk.auth.model.RefreshResponse
import com.tidal.sdk.auth.model.UpgradeResponse
import com.tidal.sdk.auth.token.TokenService
import kotlinx.coroutines.delay

internal class FakeTokenService : TokenService {

    var calls = mutableListOf<CallType>()
    var throwableToThrow: Throwable? = null

    override suspend fun getTokenFromRefreshToken(
        clientId: String,
        clientSecret: String?,
        refreshToken: String,
        grantType: String,
        scope: String,
    ): RefreshResponse {
        calls.add(CallType.Refresh)
        delay(10)
        throwableToThrow?.let {
            throw it
        } ?: run {
            return RefreshResponse(
                "credentials",
                "clientName",
                5000,
                "tokenType",
                "",
                999,

            )
        }
    }

    override suspend fun getTokenFromClientSecret(
        clientId: String,
        clientSecret: String,
        grantType: String,
        scope: String,
    ): RefreshResponse {
        calls.add(CallType.Secret)
        delay(10)
        throwableToThrow?.let {
            throw it
        } ?: run {
            return RefreshResponse(
                "credentials",
                "clientName",
                5000,
                "tokenType",
                "",
                999,
            )
        }
    }

    override suspend fun upgradeToken(
        refreshToken: String,
        clientUniqueKey: String?,
        clientId: String,
        clientSecret: String?,
        scopes: String,
        grantType: String,
    ): UpgradeResponse {
        calls.add(CallType.Upgrade)
        delay(10)
        throwableToThrow?.let {
            throw it
        } ?: run {
            return UpgradeResponse(
                accessToken = "upgradeCredentials",
                refreshToken = "upgradeRefreshToken",
                tokenType = "Bearer",
                expiresIn = 5000,
            )
        }
    }

    enum class CallType { Refresh, Secret, Upgrade }
}
