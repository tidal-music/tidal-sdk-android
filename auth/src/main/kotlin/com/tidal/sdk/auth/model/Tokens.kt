package com.tidal.sdk.auth.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Tokens(
    val credentials: Credentials,
    val refreshToken: String? = null,
)
