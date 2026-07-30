package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionSaveForLatersItemsAddMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionSaveForLatersItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionSaveForLatersItemsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionSaveForLatersItemsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionSaveForLatersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionSaveForLatersSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserCollectionSaveForLaters {
    /**
     * Get single userCollectionSaveForLater. Retrieves single userCollectionSaveForLater by id.
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
     * @param id User collection save for later id. Use &#x60;me&#x60; for the authenticated
     *   user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items, owners (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items (optional)
     * @return [UserCollectionSaveForLatersSingleResourceDataDocument]
     */
    @GET("userCollectionSaveForLaters/{id}")
    suspend fun userCollectionSaveForLatersIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserCollectionSaveForLatersSingleResourceDataDocument>

    /**
     * Delete from items relationship (\&quot;to-many\&quot;). Deletes item(s) from items
     * relationship. Responses:
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
     * @param id User collection save for later id. Use &#x60;me&#x60; for the authenticated
     *   user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userCollectionSaveForLatersItemsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(
        method = "DELETE",
        path = "userCollectionSaveForLaters/{id}/relationships/items",
        hasBody = true,
    )
    suspend fun userCollectionSaveForLatersIdRelationshipsItemsDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userCollectionSaveForLatersItemsRelationshipRemoveOperationPayload:
            UserCollectionSaveForLatersItemsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get items relationship (\&quot;to-many\&quot;). Retrieves items relationship. Responses:
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
     * @param id User collection save for later id. Use &#x60;me&#x60; for the authenticated
     *   user&#39;s resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items (optional)
     * @return [UserCollectionSaveForLatersItemsMultiRelationshipDataDocument]
     */
    @GET("userCollectionSaveForLaters/{id}/relationships/items")
    suspend fun userCollectionSaveForLatersIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserCollectionSaveForLatersItemsMultiRelationshipDataDocument>

    /**
     * Add to items relationship (\&quot;to-many\&quot;). Adds item(s) to items relationship.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Collection item limit reached; Collection already contains one or more items; Request
     *   already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User collection save for later id. Use &#x60;me&#x60; for the authenticated
     *   user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userCollectionSaveForLatersItemsRelationshipAddOperationPayload (optional)
     * @return [UserCollectionSaveForLatersItemsAddMultiRelationshipDataDocument]
     */
    @POST("userCollectionSaveForLaters/{id}/relationships/items")
    suspend fun userCollectionSaveForLatersIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userCollectionSaveForLatersItemsRelationshipAddOperationPayload:
            UserCollectionSaveForLatersItemsRelationshipAddOperationPayload? =
            null,
    ): Response<UserCollectionSaveForLatersItemsAddMultiRelationshipDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id User collection save for later id. Use &#x60;me&#x60; for the authenticated
     *   user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserCollectionSaveForLatersMultiRelationshipDataDocument]
     */
    @GET("userCollectionSaveForLaters/{id}/relationships/owners")
    suspend fun userCollectionSaveForLatersIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionSaveForLatersMultiRelationshipDataDocument>
}
