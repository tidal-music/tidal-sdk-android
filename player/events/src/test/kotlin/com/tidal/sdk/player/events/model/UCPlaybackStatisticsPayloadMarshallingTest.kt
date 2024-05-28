package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.StreamType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class UCPlaybackStatisticsPayloadMarshallingTest :
    PlaybackStatisticsPayloadMarshallingTest() {

    override val streamingSessionId = "streamingSessionId"
    override val idealStartTimestamp = Long.MIN_VALUE
    override val productType = ProductType.UC
    override val actualProductId = "actualProductId"
    override val cdm = PlaybackStatistics.Payload.Cdm.WIDEVINE
    override val stalls = listOf(
        PlaybackStatistics.Payload.Stall(
            PlaybackStatistics.Payload.Stall.Reason.SEEK,
            1.0,
            Long.MAX_VALUE,
            0L,
        ),
    )
    override val adaptations = listOf(
        PlaybackStatistics.Payload.Adaptation(
            1.0,
            Long.MIN_VALUE,
            "mimeType",
            "codecs",
            0,
            -1,
            Int.MAX_VALUE,
        ),
    )
    override val endTimestamp = -1L
    override val payloadFactory =
        {
                actualStartTimestamp: Long?,
                _: StreamType?,
                _: AssetPresentation?,
                _: AudioMode?,
                productQuality: ProductQuality?,
                mediaStorage: MediaStorage?,
                endReason: EndReason,
                cdmVersion: String?,
                errorMessage: String?,
                errorCode: String?,
            ->
            UCPlaybackStatistics.Payload(
                streamingSessionId,
                idealStartTimestamp,
                actualStartTimestamp,
                actualProductId,
                productQuality as AudioQuality,
                mediaStorage!!,
                cdm,
                cdmVersion,
                stalls,
                adaptations,
                endTimestamp,
                endReason,
                errorMessage,
                errorCode,
            )
        }

    @ParameterizedTest
    @MethodSource("combinationsForPayloads")
    fun testMarshallingPayload(
        actualStartTimestamp: Long?,
        productQuality: ProductQuality?,
        mediaStorage: MediaStorage?,
        endReason: EndReason,
        cdmVersion: String?,
        errorMessage: String?,
        errorCode: String?,
    ) {
        testMarshallingPayload(
            actualStartTimestamp,
            null,
            AssetPresentation.FULL,
            AudioMode.STEREO,
            productQuality,
            mediaStorage,
            endReason,
            cdmVersion,
            errorMessage,
            errorCode,
        )
    }

    @ParameterizedTest
    @MethodSource("combinationsForPayloads")
    fun testUnmarshallingPayload(
        actualStartTimestamp: Long?,
        productQuality: ProductQuality?,
        mediaStorage: MediaStorage?,
        endReason: EndReason,
        cdmVersion: String?,
        errorMessage: String?,
        errorCode: String?,
    ) {
        testUnmarshallingPayload(
            actualStartTimestamp,
            null,
            AssetPresentation.FULL,
            AudioMode.STEREO,
            productQuality,
            mediaStorage,
            endReason,
            cdmVersion,
            errorMessage,
            errorCode,
        )
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember", "NestedBlockDepth")
        private fun combinationsForPayloads(): Iterable<Arguments> {
            val arguments = mutableSetOf<Arguments>()
            arrayOf(null, Long.MAX_VALUE).forEach { actualStartTimestamp ->
                AudioQuality.values().forEach { productQuality ->
                    MediaStorage.values().forEach { mediaStorage ->
                        EndReason.values().forEach { endReason ->
                            arrayOf(null, "cdmVersion").forEach { cdmVersion ->
                                arrayOf(null, "errorMessage").forEach { errorMessage ->
                                    arrayOf(null, "errorCode").forEach { errorCode ->
                                        arguments.add(
                                            Arguments.of(
                                                actualStartTimestamp,
                                                productQuality,
                                                mediaStorage,
                                                endReason,
                                                cdmVersion,
                                                errorMessage,
                                                errorCode,
                                            ),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return arguments
        }
    }
}
