package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.api.Test

internal class StreamingSessionEndPayloadMarshallingTest {

    private val streamingSessionId = "streamingSessionId"
    private val timestamp = 7L
    private val payload = StreamingSessionEnd.Payload(streamingSessionId, timestamp)
    private val gson = Gson()

    @Test
    fun testMarshallingPayload() {
        val actual = gson.toJsonTree(payload).asJsonObject

        assertThat(actual["streamingSessionId"].asString).isEqualTo(streamingSessionId)
        assertThat(actual["timestamp"].asLong).isEqualTo(timestamp)
    }

    @Test
    fun testUnmarshallingPayload() {
        val src = gson.toJson(payload)

        val actual = gson.fromJson(src, StreamingSessionEnd.Payload::class.java)

        assertThat(actual).isEqualTo(payload)
    }
}
