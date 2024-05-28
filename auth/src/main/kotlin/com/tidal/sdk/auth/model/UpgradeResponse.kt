package com.tidal.sdk.auth.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class UpgradeResponse(
    @JsonNames("access_token")
    val accessToken: String?,
    @JsonNames("refresh_token")
    val refreshToken: String?,
    @JsonNames("token_type")
    val tokenType: String?,
    @JsonNames("expires_in")
    val expiresIn: Int? = 0,
    @JsonNames("user_id")
    val userId: Int? = null,
)
