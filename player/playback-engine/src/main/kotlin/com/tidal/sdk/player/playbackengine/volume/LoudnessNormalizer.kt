package com.tidal.sdk.player.playbackengine.volume

import kotlin.math.min
import kotlin.math.pow

internal class LoudnessNormalizer {

    fun getReducedGain(replayGain: Float, peakAmplitude: Float, preAmp: Int): Float {
        val replayGainLinear =
            REDUCED_GAIN_MULTIPLIER_CONSTANT.pow(
                (replayGain + preAmp) / REDUCED_GAIN_DIVIDER_CONSTANT
            )
        if (peakAmplitude <= 0) return min(replayGainLinear, 1.0f)
        return min(replayGainLinear, 1 / peakAmplitude)
    }

    companion object {
        private const val REDUCED_GAIN_MULTIPLIER_CONSTANT = 10.0F
        private const val REDUCED_GAIN_DIVIDER_CONSTANT = 20
    }
}
