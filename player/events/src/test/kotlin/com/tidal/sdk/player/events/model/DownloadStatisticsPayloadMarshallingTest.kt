package com.tidal.sdk.player.events.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.google.gson.Gson
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal abstract class DownloadStatisticsPayloadMarshallingTest {

    abstract val streamingSessionId: String
    abstract val startTimestamp: Long
    abstract val actualProductId: String
    abstract val endTimestamp: Long
    abstract val productType: ProductType
    abstract val payloadFactory:
        (AssetPresentation, AudioMode, ProductQuality, EndReason, String?, String?) ->
        DownloadStatistics.Payload
    private val gson = Gson()

    @ParameterizedTest
    @MethodSource("combinationsForEnumFieldsInPayloads")
    fun testMarshallingPayloadWithErrorMessage(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
        endReason: EndReason,
    ) = testMarshallingPayload(
        assetPresentation,
        audioMode,
        productQuality,
        endReason,
        "errorMessage",
        "errorCode",
    )

    @ParameterizedTest
    @MethodSource("combinationsForEnumFieldsInPayloads")
    fun testMarshallingPayloadWithoutErrorMessage(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
        endReason: EndReason,
    ) = testMarshallingPayload(assetPresentation, audioMode, productQuality, endReason, null, null)

    @SuppressWarnings("ComplexMethod") // Complex transfer model, complex test. Little we can do
    private fun testMarshallingPayload(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
        endReason: EndReason,
        errorMessage: String?,
        errorCode: String?,
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
        val src = payloadFactory(
            assetPresentation,
            audioMode,
            productQuality,
            endReason,
            errorMessage,
            errorCode,
        )

        val actual = gson.toJsonTree(src).asJsonObject

        assertThat(actual["streamingSessionId"].asString).isEqualTo(streamingSessionId)
        assertThat(actual["startTimestamp"].asLong).isEqualTo(startTimestamp)
        assertThat(actual["actualProductId"].asString).isEqualTo(actualProductId)
        assertThat(actual["endTimestamp"].asLong).isEqualTo(endTimestamp)
        assertThat(actual["actualAssetPresentation"].asString).isEqualTo(assetPresentation.name)
        assertThat(actual["actualAudioMode"].asString).isEqualTo(audioMode.name)
        assertThat(actual["actualQuality"].asString).isEqualTo(productQualityString)
        assertThat(actual["endReason"].asString).isEqualTo(endReason.name)
        assertThat(actual["errorMessage"]?.asString).isEqualTo(errorMessage)
        assertThat(actual["errorCode"]?.asString).isEqualTo(errorCode)
    }

    @ParameterizedTest
    @MethodSource("combinationsForEnumFieldsInPayloads")
    fun testUnmarshallingPayloadWithErrorMessage(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
        endReason: EndReason,
    ) = testUnmarshallingPayload(
        assetPresentation,
        audioMode,
        productQuality,
        endReason,
        "errorMessage",
        "errorCode",
    )

    @ParameterizedTest
    @MethodSource("combinationsForEnumFieldsInPayloads")
    fun testUnmarshallingPayloadWithoutErrorMessage(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
        endReason: EndReason,
    ) = testUnmarshallingPayload(
        assetPresentation,
        audioMode,
        productQuality,
        endReason,
        null,
        null,
    )

    private fun testUnmarshallingPayload(
        assetPresentation: AssetPresentation,
        audioMode: AudioMode,
        productQuality: ProductQuality,
        endReason: EndReason,
        errorMessage: String?,
        errorCode: String?,
    ) {
        when (productType) {
            ProductType.TRACK, ProductType.BROADCAST -> assumeTrue(productQuality is AudioQuality)
            ProductType.VIDEO -> assumeTrue(productQuality is VideoQuality)
        }
        val expected = payloadFactory(
            assetPresentation,
            audioMode,
            productQuality,
            endReason,
            errorMessage,
            errorCode,
        )
        val src = gson.toJson(expected)

        val actual = gson.fromJson(
            src,
            when (expected) {
                is AudioDownloadStatistics.Payload -> AudioDownloadStatistics.Payload::class.java
                is VideoDownloadStatistics.Payload -> VideoDownloadStatistics.Payload::class.java
            },
        )

        assertThat(actual).isEqualTo(expected)
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember", "NestedBlockDepth")
        private fun combinationsForEnumFieldsInPayloads(): Iterable<Arguments> {
            val arguments = mutableSetOf<Arguments>()
            AssetPresentation.values().forEach { assetPresentation ->
                AudioMode.values().forEach { audioMode ->
                    (
                        AudioQuality.values().toList() + VideoQuality.values()
                        ).forEach { productQuality ->
                        EndReason.values().forEach { endReason ->
                            arguments.add(
                                Arguments.of(
                                    assetPresentation,
                                    audioMode,
                                    productQuality,
                                    endReason,
                                ),
                            )
                        }
                    }
                }
            }
            return arguments
        }
    }
}
