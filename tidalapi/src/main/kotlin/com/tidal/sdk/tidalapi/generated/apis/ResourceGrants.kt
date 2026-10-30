package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsGranteeSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsSubjectSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ResourceGrantsUpdateSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ResourceGrants {

    /** enum for parameter filterRole */
    @Serializable
    enum class FilterRoleResourceGrantsGet(val value: kotlin.String) {
        @SerialName(value = "VIEWER") VIEWER("VIEWER"),
        @SerialName(value = "COLLABORATOR") COLLABORATOR("COLLABORATOR"),
        @SerialName(value = "OWNER") OWNER("OWNER"),
    }

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageResourceGrantsGet(val value: kotlin.String) {
        @SerialName(value = "grantee") grantee("grantee"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET resourceGrants Get multiple resourceGrants. Lists stored grants from every source visible
     * to the subject&#39;s owner or named user grantee, selected by subject or grantee, optionally
     * restricted by assigned role. Requires subject or grantee. Responses:
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
     * @param filterGrantee Filter by grantee. Also accepts &#x60;users:me&#x60;. (optional)
     * @param filterRole One of: VIEWER, COLLABORATOR, OWNER (e.g. &#x60;VIEWER&#x60;) (optional)
     * @param filterSubject Filter by subject. Also accepts &#x60;folders:me&#x60;. (optional)
     * @param include Include related resources. Available relationships: grantee, subject
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: grantee.artist.albums (optional)
     * @return [ResourceGrantsMultiResourceDataDocument]
     */
    @GET("resourceGrants")
    suspend fun resourceGrantsGet(
        @Query("filter[grantee]") filterGrantee: kotlin.String? = null,
        @Query("filter[role]") filterRole: FilterRoleResourceGrantsGet? = null,
        @Query("filter[subject]") filterSubject: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantsMultiResourceDataDocument>

    /**
     * DELETE resourceGrants/{id} Delete single resourceGrant. Deletes existing resourceGrant.
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
     * @param id Opaque resource grant ID
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("resourceGrants/{id}")
    suspend fun resourceGrantsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageResourceGrantsIdGet(val value: kotlin.String) {
        @SerialName(value = "grantee") grantee("grantee"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET resourceGrants/{id} Get single resourceGrant. Retrieves single resourceGrant by id.
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
     * @param id Opaque resource grant ID
     * @param include Include related resources. Available relationships: grantee, subject
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: grantee.artist.albums (optional)
     * @return [ResourceGrantsSingleResourceDataDocument]
     */
    @GET("resourceGrants/{id}")
    suspend fun resourceGrantsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantsSingleResourceDataDocument>

    /**
     * PATCH resourceGrants/{id} Update single resourceGrant. Updates existing resourceGrant.
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
     * @param id Opaque resource grant ID
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param resourceGrantsUpdateOperationPayload (optional)
     * @return [ResourceGrantsUpdateSingleResourceDataDocument]
     */
    @PATCH("resourceGrants/{id}")
    suspend fun resourceGrantsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body resourceGrantsUpdateOperationPayload: ResourceGrantsUpdateOperationPayload? = null,
    ): Response<ResourceGrantsUpdateSingleResourceDataDocument>

    /**
     * GET resourceGrants/{id}/relationships/grantee Get grantee relationship
     * (\&quot;to-one\&quot;). Retrieves grantee relationship. Responses:
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
     * @param id Opaque resource grant ID
     * @param include Include related resources. Available relationships: grantee (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: grantee.artist.albums (optional)
     * @return [ResourceGrantsGranteeSingleRelationshipDataDocument]
     */
    @GET("resourceGrants/{id}/relationships/grantee")
    suspend fun resourceGrantsIdRelationshipsGranteeGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantsGranteeSingleRelationshipDataDocument>

    /**
     * GET resourceGrants/{id}/relationships/subject Get subject relationship
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
     * @param id Opaque resource grant ID
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ResourceGrantsSubjectSingleRelationshipDataDocument]
     */
    @GET("resourceGrants/{id}/relationships/subject")
    suspend fun resourceGrantsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ResourceGrantsSubjectSingleRelationshipDataDocument>

    /**
     * POST resourceGrants Create single resourceGrant. Creates a new resourceGrant. Responses:
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
     * @param resourceGrantsCreateOperationPayload (optional)
     * @return [ResourceGrantsCreateSingleResourceDataDocument]
     */
    @POST("resourceGrants")
    suspend fun resourceGrantsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body resourceGrantsCreateOperationPayload: ResourceGrantsCreateOperationPayload? = null,
    ): Response<ResourceGrantsCreateSingleResourceDataDocument>
}
