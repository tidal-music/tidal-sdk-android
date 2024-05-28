package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import org.junit.jupiter.api.Test

internal class PlaybackStatisticsAdaptationMarshallingTest {

    private val gson = Gson()

    @Test
    fun testMarshallingAdaptation() {
        val assetPositionSeconds = 8.0
        val timestamp = 0L
        val mimeType = "mimeType"
        val codecs = "codecs"
        val bandwidthBps = 9
        val videoWidth = 1
        val videoHeight = 3
        val adaptation = PlaybackStatistics.Payload.Adaptation(
            assetPositionSeconds,
            timestamp,
            mimeType,
            codecs,
            bandwidthBps,
            videoWidth,
            videoHeight,
        )

        val actual = gson.toJsonTree(adaptation).asJsonObject

        assertThat(actual["assetPosition"].asDouble).isEqualTo(assetPositionSeconds)
        assertThat(actual["timestamp"].asLong).isEqualTo(timestamp)
        assertThat(actual["mimeType"].asString).isEqualTo(mimeType)
        assertThat(actual["codecs"].asString).isEqualTo(codecs)
        assertThat(actual["bandwidth"].asInt).isEqualTo(bandwidthBps)
        assertThat(actual["videoWidth"].asInt).isEqualTo(videoWidth)
        assertThat(actual["videoHeight"].asInt).isEqualTo(videoHeight)
    }

    @Test
    fun testUnmarshallingAdaptation() {
        val assetPositionSeconds = 8.0
        val timestamp = 0L
        val mimeType = "mimeType"
        val codecs = "codecs"
        val bandwidthBps = 9
        val videoWidth = 1
        val videoHeight = 3
        val expected = PlaybackStatistics.Payload.Adaptation(
            assetPositionSeconds,
            timestamp,
            mimeType,
            codecs,
            bandwidthBps,
            videoWidth,
            videoHeight,
        )
        val src = gson.toJson(expected)

        val actual = gson.fromJson(src, PlaybackStatistics.Payload.Adaptation::class.java)

        assertThat(actual).isEqualTo(expected)
    }
}
