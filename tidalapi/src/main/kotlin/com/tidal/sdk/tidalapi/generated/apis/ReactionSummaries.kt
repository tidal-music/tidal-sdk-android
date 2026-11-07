package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.ReactionSummariesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ReactionSummariesSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ReactionSummaries {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageReactionSummariesGet(val value: kotlin.String) {
        @SerialName(value = "subject") subject("subject")
    }

    /**
     * GET reactionSummaries Get multiple reactionSummaries. Returns one current summary per
     * accessible subject, for up to 20 subjects. Duplicates are returned once. Inaccessible or
     * missing subjects are omitted. Empty supported subjects still have a summary. IDs remain
     * stable as counts change. Responses:
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
     * @param filterSubject Subjects whose counts to return.
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: subject (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject.author.albums (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [ReactionSummariesMultiResourceDataDocument]
     */
    @GET("reactionSummaries")
    suspend fun reactionSummariesGet(
        @Query("filter[subject]")
        filterSubject: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<ReactionSummariesMultiResourceDataDocument>

    /**
     * GET reactionSummaries/{id}/relationships/subject Get subject relationship
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
     * @param id Opaque identifier of one subject&#39;s current summary
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject.author.albums (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [ReactionSummariesSubjectSingleRelationshipDataDocument]
     */
    @GET("reactionSummaries/{id}/relationships/subject")
    suspend fun reactionSummariesIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<ReactionSummariesSubjectSingleRelationshipDataDocument>
}
