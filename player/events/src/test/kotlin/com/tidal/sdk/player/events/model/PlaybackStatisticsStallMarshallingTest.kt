package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class PlaybackStatisticsStallMarshallingTest {

    private val gson = Gson()

    @ParameterizedTest
    @EnumSource(PlaybackStatistics.Payload.Stall.Reason::class)
    fun testMarshallingStall(reason: PlaybackStatistics.Payload.Stall.Reason) {
        val assetPositionSeconds = 1.0
        val startTimestamp = Long.MAX_VALUE
        val endTimestamp = Long.MIN_VALUE
        val stall = PlaybackStatistics.Payload.Stall(
            reason,
            assetPositionSeconds,
            startTimestamp,
            endTimestamp,
        )

        val actual = gson.toJsonTree(stall).asJsonObject

        assertThat(actual["reason"].asString).isEqualTo(reason.name)
        assertThat(actual["assetPosition"].asDouble).isEqualTo(assetPositionSeconds)
        assertThat(actual["startTimestamp"].asLong).isEqualTo(startTimestamp)
        assertThat(actual["endTimestamp"].asLong).isEqualTo(endTimestamp)
    }

    @ParameterizedTest
    @EnumSource(PlaybackStatistics.Payload.Stall.Reason::class)
    fun testUnmarshallingStall(reason: PlaybackStatistics.Payload.Stall.Reason) {
        val assetPositionSeconds = 1.0
        val startTimestamp = Long.MAX_VALUE
        val endTimestamp = Long.MIN_VALUE
        val expected = PlaybackStatistics.Payload.Stall(
            reason,
            assetPositionSeconds,
            startTimestamp,
            endTimestamp,
        )
        val src = gson.toJson(expected)

        val actual = gson.fromJson(src, PlaybackStatistics.Payload.Stall::class.java)

        assertThat(actual).isEqualTo(expected)
    }
}
