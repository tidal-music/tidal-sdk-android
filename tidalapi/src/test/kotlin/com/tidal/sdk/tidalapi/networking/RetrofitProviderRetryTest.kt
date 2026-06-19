package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.eventproducer.EventSender
import java.net.SocketTimeoutException
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import retrofit2.http.GET

class RetrofitProviderRetryTest {

    private lateinit var server: MockWebServer

    private interface TestApi {
        @GET("/ping") suspend fun ping(): Response<String>
    }

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    private fun api(
        retryPolicy: RetryPolicy? = DefaultRetryPolicy(),
        readTimeoutMillis: Long = RetrofitProvider.DEFAULT_READ_TIMEOUT_MILLIS,
        eventSender: EventSender? = null,
    ): TestApi =
        RetrofitProvider(
                retryPolicy = retryPolicy,
                readTimeoutMillis = readTimeoutMillis,
                eventSender = eventSender,
            )
            .provideRetrofit(server.url("/").toString(), FakeCredentialsProvider())
            .create(TestApi::class.java)

    @Test
    fun `emits retry telemetry when an event sender is supplied`() = runTest {
        // A single 500 then 200 (one retry, ~0.5s wall clock) proves the EventSender-backed
        // listener is wired through; payload assertions live in EventProducerRetryListenerTest.
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200).setBody("pong"))
        val eventSender = FakeEventSender()

        api(eventSender = eventSender).ping()

        assertEquals(listOf("tidalapi_retry"), eventSender.sentEvents.map { it.eventName })
    }

    @Test
    fun `emits no telemetry when no event sender is supplied`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200).setBody("pong"))

        api().ping()

        // No EventSender, so nothing observes the retry — just the normal recovery.
        assertEquals(2, server.requestCount)
    }

    @Test
    fun `retries a GET by default until success`() = runTest {
        // The default http-status backoff starts at 500ms, so a single retry costs ~0.5s wall
        // clock.
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200).setBody("pong"))

        val response = api().ping()

        assertEquals(200, response.code())
        assertEquals(2, server.requestCount)
    }

    @Test
    fun `does not retry when the retry policy is null`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200).setBody("pong"))

        val response = api(retryPolicy = null).ping()

        assertEquals(500, response.code())
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `the configured read timeout trips a SocketTimeoutException on a stalled read`() = runTest {
        // Retry disabled so the timeout surfaces immediately (no 8s timeout-category backoff): a
        // 200ms read timeout proves the configured value reaches the OkHttp client.
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        val thrown = runCatching { api(retryPolicy = null, readTimeoutMillis = 200L).ping() }

        assertInstanceOf(SocketTimeoutException::class.java, thrown.exceptionOrNull())
    }

    @Test
    fun `rejects a non-positive read timeout`() {
        assertThrows(IllegalArgumentException::class.java) {
            RetrofitProvider(readTimeoutMillis = 0)
        }
    }
}
