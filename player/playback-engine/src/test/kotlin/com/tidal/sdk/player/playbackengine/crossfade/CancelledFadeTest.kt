package com.tidal.sdk.player.playbackengine.crossfade

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

private const val STARTED_AT_MS = 1_000L
private const val RAMP_MS = 250L

internal class CancelledFadeTest {

    private val incoming = mock<ExtendedExoPlayer> { on { it.volume } doReturn 0.8f }
    private val outgoing = mock<ExtendedExoPlayer>()
    private val cancelledFade = CancelledFade(incoming, 0f, STARTED_AT_MS)

    @Test
    fun rampsTheOutgoingPlayerUpAndTheIncomingOneDown() {
        val isRamping = cancelledFade.tick(outgoing, 1f, STARTED_AT_MS + RAMP_MS / 2)

        assertThat(isRamping).isTrue()
        verify(outgoing).volume = 0.5f
        verify(incoming).volume = 0.4f
        verify(incoming, never()).release()
    }

    @Test
    fun endsAtTheOutgoingPlayersOwnVolumeAndReleasesTheIncomingOne() {
        val isRamping = cancelledFade.tick(outgoing, 1.2f, STARTED_AT_MS + RAMP_MS)

        assertThat(isRamping).isFalse()
        verify(outgoing).volume = 1.2f
        verify(incoming).release()
    }

    @Test
    fun releaseStopsTheIncomingPlayer() {
        cancelledFade.release()

        verify(incoming).release()
    }
}
