package com.tidal.sdk.player.playbackengine.volume

/**
 * Moves a volume linearly from [from] to a target over [durationMs], starting at [startedAtMillis].
 * The target is passed on each read, so the ramp follows a level that changes along the way.
 */
internal class VolumeRamp(
    private val from: Float,
    private val durationMs: Long,
    private val startedAtMillis: Long,
) {

    fun isDoneAt(nowMillis: Long): Boolean = progressAt(nowMillis) >= 1f

    /** Players cap volume at 1, so a louder [target] is capped too. */
    fun volumeAt(nowMillis: Long, target: Float): Float =
        from + (target.coerceAtMost(1f) - from) * progressAt(nowMillis)

    private fun progressAt(nowMillis: Long): Float =
        ((nowMillis - startedAtMillis).toFloat() / durationMs).coerceIn(0f, 1f)
}
