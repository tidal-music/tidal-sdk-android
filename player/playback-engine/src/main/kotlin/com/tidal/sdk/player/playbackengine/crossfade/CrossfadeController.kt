package com.tidal.sdk.player.playbackengine.crossfade

import android.os.Handler
import androidx.media3.common.C
import androidx.media3.common.Player
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.events.model.EndReason
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer

private const val MS_IN_SECOND = 1000L
private const val TICK_MS = 500L
private const val FADE_TICK_MS = 50L

/**
 * Everything the engine does differently for crossfade, kept apart from its gapless flow.
 *
 * While the current track may still crossfade, the next one is held off [Host.player] so it's only
 * loaded once, by whichever player ends up playing it. Once due, it's preloaded on a [Crossfade]
 * that fades it in, and the engine is asked to [Host.takeOver] when that player should become
 * current. Whenever the track won't crossfade after all, the next one is handed back for gapless.
 *
 * The engine only creates one when crossfade is enabled, and calls into it from the actions and
 * player events that crossfade changes. Each hook returns whether it handled the case, so the
 * engine carries on with its gapless flow when it didn't.
 */
@Suppress("TooManyFunctions")
internal class CrossfadeController(
    private val host: Host,
    private val handler: Handler,
    private val currentTimeMillis: () -> Long,
) {

    /** What the engine provides, and does on behalf of, a [CrossfadeController]. */
    interface Host {

        /** The current player. Changes when [takeOver] makes the incoming player current. */
        val player: ExtendedExoPlayer

        val currentProductType: ProductType?

        val playbackContext: PlaybackContext?

        /** The item set as next on [player], if any. */
        val nextProduct: MediaProduct?

        val nextPlaybackContext: PlaybackContext?

        /** Whether the engine is playing, with nothing delaying a move to the next item. */
        val isPlayingSteadily: Boolean

        /** Whether the engine has what it needs to report the next item as current. */
        val canReportNext: Boolean

        val isOperating: Boolean

        val isPausingAtEnd: Boolean

        fun createPlayer(): ExtendedExoPlayer

        /** Sets [mediaProduct] as next on [player], or takes the next one off it if null. */
        fun setNext(mediaProduct: MediaProduct?)

        /** Loads [mediaProduct] on [incoming] as the next item. */
        fun loadNext(incoming: ExtendedExoPlayer, mediaProduct: MediaProduct)

        /** Forgets the next item, which is on neither player any more. */
        fun forgetNext()

        fun currentVolume(): Float

        fun nextVolume(): Float

        fun updateVolume()

        /** Ends the current item and makes [crossfade]'s incoming player and item current. */
        fun takeOver(crossfade: Crossfade, endReason: EndReason, endPositionSeconds: Double)
    }

    var durationMs: Long = 0L
        set(value) {
            field = value
            handler.removeCallbacks(ticker)
            if (value > 0L) {
                holdNext()
                handler.post(ticker)
            } else {
                abort(restoreNext = true)
                releaseHeldNext()
            }
        }

    /** Whether the next item is loading on an incoming player rather than the current one. */
    val isPreloadingNext: Boolean
        get() = crossfade != null

    private var crossfade: Crossfade? = null

    /** The next item, kept off [Host.player] while the current one may still crossfade into it. */
    private var heldNext: MediaProduct? = null

    /** The next item [setNext] is holding back, if any. */
    val heldNextProduct: MediaProduct?
        get() = heldNext

    private var disabledForCurrent = false

    private var cancelledFade: CancelledFade? = null

    private val volumeRecoveryTicker = Runnable { host.updateVolume() }

    private val ticker =
        object : Runnable {
            override fun run() {
                tick()
                handler.postDelayed(this, nextTickDelayMs())
            }
        }

    /** Returns true if [mediaProduct] was taken as the next item, so the engine mustn't set it. */
    fun setNext(mediaProduct: MediaProduct?): Boolean {
        val crossfade = crossfade
        if (crossfade != null) {
            if (mediaProduct == crossfade.nextProduct) return true
            abort(restoreNext = false)
        }
        if (mediaProduct != null && mediaProduct == heldNext) return true

        heldNext = null
        if (mediaProduct != null && mayHold(mediaProduct)) {
            host.setNext(null)
            heldNext = mediaProduct
            return true
        }
        return false
    }

    fun play() {
        crossfade?.play()
    }

    fun pause() {
        crossfade?.pause()
        releaseCancelledFade()
    }

    /** Drops a fade a seek to [positionMs] would land in or skip, before the engine seeks. */
    fun onSeeking(positionMs: Long) {
        val crossfade = crossfade ?: return
        if (crossfade.isFading || crossfade.isInWindow(host.player, positionMs)) {
            abort(restoreNext = false)
            heldNext = crossfade.nextProduct
        }
    }

    /** After a seek to [positionMs], holds the next item again, or hands it over for gapless. */
    fun onSeeked(positionMs: Long) {
        // A seek back out of the window takes a next item handed over for gapless back.
        if (isPastStart(positionMs)) releaseHeldNext() else holdNext()
    }

    /** Returns true if it moved on to the next item itself, so the engine mustn't. */
    fun skipToNext(): Boolean {
        if (crossfade != null) {
            if (finish(EndReason.OTHER, host.player.currentPosition.toDouble() / MS_IN_SECOND)) {
                return true
            }
            abort(restoreNext = true)
        }
        releaseHeldNext()
        return false
    }

    /** Gives the next item back for gapless, as something now rules a crossfade out. */
    fun stepAside() {
        abort(restoreNext = true)
        releaseHeldNext()
    }

    /** Holds the next item again, as whatever ruled a crossfade out no longer does. */
    fun resume() = holdNext()

    /** Returns true if it moved on from the current item ending, so the engine mustn't. */
    fun onEnded(endPositionSeconds: Double): Boolean {
        if (crossfade != null) {
            if (!finish(EndReason.COMPLETE, endPositionSeconds)) {
                // The next item was kept off this player for the crossfade, so it'd end here.
                abort(restoreNext = true)
                host.player.seekToNextMediaItem()
            }
            return true
        }
        if (heldNext == null) return false
        releaseHeldNext()
        host.player.seekToNextMediaItem()
        return true
    }

    /** Returns true if it set the volume itself, as a fade is under way. */
    fun setVolume(volume: Float): Boolean {
        if (crossfade?.isFading == true) return true
        val cancelledFade = cancelledFade ?: return false
        handler.removeCallbacks(volumeRecoveryTicker)
        if (cancelledFade.tick(host.player, volume, currentTimeMillis())) {
            handler.postDelayed(volumeRecoveryTicker, FADE_TICK_MS)
        } else {
            this.cancelledFade = null
        }
        return true
    }

    /** Has the incoming player follow the current one's play/pause. */
    fun follow() {
        crossfade?.follow(host.player)
    }

    fun onCurrentChanged() {
        heldNext = null
        disabledForCurrent = false
        handler.removeCallbacks(volumeRecoveryTicker)
        releaseCancelledFade()
    }

    /** Drops any crossfade, as the engine is moving away from the current item itself. */
    fun abort() = abort(restoreNext = false)

    /** Picks back up after the engine has cleared every callback on [handler]. */
    fun restart() {
        releaseCancelledFade()
        if (durationMs > 0L) handler.post(ticker)
    }

    fun release() {
        abort(restoreNext = false)
        handler.removeCallbacks(ticker)
        handler.removeCallbacks(volumeRecoveryTicker)
        releaseCancelledFade()
    }

    /** Polls for the preload, then wakes up right as the fade window opens. */
    private fun nextTickDelayMs(): Long {
        val crossfade = crossfade
        if (crossfade?.isFading == true) return FADE_TICK_MS
        if (crossfade == null || !host.player.isPlaying) return TICK_MS
        val untilWindowMs = crossfade.msUntilWindow(host.player) ?: return TICK_MS
        return untilWindowMs.coerceIn(1L, TICK_MS)
    }

    private fun tick() {
        val crossfade = crossfade
        if (crossfade == null) {
            val heldNext = heldNext ?: return
            when {
                !mayHold(heldNext) -> releaseHeldNext()
                shouldPrepare() -> prepare(heldNext)
            }
            return
        }
        if (shouldFallBackToGapless(crossfade)) {
            disabledForCurrent = true
            abort(restoreNext = true)
            return
        }
        crossfade.tick(host.player, host.currentVolume(), host.nextVolume())
    }

    /** Whether, from what's known so far, the current item may still crossfade into [next]. */
    private fun mayHold(next: MediaProduct): Boolean {
        if (durationMs <= 0L || disabledForCurrent) return false
        if (host.isPausingAtEnd || host.player.repeatMode != Player.REPEAT_MODE_OFF) return false
        return host.currentProductType == ProductType.TRACK &&
            next.productType == ProductType.TRACK &&
            Crossfade.mayFade(host.playbackContext) &&
            Crossfade.mayBeLongEnough(host.player.duration, durationMs) &&
            !isPastStart()
    }

    /** Whether the current item is already past where its crossfade would have started. */
    private fun isPastStart(positionMs: Long = host.player.currentPosition) =
        Crossfade.isPastStart(host.player.duration, positionMs, durationMs)

    private fun shouldPrepare(): Boolean =
        host.isPlayingSteadily &&
            host.playbackContext is PlaybackContext.Track &&
            Crossfade.isDue(host.player, durationMs)

    /** Takes the next item off [Host.player] while it may still crossfade in. */
    private fun holdNext() {
        if (crossfade != null || heldNext != null) return
        val next = host.nextProduct ?: return
        if (!mayHold(next)) return
        host.setNext(null)
        heldNext = next
    }

    /** Hands [heldNext] to [Host.player] to play gaplessly, as it won't crossfade in. */
    private fun releaseHeldNext() {
        val heldNext = heldNext ?: return
        this.heldNext = null
        host.setNext(heldNext)
    }

    /** Whether [crossfade] should be dropped so the next item plays gaplessly instead. */
    private fun shouldFallBackToGapless(crossfade: Crossfade): Boolean {
        if (crossfade.isFading) return false
        if (cannotFadeInto(crossfade)) return true
        return crossfade.isInWindow(host.player) && !isReadyToFadeInto(crossfade)
    }

    /** Whether what's known so far about [crossfade]'s track already rules the fade out. */
    private fun cannotFadeInto(crossfade: Crossfade) =
        !Crossfade.mayFade(host.nextPlaybackContext) ||
            !Crossfade.mayBeLongEnough(crossfade.incoming.duration, crossfade.durationMs)

    /** Whether [crossfade]'s track is loaded far enough to start fading into it. */
    private fun isReadyToFadeInto(crossfade: Crossfade) =
        canTakeOver(crossfade) && crossfade.incoming.playbackState == Player.STATE_READY

    /** Whether the engine has everything it needs to make [crossfade]'s track current. */
    private fun canTakeOver(crossfade: Crossfade) =
        host.canReportNext && crossfade.incoming.duration != C.TIME_UNSET

    private fun prepare(nextProduct: MediaProduct) {
        heldNext = null
        releaseCancelledFade()
        val crossfade =
            Crossfade(host.createPlayer(), nextProduct, durationMs, currentTimeMillis) { failed ->
                if (this.crossfade !== failed) return@Crossfade
                disabledForCurrent = true
                abort(restoreNext = true)
            }
        this.crossfade = crossfade
        host.loadNext(crossfade.incoming, nextProduct)
    }

    private fun abort(restoreNext: Boolean) {
        val crossfade = crossfade ?: return
        this.crossfade = null
        host.forgetNext()
        releaseCancelledFade()
        if (crossfade.isFading) {
            cancelledFade =
                CancelledFade(crossfade.incoming, host.player.volume, currentTimeMillis())
        } else {
            crossfade.release()
        }
        host.updateVolume()
        if (restoreNext && host.isOperating) host.setNext(crossfade.nextProduct)
    }

    private fun releaseCancelledFade() {
        cancelledFade?.release()
        cancelledFade = null
    }

    /** Has the engine take [crossfade] over. Returns false if it isn't ready to. */
    private fun finish(endReason: EndReason, endPositionSeconds: Double): Boolean {
        val crossfade = crossfade?.takeIf(::canTakeOver) ?: return false
        this.crossfade = null
        host.takeOver(crossfade, endReason, endPositionSeconds)
        return true
    }
}
