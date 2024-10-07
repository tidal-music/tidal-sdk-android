package com.tidal.sdk.eventproducer.auth

import com.tidal.sdk.auth.CredentialsProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Request

internal const val AUTHORIZATION_HEADER_NAME = "Authorization"
internal const val X_TIDAL_TOKEN_HEADER_NAME = "X-Tidal-Token"
internal const val BEARER = "Bearer"

internal fun Request.Builder.updateAuthHeader(
    credentialsProvider: CredentialsProvider,
): Request.Builder {
    val credentials = runBlocking { credentialsProvider.getCredentials("6001").successData }
    val clientId = credentials?.clientId
    val token = credentials?.token
    return apply {
        if (!token.isNullOrEmpty()) {
            header(AUTHORIZATION_HEADER_NAME, "$BEARER $token")
        } else if (!clientId.isNullOrEmpty()) {
            removeHeader(AUTHORIZATION_HEADER_NAME)
            header(X_TIDAL_TOKEN_HEADER_NAME, clientId)
        }
    }
}
