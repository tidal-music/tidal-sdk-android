package com.tidal.sdk.auth.storage.legacycredentials

import com.tidal.sdk.auth.model.Credentials
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
sealed class LegacyCredentials {
    abstract fun toCredentials(): Credentials
}

/**
 * Represents the credentials of a user or client.
 */
@Deprecated("Use [Credentials] instead.")
@Serializable
data class LegacyCredentialsV1(
    val clientId: String,
    val requestedScopes: Scopes,
    val clientUniqueKey: String?,
    val grantedScopes: Scopes,
    val userId: String?,
    val expires: Instant?,
    val token: String?,
) : LegacyCredentials() {
    override fun toCredentials(): Credentials = Credentials(
        clientId,
        requestedScopes.scopes,
        clientUniqueKey,
        grantedScopes.scopes,
        userId,
        expires?.epochSeconds,
        token,
    )
}

/**
 * Represents the credentials of a user or client.
 */
@Deprecated("Use [Credentials] instead.")
@Serializable
data class LegacyCredentialsV2(
    val clientId: String,
    val requestedScopes: Set<String>,
    val clientUniqueKey: String?,
    val grantedScopes: Set<String>,
    val userId: String?,
    val expires: Instant?,
    val token: String?,
) : LegacyCredentials() {
    override fun toCredentials(): Credentials = Credentials(
        clientId,
        requestedScopes,
        clientUniqueKey,
        grantedScopes,
        userId,
        expires?.epochSeconds,
        token,
    )
}
