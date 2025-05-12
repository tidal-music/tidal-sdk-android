package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.auth.CredentialsProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * An [Interceptor] that adds an Authorization header to each request.
 *
 * @param[credentialsProvider] A [CredentialsProvider] that provides the access token used in the
 *   Authorization header.
 */
internal class AuthInterceptor(private val credentialsProvider: CredentialsProvider) : Interceptor {

    @Suppress("ReturnCount")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val credentials =
            runBlocking { credentialsProvider.getCredentials().successData }
                ?: return chain.proceed(request)
        return chain.proceed(
            request.newBuilder().header("Authorization", "Bearer ${credentials.token}").build()
        )
    }
}
