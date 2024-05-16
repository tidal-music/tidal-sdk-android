package com.tidal.sdk.eventproducer.network

import com.tidal.sdk.eventproducer.auth.AuthProvider
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(val authProvider: AuthProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val clientId = authProvider.clientId
        val updatedRequest = chain.request().newBuilder().apply {
            addHeader(
                CONTENT_TYPE_HEADER_NAME,
                CONTENT_TYPE_HEADER,
            )
            if (!authProvider.token.isNullOrEmpty()) {
                addHeader(
                    AUTHORIZATION_HEADER_NAME,
                    "Bearer ${authProvider.token}",
                )
            } else if (!clientId.isNullOrEmpty()) {
                addHeader(
                    X_TIDAL_TOKEN_HEADER_NAME,
                    clientId,
                )
            }
        }.build()
        return chain.proceed(updatedRequest)
    }

    companion object {
        private const val CONTENT_TYPE_HEADER = "application/x-www-form-urlencoded"
        private const val CONTENT_TYPE_HEADER_NAME = "Content-type"
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
        private const val X_TIDAL_TOKEN_HEADER_NAME = "X-Tidal-Token"
    }
}
