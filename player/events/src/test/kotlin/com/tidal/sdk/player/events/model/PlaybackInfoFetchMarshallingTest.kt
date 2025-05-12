package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import java.util.UUID
import org.junit.jupiter.api.Test

internal class PlaybackInfoFetchMarshallingTest {

    private val ts = 0L
    private val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    private val user = User(-3L, 0, "sessionId")
    private val client = Client("token", Client.DeviceType.TV, "version")
    private val payload =
        PlaybackInfoFetch.Payload(
            "streamingSessionId",
            1L,
            1L,
            EndReason.COMPLETE,
            "errorMessage",
            "errorCode",
        )
    private val playbackInfoFetch =
        PlaybackInfoFetch(ts, UUID.fromString(uuidString), user, client, payload, emptyMap())
    private val gson = Gson()

    @Test
    fun testMarshallingPlaybackInfoFetch() {
        val actual = gson.toJsonTree(playbackInfoFetch).asJsonObject

        assertThat(actual["ts"].asLong).isEqualTo(ts)
        assertThat(actual["uuid"].asString).isEqualTo(uuidString)
        assertThat(gson.fromJson(actual["user"], User::class.java)).isEqualTo(user)
        assertThat(gson.fromJson(actual["client"], Client::class.java)).isEqualTo(client)
        assertThat(gson.fromJson(actual["payload"], payload::class.java)).isEqualTo(payload)
    }

    @Test
    fun testUnmarshallingPlaybackInfoFetch() {
        val src = gson.toJson(playbackInfoFetch)

        val actual = gson.fromJson(src, PlaybackInfoFetch::class.java)

        assertThat(actual).isEqualTo(playbackInfoFetch)
    }
}
