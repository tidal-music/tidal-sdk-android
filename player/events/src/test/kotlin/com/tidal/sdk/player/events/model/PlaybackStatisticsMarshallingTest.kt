package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.api.Test

internal abstract class PlaybackStatisticsMarshallingTest {

    abstract val ts: Long
    abstract val uuidString: String
    abstract val user: User
    abstract val client: Client
    abstract val payload: PlaybackStatistics.Payload
    abstract val playbackStatistics: PlaybackStatistics<out PlaybackStatistics.Payload>
    private val gson = Gson()

    @Test
    fun testMarshallingPlaybackStatistics() {
        val actual = gson.toJsonTree(playbackStatistics).asJsonObject

        assertThat(actual["ts"].asLong).isEqualTo(ts)
        assertThat(actual["uuid"].asString).isEqualTo(uuidString)
        assertThat(gson.fromJson(actual["user"], User::class.java)).isEqualTo(user)
        assertThat(gson.fromJson(actual["client"], Client::class.java)).isEqualTo(client)
        assertThat(gson.fromJson(actual["payload"], payload::class.java)).isEqualTo(payload)
    }

    @Test
    fun testUnmarshallingPlaybackStatistics() {
        val src = gson.toJson(playbackStatistics)

        val actual = gson.fromJson(src, playbackStatistics::class.java)

        assertThat(actual).isEqualTo(playbackStatistics)
    }
}
