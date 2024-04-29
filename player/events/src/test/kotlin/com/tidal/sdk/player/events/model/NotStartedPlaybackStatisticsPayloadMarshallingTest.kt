package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.MediaStorage
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.StreamType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullSource
import org.junit.jupiter.params.provider.ValueSource

internal abstract class NotStartedPlaybackStatisticsPayloadMarshallingTest(
    override val productType: ProductType,
) : PlaybackStatisticsPayloadMarshallingTest() {

    override val streamingSessionId = "streamingSessionId"
    override val idealStartTimestamp = Long.MIN_VALUE
    override val actualProductId = null
    override val cdm = null
    override val stalls = null
    override val adaptations = null
    override val endTimestamp = -1L
    override val payloadFactory =
        {
                _: Long?,
                _: StreamType?,
                _: AssetPresentation?,
                _: AudioMode?,
                _: ProductQuality?,
                _: MediaStorage?,
                endReason: EndReason,
                _: String?,
                errorMessage: String?,
                errorCode: String?,
            ->
            NotStartedPlaybackStatistics.Payload(
                streamingSessionId,
                idealStartTimestamp,
                productType,
                endTimestamp,
                errorMessage,
                errorCode!!,
                endReason,
            )
        }

    class ProductTypeAudio : NotStartedPlaybackStatisticsPayloadMarshallingTest(ProductType.TRACK)

    class ProductTypeVideo : NotStartedPlaybackStatisticsPayloadMarshallingTest(ProductType.VIDEO)

    class ProductTypeBroadcast :
        NotStartedPlaybackStatisticsPayloadMarshallingTest(ProductType.BROADCAST)

    @ParameterizedTest
    @ValueSource(strings = ["errorMessage"])
    @NullSource
    fun testMarshallingPayload(errorMessage: String?) {
        testMarshallingPayload(
            null,
            null,
            null,
            null,
            null,
            null,
            EndReason.ERROR,
            null,
            errorMessage,
            "errorCode",
        )
    }

    @ParameterizedTest
    @ValueSource(strings = ["errorMessage"])
    @NullSource
    fun testUnmarshallingPayload(errorMessage: String?) {
        testUnmarshallingPayload(
            null,
            null,
            null,
            null,
            null,
            null,
            EndReason.ERROR,
            null,
            errorMessage,
            "errorCode",
        )
    }
}
