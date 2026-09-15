package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.FolderItemsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FolderItemsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsParentRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FolderItemsParentSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsParentUpdateSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsSubjectSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import retrofit2.Response
import retrofit2.http.*

interface FolderItems {
    /**
     * DELETE folderItems/{id} Delete single folderItem. Deletes existing folderItem. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Folder item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("folderItems/{id}")
    suspend fun folderItemsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /**
     * GET folderItems/{id} Get single folderItem. Retrieves single folderItem by id. Responses:
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
     * @param id Folder item id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, parent, subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: parent.children.subject (optional)
     * @return [FolderItemsSingleResourceDataDocument]
     */
    @GET("folderItems/{id}")
    suspend fun folderItemsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsSingleResourceDataDocument>

    /**
     * GET folderItems/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
     * Retrieves owners relationship. Responses:
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
     * @param id Folder item id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [FolderItemsOwnersMultiRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/owners")
    suspend fun folderItemsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<FolderItemsOwnersMultiRelationshipDataDocument>

    /**
     * GET folderItems/{id}/relationships/parent Get parent relationship (\&quot;to-one\&quot;).
     * Retrieves parent relationship. Responses:
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
     * @param id Folder item id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: parent (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: parent.children.subject (optional)
     * @return [FolderItemsParentSingleRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/parent")
    suspend fun folderItemsIdRelationshipsParentGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsParentSingleRelationshipDataDocument>

    /**
     * PATCH folderItems/{id}/relationships/parent Update parent relationship
     * (\&quot;to-one\&quot;). Updates parent relationship. Responses:
     * - 200: Successful response
     * - 400: A folder cannot be placed inside itself or one of its descendants
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Folder item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param folderItemsParentRelationshipUpdateOperationPayload (optional)
     * @return [FolderItemsParentUpdateSingleRelationshipDataDocument]
     */
    @PATCH("folderItems/{id}/relationships/parent")
    suspend fun folderItemsIdRelationshipsParentPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        folderItemsParentRelationshipUpdateOperationPayload:
            FolderItemsParentRelationshipUpdateOperationPayload? =
            null,
    ): Response<FolderItemsParentUpdateSingleRelationshipDataDocument>

    /**
     * GET folderItems/{id}/relationships/subject Get subject relationship (\&quot;to-one\&quot;).
     * Retrieves subject relationship. Responses:
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
     * @param id Folder item id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [FolderItemsSubjectSingleRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/subject")
    suspend fun folderItemsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsSubjectSingleRelationshipDataDocument>

    /**
     * POST folderItems Create single folderItem. Creates a new folderItem. Responses:
     * - 201: Successful response
     * - 400: A folder cannot be placed inside itself or one of its descendants
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param folderItemsCreateOperationPayload (optional)
     * @return [FolderItemsCreateSingleResourceDataDocument]
     */
    @POST("folderItems")
    suspend fun folderItemsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body folderItemsCreateOperationPayload: FolderItemsCreateOperationPayload? = null,
    ): Response<FolderItemsCreateSingleResourceDataDocument>
}
