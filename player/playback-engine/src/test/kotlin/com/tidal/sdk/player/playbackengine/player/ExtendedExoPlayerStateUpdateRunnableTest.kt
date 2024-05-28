package com.tidal.sdk.player.playbackengine.player

import android.os.Handler
import androidx.media3.exoplayer.ExoPlayer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class ExtendedExoPlayerStateUpdateRunnableTest {

    private val extendedExoPlayerState = mock<ExtendedExoPlayerState>()
    private val exoPlayer = mock<ExoPlayer>()
    private val handler = mock<Handler>()
    private val extendedExoPlayerStateUpdateRunnable = ExtendedExoPlayerStateUpdateRunnable(
        extendedExoPlayerState,
        exoPlayer,
        handler,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(extendedExoPlayerState, exoPlayer, handler)

    @Test
    fun run() {
        val currentPosition = -2L
        whenever(exoPlayer.currentPosition) doReturn currentPosition

        extendedExoPlayerStateUpdateRunnable.run()

        verify(exoPlayer).currentPosition
        verify(extendedExoPlayerState).currentPositionMs = currentPosition
        verify(handler).postDelayed(
            extendedExoPlayerStateUpdateRunnable,
            ExtendedExoPlayerStateUpdateRunnable.reflectionDELAY_MILLIS,
        )
    }
}
