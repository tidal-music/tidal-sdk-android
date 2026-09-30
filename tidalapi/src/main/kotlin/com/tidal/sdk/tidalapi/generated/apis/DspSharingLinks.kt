package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.DspSharingLinksMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.DspSharingLinksSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface DspSharingLinks {

    /** enum for parameter filterSubjectType */
    @Serializable
    enum class FilterSubjectTypeDspSharingLinksGet(val value: kotlin.String) {
        @SerialName(value = "tracks") tracks("tracks"),
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "artists") artists("artists"),
    }

    /**
     * GET dspSharingLinks Get multiple dspSharingLinks. Retrieves multiple dspSharingLinks by
     * available filters, or without if applicable. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @param filterSubject The subject whose DSP sharing links to return. Use either subject or the
     *   deprecated subject.id and subject.type pair. (optional)
     * @param filterSubjectId Deprecated: use filter[subject]. The id of the subject resource
     *   (optional)
     * @param filterSubjectType Deprecated: use filter[subject]. The type of the subject resource
     *   (e.g. &#x60;tracks&#x60;) (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [DspSharingLinksMultiResourceDataDocument]
     */
    @GET("dspSharingLinks")
    suspend fun dspSharingLinksGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject]") filterSubject: kotlin.String? = null,
        @Query("filter[subject.id]")
        filterSubjectId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject.type]")
        filterSubjectType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<DspSharingLinksMultiResourceDataDocument>

    /**
     * GET dspSharingLinks/{id}/relationships/subject Get subject relationship
     * (\&quot;to-one\&quot;). Retrieves subject relationship. Responses:
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
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [DspSharingLinksSubjectSingleRelationshipDataDocument]
     */
    @GET("dspSharingLinks/{id}/relationships/subject")
    suspend fun dspSharingLinksIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<DspSharingLinksSubjectSingleRelationshipDataDocument>
}
