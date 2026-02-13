package com.tidal.sdk.tidalapi.networking

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultAuthenticatorTest {

    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `retries with fresh token on 401`() {
        val provider = FakeCredentialsProvider(token = "refreshed-token")
        val client = OkHttpClient.Builder().authenticator(DefaultAuthenticator(provider)).build()

        // First response is 401, second succeeds
        server.enqueue(MockResponse().setResponseCode(401))
        server.enqueue(MockResponse().setBody("ok"))

        client.newCall(okhttp3.Request.Builder().url(server.url("/")).build()).execute()

        // First request has no auth header
        server.takeRequest()
        // Retry should have the refreshed token
        val retry = server.takeRequest()
        assertEquals("Bearer refreshed-token", retry.getHeader("Authorization"))
    }

    @Test
    fun `gives up when credentials fail`() {
        val provider = FakeCredentialsProvider(shouldSucceed = false)
        val client = OkHttpClient.Builder().authenticator(DefaultAuthenticator(provider)).build()

        server.enqueue(MockResponse().setResponseCode(401))

        val response =
            client.newCall(okhttp3.Request.Builder().url(server.url("/")).build()).execute()

        // Should return the 401 since authenticator returned null
        assertEquals(401, response.code)
        assertEquals(1, server.requestCount)
    }
}
