package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsPreviewGrantSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsSubjectSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantInvitationsUpdateSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ResourceGrantInvitations {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageResourceGrantInvitationsGet(val value: kotlin.String) {
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "previewGrant") previewGrant("previewGrant"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET resourceGrantInvitations Get multiple resourceGrantInvitations. Retrieves multiple
     * resourceGrantInvitations by available filters, or without if applicable. Responses:
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
     * @param filterCode Invitation code (e.g. &#x60;42179b73-6f0c-405c-b926-2fdb45363c44&#x60;)
     *   (optional)
     * @param filterSelfAppliesTo Album, track or folder owned by the caller. Selects undeleted,
     *   unexpired invitations on the resource or its ancestors; used-up ones are included. Each
     *   keeps its own subject. Cannot be combined with other filters. Missing or non-owned
     *   resources and subjects without a live access node return no records. Ordered by expiry then
     *   ID; at most 20 invitations per page. Also accepts &#x60;folders:me&#x60;. (optional)
     * @param include Include related resources. Available relationships: owners, previewGrant,
     *   subject (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: previewGrant.subject (optional)
     * @return [ResourceGrantInvitationsMultiResourceDataDocument]
     */
    @GET("resourceGrantInvitations")
    suspend fun resourceGrantInvitationsGet(
        @Query("filter[code]") filterCode: kotlin.String? = null,
        @Query("filter[self][appliesTo]") filterSelfAppliesTo: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantInvitationsMultiResourceDataDocument>

    /**
     * DELETE resourceGrantInvitations/{id} Delete single resourceGrantInvitation. Delete the
     * invitation, ending redemption and its preview. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Invitation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param revokeGrants Also revoke the grants redemptions issued; access from other sources
     *   stays. Defaults to false. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("resourceGrantInvitations/{id}")
    suspend fun resourceGrantInvitationsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Query("revokeGrants") revokeGrants: kotlin.Boolean? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageResourceGrantInvitationsIdGet(val value: kotlin.String) {
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "previewGrant") previewGrant("previewGrant"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET resourceGrantInvitations/{id} Get single resourceGrantInvitation. Retrieves single
     * resourceGrantInvitation by id. Responses:
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
     * @param id Invitation id
     * @param include Include related resources. Available relationships: owners, previewGrant,
     *   subject (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: previewGrant.subject (optional)
     * @return [ResourceGrantInvitationsSingleResourceDataDocument]
     */
    @GET("resourceGrantInvitations/{id}")
    suspend fun resourceGrantInvitationsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantInvitationsSingleResourceDataDocument>

    /**
     * PATCH resourceGrantInvitations/{id} Update single resourceGrantInvitation. Set, change or
     * remove the password, or turn the preview on or off. Users who redeemed keep their grants.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Invitation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param resourceGrantInvitationsUpdateOperationPayload (optional)
     * @return [ResourceGrantInvitationsUpdateSingleResourceDataDocument]
     */
    @PATCH("resourceGrantInvitations/{id}")
    suspend fun resourceGrantInvitationsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        resourceGrantInvitationsUpdateOperationPayload:
            ResourceGrantInvitationsUpdateOperationPayload? =
            null,
    ): Response<ResourceGrantInvitationsUpdateSingleResourceDataDocument>

    /**
     * GET resourceGrantInvitations/{id}/relationships/owners Get owners relationship
     * (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id Invitation id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ResourceGrantInvitationsOwnersMultiRelationshipDataDocument]
     */
    @GET("resourceGrantInvitations/{id}/relationships/owners")
    suspend fun resourceGrantInvitationsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ResourceGrantInvitationsOwnersMultiRelationshipDataDocument>

    /**
     * GET resourceGrantInvitations/{id}/relationships/previewGrant Get previewGrant relationship
     * (\&quot;to-one\&quot;). Retrieves previewGrant relationship. Responses:
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
     * @param id Invitation id
     * @param include Include related resources. Available relationships: previewGrant (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: previewGrant.subject (optional)
     * @return [ResourceGrantInvitationsPreviewGrantSingleRelationshipDataDocument]
     */
    @GET("resourceGrantInvitations/{id}/relationships/previewGrant")
    suspend fun resourceGrantInvitationsIdRelationshipsPreviewGrantGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantInvitationsPreviewGrantSingleRelationshipDataDocument>

    /**
     * GET resourceGrantInvitations/{id}/relationships/subject Get subject relationship
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
     * @param id Invitation id
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ResourceGrantInvitationsSubjectSingleRelationshipDataDocument]
     */
    @GET("resourceGrantInvitations/{id}/relationships/subject")
    suspend fun resourceGrantInvitationsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantInvitationsSubjectSingleRelationshipDataDocument>

    /**
     * POST resourceGrantInvitations Create single resourceGrantInvitation. Creates a new
     * resourceGrantInvitation. Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param resourceGrantInvitationsCreateOperationPayload (optional)
     * @return [ResourceGrantInvitationsCreateSingleResourceDataDocument]
     */
    @POST("resourceGrantInvitations")
    suspend fun resourceGrantInvitationsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        resourceGrantInvitationsCreateOperationPayload:
            ResourceGrantInvitationsCreateOperationPayload? =
            null,
    ): Response<ResourceGrantInvitationsCreateSingleResourceDataDocument>
}
