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
        assertEquals(
            ErrorCategory.TIMEOUT,
            classify(response = null, error = SocketTimeoutException()),
        )
    }

    @Test
    fun `other IOExceptions are network failures`() {
        assertEquals(
            ErrorCategory.NETWORK,
            classify(response = null, error = UnknownHostException()),
        )
        assertEquals(ErrorCategory.NETWORK, classify(response = null, error = ConnectException()))
        assertEquals(ErrorCategory.NETWORK, classify(response = null, error = SSLException("tls")))
        assertEquals(ErrorCategory.NETWORK, classify(response = null, error = IOException("boom")))
    }

    @Test
    fun `non-IOException throwables are not retryable`() {
        assertNull(classify(response = null, error = RuntimeException("nope")))
    }

    @Test
    fun `429 and 5xx responses are http-status failures`() {
        assertEquals(ErrorCategory.HTTP_STATUS, classify(response(429), error = null))
        assertEquals(ErrorCategory.HTTP_STATUS, classify(response(500), error = null))
        assertEquals(ErrorCategory.HTTP_STATUS, classify(response(503), error = null))
        assertEquals(ErrorCategory.HTTP_STATUS, classify(response(599), error = null))
    }

    @Test
    fun `2xx, 3xx, 401 and non-429 4xx responses are not retryable`() {
        assertNull(classify(response(200), error = null))
        assertNull(classify(response(301), error = null))
        assertNull(classify(response(400), error = null))
        assertNull(classify(response(401), error = null))
        assertNull(classify(response(403), error = null))
        assertNull(classify(response(404), error = null))
    }
}
