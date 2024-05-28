package com.tidal.sdk.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class DeviceAuthorizationResponse(
    val deviceCode: String,
    val userCode: String,
    val verificationUri: String,
    val verificationUriComplete: String?,
    val expiresIn: Int,
    val interval: Int,
)
