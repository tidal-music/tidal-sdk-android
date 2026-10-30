package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.ResourceSharingSettingsGrantsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceSharingSettingsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceSharingSettingsSharesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceSharingSettingsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceSharingSettingsSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ResourceSharingSettings {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageResourceSharingSettingsGet(val value: kotlin.String) {
        @SerialName(value = "grants") grants("grants"),
        @SerialName(value = "shares") shares("shares"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET resourceSharingSettings Get multiple resourceSharingSettings. Returns the sharing
     * settings of up to 20 live access-core subjects. Subjects without a node, or that the caller
     * does not own, return none. Responses:
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
     * @param include Include related resources. Available relationships: grants, shares, subject
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: grants.subject (optional)
     * @return [ResourceSharingSettingsMultiResourceDataDocument]
     */
    @GET("resourceSharingSettings")
    suspend fun resourceSharingSettingsGet(
        @Query("filter[subject]")
        filterSubject: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceSharingSettingsMultiResourceDataDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageResourceSharingSettingsIdGet(val value: kotlin.String) {
        @SerialName(value = "grants") grants("grants"),
        @SerialName(value = "shares") shares("shares"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET resourceSharingSettings/{id} Get single resourceSharingSetting. Retrieves single
     * resourceSharingSetting by id. Responses:
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
     * @param id Opaque resource sharing settings ID
     * @param include Include related resources. Available relationships: grants, shares, subject
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: grants.subject (optional)
     * @return [ResourceSharingSettingsSingleResourceDataDocument]
     */
    @GET("resourceSharingSettings/{id}")
    suspend fun resourceSharingSettingsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceSharingSettingsSingleResourceDataDocument>

    /**
     * GET resourceSharingSettings/{id}/relationships/grants Get grants relationship
     * (\&quot;to-many\&quot;). Each grantee&#39;s strongest grant on the subject or a folder or
     * album above it: the strongest role, then the nearest grant, then a direct grant over a saved
     * link&#39;s, then the oldest. The owner comes first, then groups, then users. A grant&#39;s
     * subject is the node it is on. Responses:
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
     * @param id Opaque resource sharing settings ID
     * @param include Include related resources. Available relationships: grants (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: grants.subject (optional)
     * @return [ResourceSharingSettingsGrantsMultiRelationshipDataDocument]
     */
    @GET("resourceSharingSettings/{id}/relationships/grants")
    suspend fun resourceSharingSettingsIdRelationshipsGrantsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceSharingSettingsGrantsMultiRelationshipDataDocument>

    /**
     * GET resourceSharingSettings/{id}/relationships/shares Get shares relationship
     * (\&quot;to-many\&quot;). The active share links on the subject or an album above it, or a
     * folder above it whose link can be saved, next to expire first. Responses:
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
     * @param id Opaque resource sharing settings ID
     * @param include Include related resources. Available relationships: shares (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: shares.sharedResources (optional)
     * @return [ResourceSharingSettingsSharesMultiRelationshipDataDocument]
     */
    @GET("resourceSharingSettings/{id}/relationships/shares")
    suspend fun resourceSharingSettingsIdRelationshipsSharesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceSharingSettingsSharesMultiRelationshipDataDocument>

    /**
     * GET resourceSharingSettings/{id}/relationships/subject Get subject relationship
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
     * @param id Opaque resource sharing settings ID
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ResourceSharingSettingsSubjectSingleRelationshipDataDocument]
     */
    @GET("resourceSharingSettings/{id}/relationships/subject")
    suspend fun resourceSharingSettingsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceSharingSettingsSubjectSingleRelationshipDataDocument>
}
