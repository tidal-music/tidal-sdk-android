package com.tidal.sdk.common

/**
 * Raised whenever an operation failed due to an incorrect configuration.
 *
 * @param code The error code returned by the API.
 * @param subStatus The TIDAL-specific error code returned by the API.
 */
class IllegalConfigurationError(
    override val code: String,
    val subStatus: Int? = null,
    val throwable: Throwable? = null,
) : TidalError
