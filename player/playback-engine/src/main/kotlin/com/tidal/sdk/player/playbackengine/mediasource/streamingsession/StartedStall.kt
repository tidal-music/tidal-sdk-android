package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.events.model.PlaybackStatistics

internal data class StartedStall(
    val reason: PlaybackStatistics.Payload.Stall.Reason,
    val assetPositionSeconds: Double,
    val startTimestamp: Long,
)
