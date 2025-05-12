package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class VideoPlaybackStatisticsPayloadMarshallingTest :
    PlaybackStatisticsPayloadMarshallingTest() {

    override val streamingSessionId = "streamingSessionId"
    override val idealStartTimestamp = 1L
    override val productType = ProductType.VIDEO
    override val actualProductId = "actualProductId"
    override val cdm = PlaybackStatistics.Payload.Cdm.NONE
    override val stalls = emptyList<PlaybackStatistics.Payload.Stall>()
    override val adaptations = emptyList<PlaybackStatistics.Payload.Adaptation>()
    override val endTimestamp = Long.MIN_VALUE
    override val payloadFactory =
        {
            actualStartTimestamp: Long?,
            streamType: StreamType?,
            assetPresentation: AssetPresentation?,
            _: AudioMode?,
            productQuality: ProductQuality?,
            mediaStorage: MediaStorage?,
            endReason: EndReason,
            cdmVersion: String?,
            errorMessage: String?,
            errorCode: String? ->
            VideoPlaybackStatistics.Payload(
                streamingSessionId,
                idealStartTimestamp,
                actualStartTimestamp,
                actualProductId,
                streamType!!,
                assetPresentation!!,
                productQuality as VideoQuality,
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
        streamType: StreamType?,
        assetPresentation: AssetPresentation?,
        productQuality: ProductQuality?,
        mediaStorage: MediaStorage?,
        endReason: EndReason,
        cdmVersion: String?,
        errorMessage: String?,
        errorCode: String?,
    ) {
        testMarshallingPayload(
            actualStartTimestamp,
            streamType,
            assetPresentation,
            null,
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
        streamType: StreamType?,
        assetPresentation: AssetPresentation?,
        productQuality: ProductQuality?,
        mediaStorage: MediaStorage?,
        endReason: EndReason,
        cdmVersion: String?,
        errorMessage: String?,
        errorCode: String?,
    ) {
        testUnmarshallingPayload(
            actualStartTimestamp,
            streamType,
            assetPresentation,
            null,
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
                StreamType.values().forEach { streamType ->
                    AssetPresentation.values().forEach { assetPresentation ->
                        VideoQuality.values().forEach { productQuality ->
                            MediaStorage.values().forEach { mediaStorage ->
                                EndReason.values().forEach { endReason ->
                                    arrayOf(null, "cdmVersion").forEach { cdmVersion ->
                                        arrayOf(null, "errorMessage").forEach { errorMessage ->
                                            arrayOf(null, "errorCode").forEach { errorCode ->
                                                arguments.add(
                                                    Arguments.of(
                                                        actualStartTimestamp,
                                                        streamType,
                                                        assetPresentation,
                                                        productQuality,
                                                        mediaStorage,
                                                        endReason,
                                                        cdmVersion,
                                                        errorMessage,
                                                        errorCode,
                                                    )
                                                )
                                            }
                                        }
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
