package com.tidal.sdk.player.playbackengine.player.trackselection

import androidx.media3.common.MimeTypes
import androidx.media3.common.Timeline
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.trackselection.ExoTrackSelection
import androidx.media3.exoplayer.upstream.BandwidthMeter

/**
 * Deterministically prefers E-AC-3 (Dolby Atmos) representations in adaptive DASH manifests that
 * expose a single adaptation set mixing E-AC-3 (JOC) with stereo representations (FLAC/AAC).
 *
 * E-AC-3 is only requested from the trackManifests API when immersive audio is enabled, so its
 * presence in a manifest already expresses the user's intent: whenever it is selectable, it should
 * play.
 *
 * The [DefaultTrackSelector][androidx.media3.exoplayer.trackselection.DefaultTrackSelector] is
 * configured with mixed MIME type/sample rate/channel count adaptiveness, so it produces a single
 * adaptive selection over all representations, ordered purely by declared bandwidth. Whether Atmos
 * plays would then depend on the throughput estimate and could change mid-track. Instead, this
 * factory pins such definitions to the E-AC-3 representations before the adaptive selection is
 * created. Definitions without E-AC-3 tracks are left untouched, keeping the existing FLAC/AAC
 * quality ladder, as are E-AC-3-only definitions. Devices that cannot play E-AC-3 are unaffected:
 * the track selector excludes renderer-unsupported tracks from adaptive definitions before this
 * factory runs.
 */
internal class TidalTrackSelectionFactory(private val delegate: ExoTrackSelection.Factory) :
    ExoTrackSelection.Factory {

    override fun createTrackSelections(
        definitions: Array<out ExoTrackSelection.Definition?>,
        bandwidthMeter: BandwidthMeter,
        mediaPeriodId: MediaSource.MediaPeriodId,
        timeline: Timeline,
    ): Array<ExoTrackSelection?> {
        val adjustedDefinitions = Array(definitions.size) { definitions[it]?.preferringEac3() }
        return delegate.createTrackSelections(
            adjustedDefinitions,
            bandwidthMeter,
            mediaPeriodId,
            timeline,
        )
    }

    private fun ExoTrackSelection.Definition.preferringEac3(): ExoTrackSelection.Definition {
        val eac3Tracks = tracks.filter { isEac3(group.getFormat(it).sampleMimeType) }
        if (eac3Tracks.isEmpty() || eac3Tracks.size == tracks.size) {
            return this
        }
        return ExoTrackSelection.Definition(group, eac3Tracks.toIntArray(), type)
    }

    private fun isEac3(sampleMimeType: String?) =
        MimeTypes.AUDIO_E_AC3_JOC == sampleMimeType || MimeTypes.AUDIO_E_AC3 == sampleMimeType
}
