package com.tidal.sdk.tidalapi.networking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BackoffSpecTest {

    private val spec = BackoffSpec(baseMillis = 500L, maxMillis = 16_000L, maxRetries = 3)

    @Test
    fun `delay grows exponentially with jitter at the top of the band`() {
        assertEquals(500L, spec.computeDelayMillis(attempt = 0) { 1.0 })
        assertEquals(1000L, spec.computeDelayMillis(attempt = 1) { 1.0 })
        assertEquals(2000L, spec.computeDelayMillis(attempt = 2) { 1.0 })
    }

    @Test
    fun `delay is capped at maxMillis before jitter`() {
        assertEquals(16_000L, spec.computeDelayMillis(attempt = 10) { 1.0 })
    }

    @Test
    fun `jitter band lower edge scales the capped delay by 0_8`() {
        assertEquals(400L, spec.computeDelayMillis(attempt = 0) { 0.0 })
    }

    @Test
    fun `jitter band midpoint scales the capped delay by 0_9`() {
        assertEquals(450L, spec.computeDelayMillis(attempt = 0) { 0.5 })
    }

    @Test
    fun `jitter band upper edge scales the capped delay by 1_0`() {
        assertEquals(500L, spec.computeDelayMillis(attempt = 0) { 1.0 })
    }

    @Test
    fun `cap is applied before jitter so a capped delay still gets the lower band factor`() {
        assertEquals(12_800L, spec.computeDelayMillis(attempt = 10) { 0.0 })
    }
}
