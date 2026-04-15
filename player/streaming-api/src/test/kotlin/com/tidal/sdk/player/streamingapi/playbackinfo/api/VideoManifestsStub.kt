package com.tidal.sdk.player.streamingapi.playbackinfo.api

import com.tidal.sdk.player.streamingapi.ApiConstants
import com.tidal.sdk.tidalapi.generated.apis.VideoManifests
import com.tidal.sdk.tidalapi.generated.models.LinkObject
import com.tidal.sdk.tidalapi.generated.models.Links
import com.tidal.sdk.tidalapi.generated.models.VideoManifestsAttributes
import com.tidal.sdk.tidalapi.generated.models.VideoManifestsResourceObject
import com.tidal.sdk.tidalapi.generated.models.VideoManifestsSingleResourceDataDocument
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

internal class VideoManifestsStub : VideoManifests {

    override suspend fun videoManifestsIdGet(
        id: String,
        uriScheme: VideoManifests.UriSchemeVideoManifestsIdGet,
        usage: VideoManifests.UsageVideoManifestsIdGet,
    ): Response<VideoManifestsSingleResourceDataDocument> {
        if (id == ID_FOR_UNCAUGHT_EXCEPTION) throw NullPointerException()
        if (id == ID_FOR_HTTP_ERROR) {
            val errorBody = ERROR_BODY.toResponseBody("application/json".toMediaType())
            return Response.error(403, errorBody)
        }

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
        const val ID_FOR_HTTP_ERROR = "-2"
        const val ERROR_BODY =
            """{"errors":[{"code":"CLIENT_NOT_ENTITLED","status":"403","detail":"Not entitled"}]}"""
    }
}
