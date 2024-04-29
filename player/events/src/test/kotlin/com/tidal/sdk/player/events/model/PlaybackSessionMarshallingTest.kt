package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.api.Test

internal abstract class PlaybackSessionMarshallingTest {

    abstract val ts: Long
    abstract val uuidString: String
    abstract val user: User
    abstract val client: Client
    abstract val payload: PlaybackSession.Payload
    abstract val playbackSession: PlaybackSession<out PlaybackSession.Payload>
    private val gson = Gson()

    @Test
    fun testMarshallingPlaybackSession() {
        val actual = gson.toJsonTree(playbackSession).asJsonObject

        assertThat(actual["ts"].asLong).isEqualTo(ts)
        assertThat(actual["uuid"].asString).isEqualTo(uuidString)
        assertThat(gson.fromJson(actual["user"], User::class.java)).isEqualTo(user)
        assertThat(gson.fromJson(actual["client"], Client::class.java)).isEqualTo(client)
        assertThat(gson.fromJson(actual["payload"], payload::class.java)).isEqualTo(payload)
    }

    @Test
    fun testUnmarshallingPlaybackSession() {
        val src = gson.toJson(playbackSession)

        val actual = gson.fromJson(
            src,
            when (playbackSession) {
                is AudioPlaybackSession -> AudioPlaybackSession::class.java
                is VideoPlaybackSession -> VideoPlaybackSession::class.java
                is BroadcastPlaybackSession -> BroadcastPlaybackSession::class.java
            },
        )

        assertThat(actual).isEqualTo(playbackSession)
    }
}
