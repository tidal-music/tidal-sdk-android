package com.tidal.sdk.auth.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class LoginResponse(
    @JsonNames("access_token") val accessToken: String,
    val clientName: String? = null,
    @JsonNames("expires_in") val expiresIn: Int,
    @JsonNames("refresh_token") val refreshToken: String,
    @JsonNames("token_type") val tokenType: String,
    @JsonNames("scope") val scopesString: String,
    @JsonNames("user_id") val userId: Int?,
)
