package com.tidal.sdk.player.playbackengine.crossfade

import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import com.tidal.sdk.player.playbackengine.volume.VolumeRamp

private const val RAMP_MS = 250L

/**
 * A fade cut short, ramping the outgoing player back up from [outgoingVolume] and [incoming] down
 * to silence from wherever the fade had got to, instead of cutting. Releases [incoming] once done.
 */
internal class CancelledFade(
    private val incoming: ExtendedExoPlayer,
    outgoingVolume: Float,
    startedAtMillis: Long,
) {

    private val outgoingRamp = VolumeRamp(outgoingVolume, RAMP_MS, startedAtMillis)
    private val incomingRamp = VolumeRamp(incoming.volume, RAMP_MS, startedAtMillis)

    /**
     * Sets both players' volumes for [nowMillis], with [outgoingTarget] as the outgoing player's
     * own volume. Returns false once the ramp is done and [incoming] released.
     */
    fun tick(outgoing: ExtendedExoPlayer, outgoingTarget: Float, nowMillis: Long): Boolean {
        if (outgoingRamp.isDoneAt(nowMillis)) {
            outgoing.volume = outgoingTarget
            release()
            return false
        }
        outgoing.volume = outgoingRamp.volumeAt(nowMillis, outgoingTarget)
        incoming.volume = incomingRamp.volumeAt(nowMillis, 0f)
        return true
    }

    fun release() = incoming.release()
}
