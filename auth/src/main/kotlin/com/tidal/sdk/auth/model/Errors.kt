package com.tidal.sdk.auth.model

import com.tidal.sdk.common.TidalError

/**
 * Error indicating that the user did not correctly authenticate themselves at the TIDAL login
 * service. Raised when the URI made to the redirect URI indicates that the authentication was not
 * succesful.
 *
 * @param code The error code returned by the API.
 * @param subStatus The TIDAL-specific error code returned by the API.
 */
class AuthorizationError(
    override val code: String,
    val subStatus: Int? = null,
    val throwable: Throwable? = null,
) : TidalError

/**
 * Error used to indicate that an access token could not be retrieved.
 *
 * @param code The error code returned by the API.
 * @param subStatus The TIDAL-specific error code returned by the API.
 */
class TokenResponseError(
    override val code: String,
    val subStatus: Int? = null,
    val throwable: Throwable? = null,
) : TidalError
