package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.auth.network.NetworkLogLevel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoggingInterceptorTest {

    private lateinit var server: MockWebServer
    private val token = "secret-access-token"
    private val lines = mutableListOf<String>()

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    private fun executeRequest(level: NetworkLogLevel) {
        val client =
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(FakeCredentialsProvider(token = token)))
                .addInterceptor(createLoggingInterceptor(level) { lines.add(it) })
                .build()
        server.enqueue(MockResponse().setResponseCode(200).setBody("pong"))

        client.newCall(Request.Builder().url(server.url("/ping")).build()).execute().close()
    }

    @Test
    fun `logs nothing at level NONE`() {
        executeRequest(NetworkLogLevel.NONE)

        assertTrue(lines.isEmpty())
    }

    @Test
    fun `logs headers unredacted at level HEADERS`() {
        executeRequest(NetworkLogLevel.HEADERS)

        assertTrue(lines.contains("Authorization: Bearer $token"))
        assertTrue(lines.none { it.contains("pong") })
    }

    @Test
    fun `logs the body at level BODY`() {
        executeRequest(NetworkLogLevel.BODY)

        assertTrue(lines.contains("Authorization: Bearer $token"))
        assertTrue(lines.any { it.contains("pong") })
    }
}
