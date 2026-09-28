package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.analytics.AnalyticsListener
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isSameInstanceAs
import assertk.assertions.isTrue
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

private const val CROSSFADE_MS = 12_000L
private const val TRACK_MS = 200_000L
private const val FADE_STARTED_AT_MS = 42L

internal class CrossfadeTest {

    private val incomingGain = mock<CrossfadeGainProcessor>()
    private val incoming =
        mock<ExtendedExoPlayer> {
            on { it.audioAttributes } doReturn AudioAttributes.DEFAULT
            on { it.crossfadeGain } doReturn incomingGain
        }
    private val outgoingGain = mock<CrossfadeGainProcessor>()
    private val outgoing =
        mock<ExtendedExoPlayer> {
            on { it.crossfadeGain } doReturn outgoingGain
            on { it.duration } doReturn TRACK_MS
            on { it.playWhenReady } doReturn true
            on { it.playbackSuppressionReason } doReturn Player.PLAYBACK_SUPPRESSION_REASON_NONE
        }
    private var failed: Crossfade? = null
    private val crossfade =
        Crossfade(incoming, mock<MediaProduct>(), CROSSFADE_MS, { FADE_STARTED_AT_MS }) {
            failed = it
        }

    @Test
    fun initSetsUpIncomingAsSilentPausedAndWithoutAudioFocus() {
        verify(incoming).analyticsListener = null
        verify(incoming).setAudioAttributes(AudioAttributes.DEFAULT, false)
        verify(incoming).volume = 0f
        verify(incoming).playWhenReady = false
        verify(incomingGain).fadeIn(CROSSFADE_MS)
    }

    @Test
    fun incomingErrorIsReportedWithTheCrossfade() {
        val listener = argumentCaptor<Player.Listener>()
        verify(incoming).addListener(listener.capture())

        listener.firstValue.onPlayerError(mock<PlaybackException>())

        assertThat(failed).isSameInstanceAs(crossfade)
    }

    @Test
    fun tickBeforeTheFadeWindowOnlySetsUpTheFadeOut() {
        whenever(incoming.playbackState) doReturn Player.STATE_READY
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS - 1

        crossfade.tick(outgoing, 1f, 1f)

        assertThat(crossfade.isFading).isFalse()
        verify(outgoingGain).fadeOut(TRACK_MS - CROSSFADE_MS, CROSSFADE_MS)
        verify(outgoing, never()).volume = any()
    }

    @Test
    fun tickInTheFadeWindowWaitsForIncomingToBeReady() {
        whenever(incoming.playbackState) doReturn Player.STATE_BUFFERING
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS

        crossfade.tick(outgoing, 1f, 1f)

        assertThat(crossfade.isFading).isFalse()
        verify(outgoingGain, never()).fadeOut(any(), any())
    }

    @Test
    fun tickWithUnknownDurationDoesNothing() {
        whenever(outgoing.duration) doReturn C.TIME_UNSET

        crossfade.tick(outgoing, 1f, 1f)

        assertThat(crossfade.fadeStartedAtMillis).isNull()
    }

    @Test
    fun tickStartsTheFadeAtTheNormalizedVolumes() {
        whenever(incoming.playbackState) doReturn Player.STATE_READY
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS

        crossfade.tick(outgoing, 0.8f, 0.5f)

        assertThat(crossfade.fadeStartedAtMillis).isEqualTo(FADE_STARTED_AT_MS)
        verify(incoming).playWhenReady = true
        verify(outgoing).volume = 0.8f
        verify(incoming).volume = 0.5f
    }

    @Test
    fun tickMirrorsAPausedOutgoingPlayer() {
        whenever(incoming.playbackState) doReturn Player.STATE_READY
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS
        whenever(outgoing.playWhenReady) doReturn false

        crossfade.tick(outgoing, 1f, 1f)

        verify(incoming, times(2)).playWhenReady = false
    }

    @Test
    fun followLeavesIncomingPausedBeforeTheFade() {
        crossfade.follow(outgoing)

        verify(incoming, never()).playWhenReady = true
    }

    @Test
    fun playOnlyStartsIncomingOnceFading() {
        crossfade.play()

        verify(incoming, never()).play()
    }

    @Test
    fun handOverReleasesOutgoingAndMovesListenerAndFocusToIncoming() {
        val analyticsListener = mock<AnalyticsListener>()

        val current = crossfade.handOver(outgoing, analyticsListener)

        assertThat(current).isSameInstanceAs(incoming)
        verify(outgoing).analyticsListener = null
        verify(outgoing).release()
        verify(incoming).analyticsListener = analyticsListener
        verify(incoming).setAudioAttributes(AudioAttributes.DEFAULT, true)
        verify(incoming).playWhenReady = true
        verify(incomingGain).clear()
    }

    @Test
    fun isDueWithinTheFadeAndPreloadWindow() {
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS - 15_000L

        assertThat(Crossfade.isDue(outgoing, CROSSFADE_MS)).isTrue()
    }

    @Test
    fun isDueIsFalseBeforeThePreloadWindow() {
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS - 15_001L

        assertThat(Crossfade.isDue(outgoing, CROSSFADE_MS)).isFalse()
    }

    @Test
    fun isDueIsFalseWithRepeatOne() {
        whenever(outgoing.repeatMode) doReturn Player.REPEAT_MODE_ONE
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS - 1

        assertThat(Crossfade.isDue(outgoing, CROSSFADE_MS)).isFalse()
    }

    @Test
    fun isDueIsFalseForTracksShorterThanTwoFades() {
        whenever(outgoing.duration) doReturn CROSSFADE_MS * 2
        whenever(outgoing.currentPosition) doReturn 0L

        assertThat(Crossfade.isDue(outgoing, CROSSFADE_MS)).isFalse()
    }

    @Test
    fun isDueIsFalseOnceTheFadeWindowHasOpened() {
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS

        assertThat(Crossfade.isDue(outgoing, CROSSFADE_MS)).isFalse()
    }

    @Test
    fun isInWindowFromTheFadeLengthBeforeTheEnd() {
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS

        assertThat(crossfade.isInWindow(outgoing)).isTrue()
    }

    @Test
    fun isInWindowIsFalseBeforeTheFadeLengthBeforeTheEnd() {
        whenever(outgoing.currentPosition) doReturn TRACK_MS - CROSSFADE_MS - 1

        assertThat(crossfade.isInWindow(outgoing)).isFalse()
    }

    @Test
    fun isInWindowIsFalseWithUnknownDuration() {
        whenever(outgoing.duration) doReturn C.TIME_UNSET

        assertThat(crossfade.isInWindow(outgoing)).isFalse()
    }

    @Test
    fun isLongEnoughOnlyForTracksLongerThanTwoFades() {
        assertThat(Crossfade.isLongEnough(CROSSFADE_MS * 2 + 1, CROSSFADE_MS)).isTrue()
        assertThat(Crossfade.isLongEnough(CROSSFADE_MS * 2, CROSSFADE_MS)).isFalse()
        assertThat(Crossfade.isLongEnough(C.TIME_UNSET, CROSSFADE_MS)).isFalse()
    }
}
