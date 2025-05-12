package com.tidal.eventproducer.auth

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.eventproducer.fakes.FakeCredentialsProvider
import com.tidal.sdk.eventproducer.auth.AUTHORIZATION_HEADER_NAME
import com.tidal.sdk.eventproducer.auth.BEARER
import com.tidal.sdk.eventproducer.auth.DefaultAuthenticator
import com.tidal.sdk.eventproducer.auth.X_TIDAL_TOKEN_HEADER_NAME
import com.tidal.sdk.eventproducer.network.HeadersInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Test

class DefaultAuthenticatorTest {

    private val server = MockWebServer()

    private val request = Request.Builder().url(server.url("/")).build()

    @Test
    fun `Should not retry request when CredentialsProvider returns failure AuthResult`() {
        // given
        val credentialsProvider = FakeCredentialsProvider(false)
        val defaultAuthenticator = DefaultAuthenticator(credentialsProvider)
        val okHttpClient = OkHttpClient.Builder().authenticator(defaultAuthenticator).build()

        val unauthorisedResponse = MockResponse().setResponseCode(401)
        server.enqueue(unauthorisedResponse)

        // when
        val response = okHttpClient.newCall(request).execute()

        // then
        assertThat(response.code).isEqualTo(401)
    }

    @Test
    fun `Should retry request with new access token when CredentialsProvider successfully got new token`() {
        // given
        val newAccessToken = "newAccessToken"
        val credentialsProvider = FakeCredentialsProvider(true, newAccessToken)
        val headersInterceptor = HeadersInterceptor(credentialsProvider)
        val defaultAuthenticator = DefaultAuthenticator(credentialsProvider)
        val okHttpClient =
            OkHttpClient.Builder()
                .authenticator(defaultAuthenticator)
                .addInterceptor(headersInterceptor)
                .build()

        val unauthorisedResponse = MockResponse().setResponseCode(401)
        val successResponse = MockResponse().setResponseCode(200)

        server.enqueue(unauthorisedResponse)
        server.enqueue(successResponse)

        // when
        val response = okHttpClient.newCall(request).execute()

        // then
        assertThat(response.code).isEqualTo(200)
        assertThat(response.request.header(AUTHORIZATION_HEADER_NAME))
            .isEqualTo("$BEARER $newAccessToken")
    }

    @Test
    fun `Should change request header to contain client id instead of the token after refresh that changed credentials level`() {
        // given
        val refreshToken = ""
        val refreshClientId = "123"
        val credentialsProvider = FakeCredentialsProvider(true, refreshToken, refreshClientId)
        val headersInterceptor = HeadersInterceptor(credentialsProvider)
        val defaultAuthenticator = DefaultAuthenticator(credentialsProvider)
        val okHttpClient =
            OkHttpClient.Builder()
                .authenticator(defaultAuthenticator)
                .addInterceptor(headersInterceptor)
                .build()

        val unauthorisedResponse = MockResponse().setResponseCode(401)
        val successResponse = MockResponse().setResponseCode(200)

        server.enqueue(unauthorisedResponse)
        server.enqueue(successResponse)

        // when
        val response = okHttpClient.newCall(request).execute()

        // then
        assertThat(response.code).isEqualTo(200)
        assertThat(response.request.header(X_TIDAL_TOKEN_HEADER_NAME)).isEqualTo(refreshClientId)
        assertThat(response.request.header(AUTHORIZATION_HEADER_NAME)).isEqualTo(null)
    }
}
