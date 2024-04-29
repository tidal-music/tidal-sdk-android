package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality
import java.util.UUID
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal abstract class PlaybackSessionPayloadMarshallingTest {

    abstract val startTimestamp: Long
    abstract val startAssetPositionSeconds: Double
    abstract val productType: ProductType
    abstract val requestedProductId: String
    abstract val actualProductId: String
    abstract val sourceType: String
    abstract val sourceId: String
    abstract val actions: List<PlaybackSession.Payload.Action>
    abstract val endTimestamp: Long
    abstract val endAssetPositionSeconds: Double
    abstract val payloadFactory: (UUID, AssetPresentation, AudioMode, ProductQuality) ->
    PlaybackSession.Payload
    private val uuidString = "123e4567-e89b-12d3-a456-426614174000"
    private val playbackSessionId = UUID.fromString(uuidString)
    private val gson = Gson()

    @ParameterizedTest
    @MethodSource("combinationsForEnumFieldsInPayloads")
    fun testMarshallingPayload(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
    ) {
        val productQualityString = when (productQuality) {
            is AudioQuality -> {
                assumeTrue(productType == ProductType.TRACK) // Skip known invalid combinations
                productQuality.name
            }

            is VideoQuality -> {
                assumeTrue(productType == ProductType.VIDEO) // Skip known invalid combinations
                productQuality.name
            }

            else -> error(
                "Missing branch for implementation of ProductQuality: ${productQuality::class.java}",
            )
        }
        val audioModeString = when (productType) {
            ProductType.TRACK, ProductType.BROADCAST -> audioMode.name
            ProductType.VIDEO -> null
        }
        val payload = payloadFactory(
            playbackSessionId,
            assetPresentation,
            audioMode,
            productQuality,
        )

        val actual = gson.toJsonTree(payload).asJsonObject

        assertThat(actual["playbackSessionId"].asString).isEqualTo(uuidString)
        assertThat(actual["startTimestamp"].asLong).isEqualTo(startTimestamp)
        assertThat(actual["startAssetPosition"].asDouble).isEqualTo(startAssetPositionSeconds)
        assertThat(actual["isPostPaywall"].asBoolean).isTrue()
        assertThat(actual["productType"].asString).isEqualTo(productType.name)
        assertThat(actual["requestedProductId"].asString).isEqualTo(requestedProductId)
        assertThat(actual["actualProductId"].asString).isEqualTo(actualProductId)
        assertThat(actual["actualAssetPresentation"].asString).isEqualTo(assetPresentation.name)
        assertThat(actual["actualAudioMode"]?.asString).isEqualTo(audioModeString)
        assertThat(actual["actualQuality"].asString).isEqualTo(productQualityString)
        assertThat(actual["sourceType"].asString).isEqualTo(sourceType)
        assertThat(actual["sourceId"].asString).isEqualTo(sourceId)
        actual["actions"].asJsonArray.forEachIndexed { i, jsonElement ->
            assertThat(gson.fromJson(jsonElement, PlaybackSession.Payload.Action::class.java))
                .isEqualTo(actions[i])
        }
        assertThat(actual["endTimestamp"].asLong).isEqualTo(endTimestamp)
        assertThat(actual["endAssetPosition"].asDouble).isEqualTo(endAssetPositionSeconds)
    }

    @ParameterizedTest
    @MethodSource("combinationsForEnumFieldsInPayloads")
    fun testUnmarshallingPayload(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
    ) {
        when (productType) {
            ProductType.TRACK, ProductType.BROADCAST -> assumeTrue(productQuality is AudioQuality)
            ProductType.VIDEO -> assumeTrue(productQuality is VideoQuality)
        }
        val payload = payloadFactory(
            playbackSessionId,
            assetPresentation,
            audioMode,
            productQuality,
        )
        val src = gson.toJson(payload)

        val actual = gson.fromJson(src, payload::class.java)

        assertThat(actual).isEqualTo(payload)
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun combinationsForEnumFieldsInPayloads(): Iterable<Arguments> {
            val arguments = mutableSetOf<Arguments>()
            AssetPresentation.values().forEach { assetPresentation ->
                AudioMode.values().forEach { audioMode ->
                    (AudioQuality.values().toList() + VideoQuality.values())
                        .forEach { productQuality ->
                            arguments.add(
                                Arguments.of(assetPresentation, audioMode, productQuality),
                            )
                        }
                }
            }
            return arguments
        }
    }
}
