package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.SharingSummariesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SharingSummariesSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface SharingSummaries {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageSharingSummariesGet(val value: kotlin.String) {
        @SerialName(value = "subject") subject("subject")
    }

    /**
     * GET sharingSummaries Get multiple sharingSummaries. Returns one current summary per distinct
     * owned subject, for up to 20 subjects. Live subjects with no sharing return false flags.
     * Missing or inaccessible subjects are omitted. IDs remain stable as sharing changes.
     * Responses:
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
     * @param filterSubject Filter by subject. Also accepts &#x60;folders:me&#x60;.
     * @param include Include related resources. Available relationships: subject (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [SharingSummariesMultiResourceDataDocument]
     */
    @GET("sharingSummaries")
    suspend fun sharingSummariesGet(
        @Query("filter[subject]")
        filterSubject: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<SharingSummariesMultiResourceDataDocument>

    /**
     * GET sharingSummaries/{id}/relationships/subject Get subject relationship
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
     * @param id Opaque sharing summary ID
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [SharingSummariesSubjectSingleRelationshipDataDocument]
     */
    @GET("sharingSummaries/{id}/relationships/subject")
    suspend fun sharingSummariesIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<SharingSummariesSubjectSingleRelationshipDataDocument>
}
