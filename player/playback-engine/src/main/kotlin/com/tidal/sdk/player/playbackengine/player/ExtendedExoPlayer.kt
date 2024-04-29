package com.tidal.sdk.player.playbackengine.player

import androidx.media3.common.C
import androidx.media3.common.Timeline
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.mediasource.MediaSourcerer
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSource
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoListener
import kotlin.properties.Delegates

/**
 * A proxy to an [ExoPlayer] instance which defines requirements for additional functionality to
 * satisfy library capabilities.
 * @param delegate An [ExoPlayer] instance that all calls needing one will utilize.
 * @param playerCache A [PlayerCache] instance that will be used to cache assets.
 * @param loadControl A [LoadControl] instance that controls the buffering of media.
 * @param mediaSourcerer A [MediaSourcerer] instance that holds the MediaSource of what we play.
 * @param extendedExoPlayerState A [ExtendedExoPlayerState] instance that holds the some shared
 * state for ExtendedExoPlayer.
 */
internal class ExtendedExoPlayer(
    private val delegate: ExoPlayer,
    private val playerCache: PlayerCache,
    private val loadControl: LoadControl,
    private val mediaSourcerer: MediaSourcerer,
    private val extendedExoPlayerState: ExtendedExoPlayerState,
) : ExoPlayer by delegate {

    val currentPositionMs: Long
        get() = extendedExoPlayerState.currentPositionMs

    val currentPositionSinceEpochMs: Long
        get() {
            return getPositionSinceEpochMs(currentPosition)
        }

    val currentStreamingSession by mediaSourcerer::currentStreamingSession

    val nextStreamingSession by mediaSourcerer::nextStreamingSession

    var analyticsListener: AnalyticsListener? by
        Delegates.observable(null) { _, oldValue, newValue ->
            oldValue?.let { delegate.removeAnalyticsListener(it) }
            newValue?.let { delegate.addAnalyticsListener(it) }
        }

    fun getPositionSinceEpochMs(positionMs: Long): Long {
        val window = currentTimeline.getWindow(currentMediaItemIndex, Timeline.Window())
        return window.windowStartTimeMs + positionMs
    }

    fun load(
        forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>,
    ): PlaybackInfoMediaSource {
        val playbackInfoMediaSource = mediaSourcerer.load(forwardingMediaProduct)
        delegate.prepare()
        return playbackInfoMediaSource
    }

    fun setNext(forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>?) =
        mediaSourcerer.setNext(forwardingMediaProduct)

    fun shouldStartPlaybackAfterUserAction() = loadControl.shouldStartPlayback(
        C.msToUs(delegate.totalBufferedDuration),
        delegate.playbackParameters.speed,
        false,
        C.TIME_UNSET,
    )

    fun onCurrentItemFinished() {
        mediaSourcerer.onCurrentItemFinished()
    }

    fun onRepeatOne(forwardingMediaProduct: ForwardingMediaProduct<*>) =
        mediaSourcerer.onRepeatOne(forwardingMediaProduct)

    override fun release() {
        delegate.release()
        if (playerCache is PlayerCache.Internal) {
            playerCache.cache.release()
        }
        mediaSourcerer.release()
        extendedExoPlayerState.playbackInfoListener = null
        analyticsListener = null
    }

    fun setPlaybackInfoListener(playbackInfoListener: PlaybackInfoListener) {
        extendedExoPlayerState.playbackInfoListener = playbackInfoListener
    }

    fun updatePosition(positionMs: Long) {
        extendedExoPlayerState.currentPositionMs = positionMs
    }
}
