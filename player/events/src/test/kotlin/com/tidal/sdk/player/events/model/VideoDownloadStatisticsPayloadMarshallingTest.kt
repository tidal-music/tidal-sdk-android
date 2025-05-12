package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality

internal class VideoDownloadStatisticsPayloadMarshallingTest :
    DownloadStatisticsPayloadMarshallingTest() {

    override val streamingSessionId = "streamingSessionId"
    override val startTimestamp = -3L
    override val actualProductId = "actualProductId"
    override val endTimestamp = -4L
    override val productType = ProductType.VIDEO
    override val payloadFactory =
        {
            assetPresentation: AssetPresentation,
            audioMode: AudioMode,
            productQuality: ProductQuality,
            endReason: EndReason,
            errorMessage: String?,
            errorCode: String? ->
            VideoDownloadStatistics.Payload(
                streamingSessionId,
                startTimestamp,
                actualProductId,
                assetPresentation,
                audioMode,
                productQuality as VideoQuality,
                endReason,
                endTimestamp,
                errorMessage,
                errorCode,
            )
        }
}
