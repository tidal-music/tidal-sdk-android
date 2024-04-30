package com.tidal.sdk.auth.model

import com.tidal.sdk.auth.util.TimeProvider
import com.tidal.sdk.common.d
import com.tidal.sdk.common.logger
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Represents the credentials of a user or client.
 */
@Serializable
data class Credentials(
    val clientId: String,
    val requestedScopes: Set<String>,
    val clientUniqueKey: String?,
    val grantedScopes: Set<String>,
    val userId: String?,
    val expires: Instant?,
    val token: String?,
) {

    /**
     * The level of the credentials.
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

    /**
     * Checks if the token is expired.
     */
    internal fun isExpired(timeProvider: TimeProvider): Boolean {
        val now = timeProvider.now.epochSeconds
        val validUntil = expires?.epochSeconds ?: null
        logger.d {
            "Checking if token is expired: Now: $now, valid until: $validUntil, " +
                "limit: $EXPIRY_LIMIT_SECONDS"
        }
        val isExpired = validUntil?.let {
            now > validUntil - EXPIRY_LIMIT_SECONDS
        } ?: true
        return isExpired.also {
            logger.d { "Token is expired: $it" }
        }
    }

    /**
     * Represents the level of the credentials.
     * @property BASIC credentials represent valid, but unauthorized credentials. They contain
     * neither a user ID nor a token.
     * @property USER credentials represent valid, authorized credentials without a user ID.
     * They will grant access to certain features that don't require an user account.
     * @property CLIENT credentials represent valid, authorized credentials for a specific user.
     * They contain a token and user ID, and will grant full access to the TIDAL API.
     */
    enum class Level {
        BASIC,
        CLIENT,
        USER,
    }

    companion object {
        const val EXPIRY_LIMIT_SECONDS = 60

        internal fun createBasic(
            authConfig: AuthConfig,
        ): Credentials {
            return Credentials(
                clientId = authConfig.clientId,
                requestedScopes = authConfig.scopes,
                clientUniqueKey = authConfig.clientUniqueKey,
                grantedScopes = setOf(),
                userId = null,
                expires = null,
                token = null,
            )
        }

        internal fun create(
            authConfig: AuthConfig,
            timeProvider: TimeProvider,
            response: RefreshResponse,
        ) = create(
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
            expiresIn: Int?,
        ) = Credentials(
            clientId = authConfig.clientId,
            requestedScopes = authConfig.scopes,
            clientUniqueKey = authConfig.clientUniqueKey,
            grantedScopes = grantedScopes ?: setOf(),
            userId = userId,
            expires = expiresIn?.let {
                Instant.fromEpochSeconds(timeProvider.now.epochSeconds + it.toLong())
            },
            token = token,
        )
    }
}
