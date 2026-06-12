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
            BackoffConfiguration(baseMillis = 500L, maxMillis = 16_000L, maxRetries = 3),
            policy.httpStatus,
        )
    }

    @Test
    fun `DefaultRetryPolicy exposes the timeout constants`() {
        assertEquals(
            BackoffConfiguration(baseMillis = 8_000L, maxMillis = 32_000L, maxRetries = 3),
            policy.timeout,
        )
    }

    @Test
    fun `DefaultRetryPolicy exposes the network constants`() {
        assertEquals(
            BackoffConfiguration(baseMillis = 1_000L, maxMillis = 16_000L, maxRetries = 10),
            policy.network,
        )
    }

    @Test
    fun `getConfigurationFor returns the matching configuration per category`() {
        assertEquals(policy.httpStatus, policy.getConfigurationFor(ErrorCategory.HTTP_STATUS))
        assertEquals(policy.timeout, policy.getConfigurationFor(ErrorCategory.TIMEOUT))
        assertEquals(policy.network, policy.getConfigurationFor(ErrorCategory.NETWORK))
    }

    @Test
    fun `isRetryableRequest allows GET, HEAD and OPTIONS`() {
        assertTrue(policy.isRetryableRequest("GET"))
        assertTrue(policy.isRetryableRequest("HEAD"))
        assertTrue(policy.isRetryableRequest("OPTIONS"))
    }

    @Test
    fun `isRetryableRequest rejects mutations`() {
        assertFalse(policy.isRetryableRequest("POST"))
        assertFalse(policy.isRetryableRequest("PATCH"))
        assertFalse(policy.isRetryableRequest("DELETE"))
    }
}
