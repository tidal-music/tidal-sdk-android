package com.tidal.sdk.player.playbackengine.player

import android.os.Handler
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoListener
import com.tidal.sdk.player.playbackengine.player.di.ExtendedExoPlayerComponent
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ExtendedExoPlayerFactoryTest {

    private val extendedExoPlayerComponentFactory = mock<ExtendedExoPlayerComponent.Factory>()
    private val playbackInfoListener = mock<PlaybackInfoListener>()
    private val analyticsListener = mock<AnalyticsListener>()
    private val extendedExoPlayerFactory =
        ExtendedExoPlayerFactory(extendedExoPlayerComponentFactory)

    @Test
    fun createReturnsPlayerFromTheComponent() {
        val extendedExoPlayer = mock<ExtendedExoPlayer>()
        val handler = mock<Handler>()
        val stateUpdateRunnable = mock<ExtendedExoPlayerStateUpdateRunnable>()
        val extendedExoPlayerComponent =
            mock<ExtendedExoPlayerComponent> {
                on { it.extendedExoPlayer } doReturn extendedExoPlayer
                on { it.handler } doReturn handler
                on { it.stateUpdateRunnable } doReturn stateUpdateRunnable
            }
        whenever(extendedExoPlayerComponentFactory.create()) doReturn extendedExoPlayerComponent

        val actual = extendedExoPlayerFactory.create(playbackInfoListener, analyticsListener)

        assertSame(extendedExoPlayer, actual)
        verify(extendedExoPlayer).analyticsListener = analyticsListener
        verify(extendedExoPlayer).setPlaybackInfoListener(playbackInfoListener)
        verify(handler).post(stateUpdateRunnable)
    }

    @Test
    fun createReturnsPlayerFromTheComponentAndDoesNotCrashIfReceiverIsNull() {
        val extendedExoPlayer = mock<ExtendedExoPlayer>()
        val handler = mock<Handler>()
        val stateUpdateRunnable = mock<ExtendedExoPlayerStateUpdateRunnable>()
        val extendedExoPlayerComponent =
            mock<ExtendedExoPlayerComponent> {
                on { it.extendedExoPlayer } doReturn extendedExoPlayer
                on { it.handler } doReturn handler
                on { it.stateUpdateRunnable } doReturn stateUpdateRunnable
            }
        whenever(extendedExoPlayerComponentFactory.create()) doReturn extendedExoPlayerComponent

        val actual = extendedExoPlayerFactory.create(playbackInfoListener, analyticsListener)

        assertSame(extendedExoPlayer, actual)
        verify(extendedExoPlayer).analyticsListener = analyticsListener
        verify(extendedExoPlayer).setPlaybackInfoListener(playbackInfoListener)
        verify(handler).post(stateUpdateRunnable)
    }
}
