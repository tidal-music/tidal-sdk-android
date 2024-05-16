package com.tidal.eventproducer.auth

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.eventproducer.auth.AuthProvider
import com.tidal.sdk.eventproducer.auth.DefaultAuthenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultAuthenticatorTest {

    private val server = MockWebServer()

    private var accessToken = ""

    private val request = Request.Builder()
        .url(server.url("/"))
        .header("Authorization", "Bearer accessToken")
        .build()

    @BeforeEach
    fun setUp() {
        accessToken = "accessToken"
    }

    @Test
    fun `Should not retry request when AuthProvider returns false after refresh token attempt`() {
        // given
        val authProvider = object : AuthProvider {
            override val token
                get() = accessToken
            override val clientId = ""

            override suspend fun handleAuthError(response: Response) = false
        }

        val defaultAuthenticator = DefaultAuthenticator(authProvider)
        val okHttpClient = OkHttpClient.Builder()
            .authenticator(defaultAuthenticator)
            .build()

        val unauthorisedResponse = MockResponse().setResponseCode(401)
        server.enqueue(unauthorisedResponse)

        // when
        val response = okHttpClient.newCall(request).execute()

        // then
        assertThat(response.code).isEqualTo(401)
    }

    @Test
    fun `Should retry request with new access token when AuthProvider successfully refreshed token`() {
        // given
        val authProvider = object : AuthProvider {
            override val token
                get() = accessToken
            override val clientId = ""

            override suspend fun handleAuthError(response: Response): Boolean {
                accessToken = "newAccessToken"
                return true
            }
        }

        val defaultAuthenticator = DefaultAuthenticator(authProvider)
        val okHttpClient = OkHttpClient.Builder()
            .authenticator(defaultAuthenticator)
            .build()

        val unauthorisedResponse = MockResponse().setResponseCode(401)
        val successResponse = MockResponse().setResponseCode(200)

        server.enqueue(unauthorisedResponse)
        server.enqueue(successResponse)

        // when
        val response = okHttpClient.newCall(request).execute()

        // then
        assertThat(response.code).isEqualTo(200)
        assertThat(response.request.header("Authorization"))
            .isEqualTo("Bearer newAccessToken")
    }
}
