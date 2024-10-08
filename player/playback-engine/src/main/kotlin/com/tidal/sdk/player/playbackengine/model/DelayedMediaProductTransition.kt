package com.tidal.sdk.player.playbackengine.model

import androidx.media3.exoplayer.analytics.AnalyticsListener.EventTime
import com.tidal.sdk.player.playbackengine.ExoPlayerPlaybackEngine
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSource
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackStatistics

internal data class DelayedMediaProductTransition(
    val from: PlaybackInfoMediaSource,
    val to: PlaybackInfoMediaSource,
    @Suppress("UnsafeOptInUsageError") val eventTime: EventTime,
    val invokedAtMillis: Long,
    val newPositionSeconds: Double,
) {

    operator fun invoke(
        exoPlayerPlaybackEngine: ExoPlayerPlaybackEngine,
        playbackContext: PlaybackContext,
        playbackStatistics: PlaybackStatistics.Undetermined,
        playbackSession: PlaybackSession,
    ) = exoPlayerPlaybackEngine.handleTransitionForRepeatOff(
        eventTime,
        invokedAtMillis,
        newPositionSeconds,
        playbackContext,
        playbackStatistics,
        playbackSession,
    )
}
