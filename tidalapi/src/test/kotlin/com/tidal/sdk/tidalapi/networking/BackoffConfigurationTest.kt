package com.tidal.sdk.tidalapi.networking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BackoffConfigurationTest {

    private val baseMillis = 500L
    private val maxMillis = 16_000L
    private val configuration =
        BackoffConfiguration(baseMillis = baseMillis, maxMillis = maxMillis, maxRetries = 3)

    // computeDelayMillis multiplies the (capped) delay by 0.8 + 0.2 * random(), so the random
    // source's [0.0, 1.0] range maps onto the [0.8, 1.0] jitter band.
    private val jitterLowEdge = { 0.0 } // factor 0.8
    private val jitterMidpoint = { 0.5 } // factor 0.9
    private val jitterHighEdge = { 1.0 } // factor 1.0

    @Test
    fun `delay grows exponentially with jitter at the top of the band`() {
        assertEquals(
            baseMillis,
            configuration.computeDelayMillis(attempt = 0, random = jitterHighEdge),
        )
        assertEquals(
            baseMillis * 2,
            configuration.computeDelayMillis(attempt = 1, random = jitterHighEdge),
        )
        assertEquals(
            baseMillis * 4,
            configuration.computeDelayMillis(attempt = 2, random = jitterHighEdge),
        )
    }

    @Test
    fun `delay is capped at maxMillis before jitter`() {
        assertEquals(
            maxMillis,
            configuration.computeDelayMillis(attempt = 10, random = jitterHighEdge),
        )
    }

    @Test
    fun `jitter band lower edge scales the capped delay by 0_8`() {
        assertEquals(
            (baseMillis * 0.8).toLong(),
            configuration.computeDelayMillis(attempt = 0, random = jitterLowEdge),
        )
    }

    @Test
    fun `jitter band midpoint scales the capped delay by 0_9`() {
        assertEquals(
            (baseMillis * 0.9).toLong(),
            configuration.computeDelayMillis(attempt = 0, random = jitterMidpoint),
        )
    }

    @Test
    fun `jitter band upper edge scales the capped delay by 1_0`() {
        assertEquals(
            baseMillis,
            configuration.computeDelayMillis(attempt = 0, random = jitterHighEdge),
        )
    }

    @Test
    fun `cap is applied before jitter so a capped delay still gets the lower band factor`() {
        assertEquals(
            (maxMillis * 0.8).toLong(),
            configuration.computeDelayMillis(attempt = 10, random = jitterLowEdge),
        )
    }
}
