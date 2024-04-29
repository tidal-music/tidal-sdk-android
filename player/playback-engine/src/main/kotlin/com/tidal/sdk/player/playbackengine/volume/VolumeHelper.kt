package com.tidal.sdk.player.playbackengine.volume

import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

private const val FULL_VOLUME = 1.0F

const val LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT = 4
const val LOUDNESS_NORMALIZATION_PRE_AMP_TV = 0

/**
 * Intended to give the correct volume for given loudness configuration for a given [PlaybackInfo].
 */
internal class VolumeHelper(
    private val loudnessNormalizer: LoudnessNormalizer,
    var loudnessNormalizationMode: LoudnessNormalizationMode = LoudnessNormalizationMode.ALBUM,
    var loudnessNormalizationPreAmp: Int = LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT,
) {

    fun getVolume(playbackInfo: PlaybackInfo?) = playbackInfo?.let {
        when (loudnessNormalizationMode) {
            LoudnessNormalizationMode.NONE -> FULL_VOLUME
            LoudnessNormalizationMode.TRACK -> loudnessNormalizer.getReducedGain(
                it.trackReplayGain,
                it.trackPeakAmplitude,
                loudnessNormalizationPreAmp,
            )

            LoudnessNormalizationMode.ALBUM -> loudnessNormalizer.getReducedGain(
                it.albumReplayGain,
                it.albumPeakAmplitude,
                loudnessNormalizationPreAmp,
            )
        }
    } ?: FULL_VOLUME
}
