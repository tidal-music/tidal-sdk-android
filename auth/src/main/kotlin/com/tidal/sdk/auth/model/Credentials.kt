package com.tidal.sdk.auth.model

import com.tidal.sdk.auth.model.Credentials.Level.BASIC
import com.tidal.sdk.auth.model.Credentials.Level.CLIENT
import com.tidal.sdk.auth.model.Credentials.Level.USER
import com.tidal.sdk.auth.util.TimeProvider
import kotlinx.serialization.Serializable

/**
 * Represents the credentials of a user or client.
 *
 * @property clientId The client ID. This identifies the type of client.
 * @property requestedScopes The requested scopes.
 * @property clientUniqueKey The client unique key.
 * @property grantedScopes The granted scopes.
 * @property userId The user ID. the unique Id attributed to one user.
 * @property expires The expiration time of the token. This is a Unix timestamp in seconds since
 *   Epoch.
 * @property token The token. This is the OAuth2 token to authenticate API calls with.
 */
@Serializable
data class Credentials(
    val clientId: String,
    val requestedScopes: Set<String>,
    val clientUniqueKey: String?,
    val grantedScopes: Set<String>,
    val userId: String?,
    val expires: Long?,
    val token: String?,
) {

    /**
     * The level of the credentials.
     *
     * @see [Level]
     */
    val level: Level
        get() {
            return when {
                !userId.isNullOrBlank() && !token.isNullOrBlank() -> Level.USER
                userId.isNullOrBlank() && !token.isNullOrBlank() -> Level.CLIENT
                else -> Level.BASIC
            }
        }

    /** Checks if the token is expired. */
    internal fun isExpired(timeProvider: TimeProvider): Boolean {
        val now = timeProvider.now
        val validUntil = expires
        val isExpired = validUntil?.let { now > validUntil - EXPIRY_LIMIT_SECONDS } ?: true
        return isExpired
    }

    /**
     * Represents the level of the credentials.
     *
     * @property BASIC credentials represent valid, but unauthorized credentials. They contain
     *   neither a user ID nor a token.
     * @property USER credentials represent valid, authorized credentials without a user ID. They
     *   will grant access to certain features that don't require an user account.
     * @property CLIENT credentials represent valid, authorized credentials for a specific user.
     *   They contain a token and user ID, and will grant full access to the TIDAL API.
     */
    enum class Level {
        BASIC,
        CLIENT,
        USER,
    }

    companion object {
        const val EXPIRY_LIMIT_SECONDS = 60

        internal fun createBasic(authConfig: AuthConfig): Credentials =
            Credentials(
                clientId = authConfig.clientId,
                requestedScopes = authConfig.scopes,
                clientUniqueKey = authConfig.clientUniqueKey,
                grantedScopes = setOf(),
                userId = null,
                expires = null,
                token = null,
            )

        internal fun create(
            authConfig: AuthConfig,
            timeProvider: TimeProvider,
            response: RefreshResponse,
        ) =
            create(
                authConfig = authConfig,
                timeProvider = timeProvider,
                grantedScopes = response.scopesString.split(", ").toSet(),
                userId = response.userId?.toString(),
                expiresIn = response.expiresIn,
                token = response.accessToken,
            )

        @Suppress("LongParameterList")
        internal fun create(
            authConfig: AuthConfig,
            timeProvider: TimeProvider,
            grantedScopes: Set<String>? = null,
            userId: String?,
            token: String?,
            expiresIn: Long,
        ) =
            Credentials(
                clientId = authConfig.clientId,
                requestedScopes = authConfig.scopes,
                clientUniqueKey = authConfig.clientUniqueKey,
                grantedScopes = grantedScopes ?: setOf(),
                userId = userId,
                expires = timeProvider.now + expiresIn,
                token = token,
            )
    }
}
