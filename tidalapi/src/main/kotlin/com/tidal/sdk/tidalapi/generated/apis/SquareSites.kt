package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.SquareSitesItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareSitesItemsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareSitesItemsRelationshipRemoveOperationPayload
import retrofit2.Response
import retrofit2.http.*

interface SquareSites {
    /**
     * DELETE squareSites/{id}/relationships/items Delete from items relationship
     * (\&quot;to-many\&quot;). Removes the items from the site; they stay in the catalog. Items not
     * published and repeated items are left alone. An item missing from the catalog fails the
     * request before anything changes. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Another request is changing the Square catalog; The site isn't the seller's selected Square
     *   Online site; select it to manage its items; Request already in progress for this
     *   idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square Online site id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param squareSitesItemsRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(method = "DELETE", path = "squareSites/{id}/relationships/items", hasBody = true)
    suspend fun squareSitesIdRelationshipsItemsDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        squareSitesItemsRelationshipRemoveOperationPayload:
            SquareSitesItemsRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * GET squareSites/{id}/relationships/items Get items relationship (\&quot;to-many\&quot;).
     * Items published in the site, in title order. Missing catalog scopes return
     * MISSING_ITEMS_SCOPE. A page can be short or empty and still have a next cursor. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   The site isn't the seller's selected Square Online site; select it to manage its items
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square Online site id
     * @param include Include related resources. Available relationships: items (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareSitesItemsMultiRelationshipDataDocument]
     */
    @GET("squareSites/{id}/relationships/items")
    suspend fun squareSitesIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareSitesItemsMultiRelationshipDataDocument>

    /**
     * POST squareSites/{id}/relationships/items Add to items relationship (\&quot;to-many\&quot;).
     * Publishes the items in the site. Items already published and repeated items are left alone.
     * An item missing from the catalog fails the request before anything changes. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Another request is changing the Square catalog; The site isn't the seller's selected Square
     *   Online site; select it to manage its items; Request already in progress for this
     *   idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square Online site id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param squareSitesItemsRelationshipAddOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @POST("squareSites/{id}/relationships/items")
    suspend fun squareSitesIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        squareSitesItemsRelationshipAddOperationPayload:
            SquareSitesItemsRelationshipAddOperationPayload? =
            null,
    ): Response<MutationResponseDocument>
}
