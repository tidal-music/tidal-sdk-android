package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ViewerContextsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ViewerContextsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ViewerContextsSubjectSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ViewerContextsViewerSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ViewerContexts {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageViewerContextsGet(val value: kotlin.String) {
        @SerialName(value = "subject") subject("subject"),
        @SerialName(value = "viewer") viewer("viewer"),
    }

    /**
     * GET viewerContexts Get multiple viewerContexts. Returns viewer contexts for up to 20 original
     * subjects. Duplicate subjects are returned once. Responses:
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
     * @param filterSubject Original subjects to look up.
     * @param include Include related resources. Available relationships: subject, viewer (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ViewerContextsMultiResourceDataDocument]
     */
    @GET("viewerContexts")
    suspend fun viewerContextsGet(
        @Query("filter[subject]")
        filterSubject: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ViewerContextsMultiResourceDataDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageViewerContextsIdGet(val value: kotlin.String) {
        @SerialName(value = "subject") subject("subject"),
        @SerialName(value = "viewer") viewer("viewer"),
    }

    /**
     * GET viewerContexts/{id} Get single viewerContext. Retrieves single viewerContext by id.
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
     * @param id Opaque identifier of one authenticated viewer and original subject pair
     * @param include Include related resources. Available relationships: subject, viewer (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ViewerContextsSingleResourceDataDocument]
     */
    @GET("viewerContexts/{id}")
    suspend fun viewerContextsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ViewerContextsSingleResourceDataDocument>

    /**
     * GET viewerContexts/{id}/relationships/subject Get subject relationship
     * (\&quot;to-one\&quot;). Returns the subject of this viewer context. Responses:
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
     * @param id Opaque identifier of one authenticated viewer and original subject pair
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ViewerContextsSubjectSingleRelationshipDataDocument]
     */
    @GET("viewerContexts/{id}/relationships/subject")
    suspend fun viewerContextsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ViewerContextsSubjectSingleRelationshipDataDocument>

    /**
     * GET viewerContexts/{id}/relationships/viewer Get viewer relationship (\&quot;to-one\&quot;).
     * Returns the authenticated viewer of this context. Responses:
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
     * @param id Opaque identifier of one authenticated viewer and original subject pair
     * @param include Include related resources. Available relationships: viewer (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: viewer.artist.albums (optional)
     * @return [ViewerContextsViewerSingleRelationshipDataDocument]
     */
    @GET("viewerContexts/{id}/relationships/viewer")
    suspend fun viewerContextsIdRelationshipsViewerGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ViewerContextsViewerSingleRelationshipDataDocument>
}
