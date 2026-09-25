package com.tidal.sdk.player.playbackengine

import android.os.Handler
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.playbackengine.audiomode.AudioModeRepository
import com.tidal.sdk.player.playbackengine.dj.DjSessionManager
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSource
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackStatistics
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.UndeterminedPlaybackSessionResolver
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDeviceManager
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerFactory
import com.tidal.sdk.player.playbackengine.player.PlayerCache
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.util.SynchronousSurfaceHolder
import com.tidal.sdk.player.playbackengine.volume.VolumeHelper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.argThat
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val CROSSFADE_MS = 12_000L
private const val TRACK_MS = 200_000L
private const val PRELOAD_POSITION_MS = TRACK_MS - CROSSFADE_MS - 5_000L
private const val FADE_POSITION_MS = TRACK_MS - CROSSFADE_MS

internal class ExoPlayerPlaybackEngineCrossfadeTest {

    private val outgoing =
        mock<ExtendedExoPlayer> {
            on { it.duration } doReturn TRACK_MS
            on { it.currentPosition } doReturn PRELOAD_POSITION_MS
            on { it.repeatMode } doReturn Player.REPEAT_MODE_OFF
            on { it.playWhenReady } doReturn true
            on { it.playbackSuppressionReason } doReturn Player.PLAYBACK_SUPPRESSION_REASON_NONE
        }
    private val incoming =
        mock<ExtendedExoPlayer> {
            on { it.audioAttributes } doReturn AudioAttributes.DEFAULT
            on { it.duration } doReturn C.TIME_UNSET
            on { it.playbackState } doReturn Player.STATE_BUFFERING
        }
    private val incomingMediaSource = mock<PlaybackInfoMediaSource>()
    private val internalHandler = mock<Handler>()
    private val playbackContextFactory = mock<PlaybackContextFactory>()
    private val nextMediaProduct = MediaProduct(ProductType.TRACK, "2")
    private lateinit var playbackEngine: ExoPlayerPlaybackEngine
    private lateinit var ticker: Runnable

    @BeforeEach
    fun beforeEach() {
        val extendedExoPlayerFactory =
            mock<ExtendedExoPlayerFactory> {
                on { it.create(any(), any()) }.doReturn(outgoing, incoming)
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
                playbackContextFactory,
                mock<AudioQualityRepository>(),
                mock<AudioModeRepository>(),
                mock<VolumeHelper>(),
                mock<TrueTimeWrapper>(),
                mock<EventReporter>(),
                mock<ErrorHandler>(),
                mock<DjSessionManager>(),
                mock<UndeterminedPlaybackSessionResolver>(),
                mock<OutputDeviceManager>(),
                mock<PlayerCache.Internal>(),
            )
        whenever(outgoing.load(any())).then { invocation ->
            mediaSourceFor(invocation.getArgument(0))
        }
        whenever(outgoing.setNext(anyOrNull())).then { invocation ->
            invocation.getArgument<ForwardingMediaProduct<MediaProduct>?>(0)?.let(::mediaSourceFor)
        }
        whenever(incoming.loadAsNext(any())).then { invocation ->
            val product = invocation.getArgument<ForwardingMediaProduct<MediaProduct>>(0)
            whenever(incomingMediaSource.forwardingMediaProduct) doReturn product
            incomingMediaSource
        }
        playbackEngine.load(MediaProduct(ProductType.TRACK, "1"))
        playbackEngine.reflectionSetPlaybackState(PlaybackState.PLAYING)
        playbackEngine.reflectionPlaybackContext = trackContext(AudioMode.STEREO)
        playbackEngine.setNext(nextMediaProduct)

        playbackEngine.crossfadeDurationMs = CROSSFADE_MS
        val captor = argumentCaptor<Runnable>()
        verify(internalHandler).postDelayed(captor.capture(), eq(50L))
        ticker = captor.firstValue
        verify(incoming).loadAsNext(argThat { delegate === nextMediaProduct })
    }

    @Test
    fun fadesIntoALoadedTrack() {
        incomingIsLoaded(AudioMode.STEREO, durationMs = TRACK_MS)

        tickAt(FADE_POSITION_MS)

        verify(incoming).playWhenReady = true
        verify(incoming, never()).release()
    }

    @Test
    fun aShortIncomingTrackFallsBackToGaplessBeforeTheFade() {
        incomingIsLoaded(AudioMode.STEREO, durationMs = CROSSFADE_MS * 2)

        tickAt(PRELOAD_POSITION_MS)

        assertFellBackToGapless()
    }

    @Test
    fun anAtmosIncomingTrackFallsBackToGaplessBeforeTheFade() {
        incomingIsLoaded(AudioMode.DOLBY_ATMOS, durationMs = TRACK_MS)

        tickAt(PRELOAD_POSITION_MS)

        assertFellBackToGapless()
    }

    @Test
    fun aSlowIncomingTrackIsWaitedForUntilTheFadeWindow() {
        tickAt(PRELOAD_POSITION_MS)

        verify(incoming, never()).release()
    }

    @Test
    fun aSlowIncomingTrackFallsBackToGaplessWhenTheFadeWindowOpens() {
        tickAt(FADE_POSITION_MS)

        assertFellBackToGapless()
    }

    private fun incomingIsLoaded(audioMode: AudioMode, durationMs: Long) {
        val playbackInfo = mock<PlaybackInfo.Track>()
        val playbackContext = trackContext(audioMode)
        whenever(playbackContextFactory.create(eq(playbackInfo), anyOrNull()))
            .thenReturn(playbackContext)
        val playbackStatistics = mock<PlaybackStatistics.Undetermined>()
        val streamingSession =
            mock<StreamingSession.Implicit> {
                on { it.createUndeterminedPlaybackStatistics(any(), anyOrNull()) } doReturn
                    playbackStatistics
            }
        whenever(incomingMediaSource.playbackInfo) doReturn playbackInfo
        whenever(incoming.playbackState) doReturn Player.STATE_READY
        whenever(incoming.duration) doReturn durationMs
        playbackEngine.onPlaybackInfoFetched(
            streamingSession,
            incomingMediaSource.forwardingMediaProduct,
            playbackInfo,
        )
    }

    private fun tickAt(positionMs: Long) {
        whenever(outgoing.currentPosition) doReturn positionMs
        ticker.run()
    }

    private fun assertFellBackToGapless() {
        verify(incoming).release()
        verify(incoming, never()).playWhenReady = true
        verify(outgoing, times(2))
            .setNext(
                argThat<ForwardingMediaProduct<MediaProduct>> { delegate === nextMediaProduct }
            )
    }

    private fun mediaSourceFor(product: ForwardingMediaProduct<MediaProduct>) =
        mock<PlaybackInfoMediaSource> { on { it.forwardingMediaProduct } doReturn product }

    private fun trackContext(audioMode: AudioMode) =
        mock<PlaybackContext.Track> {
            on { it.audioMode } doReturn audioMode
            on { it.playbackSessionId } doReturn audioMode.name
        }
}
