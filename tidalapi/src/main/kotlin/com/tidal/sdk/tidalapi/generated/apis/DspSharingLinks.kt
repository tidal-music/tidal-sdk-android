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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @param filterSubjectId The id of the subject resource (optional)
     * @param filterSubjectType The type of the subject resource (e.g., albums, tracks, artists)
     *   (e.g. &#x60;tracks&#x60;) (optional)
     * @return [DspSharingLinksMultiResourceDataDocument]
     */
    @GET("dspSharingLinks")
    suspend fun dspSharingLinksGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject.id]")
        filterSubjectId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject.type]")
        filterSubjectType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DspSharingLinksMultiResourceDataDocument>

    /**
     * Get subject relationship (\&quot;to-one\&quot;). Retrieves subject relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
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
