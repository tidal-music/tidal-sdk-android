package com.tidal.sdk.tidalapi.networking

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AuthInterceptorTest {

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
    fun `adds Bearer token header when credentials succeed`() {
        server.enqueue(MockResponse().setBody("ok"))
        val provider = FakeCredentialsProvider(token = "my-token")
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor(provider)).build()

        client.newCall(okhttp3.Request.Builder().url(server.url("/")).build()).execute()

        val recorded = server.takeRequest()
        assertEquals("Bearer my-token", recorded.getHeader("Authorization"))
    }

    @Test
    fun `proceeds without header when credentials fail`() {
        server.enqueue(MockResponse().setBody("ok"))
        val provider = FakeCredentialsProvider(shouldSucceed = false)
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor(provider)).build()

        client.newCall(okhttp3.Request.Builder().url(server.url("/")).build()).execute()

        val recorded = server.takeRequest()
        assertNull(recorded.getHeader("Authorization"))
    }
}
