package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SubscriptionPriceChangeDecisionsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SubscriptionPriceChangeDecisionsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SubscriptionPriceChangeDecisionsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SubscriptionPriceChangeDecisionsPriceChangeSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SubscriptionPriceChangeDecisionsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SubscriptionPriceChangeDecisionsUpdateSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SubscriptionPriceChangeDecisions {
    /**
     * Get multiple subscriptionPriceChangeDecisions. Retrieves multiple
     * subscriptionPriceChangeDecisions by available filters, or without if applicable. Responses:
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
     *   Available options: priceChange (optional)
     * @return [SubscriptionPriceChangeDecisionsMultiResourceDataDocument]
     */
    @GET("subscriptionPriceChangeDecisions")
    suspend fun subscriptionPriceChangeDecisionsGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SubscriptionPriceChangeDecisionsMultiResourceDataDocument>

    /**
     * Update single subscriptionPriceChangeDecision. Updates existing
     * subscriptionPriceChangeDecision. Responses:
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
     * @param id Price change decision id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param subscriptionPriceChangeDecisionsUpdateOperationPayload (optional)
     * @return [SubscriptionPriceChangeDecisionsUpdateSingleResourceDataDocument]
     */
    @PATCH("subscriptionPriceChangeDecisions/{id}")
    suspend fun subscriptionPriceChangeDecisionsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        subscriptionPriceChangeDecisionsUpdateOperationPayload:
            SubscriptionPriceChangeDecisionsUpdateOperationPayload? =
            null,
    ): Response<SubscriptionPriceChangeDecisionsUpdateSingleResourceDataDocument>

    /**
     * Get priceChange relationship (\&quot;to-one\&quot;). Retrieves priceChange relationship.
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
     * @param id Price change decision id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: priceChange (optional)
     * @return [SubscriptionPriceChangeDecisionsPriceChangeSingleRelationshipDataDocument]
     */
    @GET("subscriptionPriceChangeDecisions/{id}/relationships/priceChange")
    suspend fun subscriptionPriceChangeDecisionsIdRelationshipsPriceChangeGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SubscriptionPriceChangeDecisionsPriceChangeSingleRelationshipDataDocument>

    /**
     * Create single subscriptionPriceChangeDecision. Creates a new subscriptionPriceChangeDecision.
     * Responses:
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
     * @param subscriptionPriceChangeDecisionsCreateOperationPayload (optional)
     * @return [SubscriptionPriceChangeDecisionsCreateSingleResourceDataDocument]
     */
    @POST("subscriptionPriceChangeDecisions")
    suspend fun subscriptionPriceChangeDecisionsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        subscriptionPriceChangeDecisionsCreateOperationPayload:
            SubscriptionPriceChangeDecisionsCreateOperationPayload? =
            null,
    ): Response<SubscriptionPriceChangeDecisionsCreateSingleResourceDataDocument>
}
