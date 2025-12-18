package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TrackManifestsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface TrackManifests {

    /** enum for parameter manifestType */
    enum class ManifestTypeTrackManifestsIdGet(val value: kotlin.String) {
        @SerialName(value = "HLS") HLS("HLS"),
        @SerialName(value = "MPEG_DASH") MPEG_DASH("MPEG_DASH"),
    }

    /** enum for parameter formats */
    enum class FormatsTrackManifestsIdGet(val value: kotlin.String) {
        @SerialName(value = "HEAACV1") HEAACV1("HEAACV1"),
        @SerialName(value = "AACLC") AACLC("AACLC"),
        @SerialName(value = "FLAC") FLAC("FLAC"),
        @SerialName(value = "FLAC_HIRES") FLAC_HIRES("FLAC_HIRES"),
        @SerialName(value = "EAC3_JOC") EAC3_JOC("EAC3_JOC"),
    }

    /** enum for parameter uriScheme */
    enum class UriSchemeTrackManifestsIdGet(val value: kotlin.String) {
        @SerialName(value = "HTTPS") HTTPS("HTTPS"),
        @SerialName(value = "DATA") DATA("DATA"),
    }

    /** enum for parameter usage */
    enum class UsageTrackManifestsIdGet(val value: kotlin.String) {
        @SerialName(value = "PLAYBACK") PLAYBACK("PLAYBACK"),
        @SerialName(value = "DOWNLOAD") DOWNLOAD("DOWNLOAD"),
    }

    /**
     * Get single trackManifest. Retrieves single trackManifest by id. Responses:
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
     * @param id Track manifest id
     * @param manifestType
     * @param formats
     * @param uriScheme
     * @param usage
     * @param adaptive
     * @return [TrackManifestsSingleResourceDataDocument]
     */
    @GET("trackManifests/{id}")
    suspend fun trackManifestsIdGet(
        @Path("id") id: kotlin.String,
        @Query("manifestType") manifestType: ManifestTypeTrackManifestsIdGet,
        @Query("formats") formats: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("uriScheme") uriScheme: UriSchemeTrackManifestsIdGet,
        @Query("usage") usage: UsageTrackManifestsIdGet,
        @Query("adaptive") adaptive: kotlin.Boolean,
    ): Response<TrackManifestsSingleResourceDataDocument>
}
