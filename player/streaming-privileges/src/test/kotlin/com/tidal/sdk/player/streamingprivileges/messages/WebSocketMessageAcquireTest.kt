package com.tidal.sdk.player.streamingprivileges.messages

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.junit.jupiter.api.Test

internal class WebSocketMessageAcquireTest {

    private val gson = Gson()

    @Test
    fun messageSerializesAsExpected() {
        val startAtMillis = -83L

        val actual = gson.toJson(WebSocketMessage.Outgoing.Acquire(startAtMillis))
        val parsed = gson.fromJson(actual, JsonObject::class.java)

        assertThat(parsed["type"].asString).isEqualTo("USER_ACTION")
        assertThat(parsed["payload"].asJsonObject["startedAt"].asLong).isEqualTo(startAtMillis)
    }
}
