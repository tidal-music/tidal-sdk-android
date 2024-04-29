package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.StreamType

internal abstract class PlaybackStatisticsPayloadMarshallingTest {

    abstract val streamingSessionId: String
    abstract val idealStartTimestamp: Long
    abstract val productType: ProductType
    abstract val actualProductId: String?
    abstract val cdm: PlaybackStatistics.Payload.Cdm?
    abstract val stalls: List<PlaybackStatistics.Payload.Stall>?
    abstract val adaptations: List<PlaybackStatistics.Payload.Adaptation>?
    abstract val endTimestamp: Long
    abstract val payloadFactory: (
        Long?,
        StreamType?,
        AssetPresentation?,
        AudioMode?,
        ProductQuality?,
        MediaStorage?,
        EndReason,
        String?,
        String?,
        String?,
    ) -> PlaybackStatistics.Payload
    private val gson = Gson()

    fun testMarshallingPayload(
        actualStartTimestamp: Long?,
        streamType: StreamType?,
        assetPresentation: AssetPresentation?,
        audioMode: AudioMode?,
        productQuality: ProductQuality?,
        mediaStorage: MediaStorage?,
        endReason: EndReason,
        cdmVersion: String?,
        errorMessage: String?,
        errorCode: String?,
    ) {
        val payload = payloadFactory(
            actualStartTimestamp,
            streamType,
            assetPresentation,
            audioMode,
            productQuality,
            mediaStorage,
            endReason,
            cdmVersion,
            errorMessage,
            errorCode,
        )

        val actual = gson.toJsonTree(payload).asJsonObject

        assertThat(actual["streamingSessionId"].asString).isEqualTo(streamingSessionId)
        assertThat(actual["idealStartTimestamp"].asLong).isEqualTo(idealStartTimestamp)
        assertThat(actual["actualStartTimestamp"]?.asLong).isEqualTo(actualStartTimestamp)
        assertThat(actual["hasAds"]?.asBoolean ?: false).isFalse()
        assertThat(actual["productType"].asString).isEqualTo(productType.name)
        assertThat(actual["actualProductId"]?.asString).isEqualTo(actualProductId)
        assertThat(actual["actualStreamType"]?.asString).isEqualTo(streamType?.name)
        assertThat(actual["actualAssetPresentation"]?.asString).isEqualTo(assetPresentation?.name)
        assertThat(actual["actualAudioMode"]?.asString).isEqualTo(audioMode?.name)
        assertThat(actual["actualQuality"]?.asString).isEqualTo(productQuality?.toString())
        assertThat(actual["mediaStorage"]?.asString).isEqualTo(mediaStorage?.name)
        assertThat(actual["cdm"]?.asString).isEqualTo(cdm?.name)
        assertThat(actual["cdmVersion"]?.asString).isEqualTo(cdmVersion)
        actual["stalls"]?.asJsonArray?.forEachIndexed { i, jsonElement ->
            assertThat(gson.fromJson(jsonElement, PlaybackStatistics.Payload.Stall::class.java))
                .isEqualTo(stalls!![i])
        }
        actual["adaptations"]?.asJsonArray?.forEachIndexed { i, jsonElement ->
            assertThat(
                gson.fromJson(jsonElement, PlaybackStatistics.Payload.Adaptation::class.java),
            ).isEqualTo(adaptations!![i])
        }
        assertThat(actual["endTimestamp"].asLong).isEqualTo(endTimestamp)
        assertThat(actual["endReason"].asString).isEqualTo(endReason.name)
        assertThat(actual["errorMessage"]?.asString).isEqualTo(errorMessage)
        assertThat(actual["errorCode"]?.asString).isEqualTo(errorCode)
    }

    fun testUnmarshallingPayload(
        actualStartTimestamp: Long?,
        streamType: StreamType?,
        assetPresentation: AssetPresentation?,
        audioMode: AudioMode?,
        productQuality: ProductQuality?,
        mediaStorage: MediaStorage?,
        endReason: EndReason,
        cdmVersion: String?,
        errorMessage: String?,
        errorCode: String?,
    ) {
        val payload = payloadFactory(
            actualStartTimestamp,
            streamType,
            assetPresentation,
            audioMode,
            productQuality,
            mediaStorage,
            endReason,
            cdmVersion,
            errorMessage,
            errorCode,
        )
        val src = gson.toJson(payload)

        val actual = gson.fromJson(src, payload::class.java)

        assertThat(actual).isEqualTo(payload)
    }
}
