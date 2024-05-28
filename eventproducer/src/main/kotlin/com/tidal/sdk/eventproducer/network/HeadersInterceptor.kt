package com.tidal.sdk.eventproducer.network

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.auth.updateAuthHeader
import okhttp3.Interceptor
import okhttp3.Response

internal class HeadersInterceptor(
    private val credentialsProvider: CredentialsProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val updatedRequest = chain.request().newBuilder().apply {
            addHeader(
                CONTENT_TYPE_HEADER_NAME,
                CONTENT_TYPE_HEADER,
            )
            updateAuthHeader(credentialsProvider)
        }.build()
        return chain.proceed(updatedRequest)
    }

    companion object {
        private const val CONTENT_TYPE_HEADER = "application/x-www-form-urlencoded"
        private const val CONTENT_TYPE_HEADER_NAME = "Content-type"
    }
}
