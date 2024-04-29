package com.tidal.sdk.auth.model

import java.util.Locale

/**
 * Configuration parameters for the TIDAL login service.
 * @param locale (ISO 639-1 e.g. en/de/it) is used to set the language for the TIDAL login service.
 * If left blank the browser language will be used.
 * @param email Optional email address to be pre-filled on the login screen.
 * @param customParams Key value map used to add custom parameters to be passed through to the
 * TIDAL login service. If set to null, a default configuration of TIDAL login service will be
 * applied (@see [QueryParameter].
 */
data class LoginConfig(
    val locale: Locale? = Locale.getDefault(),
    val email: String? = null,
    val customParams: Set<QueryParameter> = setOf(),
)
