package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.api.Test

internal class ProgressPayloadPlaybackSourceMarshallingTest {

    private val gson = Gson()

    @Test
    fun testMarshallingSource() {
        val type = "type"
        val id = "id"
        val src = Progress.Payload.Playback.Source(type, id)

        val actual = gson.toJsonTree(src).asJsonObject

        assertThat(actual["type"].asString).isEqualTo(type)
        assertThat(actual["id"].asString).isEqualTo(id)
    }

    @Test
    fun testUnmarshallingSource() {
        val type = "type"
        val id = "id"
        val expected = Progress.Payload.Playback.Source(type, id)
        val src = gson.toJson(expected)

        val actual = gson.fromJson(src, Progress.Payload.Playback.Source::class.java)

        assertThat(actual).isEqualTo(expected)
    }
}
