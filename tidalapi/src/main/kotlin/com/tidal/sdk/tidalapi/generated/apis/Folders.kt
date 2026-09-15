package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.FoldersChildrenMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FoldersCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FoldersUpdateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import retrofit2.Response
import retrofit2.http.*

interface Folders {
    /**
     * DELETE folders/{id} Delete single folder. Deletes existing folder. Responses:
     * - 200: Successful response
     * - 400: The root folder cannot be renamed or deleted
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("folders/{id}")
    suspend fun foldersIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /**
     * GET folders/{id} Get single folder. Retrieves single folder by id. Responses:
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: children, owners (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: children.subject (optional)
     * @return [FoldersSingleResourceDataDocument]
     */
    @GET("folders/{id}")
    suspend fun foldersIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FoldersSingleResourceDataDocument>

    /**
     * PATCH folders/{id} Update single folder. Updates existing folder. Responses:
     * - 200: Successful response
     * - 400: The root folder cannot be renamed or deleted
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param foldersUpdateOperationPayload (optional)
     * @return [FoldersUpdateSingleResourceDataDocument]
     */
    @PATCH("folders/{id}")
    suspend fun foldersIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body foldersUpdateOperationPayload: FoldersUpdateOperationPayload? = null,
    ): Response<FoldersUpdateSingleResourceDataDocument>

    /**
     * GET folders/{id}/relationships/children Get children relationship (\&quot;to-many\&quot;).
     * Retrieves children relationship. Responses:
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: children (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: children.subject (optional)
     * @return [FoldersChildrenMultiRelationshipDataDocument]
     */
    @GET("folders/{id}/relationships/children")
    suspend fun foldersIdRelationshipsChildrenGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FoldersChildrenMultiRelationshipDataDocument>

    /**
     * GET folders/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [FoldersOwnersMultiRelationshipDataDocument]
     */
    @GET("folders/{id}/relationships/owners")
    suspend fun foldersIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<FoldersOwnersMultiRelationshipDataDocument>

    /**
     * POST folders Create single folder. Creates a new folder. Responses:
     * - 201: Successful response
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
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param foldersCreateOperationPayload (optional)
     * @return [FoldersCreateSingleResourceDataDocument]
     */
    @POST("folders")
    suspend fun foldersPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body foldersCreateOperationPayload: FoldersCreateOperationPayload? = null,
    ): Response<FoldersCreateSingleResourceDataDocument>
}
