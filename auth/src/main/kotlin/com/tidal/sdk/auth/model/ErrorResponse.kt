package com.tidal.sdk.auth.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal data class ErrorResponse(
    val status: Int,
    val error: String,
    @JsonNames("sub_status") val subStatus: Int,
    @JsonNames("error_description") val errorDescription: String,
)
