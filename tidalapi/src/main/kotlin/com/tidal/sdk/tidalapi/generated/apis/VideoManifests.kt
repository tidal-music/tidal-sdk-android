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
     * - 400: The request is malformed or invalid
     * - 403: Cannot fulfill this request because required prerequisites are missing; The requested
     *   content is not available in your location; The requested content must be purchased to be
     *   accessed; Client is not allowed to access this content; This account is playing on another
     *   app or device
     * - 404: The requested content does not exist or is no longer available
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
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
