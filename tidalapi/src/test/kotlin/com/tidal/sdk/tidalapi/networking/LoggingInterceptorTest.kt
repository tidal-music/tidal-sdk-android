package com.tidal.sdk.tidalapi.networking

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoggingInterceptorTest {

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
    fun `logs the request without the bearer token`() {
        val token = "secret-access-token"
        val lines = mutableListOf<String>()
        val client =
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(FakeCredentialsProvider(token = token)))
                .addInterceptor(createLoggingInterceptor { lines.add(it) })
                .build()
        server.enqueue(MockResponse().setResponseCode(200).setBody("pong"))

        client.newCall(Request.Builder().url(server.url("/ping")).build()).execute().close()

        assertEquals("Bearer $token", server.takeRequest().getHeader("Authorization"))
        assertFalse(lines.any { it.contains(token) })
        assertTrue(lines.any { it.startsWith("Authorization: ") })
        assertTrue(lines.any { it.contains("pong") })
    }
}
