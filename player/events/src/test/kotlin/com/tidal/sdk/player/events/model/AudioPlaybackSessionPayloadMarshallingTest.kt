package com.tidal.sdk.player.events.model

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.ProductQuality
import com.tidal.sdk.player.common.model.ProductType
import java.util.UUID

internal class AudioPlaybackSessionPayloadMarshallingTest :
    PlaybackSessionPayloadMarshallingTest() {

    override val startTimestamp = 0L
    override val startAssetPositionSeconds = -2.0
    override val productType = ProductType.TRACK
    override val requestedProductId = "10"
    override val actualProductId = "5"
    override val sourceType = "sourceType"
    override val sourceId = "sourceId"
    override val actions = listOf(
        PlaybackSession.Payload.Action(
            1L,
            1.0,
            PlaybackSession.Payload.Action.Type.PLAYBACK_STOP,
        ),
        PlaybackSession.Payload.Action(
            2L,
            2.0,
            PlaybackSession.Payload.Action.Type.PLAYBACK_START,
        ),
    )
    override val endTimestamp = 3L
    override val endAssetPositionSeconds = 3.0
    override val payloadFactory =
        {
                playbackSessionId: UUID,
                assetPresentation: AssetPresentation,
                audioMode: AudioMode,
                productQuality: ProductQuality,
            ->
            AudioPlaybackSession.Payload(
                playbackSessionId,
                startTimestamp,
                startAssetPositionSeconds,
                requestedProductId,
                actualProductId,
                assetPresentation,
                audioMode,
                productQuality as AudioQuality,
                sourceType,
                sourceId,
                actions,
                endTimestamp,
                endAssetPositionSeconds,
            )
        }
}
