package com.tidal.sdk.player.playbackengine.player

import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoListener
import com.tidal.sdk.player.playbackengine.player.di.ExtendedExoPlayerComponent

internal class ExtendedExoPlayerFactory(
    private val extendedExoPlayerComponentFactory: ExtendedExoPlayerComponent.Factory,
) {
    fun create(
        playbackInfoListener: PlaybackInfoListener,
        analyticsListener: AnalyticsListener,
    ): ExtendedExoPlayer {
        val component = extendedExoPlayerComponentFactory.create()
        component.handler.post(component.stateUpdateRunnable)
        return component.extendedExoPlayer.apply {
            this.analyticsListener = analyticsListener
            setPlaybackInfoListener(playbackInfoListener)
        }
    }
}
