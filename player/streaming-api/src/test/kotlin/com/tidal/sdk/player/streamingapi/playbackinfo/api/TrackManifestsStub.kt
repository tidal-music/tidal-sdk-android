package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.tidalapi.generated.apis.TrackManifests
import com.tidal.sdk.tidalapi.generated.models.AudioNormalizationData
import com.tidal.sdk.tidalapi.generated.models.Links
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsAttributes
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsResourceObject
import com.tidal.sdk.tidalapi.generated.models.TrackManifestsSingleResourceDataDocument
import retrofit2.Response

internal class TrackManifestsStub : TrackManifests {

    override suspend fun trackManifestsIdGet(
        id: String,
        manifestType: TrackManifests.ManifestTypeTrackManifestsIdGet,
        formats: List<String>,
        uriScheme: TrackManifests.UriSchemeTrackManifestsIdGet,
        usage: TrackManifests.UsageTrackManifestsIdGet,
        adaptive: Boolean,
        shareCode: String?,
    ): Response<TrackManifestsSingleResourceDataDocument> {
        val attributes =
            TrackManifestsAttributes(
                albumAudioNormalizationData =
                    AudioNormalizationData(replayGain = -9.8f, peakAmplitude = 0.999923f),
                trackAudioNormalizationData =
                    AudioNormalizationData(replayGain = -9.8f, peakAmplitude = 0.999923f),
                formats = listOf(TrackManifestsAttributes.Formats.HEAACV1),
                hash = ApiConstants.MANIFEST_HASH,
                trackPresentation = TrackManifestsAttributes.TrackPresentation.FULL,
                uri = "data:application/dash+xml;base64,${ApiConstants.MANIFEST}",
                drmData = null,
            )
        val resource =
            TrackManifestsResourceObject(id = id, type = "trackManifests", attributes = attributes)
        val document =
            TrackManifestsSingleResourceDataDocument(data = resource, links = Links(self = ""))
        return Response.success(document)
    }
}
