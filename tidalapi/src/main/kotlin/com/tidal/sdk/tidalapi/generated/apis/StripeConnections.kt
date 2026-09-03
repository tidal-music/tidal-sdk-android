package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsOwnersMultiRelationshipDataDocument
import retrofit2.Response
import retrofit2.http.*

interface StripeConnections {
    /**
     * Get multiple stripeConnections. Retrieves multiple stripeConnections by available filters, or
     * without if applicable. Responses:
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
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [StripeConnectionsMultiResourceDataDocument]
     */
    @GET("stripeConnections")
    suspend fun stripeConnectionsGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<StripeConnectionsMultiResourceDataDocument>

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
     * @param id Stripe connection id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [StripeConnectionsOwnersMultiRelationshipDataDocument]
     */
    @GET("stripeConnections/{id}/relationships/owners")
    suspend fun stripeConnectionsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<StripeConnectionsOwnersMultiRelationshipDataDocument>

    /**
     * Create single stripeConnection. Creates a new stripeConnection. Responses:
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
     * @param stripeConnectionsCreateOperationPayload (optional)
     * @return [StripeConnectionsCreateSingleResourceDataDocument]
     */
    @POST("stripeConnections")
    suspend fun stripeConnectionsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        stripeConnectionsCreateOperationPayload: StripeConnectionsCreateOperationPayload? = null,
    ): Response<StripeConnectionsCreateSingleResourceDataDocument>
}
