package com.tidal.sdk.player.playbackengine.player

import android.os.Handler
import androidx.media3.exoplayer.ExoPlayer

internal class ExtendedExoPlayerStateUpdateRunnable(
    private val extendedExoPlayerState: ExtendedExoPlayerState,
    private val exoPlayer: ExoPlayer,
    private val handler: Handler,
) : Runnable {

    override fun run() {
        extendedExoPlayerState.currentPositionMs = exoPlayer.currentPosition
        handler.postDelayed(this, DELAY_MILLIS)
    }

    companion object {

        private const val DELAY_MILLIS = 200L
    }
}
