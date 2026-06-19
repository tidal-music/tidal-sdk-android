package com.tidal.sdk.tidalapi.networking

import java.io.IOException
import java.util.concurrent.TimeUnit
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TidalApiRetryInterceptorTest {

    private lateinit var server: MockWebServer
    private val recordedDelays = mutableListOf<Long>()
    private val policy = DefaultRetryPolicy()
    private var listener = FakeRetryListener()

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
        recordedDelays.clear()
        listener = FakeRetryListener()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    private fun client(
        retryPolicy: RetryPolicy = policy,
        random: () -> Double = { 1.0 },
        sleep: (Long) -> Unit = recordedDelays::add,
        retryListener: TidalApiRetryListener = listener,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(200, TimeUnit.MILLISECONDS)
            .addInterceptor(TidalApiRetryInterceptor(retryPolicy, random, sleep, retryListener))
            .build()

    private fun get() = Request.Builder().url(server.url("/")).build()

    private fun head() = Request.Builder().url(server.url("/")).head().build()

    private fun post() = Request.Builder().url(server.url("/")).post("body".toRequestBody()).build()

    private fun patch() =
        Request.Builder().url(server.url("/")).patch("body".toRequestBody()).build()

    private fun delete() = Request.Builder().url(server.url("/")).delete().build()

    private fun keyedPost() =
        Request.Builder()
            .url(server.url("/"))
            .header("Idempotency-Key", "k")
            .post("body".toRequestBody())
            .build()

    private fun keyedPatch() =
        Request.Builder()
            .url(server.url("/"))
            .header("Idempotency-Key", "k")
            .patch("body".toRequestBody())
            .build()

    private fun keyedDelete() =
        Request.Builder().url(server.url("/")).header("Idempotency-Key", "k").delete().build()

    @Test
    fun `http-status category retries up to 3 then surfaces the last 5xx`() {
        repeat(4) { server.enqueue(MockResponse().setResponseCode(500)) }

        val response = client().newCall(get()).execute()

        assertEquals(500, response.code)
        assertEquals(4, server.requestCount)
    }

    @Test
    fun `http-status category retries 503 until success`() {
        server.enqueue(MockResponse().setResponseCode(503))
        server.enqueue(MockResponse().setResponseCode(503))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(get()).execute()

        assertEquals(200, response.code)
        assertEquals(3, server.requestCount)
    }

    @Test
    fun `http-status category retries 429 until success`() {
        server.enqueue(MockResponse().setResponseCode(429))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(get()).execute()

        assertEquals(200, response.code)
        assertEquals(2, server.requestCount)
    }

    @Test
    fun `http-status backoff follows the capped curve at the top of the jitter band`() {
        repeat(4) { server.enqueue(MockResponse().setResponseCode(500)) }

        client().newCall(get()).execute()

        // base 500ms, cap 16s: 500, 1000, 2000 (× jitter 1.0).
        assertEquals(listOf(500L, 1000L, 2000L), recordedDelays)
    }

    @Test
    fun `network category retries up to 10 on a non-timeout IOException`() {
        repeat(11) {
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
        }

        assertThrows(IOException::class.java) { client().newCall(get()).execute() }
        assertEquals(11, server.requestCount)
    }

    @Test
    fun `network category recovers when a success arrives before exhaustion`() {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(get()).execute()

        assertEquals(200, response.code)
        assertEquals(2, server.requestCount)
    }

    @Test
    fun `network backoff grows toward the 16s cap`() {
        repeat(11) {
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
        }

        assertThrows(IOException::class.java) { client().newCall(get()).execute() }

        // base 1s, cap 16s: 1000, 2000, 4000, 8000, 16000, then clamped at 16000 (× jitter 1.0).
        assertEquals(
            listOf(
                1000L,
                2000L,
                4000L,
                8000L,
                16_000L,
                16_000L,
                16_000L,
                16_000L,
                16_000L,
                16_000L,
            ),
            recordedDelays,
        )
    }

    @Test
    fun `timeout category retries up to 3 on a SocketTimeoutException`() {
        repeat(4) { server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE)) }

        assertThrows(IOException::class.java) { client().newCall(get()).execute() }
        assertEquals(4, server.requestCount)
        // base 8s, cap 32s: 8000, 16000, 32000 (× jitter 1.0).
        assertEquals(listOf(8_000L, 16_000L, 32_000L), recordedDelays)
    }

    @Test
    fun `independent per-category counts let a network error retry after timeouts`() {
        repeat(3) { server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE)) }
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(get()).execute()

        assertEquals(200, response.code)
        // 3 timeouts (exhausting timeout N=3) + 1 network failure + success: timeout budget spent
        // does not block the still-available network budget.
        assertEquals(5, server.requestCount)
    }

    @Test
    fun `HEAD is retried`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(head()).execute()

        assertEquals(200, response.code)
        assertEquals(2, server.requestCount)
    }

    @Test
    fun `POST is not retried`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(post()).execute()

        assertEquals(500, response.code)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `PATCH is not retried`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(patch()).execute()

        assertEquals(500, response.code)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `DELETE is not retried`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(delete()).execute()

        assertEquals(500, response.code)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `keyed POST is retried on 5xx`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(keyedPost()).execute()

        assertEquals(200, response.code)
        assertEquals(2, server.requestCount)
    }

    @Test
    fun `keyed DELETE is retried on 5xx`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(keyedDelete()).execute()

        assertEquals(200, response.code)
        assertEquals(2, server.requestCount)
    }

    @Test
    fun `keyed PATCH retries up to 3 on a SocketTimeoutException`() {
        repeat(4) { server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE)) }

        assertThrows(IOException::class.java) { client().newCall(keyedPatch()).execute() }
        assertEquals(4, server.requestCount)
        // base 8s, cap 32s: 8000, 16000, 32000 (× jitter 1.0).
        assertEquals(listOf(8_000L, 16_000L, 32_000L), recordedDelays)
    }

    @Test
    fun `keyed PATCH retries up to 10 on a network IOException`() {
        repeat(11) {
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
        }

        assertThrows(IOException::class.java) { client().newCall(keyedPatch()).execute() }
        assertEquals(11, server.requestCount)
    }

    @Test
    fun `401 is not retried`() {
        server.enqueue(MockResponse().setResponseCode(401))

        val response = client().newCall(get()).execute()

        assertEquals(401, response.code)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `non-429 4xx is not retried`() {
        server.enqueue(MockResponse().setResponseCode(404))

        val response = client().newCall(get()).execute()

        assertEquals(404, response.code)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `a successful response is not retried`() {
        server.enqueue(MockResponse().setResponseCode(200))

        val response = client().newCall(get()).execute()

        assertEquals(200, response.code)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `an interrupted backoff stops retrying, restores the interrupt flag and surfaces an IOException`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))
        val interrupting = client(sleep = { throw InterruptedException("interrupted") })

        assertThrows(IOException::class.java) { interrupting.newCall(get()).execute() }
        assertEquals(1, server.requestCount)
        assertTrue(Thread.interrupted())
    }

    @Test
    fun `a call cancelled during backoff stops retrying and surfaces an IOException`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))
        lateinit var call: Call
        val cancelling = client(sleep = { call.cancel() })
        call = cancelling.newCall(get())

        assertThrows(IOException::class.java) { call.execute() }
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `onRetry fires once per retry with ascending attempt and the applied delay`() {
        repeat(4) { server.enqueue(MockResponse().setResponseCode(500)) }

        client().newCall(get()).execute()

        // base 500ms, cap 16s: 500, 1000, 2000 (× jitter 1.0) — the same values sleep recorded.
        assertEquals(recordedDelays, listener.retries.map { it.delayMillis })
        assertEquals(
            listOf(
                FakeRetryListener.Retry(ErrorCategory.HTTP_STATUS, 0, 500L),
                FakeRetryListener.Retry(ErrorCategory.HTTP_STATUS, 1, 1000L),
                FakeRetryListener.Retry(ErrorCategory.HTTP_STATUS, 2, 2000L),
            ),
            listener.retries,
        )
    }

    @Test
    fun `onRetriesExhausted fires once on the response path when the http-status budget is spent`() {
        repeat(4) { server.enqueue(MockResponse().setResponseCode(500)) }

        client().newCall(get()).execute()

        assertEquals(
            listOf(FakeRetryListener.Exhausted(ErrorCategory.HTTP_STATUS, 3)),
            listener.exhaustions,
        )
    }

    @Test
    fun `onRetriesExhausted fires once on the exception path when the network budget is spent`() {
        repeat(11) {
            server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
        }

        assertThrows(IOException::class.java) { client().newCall(get()).execute() }

        assertEquals(
            listOf(FakeRetryListener.Exhausted(ErrorCategory.NETWORK, 10)),
            listener.exhaustions,
        )
    }

    @Test
    fun `onRetriesExhausted fires once on the exception path when the timeout budget is spent`() {
        repeat(4) { server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE)) }

        assertThrows(IOException::class.java) { client().newCall(get()).execute() }

        assertEquals(
            listOf(FakeRetryListener.Exhausted(ErrorCategory.TIMEOUT, 3)),
            listener.exhaustions,
        )
    }

    @Test
    fun `a successful response reports nothing`() {
        server.enqueue(MockResponse().setResponseCode(200))

        client().newCall(get()).execute()

        assertTrue(listener.retries.isEmpty())
        assertTrue(listener.exhaustions.isEmpty())
    }

    @Test
    fun `a non-429 4xx reports nothing`() {
        server.enqueue(MockResponse().setResponseCode(404))

        client().newCall(get()).execute()

        assertTrue(listener.retries.isEmpty())
        assertTrue(listener.exhaustions.isEmpty())
    }

    @Test
    fun `a non-retried mutation reports nothing`() {
        server.enqueue(MockResponse().setResponseCode(500))

        client().newCall(post()).execute()

        assertTrue(listener.retries.isEmpty())
        assertTrue(listener.exhaustions.isEmpty())
    }

    @Test
    fun `an interrupted backoff reports nothing`() {
        server.enqueue(MockResponse().setResponseCode(500))
        val interrupting = client(sleep = { throw InterruptedException("interrupted") })

        assertThrows(IOException::class.java) { interrupting.newCall(get()).execute() }
        assertTrue(listener.exhaustions.isEmpty())
        Thread.interrupted()
    }

    @Test
    fun `a call cancelled before backoff reports nothing`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200))
        lateinit var call: Call
        val cancelling = client(sleep = { call.cancel() })
        call = cancelling.newCall(get())

        assertThrows(IOException::class.java) { call.execute() }
        assertTrue(listener.exhaustions.isEmpty())
    }
}
