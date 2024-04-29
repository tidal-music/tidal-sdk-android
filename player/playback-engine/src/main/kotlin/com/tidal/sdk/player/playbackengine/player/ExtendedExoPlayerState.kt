package com.tidal.sdk.player.playbackengine.player

import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoListener

/**
 * Holds a [PlaybackInfoListener].
 */
internal class ExtendedExoPlayerState {
    var playbackInfoListener: PlaybackInfoListener? = null
    var currentPositionMs: Long = 0L
}
