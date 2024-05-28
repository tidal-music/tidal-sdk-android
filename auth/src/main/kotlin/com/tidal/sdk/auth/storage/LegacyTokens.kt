package com.tidal.sdk.auth.storage

import com.tidal.sdk.auth.model.Tokens
import kotlinx.serialization.Serializable

@Serializable
internal data class LegacyTokens(
    val credentials: LegacyCredentials,
    val refreshToken: String? = null,
) {
    fun toTokens(): Tokens {
        return Tokens(
            credentials.toCredentials(),
            refreshToken,
        )
    }
}
