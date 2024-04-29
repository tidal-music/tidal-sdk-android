package com.tidal.sdk.player.events.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import java.util.Locale
import org.junit.jupiter.api.Test

internal class HardwarePlatformTest {

    @Test
    fun modelIncludesManufacturerCaseMatch() {
        val manufacturer = "Manufacturer"
        val model = "model$manufacturer"

        val actual = HardwarePlatform(model, manufacturer).value

        assertThat(actual).isEqualTo(model)
    }

    @Test
    fun modelIncludesManufacturerCaseMismatch() {
        val manufacturer = "MaNuFaCtUrEr"
        val model = "model${manufacturer.lowercase(Locale.ENGLISH)}"

        val actual = HardwarePlatform(model, manufacturer).value

        assertThat(actual).isEqualTo(model)
    }

    @Test
    fun modelDoesNotIncludeManufacturer() {
        val model = "model"
        val manufacturer = "manufacturer"

        val actual = HardwarePlatform(model, manufacturer).value

        assertThat(actual).isEqualTo("$manufacturer $model")
    }
}
