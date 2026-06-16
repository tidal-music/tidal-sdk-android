package com.tidal.sdk.tidalapi.networking

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class RetryClassifierTest {

    private fun response(code: Int): Response =
        Response.Builder()
            .request(Request.Builder().url("https://example.com/").build())
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message("")
            .build()

    @Test
    fun `SocketTimeoutException is a timeout`() {
        assertEquals(ErrorCategory.TIMEOUT, SocketTimeoutException().toRetryCategory())
    }

    @Test
    fun `other IOExceptions are network failures`() {
        assertEquals(ErrorCategory.NETWORK, UnknownHostException().toRetryCategory())
        assertEquals(ErrorCategory.NETWORK, ConnectException().toRetryCategory())
        assertEquals(ErrorCategory.NETWORK, SSLException("tls").toRetryCategory())
        assertEquals(ErrorCategory.NETWORK, IOException("boom").toRetryCategory())
    }

    @Test
    fun `non-IOException throwables are not retryable`() {
        assertNull(RuntimeException("nope").toRetryCategory())
    }

    @Test
    fun `429 and 5xx responses are http-status failures`() {
        assertEquals(ErrorCategory.HTTP_STATUS, response(429).toRetryCategory())
        assertEquals(ErrorCategory.HTTP_STATUS, response(500).toRetryCategory())
        assertEquals(ErrorCategory.HTTP_STATUS, response(503).toRetryCategory())
        assertEquals(ErrorCategory.HTTP_STATUS, response(599).toRetryCategory())
    }

    @Test
    fun `2xx, 3xx, 401 and non-429 4xx responses are not retryable`() {
        assertNull(response(200).toRetryCategory())
        assertNull(response(301).toRetryCategory())
        assertNull(response(400).toRetryCategory())
        assertNull(response(401).toRetryCategory())
        assertNull(response(403).toRetryCategory())
        assertNull(response(404).toRetryCategory())
    }
}
