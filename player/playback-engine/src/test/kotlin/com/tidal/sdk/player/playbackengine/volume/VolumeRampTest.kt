package com.tidal.sdk.player.playbackengine.volume

import assertk.assertThat
import assertk.assertions.isCloseTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

private const val STARTED_AT_MS = 1_000L
private const val RAMP_MS = 200L
private const val TOLERANCE = 0.0001f

internal class VolumeRampTest {

    private val ramp = VolumeRamp(0.2f, RAMP_MS, STARTED_AT_MS)

    @Test
    fun startsAtFrom() {
        assertThat(ramp.volumeAt(STARTED_AT_MS, 1f)).isCloseTo(0.2f, TOLERANCE)
    }

    @Test
    fun movesLinearlyTowardsTheTarget() {
        assertThat(ramp.volumeAt(STARTED_AT_MS + RAMP_MS / 2, 1f)).isCloseTo(0.6f, TOLERANCE)
    }

    @Test
    fun followsATargetThatChangesAlongTheWay() {
        assertThat(ramp.volumeAt(STARTED_AT_MS + RAMP_MS / 2, 0.6f)).isCloseTo(0.4f, TOLERANCE)
    }

    @Test
    fun rampsDownToo() {
        assertThat(ramp.volumeAt(STARTED_AT_MS + RAMP_MS / 2, 0f)).isCloseTo(0.1f, TOLERANCE)
    }

    @Test
    fun capsATargetLouderThanFullVolume() {
        assertThat(ramp.volumeAt(STARTED_AT_MS + RAMP_MS, 1.5f)).isCloseTo(1f, TOLERANCE)
    }

    @Test
    fun staysWithinTheRampOutsideItsTime() {
        assertThat(ramp.volumeAt(STARTED_AT_MS - RAMP_MS, 1f)).isCloseTo(0.2f, TOLERANCE)
        assertThat(ramp.volumeAt(STARTED_AT_MS + RAMP_MS * 2, 1f)).isCloseTo(1f, TOLERANCE)
    }

    @Test
    fun isDoneOnceItsTimeIsUp() {
        assertThat(ramp.isDoneAt(STARTED_AT_MS + RAMP_MS - 1)).isFalse()
        assertThat(ramp.isDoneAt(STARTED_AT_MS + RAMP_MS)).isTrue()
    }
}
