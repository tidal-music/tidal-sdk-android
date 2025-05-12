package com.tidal.sdk.player.auth

import com.tidal.sdk.auth.model.Credentials
import okhttp3.Request

const val HEADER_AUTHORIZATION = "Authorization"

internal class RequestAuthorizationDelegate(
    private val endpointsRequiringCredentialLevels: Map<String, Set<Credentials.Level>>
) {
    operator fun invoke(request: Request, credentials: Credentials): Request? {
        val isLevelValid =
            endpointsRequiringCredentialLevels[request.url.toString()]?.contains(credentials.level)
                ?: true
        if (!isLevelValid) {
            return null
        }
        return request
            .newBuilder()
            .header(HEADER_AUTHORIZATION, "${TokenType.BEARER.type} ${credentials.token}")
            .build()
    }
}
