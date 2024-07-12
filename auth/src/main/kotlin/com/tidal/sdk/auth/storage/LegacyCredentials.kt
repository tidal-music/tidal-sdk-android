package com.tidal.sdk.auth.storage

import com.tidal.sdk.auth.model.Credentials
import kotlinx.serialization.Serializable

/**
 * Represents the credentials of a user or client.
 */
@Deprecated("Use [Credentials] instead.")
@Serializable
internal data class LegacyCredentials(
    val clientId: String,
    val requestedScopes: Scopes,
    val clientUniqueKey: String?,
    val grantedScopes: Scopes,
    val userId: String?,
    val expires: Long?,
    val token: String?,
) {
    fun toCredentials(): Credentials {
        return Credentials(
            clientId,
            requestedScopes.scopes,
            clientUniqueKey,
            grantedScopes.scopes,
            userId,
            expires,
            token,
        )
    }
}
