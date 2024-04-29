package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class PlaybackSessionPayloadActionMarshallingTest {

    private val gson = Gson()

    @ParameterizedTest
    @EnumSource(PlaybackSession.Payload.Action.Type::class)
    fun testMarshallingAction(actionType: PlaybackSession.Payload.Action.Type) {
        val timestamp = -7L
        val assetPositionSeconds = 0.0
        val src = PlaybackSession.Payload.Action(timestamp, assetPositionSeconds, actionType)

        val actual = gson.toJsonTree(src).asJsonObject

        assertThat(actual["timestamp"].asLong).isEqualTo(timestamp)
        assertThat(actual["assetPosition"].asDouble).isEqualTo(assetPositionSeconds)
        assertThat(actual["actionType"].asString).isEqualTo(actionType.name)
    }

    @ParameterizedTest
    @EnumSource(PlaybackSession.Payload.Action.Type::class)
    fun testUnmarshallingAction(actionType: PlaybackSession.Payload.Action.Type) {
        val timestamp = 1L
        val assetPositionSeconds = 2.2
        val expected = PlaybackSession.Payload.Action(timestamp, assetPositionSeconds, actionType)
        val action = gson.toJson(expected)

        val actual = gson.fromJson(action, PlaybackSession.Payload.Action::class.java)

        assertThat(actual).isEqualTo(expected)
    }
}
