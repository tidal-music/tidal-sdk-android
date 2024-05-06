package com.tidal.sdk.common

/**
 * An error to be raised for unexpected errors. Can be used as a "catch all" error.
 *
 * @param code The error code returned by the API.
 * @param subStatus The TIDAL-specific error code returned by the API.
 */
class UnexpectedError(
    override val code: String,
    val subStatus: Int? = null,
    val throwable: Throwable? = null,
) : TidalError
