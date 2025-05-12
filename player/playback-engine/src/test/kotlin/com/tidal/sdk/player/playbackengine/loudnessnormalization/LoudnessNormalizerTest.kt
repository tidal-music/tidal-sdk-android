package com.tidal.sdk.player.playbackengine.loudnessnormalization

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.playbackengine.volume.LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT
import com.tidal.sdk.player.playbackengine.volume.LOUDNESS_NORMALIZATION_PRE_AMP_TV
import com.tidal.sdk.player.playbackengine.volume.LoudnessNormalizer
import org.junit.jupiter.api.Test

internal class LoudnessNormalizerTest {

    private val loudnessNormalizer = LoudnessNormalizer()

    @Test
    fun getReducedGainShouldReturnCorrectValuesForRandomInputValues() {
        assertThat(
                loudnessNormalizer.getReducedGain(
                    -5.58F,
                    1.0F,
                    LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT,
                )
            )
            .isEqualTo(0.83368117F)

        assertThat(
                loudnessNormalizer.getReducedGain(
                    -8.24F,
                    1.0F,
                    LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT,
                )
            )
            .isEqualTo(0.613762F)

        assertThat(
                loudnessNormalizer.getReducedGain(
                    -12.87F,
                    1.0F,
                    LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT,
                )
            )
            .isEqualTo(0.36016378F)

        assertThat(
                loudnessNormalizer.getReducedGain(
                    -8.01F,
                    0.882751F,
                    LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT,
                )
            )
            .isEqualTo(0.6302313F)

        assertThat(
                loudnessNormalizer.getReducedGain(
                    -8.24F,
                    0.5F,
                    LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT,
                )
            )
            .isEqualTo(0.613762F)

        assertThat(
                loudnessNormalizer.getReducedGain(
                    -12.87F,
                    0.5F,
                    LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT,
                )
            )
            .isEqualTo(0.36016378F)
    }

    @Test
    fun getReducedGainShouldReturnCorrectValuesForRandomInputValuesForTV() {
        assertThat(
                loudnessNormalizer.getReducedGain(-5.58F, 1.0F, LOUDNESS_NORMALIZATION_PRE_AMP_TV)
            )
            .isEqualTo(0.5260173F)

        assertThat(
                loudnessNormalizer.getReducedGain(-8.24F, 1.0F, LOUDNESS_NORMALIZATION_PRE_AMP_TV)
            )
            .isEqualTo(0.38725764F)

        assertThat(
                loudnessNormalizer.getReducedGain(-12.87F, 1.0F, LOUDNESS_NORMALIZATION_PRE_AMP_TV)
            )
            .isEqualTo(0.22724798F)
    }

    @Test
    fun getReducedGainShouldReturnCorrectValuesForRandomInputValuesForRandomPreAmp() {
        assertThat(loudnessNormalizer.getReducedGain(-8.01F, 1.39F, 14)).isEqualTo(0.7194245F)

        assertThat(loudnessNormalizer.getReducedGain(-8.01F, 0.39F, 14)).isEqualTo(1.9929664F)
    }
}
