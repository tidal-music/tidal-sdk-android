package com.tidal.sdk.player.playbackengine.volume

import android.os.Handler

private const val TICK_MS = 50L

/**
 * A fade of the volume down to silence, stepping through [onStep] so the volume can be set to
 * [level], then calling [onSilent] once there.
 */
internal class FadeOut(
    private val handler: Handler,
    private val currentTimeMillis: () -> Long,
    private val onStep: () -> Unit,
    private val onSilent: () -> Unit,
) {

    private var ramp: VolumeRamp? = null

    private val ticker = Runnable { tick() }

    val isActive: Boolean
        get() = ramp != null

    /** How far down the fade has got, from 1 at the start to 0 at silence. */
    val level: Float
        get() = levelAt(currentTimeMillis())

    /** Fades over [durationMs], starting from wherever a fade already under way had got to. */
    fun start(durationMs: Long) {
        val nowMillis = currentTimeMillis()
        ramp = VolumeRamp(levelAt(nowMillis), durationMs, nowMillis)
        handler.removeCallbacks(ticker)
        tick()
    }

    /** Stops the fade. Returns whether one was under way. */
    fun cancel(): Boolean {
        if (ramp == null) return false
        ramp = null
        handler.removeCallbacks(ticker)
        return true
    }

    /** Forgets the fade, for when [handler]'s callbacks have already been cleared. */
    fun clear() {
        ramp = null
    }

    private fun levelAt(nowMillis: Long): Float = ramp?.volumeAt(nowMillis, 0f) ?: 1f

    private fun tick() {
        val ramp = ramp ?: return
        if (ramp.isDoneAt(currentTimeMillis())) {
            onSilent()
            return
        }
        onStep()
        handler.postDelayed(ticker, TICK_MS)
    }
}
