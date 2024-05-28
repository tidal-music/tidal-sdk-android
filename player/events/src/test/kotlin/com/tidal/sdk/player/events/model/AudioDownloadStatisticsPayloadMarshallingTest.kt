package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType

internal class AudioDownloadStatisticsPayloadMarshallingTest :
    DownloadStatisticsPayloadMarshallingTest() {

    override val streamingSessionId = "streamingSessionId"
    override val startTimestamp = -3L
    override val actualProductId = "actualProductId"
    override val endTimestamp = -4L
    override val productType = ProductType.TRACK
    override val payloadFactory =
        {
                assetPresentation: AssetPresentation,
                audioMode: AudioMode,
                productQuality: ProductQuality,
                endReason: EndReason,
                errorMessage: String?,
                errorCode: String?,
            ->
            AudioDownloadStatistics.Payload(
                streamingSessionId,
                startTimestamp,
                actualProductId,
                assetPresentation,
                audioMode,
                productQuality as AudioQuality,
                endReason,
                endTimestamp,
                errorMessage,
                errorCode,
            )
        }
}
