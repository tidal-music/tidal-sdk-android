package com.tidal.sdk.player.playbackengine.quality

import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.playbackengine.network.NetworkTransportHelper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class AudioQualityRepositoryTest {

    private val networkTransportHelper = mock<NetworkTransportHelper>()

    @AfterEach fun afterEach() = verifyNoMoreInteractions(networkTransportHelper)

    @ParameterizedTest
    @EnumSource(AudioQuality::class)
    fun getStreamingQualityShouldReturnCorrectWhenWifi(audioQuality: AudioQuality) {
        val audioQualityRepository = AudioQualityRepository(networkTransportHelper, audioQuality)
        whenever(networkTransportHelper.isWifiOrEthernet()).thenReturn(true)

        val actual = audioQualityRepository.streamingQuality

        assertThat(actual).isSameInstanceAs(audioQuality)
        verify(networkTransportHelper).isWifiOrEthernet()
    }

    @ParameterizedTest
    @EnumSource(AudioQuality::class)
    fun getStreamingQualityShouldReturnCorrectWhenCellular(audioQuality: AudioQuality) {
        val audioQualityRepository =
            AudioQualityRepository(
                networkTransportHelper,
                streamingCellularAudioQuality = audioQuality,
            )
        whenever(networkTransportHelper.isWifiOrEthernet()).thenReturn(false)

        val actual = audioQualityRepository.streamingQuality

        assertThat(actual).isSameInstanceAs(audioQuality)
        verify(networkTransportHelper).isWifiOrEthernet()
    }
}
