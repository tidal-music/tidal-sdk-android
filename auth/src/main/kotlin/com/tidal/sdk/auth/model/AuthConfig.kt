package com.tidal.sdk.auth.model

import com.tidal.sdk.auth.TidalAuth
import com.tidal.sdk.auth.network.NetworkLogLevel

/**
 * Configuration object for the Auth module.
 * @param clientId The client ID of the application.
 * @param clientUniqueKey The unique key of the application.
 * @param clientSecret The client secret of the application.
 * @param credentialsKey The key used to encrypt and store store credentials on the device.
 * @param scopes The scopes that the application requests.
 * @param tidalLoginServiceBaseUrl The base URL of the TIDAL login service. Only pass in a value if
 * you want to override the default value.
 * @param tidalAuthServiceBaseUrl The base URL of the TIDAL auth service. Only pass in a value if
 * you want to override the default value.
 * @param enableCertificatePinning Whether certificate pinning is enabled.
 * @param logLevel The [NetworkLogLevel] for the network layer. Default is [NetworkLogLevel.NONE],
 * which means no logging.
 */
data class AuthConfig(
    val clientId: String,
    val clientUniqueKey: String? = null,
    val clientSecret: String? = null,
    val credentialsKey: String,
    val scopes: Set<String> = setOf(),
    val tidalLoginServiceBaseUrl: String = TidalAuth.LOGIN_SERVICE_BASE_URL,
    val tidalAuthServiceBaseUrl: String = TidalAuth.AUTH_SERVICE_BASE_URL,
    val enableCertificatePinning: Boolean = true,
    val logLevel: NetworkLogLevel = NetworkLogLevel.NONE,
)
