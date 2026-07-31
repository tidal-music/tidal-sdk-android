package com.tidal.sdk.player.playbackengine.player.trackselection

import androidx.media3.common.Format
import androidx.media3.common.MimeTypes
import androidx.media3.common.Timeline
import androidx.media3.common.TrackGroup
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.trackselection.ExoTrackSelection
import androidx.media3.exoplayer.upstream.BandwidthMeter
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isSameInstanceAs
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * The fixture mirrors a real adaptive DASH manifest from the trackManifests API: a single
 * adaptation set with EAC3_JOC (signalled as plain codecs="ec-3", so parsed as audio/eac3, 769
 * kbps, 48 kHz) plus FLAC, AACLC and HEAACV1 stereo representations at 44.1 kHz.
 */
internal class TidalTrackSelectionFactoryTest {

    private val delegate = mock<ExoTrackSelection.Factory>()
    private val tidalTrackSelectionFactory = TidalTrackSelectionFactory(delegate)

    private val bandwidthMeter = mock<BandwidthMeter>()
    private val mediaPeriodId = MediaSource.MediaPeriodId("periodUid")
    private val timeline = Timeline.EMPTY

    private val eac3Format =
        Format.Builder()
            .setId("eac3joc")
            .setSampleMimeType(MimeTypes.AUDIO_E_AC3)
            .setCodecs("ec-3")
            .setAverageBitrate(769_000)
            .setPeakBitrate(769_000)
            .setSampleRate(48_000)
            .setChannelCount(2)
            .build()
    private val flacFormat =
        Format.Builder()
            .setId("flac")
            .setSampleMimeType(MimeTypes.AUDIO_FLAC)
            .setCodecs("flac")
            .setAverageBitrate(266_000)
            .setPeakBitrate(266_000)
            .setSampleRate(44_100)
            .setChannelCount(2)
            .build()
    private val aacLcFormat =
        Format.Builder()
            .setId("aaclc")
            .setSampleMimeType(MimeTypes.AUDIO_AAC)
            .setCodecs("mp4a.40.2")
            .setAverageBitrate(322_000)
            .setPeakBitrate(322_000)
            .setSampleRate(44_100)
            .setChannelCount(2)
            .build()
    private val heAacV1Format =
        Format.Builder()
            .setId("heaacv1")
            .setSampleMimeType(MimeTypes.AUDIO_AAC)
            .setCodecs("mp4a.40.5")
            .setAverageBitrate(97_000)
            .setPeakBitrate(97_000)
            .setSampleRate(44_100)
            .setChannelCount(2)
            .build()

    private val mixedTrackGroup = trackGroupOf(eac3Format, flacFormat, aacLcFormat, heAacV1Format)

    /**
     * [TrackGroup]'s constructor is not usable in unit tests (it relies on unmocked android.jar
     * methods), so a mock serving the same formats is used instead.
     */
    private fun trackGroupOf(vararg formats: Format) =
        mock<TrackGroup> {
            formats.forEachIndexed { index, format -> on { getFormat(index) } doReturn format }
        }

    private fun createTrackSelections(
        definitions: Array<ExoTrackSelection.Definition?>
    ): Array<out ExoTrackSelection.Definition?> {
        val expectedResult = emptyArray<ExoTrackSelection?>()
        whenever(
                delegate.createTrackSelections(
                    any(),
                    eq(bandwidthMeter),
                    eq(mediaPeriodId),
                    eq(timeline),
                )
            )
            .doReturn(expectedResult)

        val actualResult =
            tidalTrackSelectionFactory.createTrackSelections(
                definitions,
                bandwidthMeter,
                mediaPeriodId,
                timeline,
            )

        val definitionsCaptor = argumentCaptor<Array<ExoTrackSelection.Definition?>>()
        verify(delegate)
            .createTrackSelections(
                definitionsCaptor.capture(),
                eq(bandwidthMeter),
                eq(mediaPeriodId),
                eq(timeline),
            )
        assertThat(actualResult).isSameInstanceAs(expectedResult)
        return definitionsCaptor.firstValue
    }

    @Test
    fun mixedDefinitionIsPinnedToEac3() {
        val definition = ExoTrackSelection.Definition(mixedTrackGroup, 0, 1, 2, 3)

        val adjustedDefinitions = createTrackSelections(arrayOf(definition))

        val adjusted = adjustedDefinitions[0]!!
        assertThat(adjusted.group).isSameInstanceAs(mixedTrackGroup)
        assertThat(adjusted.tracks.toList()).isEqualTo(listOf(0))
        assertThat(adjusted.type).isEqualTo(definition.type)
    }

    @Test
    fun mixedDefinitionRecognisesEac3JocSampleMimeType() {
        val eac3JocFormat =
            eac3Format.buildUpon().setSampleMimeType(MimeTypes.AUDIO_E_AC3_JOC).build()
        val trackGroup = trackGroupOf(flacFormat, eac3JocFormat)
        val definition = ExoTrackSelection.Definition(trackGroup, 0, 1)

        val adjustedDefinitions = createTrackSelections(arrayOf(definition))

        assertThat(adjustedDefinitions[0]!!.tracks.toList()).isEqualTo(listOf(1))
    }

    @Test
    fun eac3OnlyDefinitionIsUntouched() {
        val definition = ExoTrackSelection.Definition(trackGroupOf(eac3Format), 0)

        val adjustedDefinitions = createTrackSelections(arrayOf(definition))

        assertThat(adjustedDefinitions[0]).isSameInstanceAs(definition)
    }

    @Test
    fun definitionWithoutEac3IsUntouched() {
        val definition =
            ExoTrackSelection.Definition(
                trackGroupOf(flacFormat, aacLcFormat, heAacV1Format),
                0,
                1,
                2,
            )

        val adjustedDefinitions = createTrackSelections(arrayOf(definition))

        assertThat(adjustedDefinitions[0]).isSameInstanceAs(definition)
    }

    @Test
    fun nullDefinitionsArePreserved() {
        val definition = ExoTrackSelection.Definition(mixedTrackGroup, 0, 1, 2, 3)

        val adjustedDefinitions = createTrackSelections(arrayOf(null, definition, null))

        assertThat(adjustedDefinitions[0]).isNull()
        assertThat(adjustedDefinitions[1]!!.tracks.toList()).isEqualTo(listOf(0))
        assertThat(adjustedDefinitions[2]).isNull()
    }
}
