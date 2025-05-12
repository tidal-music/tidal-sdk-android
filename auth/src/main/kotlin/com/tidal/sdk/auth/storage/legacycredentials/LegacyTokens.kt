package com.tidal.sdk.auth.storage.legacycredentials

import com.tidal.sdk.auth.model.Tokens
import kotlinx.serialization.Serializable

internal sealed class LegacyTokens {
    abstract fun toTokens(): Tokens
}

@Serializable
internal data class TokensV1(
    val credentials: LegacyCredentialsV1,
    val refreshToken: String? = null,
) : LegacyTokens() {
    override fun toTokens(): Tokens = Tokens(credentials.toCredentials(), refreshToken)
}

@Serializable
internal data class TokensV2(
    val credentials: LegacyCredentialsV2,
    val refreshToken: String? = null,
) : LegacyTokens() {
    override fun toTokens(): Tokens = Tokens(credentials.toCredentials(), refreshToken)
}
