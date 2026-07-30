package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.VideoManifestsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface VideoManifests {

    /** enum for parameter uriScheme */
    enum class UriSchemeVideoManifestsIdGet(val value: kotlin.String) {
        @SerialName(value = "HTTPS") HTTPS("HTTPS"),
        @SerialName(value = "DATA") DATA("DATA"),
    }

    /** enum for parameter usage */
    enum class UsageVideoManifestsIdGet(val value: kotlin.String) {
        @SerialName(value = "PLAYBACK") PLAYBACK("PLAYBACK"),
        @SerialName(value = "DOWNLOAD") DOWNLOAD("DOWNLOAD"),
    }

    /**
     * Get single videoManifest. Retrieves single videoManifest by id. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Required playback prerequisites are missing; Content is unavailable in your location;
     *   Content must be purchased before playback; Client cannot access this content; Account is
     *   playing on another app or device
     * - 404: Content does not exist or is no longer available
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Video manifest id
     * @param uriScheme
     * @param usage
     * @return [VideoManifestsSingleResourceDataDocument]
     */
    @GET("videoManifests/{id}")
    suspend fun videoManifestsIdGet(
        @Path("id") id: kotlin.String,
        @Query("uriScheme") uriScheme: UriSchemeVideoManifestsIdGet,
        @Query("usage") usage: UsageVideoManifestsIdGet,
    ): Response<VideoManifestsSingleResourceDataDocument>
}
