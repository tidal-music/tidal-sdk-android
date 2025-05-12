package com.tidal.sdk.player.auth

import com.tidal.sdk.auth.CredentialsProvider
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * An [Interceptor] that adds an Authorization header to each request.
 *
 * @param[credentialsProvider] A [CredentialsProvider] that provides the access token used in the
 *   Authorization header.
 */
internal class AuthorizationInterceptor(
    private val credentialsProvider: CredentialsProvider,
    private val requestAuthorizationDelegate: RequestAuthorizationDelegate,
) : Interceptor {

    @Suppress("ReturnCount")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val credentials =
            runBlocking { credentialsProvider.getCredentials().successData }
                ?: return chain.proceed(request)
        return requestAuthorizationDelegate(request, credentials)?.let { chain.proceed(it) }
            ?: Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_2)
                .message("Access token lacks appropriate level")
                .code(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("".toResponseBody("text/plain".toMediaType()))
                .build()
    }
}
