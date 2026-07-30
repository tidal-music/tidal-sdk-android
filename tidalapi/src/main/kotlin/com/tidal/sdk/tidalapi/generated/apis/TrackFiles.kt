package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TrackFilesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface TrackFiles {

    /** enum for parameter formats */
    enum class FormatsTrackFilesIdGet(val value: kotlin.String) {
        @SerialName(value = "HEAACV1") HEAACV1("HEAACV1"),
        @SerialName(value = "AACLC") AACLC("AACLC"),
        @SerialName(value = "FLAC") FLAC("FLAC"),
        @SerialName(value = "FLAC_HIRES") FLAC_HIRES("FLAC_HIRES"),
        @SerialName(value = "EAC3_JOC") EAC3_JOC("EAC3_JOC"),
    }

    /** enum for parameter usage */
    enum class UsageTrackFilesIdGet(val value: kotlin.String) {
        @SerialName(value = "PLAYBACK") PLAYBACK("PLAYBACK"),
        @SerialName(value = "DOWNLOAD") DOWNLOAD("DOWNLOAD"),
    }

    /**
     * Get single trackFile. Retrieves single trackFile by id. Responses:
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
     * @param id Track file id
     * @param formats
     * @param usage
     * @return [TrackFilesSingleResourceDataDocument]
     */
    @GET("trackFiles/{id}")
    suspend fun trackFilesIdGet(
        @Path("id") id: kotlin.String,
        @Query("formats") formats: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("usage") usage: UsageTrackFilesIdGet,
    ): Response<TrackFilesSingleResourceDataDocument>
}
