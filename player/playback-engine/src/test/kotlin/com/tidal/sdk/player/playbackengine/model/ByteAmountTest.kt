package com.tidal.sdk.player.playbackengine.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.playbackengine.model.ByteAmount.Companion.bytes
import com.tidal.sdk.player.playbackengine.model.ByteAmount.Companion.gigabytes
import com.tidal.sdk.player.playbackengine.model.ByteAmount.Companion.kilobytes
import com.tidal.sdk.player.playbackengine.model.ByteAmount.Companion.megabytes
import org.junit.jupiter.api.Test

internal class ByteAmountTest {

    @Test
    fun byteCompanionPropertyMultipliesByCorrectFactor() {
        val value = 7L

        assertThat(value.bytes).isEqualTo(ByteAmount(value))
    }

    @Test
    fun kilobyteCompanionPropertyMultipliesByCorrectFactor() {
        val value = -83L

        assertThat(value.kilobytes).isEqualTo(ByteAmount(value * 1024))
    }

    @Test
    fun megabyteCompanionPropertyMultipliesByCorrectFactor() {
        val value = 10L

        assertThat(value.megabytes).isEqualTo(ByteAmount(value * 1024 * 1024))
    }

    @Test
    fun gigabyteCompanionPropertyMultipliesByCorrectFactor() {
        val value = 10L

        assertThat(value.gigabytes).isEqualTo(ByteAmount(value * 1024 * 1024 * 1024))
    }
}
