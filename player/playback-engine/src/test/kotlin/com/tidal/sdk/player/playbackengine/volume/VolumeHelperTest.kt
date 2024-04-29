package com.tidal.sdk.player.playbackengine.volume

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class VolumeHelperTest {

    private val loudnessNormalizer = mock<LoudnessNormalizer>()

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(loudnessNormalizer)

    @Test
    fun getVolumeWhenPlaybackInfoIsNull() {
        val actualVolume = VolumeHelper(loudnessNormalizer).getVolume(null)

        assertThat(actualVolume).isEqualTo(1.0F)
    }

    @Test
    fun getVolumeWhenLoudnessNormalizationModeIsNone() {
        val volumeHelper = VolumeHelper(loudnessNormalizer, LoudnessNormalizationMode.NONE)

        val actualVolume = volumeHelper.getVolume(mock<PlaybackInfo.Track>())

        assertThat(actualVolume).isEqualTo(1.0F)
    }

    @Test
    fun getVolumeWhenLoudnessNormalizationModeIsTrack() {
        val replayGain = -10.98F
        val peakAmplitude = 0.997437F
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { it.trackReplayGain } doReturn replayGain
            on { it.trackPeakAmplitude } doReturn peakAmplitude
        }
        val preAmp = 4
        val expectedVolume = 0.44771332F
        val volumeHelper = VolumeHelper(loudnessNormalizer, LoudnessNormalizationMode.TRACK, preAmp)
        whenever(loudnessNormalizer.getReducedGain(replayGain, peakAmplitude, preAmp))
            .thenReturn(expectedVolume)

        val actualVolume = volumeHelper.getVolume(playbackInfo)

        assertThat(actualVolume).isEqualTo(expectedVolume)
        verify(playbackInfo).trackReplayGain
        verify(playbackInfo).trackPeakAmplitude
        verify(loudnessNormalizer).getReducedGain(replayGain, peakAmplitude, preAmp)
        verifyNoMoreInteractions(playbackInfo)
    }

    @Test
    fun getVolumeWhenLoudnessNormalizationModeIsAlbum() {
        val replayGain = -10.98F
        val peakAmplitude = 0.997437F
        val playbackInfo = mock<PlaybackInfo.Track> {
            on { it.albumReplayGain } doReturn replayGain
            on { it.albumPeakAmplitude } doReturn peakAmplitude
        }
        val preAmp = 4
        val expectedVolume = 0.44771332F
        val volumeHelper = VolumeHelper(loudnessNormalizer, LoudnessNormalizationMode.ALBUM, preAmp)
        whenever(loudnessNormalizer.getReducedGain(replayGain, peakAmplitude, preAmp))
            .thenReturn(expectedVolume)

        val actualVolume = volumeHelper.getVolume(playbackInfo)

        assertThat(actualVolume).isEqualTo(expectedVolume)
        verify(playbackInfo).albumReplayGain
        verify(playbackInfo).albumPeakAmplitude
        verify(loudnessNormalizer).getReducedGain(replayGain, peakAmplitude, preAmp)
        verifyNoMoreInteractions(playbackInfo)
    }
}
