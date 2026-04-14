package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.tidalapi.generated.apis.VideoManifests
import com.tidal.sdk.tidalapi.generated.models.LinkObject
import com.tidal.sdk.tidalapi.generated.models.Links
import com.tidal.sdk.tidalapi.generated.models.VideoManifestsAttributes
import com.tidal.sdk.tidalapi.generated.models.VideoManifestsResourceObject
import com.tidal.sdk.tidalapi.generated.models.VideoManifestsSingleResourceDataDocument
import retrofit2.Response

internal class VideoManifestsStub : VideoManifests {

    override suspend fun videoManifestsIdGet(
        id: String,
        uriScheme: VideoManifests.UriSchemeVideoManifestsIdGet,
        usage: VideoManifests.UsageVideoManifestsIdGet,
    ): Response<VideoManifestsSingleResourceDataDocument> {
        if (id == ID_FOR_UNCAUGHT_EXCEPTION) throw NullPointerException()

        val attributes =
            VideoManifestsAttributes(
                link =
                    LinkObject(
                        href = "data:application/vnd.apple.mpegurl;base64,${ApiConstants.MANIFEST}"
                    ),
                videoPresentation = VideoManifestsAttributes.VideoPresentation.FULL,
                drmData = null,
                previewReason = null,
            )
        val resource =
            VideoManifestsResourceObject(id = id, type = "videoManifests", attributes = attributes)
        val document =
            VideoManifestsSingleResourceDataDocument(data = resource, links = Links(self = ""))
        return Response.success(document)
    }

    companion object {
        const val ID_FOR_UNCAUGHT_EXCEPTION = "-1"
    }
}
