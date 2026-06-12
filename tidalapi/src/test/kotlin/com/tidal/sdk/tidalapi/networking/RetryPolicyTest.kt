package com.tidal.sdk.tidalapi.networking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RetryPolicyTest {

    private val policy = DefaultRetryPolicy()

    @Test
    fun `DefaultRetryPolicy exposes the http-status constants`() {
        assertEquals(
            BackoffSpec(baseMillis = 500L, maxMillis = 16_000L, maxRetries = 3),
            policy.httpStatus,
        )
    }

    @Test
    fun `DefaultRetryPolicy exposes the timeout constants`() {
        assertEquals(
            BackoffSpec(baseMillis = 8_000L, maxMillis = 32_000L, maxRetries = 3),
            policy.timeout,
        )
    }

    @Test
    fun `DefaultRetryPolicy exposes the network constants`() {
        assertEquals(
            BackoffSpec(baseMillis = 1_000L, maxMillis = 16_000L, maxRetries = 10),
            policy.network,
        )
    }

    @Test
    fun `specFor returns the matching BackoffSpec per category`() {
        assertEquals(policy.httpStatus, policy.specFor(ErrorCategory.HTTP_STATUS))
        assertEquals(policy.timeout, policy.specFor(ErrorCategory.TIMEOUT))
        assertEquals(policy.network, policy.specFor(ErrorCategory.NETWORK))
    }

    @Test
    fun `isRetryableRequest allows GET and HEAD without a key`() {
        assertTrue(policy.isRetryableRequest("GET", hasIdempotencyKey = false))
        assertTrue(policy.isRetryableRequest("HEAD", hasIdempotencyKey = false))
    }

    @Test
    fun `isRetryableRequest rejects mutations even with a key (Step 1 ignores the key)`() {
        assertFalse(policy.isRetryableRequest("POST", hasIdempotencyKey = false))
        assertFalse(policy.isRetryableRequest("POST", hasIdempotencyKey = true))
        assertFalse(policy.isRetryableRequest("PATCH", hasIdempotencyKey = true))
        assertFalse(policy.isRetryableRequest("DELETE", hasIdempotencyKey = true))
    }
}
