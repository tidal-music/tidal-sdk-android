package com.tidal.sdk.player.playbackengine

import android.os.Handler
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.playbackengine.audiomode.AudioModeRepository
import com.tidal.sdk.player.playbackengine.dj.DjSessionManager
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSource
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackStatistics
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDeviceManager
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerFactory
import com.tidal.sdk.player.playbackengine.player.PlayerCache
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.util.SynchronousSurfaceHolder
import com.tidal.sdk.player.playbackengine.volume.VolumeHelper
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val FADE_OUT_MS = 8_000L
private const val FADE_OUT_TICK_MS = 50L

internal class ExoPlayerPlaybackEnginePauseTest {

    private val player = mock<ExtendedExoPlayer> { on { it.playWhenReady } doReturn true }
    private val nextPlayer = mock<ExtendedExoPlayer>()
    private val internalHandler = mock<Handler>()
    private var nowMs = 0L
    private val trueTimeWrapper =
        mock<TrueTimeWrapper> { on { it.currentTimeMillis } doAnswer { nowMs } }
    private val volumeHelper = mock<VolumeHelper> { on { it.getVolume(anyOrNull()) } doReturn 1f }
    private lateinit var playbackEngine: ExoPlayerPlaybackEngine

    @BeforeEach
    fun beforeEach() {
        val extendedExoPlayerFactory =
            mock<ExtendedExoPlayerFactory> {
                on { it.create(any(), any()) }.doReturn(player, nextPlayer)
            }
        whenever(internalHandler.post(any())).then {
            (it.arguments.single() as Runnable).run()
            true
        }
        playbackEngine =
            ExoPlayerPlaybackEngine(
                CoroutineScope(StandardTestDispatcher()),
                extendedExoPlayerFactory,
                internalHandler,
                MutableSharedFlow<Event>(),
                mock<SynchronousSurfaceHolder.Factory>(),
                mock<StreamingPrivileges>(),
                mock<PlaybackContextFactory>(),
                mock<AudioQualityRepository>(),
                mock<AudioModeRepository>(),
                volumeHelper,
                trueTimeWrapper,
                mock(),
                mock<ErrorHandler>(),
                mock<DjSessionManager>(),
                mock(),
                mock<OutputDeviceManager>(),
                mock<PlayerCache.Internal>(),
                false,
            )
        whenever(player.load(any())).then { invocation ->
            val product = invocation.getArgument<ForwardingMediaProduct<MediaProduct>>(0)
            mock<PlaybackInfoMediaSource> { on { it.forwardingMediaProduct } doReturn product }
        }
        playbackEngine.load(MediaProduct(ProductType.TRACK, "1"))
        playbackEngine.reflectionSetPlaybackState(PlaybackState.PLAYING)
        // Not prepared yet, so neither play nor reset has anything to report.
        playbackEngine.reflectionCurrentPlaybackStatistics =
            PlaybackStatistics.Undetermined(
                UUID.randomUUID(),
                PlaybackStatistics.IdealStartTimestampMs.NotYetKnown,
                emptyList(),
                null,
                false,
            )
        clearInvocations(player, internalHandler)
    }

    @Test
    fun aFadeOutRampsTheVolumeDownThenPausesAndPutsItBack() {
        playbackEngine.pause(FADE_OUT_MS)
        val ticker = fadeOutTicker()

        nowMs = FADE_OUT_MS / 2
        ticker.run()
        verify(player).volume = 0.5f
        verify(player, never()).pause()

        nowMs = FADE_OUT_MS
        ticker.run()
        inOrder(player) {
            verify(player).pause()
            verify(player).volume = 1f
        }
    }

    @Test
    fun aLouderNormalizedVolumeFadesFromFull() {
        whenever(volumeHelper.getVolume(anyOrNull())) doReturn 1.5f
        playbackEngine.pause(FADE_OUT_MS)
        val ticker = fadeOutTicker()

        nowMs = FADE_OUT_MS / 4
        ticker.run()

        verify(player).volume = 0.75f
    }

