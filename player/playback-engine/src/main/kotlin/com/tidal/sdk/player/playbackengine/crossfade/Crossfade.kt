package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.C
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer

private const val PREPARE_LEAD_MS = 15_000L

/**
 * The next track preloaded on its own silent [incoming] player, fading in over the last
 * [durationMs] of the outgoing one.
 *
 * Owns the fade itself: the incoming player's setup, the S-curve each player's
 * [CrossfadeGainProcessor] applies, mirroring the outgoing player's play/pause, and swapping the
 * players at the end. Moving the reporting state over to the incoming track is left to the engine,
 * which owns it.
 */
internal class Crossfade(
    val incoming: ExtendedExoPlayer,
    val nextProduct: MediaProduct,
    val durationMs: Long,
    private val currentTimeMillis: () -> Long,
    onIncomingError: (Crossfade) -> Unit,
) {

    /** When the incoming player started playing, or null while it's still waiting. */
    var fadeStartedAtMillis: Long? = null
        private set

    val isFading: Boolean
        get() = fadeStartedAtMillis != null

    init {
        incoming.apply {
            analyticsListener = null
            setAudioAttributes(audioAttributes, false)
            volume = 0f
            playWhenReady = false
            crossfadeGain.fadeIn(durationMs)
            addListener(
                object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) =
                        onIncomingError(this@Crossfade)
                }
            )
        }
    }

    /** Whether [outgoing] is within [durationMs] of its end. */
    fun isInWindow(outgoing: ExtendedExoPlayer): Boolean =
        remainingMs(outgoing)?.let { it <= durationMs } ?: false

    /**
     * Starts the fade once the outgoing player is within [durationMs] of its end and the incoming
     * one is ready, then keeps both players in step. Call it periodically.
     */
    fun tick(outgoing: ExtendedExoPlayer, outgoingVolume: Float, incomingVolume: Float) {
        val remainingMs = remainingMs(outgoing) ?: return
        outgoing.crossfadeGain.fadeOut(outgoing.duration - durationMs, durationMs)
        if (!isFading) {
            if (remainingMs > durationMs || incoming.playbackState != Player.STATE_READY) return
            fadeStartedAtMillis = currentTimeMillis()
        }
        incoming.playWhenReady =
            outgoing.playWhenReady &&
                outgoing.playbackSuppressionReason == Player.PLAYBACK_SUPPRESSION_REASON_NONE
        outgoing.volume = outgoingVolume
        incoming.volume = incomingVolume
    }

    fun play() {
        if (isFading) incoming.play()
    }

    fun pause() = incoming.pause()

    fun release() = incoming.release()

    /**
     * Releases [outgoing] and gives [incoming] the [analyticsListener] and audio focus it had.
     * Returns [incoming], now the current player.
     */
    fun handOver(
        outgoing: ExtendedExoPlayer,
        analyticsListener: AnalyticsListener,
    ): ExtendedExoPlayer {
        val playWhenReady = outgoing.playWhenReady
        outgoing.analyticsListener = null
        outgoing.release()
        return incoming.apply {
            crossfadeGain.clear()
            this.analyticsListener = analyticsListener
            setAudioAttributes(audioAttributes, true)
            this.playWhenReady = playWhenReady
        }
    }

    private fun remainingMs(outgoing: ExtendedExoPlayer): Long? {
        val outgoingDurationMs = outgoing.duration
        if (outgoingDurationMs == C.TIME_UNSET) return null
        return (outgoingDurationMs - outgoing.currentPosition).coerceAtLeast(0L)
    }

    companion object {

        /**
         * Whether [current] is close enough to its end to preload the next track, but not yet in
         * the fade window.
         */
        fun isDue(current: ExtendedExoPlayer, crossfadeDurationMs: Long): Boolean {
            val durationMs = current.duration
            if (
                current.repeatMode != Player.REPEAT_MODE_OFF ||
                    !isLongEnough(durationMs, crossfadeDurationMs)
            ) {
                return false
            }
            val remainingMs = durationMs - current.currentPosition
            return remainingMs > crossfadeDurationMs &&
                remainingMs <= crossfadeDurationMs + PREPARE_LEAD_MS
        }

        /** Whether a track of [durationMs] is long enough to fade in over [crossfadeDurationMs]. */
        fun isLongEnough(durationMs: Long, crossfadeDurationMs: Long): Boolean =
            durationMs != C.TIME_UNSET && durationMs > crossfadeDurationMs * 2
    }
}
