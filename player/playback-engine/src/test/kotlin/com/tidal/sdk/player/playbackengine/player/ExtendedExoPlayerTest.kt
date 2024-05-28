package com.tidal.sdk.player.playbackengine.player

import androidx.media3.common.C
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Timeline
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.analytics.AnalyticsListener
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.mediasource.MediaSourcerer
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoListener
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.NullSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class ExtendedExoPlayerTest {

    private val delegate = mock<ExoPlayer>()
    private val playerCache = mock<PlayerCache.Internal>()
    private val loadControl = mock<LoadControl>()
    private val mediaSourcerer = mock<MediaSourcerer>()
    private val extendedExoPlayerState = mock<ExtendedExoPlayerState>()
    private val extendedExoPlayer by lazy {
        ExtendedExoPlayer(
            delegate,
            playerCache,
            loadControl,
            mediaSourcerer,
            extendedExoPlayerState,
        )
    }

    @Test
    fun currentPosition() {
        val expectedPosition = 5000L
        whenever(extendedExoPlayerState.currentPositionMs) doReturn expectedPosition

        val actual = extendedExoPlayer.currentPositionMs

        verify(extendedExoPlayerState).currentPositionMs
        assertThat(actual).isEqualTo(expectedPosition)
    }

    @Test
    fun currentPositionSinceEpoch() {
        val expectedPosition = 246L
        val windowStartTimeMs = 123L
        val window = mock<Timeline.Window>()
        window.windowStartTimeMs = windowStartTimeMs
        val currentMediaItemIndex = 12
        whenever(delegate.currentMediaItemIndex) doReturn currentMediaItemIndex
        val currentTimeline = mock<Timeline> {
            on { it.getWindow(eq(currentMediaItemIndex), any()) } doReturn window
        }
        whenever(delegate.currentTimeline) doReturn currentTimeline
        val currentPosition = 123L
        whenever(delegate.currentPosition) doReturn currentPosition

        val actual = extendedExoPlayer.currentPositionSinceEpochMs

        verify(delegate).currentTimeline
        verify(delegate).currentMediaItemIndex
        verify(delegate).currentPosition
        assertThat(actual).isEqualTo(expectedPosition)
    }

    @Test
    fun getPositionSinceEpoch() {
        val expectedPosition = 246L
        val windowStartTimeMs = 123L
        val window = mock<Timeline.Window>()
        window.windowStartTimeMs = windowStartTimeMs
        val currentMediaItemIndex = 12
        whenever(delegate.currentMediaItemIndex) doReturn currentMediaItemIndex
        val currentTimeline = mock<Timeline> {
            on { it.getWindow(eq(currentMediaItemIndex), any()) } doReturn window
        }
        whenever(delegate.currentTimeline) doReturn currentTimeline
        val position = 123L

        val actual = extendedExoPlayer.getPositionSinceEpochMs(position)

        verify(delegate).currentTimeline
        verify(delegate).currentMediaItemIndex
        assertThat(actual).isEqualTo(expectedPosition)
    }

    @Test
    fun load() = runBlocking {
        val mediaProduct = ForwardingMediaProduct(MediaProduct(ProductType.TRACK, "1"))

        extendedExoPlayer.load(mediaProduct)

        verify(mediaSourcerer).load(mediaProduct)
        verify(delegate).prepare()
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("nextMediaProducts")
    fun next(mediaProduct: ForwardingMediaProduct<MediaProduct>?) = runBlocking {
        extendedExoPlayer.setNext(mediaProduct)

        verify(mediaSourcerer).setNext(mediaProduct)
    }

    @Test
    fun shouldStartPlaybackAfterUserActionDelegatesToLoadControl() {
        val totalBufferedDuration = 33L
        val speed = 1F
        val playbackParameters = PlaybackParameters(speed)
        whenever(delegate.playbackParameters).thenReturn(playbackParameters)
        whenever(extendedExoPlayer.totalBufferedDuration).thenReturn(totalBufferedDuration)
        whenever(
            loadControl.shouldStartPlayback(
                C.msToUs(totalBufferedDuration),
                speed,
                false,
                C.TIME_UNSET,
            ),
        ).thenReturn(true)

        val actual = extendedExoPlayer.shouldStartPlaybackAfterUserAction()

        verify(loadControl).shouldStartPlayback(
            C.msToUs(totalBufferedDuration),
            speed,
            false,
            C.TIME_UNSET,
        )
        assertThat(actual).isTrue()
    }

    @Test
    fun releaseForwardsToDelegateAndDontReleaseCache() {
        val playerCache = mock<PlayerCache.Provided> {
            on { cache } doReturn mock()
        }
        val extendedExoPlayer = ExtendedExoPlayer(
            delegate,
            playerCache,
            loadControl,
            mediaSourcerer,
            extendedExoPlayerState,
        )

        extendedExoPlayer.release()

        verify(delegate).release()
        verify(playerCache.cache, never()).release()
        verify(mediaSourcerer).release()
        verify(extendedExoPlayerState).playbackInfoListener = null
    }

    @Test
    fun releaseForwardsToDelegateAndReleasesCache() {
        val playerCache = mock<PlayerCache.Internal> {
            on { cache } doReturn mock()
        }
        val extendedExoPlayer = ExtendedExoPlayer(
            delegate,
            playerCache,
            loadControl,
            mediaSourcerer,
            extendedExoPlayerState,
        )

        extendedExoPlayer.release()

        verify(delegate).release()
        verify(playerCache.cache).release()
        verify(mediaSourcerer).release()
        verify(extendedExoPlayerState).playbackInfoListener = null
    }

    @Test
    fun setPlaybackInfoListener() {
        val playbackInfoListener = mock<PlaybackInfoListener>()

        extendedExoPlayer.setPlaybackInfoListener(playbackInfoListener)

        verify(extendedExoPlayerState).playbackInfoListener = playbackInfoListener
    }

    @Test
    fun updatePosition() {
        val positionMs = 123L

        extendedExoPlayer.updatePosition(positionMs)

        verify(extendedExoPlayerState).currentPositionMs = positionMs
    }

    @Test
    fun setNullAnalyticsListener() {
        val previousAnalyticsListener = mock<AnalyticsListener>()
        extendedExoPlayer.analyticsListener = previousAnalyticsListener
        reset(delegate)

        extendedExoPlayer.analyticsListener = null

        verify(delegate).removeAnalyticsListener(previousAnalyticsListener)
        verifyNoMoreInteractions(previousAnalyticsListener, delegate)
    }

    @Test
    fun setNonNullAnalyticsListener() {
        val previousAnalyticsListener = mock<AnalyticsListener>()
        val newAnalyticsListener = mock<AnalyticsListener>()
        extendedExoPlayer.analyticsListener = previousAnalyticsListener
        reset(delegate)

        extendedExoPlayer.analyticsListener = newAnalyticsListener

        verify(delegate).removeAnalyticsListener(previousAnalyticsListener)
        verify(delegate).addAnalyticsListener(newAnalyticsListener)
        verifyNoMoreInteractions(previousAnalyticsListener, newAnalyticsListener, delegate)
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun nextMediaProducts() = setOf(
            MediaProduct(ProductType.TRACK, "2"),
            MediaProduct(ProductType.VIDEO, "3"),
        ).map {
            Arguments.of(ForwardingMediaProduct(it))
        }
    }
}
