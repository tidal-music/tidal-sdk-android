package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ProductType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ProgressPayloadPlaybackMarshallingTest {

    private val mediaProductId = "mediaProductId"
    private val playedMs = 3
    private val durationMs = -1
    private val source = Progress.Payload.Playback.Source("type", "id")
    private val playbackFactory: (ProductType) -> Progress.Payload.Playback = {
        Progress.Payload.Playback(
            mediaProductId,
            playedMs,
            durationMs,
            it,
            source,
        )
    }
    private val gson = Gson()

    @ParameterizedTest
    @EnumSource(ProductType::class)
    fun testMarshallingPlayback(productType: ProductType) {
        val actual = gson.toJsonTree(playbackFactory(productType)).asJsonObject

        assertThat(actual["id"].asString).isEqualTo(mediaProductId)
        assertThat(actual["playedMS"].asInt).isEqualTo(playedMs)
        assertThat(actual["durationMS"].asInt).isEqualTo(durationMs)
        assertThat(actual["type"].asString).isEqualTo(productType.name)
        assertThat(gson.fromJson(actual["source"], Progress.Payload.Playback.Source::class.java))
            .isEqualTo(source)
    }

    @ParameterizedTest
    @EnumSource(ProductType::class)
    fun testUnmarshallingPlayback(productType: ProductType) {
        val playback = playbackFactory(productType)
        val src = gson.toJson(playback)

        val actual = gson.fromJson(src, Progress.Payload.Playback::class.java)

        assertThat(actual).isEqualTo(playback)
    }
}
