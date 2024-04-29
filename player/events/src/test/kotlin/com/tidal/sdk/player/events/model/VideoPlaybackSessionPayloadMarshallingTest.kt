package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.common.model.VideoQuality
import java.util.UUID

internal class VideoPlaybackSessionPayloadMarshallingTest :
    PlaybackSessionPayloadMarshallingTest() {

    override val startTimestamp = 0L
    override val startAssetPositionSeconds = -2.0
    override val productType = ProductType.VIDEO
    override val requestedProductId = "3"
    override val actualProductId = "-1"
    override val sourceType = "sourceType"
    override val sourceId = "sourceId"
    override val actions = emptyList<PlaybackSession.Payload.Action>()
    override val endTimestamp = 3L
    override val endAssetPositionSeconds = 3.0
    override val payloadFactory =
        {
                playbackSessionId: UUID,
                assetPresentation: AssetPresentation,
                _: AudioMode?,
                productQuality: ProductQuality,
            ->
            VideoPlaybackSession.Payload(
                playbackSessionId,
                startTimestamp,
                startAssetPositionSeconds,
                requestedProductId,
                actualProductId,
                assetPresentation,
                productQuality as VideoQuality,
                sourceType,
                sourceId,
                actions,
                endTimestamp,
                endAssetPositionSeconds,
            )
        }
}
