package com.tidal.sdk.player.playbackengine.quality

import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.common.model.VideoQuality
import org.junit.jupiter.api.Test

internal class VideoQualityRepositoryTest {

    private val videoQualityRepository = VideoQualityRepository()

    @Test
    fun getStreamingQualityShouldReturnCorrect() {
        val actual = videoQualityRepository.streamingQuality

        assertThat(actual).isSameAs(VideoQuality.HIGH)
    }
}