    @Test
    fun aVolumeChangeDuringAFadeOutStaysFaded() {
        playbackEngine.pause(FADE_OUT_MS)
        nowMs = FADE_OUT_MS / 2

        playbackEngine.loudnessNormalizationPreAmp = 3

        verify(player).volume = 0.5f
    }

    @Test
    fun aSecondFadeOutCarriesOnFromTheVolumeTheFirstGotTo() {
        playbackEngine.pause(FADE_OUT_MS)
        val ticker = fadeOutTicker()
        nowMs = FADE_OUT_MS / 2
        ticker.run()

        playbackEngine.pause(1_000L)
        nowMs += 500L
        ticker.run()

        verify(player).volume = 0.25f
    }

    @Test
    fun aFadeOutWhilePausedJustPauses() {
        whenever(player.playWhenReady) doReturn false

        playbackEngine.pause(FADE_OUT_MS)

        verify(player).pause()
        verify(internalHandler, never()).postDelayed(any(), any())
    }

    @Test
    fun playDuringAFadeOutPutsTheVolumeBackAndKeepsPlaying() {
        playbackEngine.pause(FADE_OUT_MS)
        val ticker = fadeOutTicker()
        nowMs = FADE_OUT_MS / 2
        ticker.run()
        clearInvocations(player, internalHandler)

        playbackEngine.play()

        verify(player).volume = 1f
        verify(internalHandler).removeCallbacks(ticker)
        nowMs = FADE_OUT_MS
        ticker.run()
        verify(player, never()).pause()
    }

    @Test
    fun pauseDuringAFadeOutPausesStraightAway() {
        playbackEngine.pause(FADE_OUT_MS)
        val ticker = fadeOutTicker()
        nowMs = FADE_OUT_MS / 2
        ticker.run()
        clearInvocations(player, internalHandler)

        playbackEngine.pause()

        inOrder(player) {
            verify(player).pause()
            verify(player).volume = 1f
        }
        verify(internalHandler).removeCallbacks(ticker)
    }

    @Test
    fun skipToNextDuringAFadeOutPutsTheVolumeBack() {
        playbackEngine.pause(FADE_OUT_MS)
        val ticker = fadeOutTicker()
        nowMs = FADE_OUT_MS / 2
        ticker.run()
        clearInvocations(player, internalHandler)

        playbackEngine.skipToNext()

        verify(player).volume = 1f
        verify(player).seekToNextMediaItem()
        verify(internalHandler).removeCallbacks(ticker)
    }

    @Test
    fun resetDropsAFadeOut() {
        playbackEngine.pause(FADE_OUT_MS)
        val ticker = fadeOutTicker()

        playbackEngine.reset()
        nowMs = FADE_OUT_MS
        ticker.run()

        verify(player, never()).pause()
        verify(nextPlayer, never()).pause()
    }

    @Test
    fun pausingAtTheEndOfEachMediaProductPausesThePlayerAtTheEndOfEachItem() {
        playbackEngine.setPauseAtEndOfMediaProduct(true)
        verify(player).pauseAtEndOfMediaItems = true

        playbackEngine.setPauseAtEndOfMediaProduct(false)
        verify(player).pauseAtEndOfMediaItems = false
    }

    @Test
    fun pausingAtTheEndOfEachMediaProductCarriesOverToANewPlayer() {
        playbackEngine.setPauseAtEndOfMediaProduct(true)

        playbackEngine.reset()

        verify(nextPlayer).pauseAtEndOfMediaItems = true
    }

    @Test
    fun aNewPlayerIsLeftAloneWhileNotPausingAtTheEnd() {
        playbackEngine.reset()

        verify(nextPlayer, never()).pauseAtEndOfMediaItems = any()
    }

    private fun fadeOutTicker(): Runnable {
        val captor = argumentCaptor<Runnable>()
        verify(internalHandler, atLeastOnce()).postDelayed(captor.capture(), eq(FADE_OUT_TICK_MS))
        return captor.lastValue
    }
}
