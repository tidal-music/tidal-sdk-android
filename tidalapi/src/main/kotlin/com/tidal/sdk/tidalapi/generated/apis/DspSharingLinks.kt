package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.DspSharingLinksMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.DspSharingLinksSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface DspSharingLinks {

    /** enum for parameter filterSubjectType */
    enum class FilterSubjectTypeDspSharingLinksGet(val value: kotlin.String) {
        @SerialName(value = "tracks") tracks("tracks"),
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "artists") artists("artists"),
    }

    /**
     * Get multiple dspSharingLinks. Retrieves multiple dspSharingLinks by available filters, or
     * without if applicable. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param filterSubjectId The id of the subject resource
     * @param filterSubjectType The type of the subject resource (e.g., albums, tracks, artists)
     *   (e.g. &#x60;tracks&#x60;)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @return [DspSharingLinksMultiResourceDataDocument]
     */
    @GET("dspSharingLinks")
    suspend fun dspSharingLinksGet(
        @Query("filter[subject.id]")
        filterSubjectId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("filter[subject.type]")
        filterSubjectType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DspSharingLinksMultiResourceDataDocument>

    /**
     * Get subject relationship (\&quot;to-one\&quot;). Retrieves subject relationship. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id DspSharingLinks Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @return [DspSharingLinksSingleRelationshipDataDocument]
     */
    @GET("dspSharingLinks/{id}/relationships/subject")
    suspend fun dspSharingLinksIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DspSharingLinksSingleRelationshipDataDocument>
}
