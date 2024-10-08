package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ProductType
import java.util.UUID
import org.junit.jupiter.api.Test

internal class StreamingSessionStartMarshallingTest {

    private val ts = 0L
    private val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    private val user = User(-3L, 0, "sessionId")
    private val client = Client("token", Client.DeviceType.TV, "version")
    private val decoratedPayload = StreamingSessionStart.DecoratedPayload(
        "streamingSessionId",
        1L,
        false,
        StreamingSessionStart.StartReason.IMPLICIT,
        "hardwarePlatform",
        "operatingSystem",
        "operatingSystemVersion",
        -1,
        -2,
        StreamingSessionStart.NetworkType.ETHERNET,
        "mobileNetworkType",
        StreamingSessionStart.SessionType.PLAYBACK,
        ProductType.TRACK,
        "123",
    )
    private val streamingSessionStart = StreamingSessionStart(
        ts,
        UUID.fromString(uuidString),
        user,
        client,
        decoratedPayload,
        null,
    )
    private val gson = Gson()

    @Test
    fun testMarshallingStreamingSessionStart() {
        val actual = gson.toJsonTree(streamingSessionStart).asJsonObject

        assertThat(actual["ts"].asLong).isEqualTo(ts)
        assertThat(actual["uuid"].asString).isEqualTo(uuidString)
        assertThat(gson.fromJson(actual["user"], User::class.java)).isEqualTo(user)
        assertThat(gson.fromJson(actual["client"], Client::class.java)).isEqualTo(client)
        assertThat(
            gson.fromJson(actual["payload"], decoratedPayload::class.java),
        ).isEqualTo(decoratedPayload)
    }

    @Test
    fun testUnmarshallingStreamingSessionStart() {
        val src = gson.toJson(streamingSessionStart)

        val actual = gson.fromJson(src, StreamingSessionStart::class.java)

        assertThat(actual).isEqualTo(streamingSessionStart)
    }
}
