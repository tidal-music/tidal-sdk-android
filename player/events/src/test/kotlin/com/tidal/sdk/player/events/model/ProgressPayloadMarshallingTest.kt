package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ProductType
import org.junit.jupiter.api.Test

internal class ProgressPayloadMarshallingTest {

    private val source = Progress.Payload.Playback.Source("type", "id")
    private val playback = Progress.Payload.Playback(
        "mediaProductId",
        -1,
        2,
        ProductType.VIDEO,
        source,
    )
    private val payload = Progress.Payload(playback)
    private val gson = Gson()

    @Test
    fun testMarshallingPayload() {
        val actual = gson.toJsonTree(payload).asJsonObject

        assertThat(gson.fromJson(actual["playback"], playback::class.java)).isEqualTo(playback)
    }

    @Test
    fun testUnmarshallingPayload() {
        val src = gson.toJson(payload)

        val actual = gson.fromJson(src, Progress.Payload::class.java)

        assertThat(actual).isEqualTo(payload)
    }
}
