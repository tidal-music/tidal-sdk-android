package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.StripeConnectionsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface StripeConnections {
    /**
     * Get multiple stripeConnections. Retrieves multiple stripeConnections by available filters, or
     * without if applicable. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user (optional)
     * @return [StripeConnectionsMultiResourceDataDocument]
     */
    @GET("stripeConnections")
    suspend fun stripeConnectionsGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<StripeConnectionsMultiResourceDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Stripe connection id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [StripeConnectionsMultiRelationshipDataDocument]
     */
    @GET("stripeConnections/{id}/relationships/owners")
    suspend fun stripeConnectionsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<StripeConnectionsMultiRelationshipDataDocument>

    /**
     * Create single stripeConnection. Creates a new stripeConnection. Responses:
     * - 201: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: A request with this idempotency key is currently being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param stripeConnectionsCreateOperationPayload (optional)
     * @return [StripeConnectionsSingleResourceDataDocument]
     */
    @POST("stripeConnections")
    suspend fun stripeConnectionsPost(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        stripeConnectionsCreateOperationPayload: StripeConnectionsCreateOperationPayload? = null,
    ): Response<StripeConnectionsSingleResourceDataDocument>
}
