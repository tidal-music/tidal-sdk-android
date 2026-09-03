package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserSubscriptionPriceChangesDecisionSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserSubscriptionPriceChangesMultiResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserSubscriptionPriceChanges {
    /**
     * Get multiple userSubscriptionPriceChanges. Retrieves multiple userSubscriptionPriceChanges by
     * available filters, or without if applicable. Responses:
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
     *   Available options: decision (optional)
     * @return [UserSubscriptionPriceChangesMultiResourceDataDocument]
     */
    @GET("userSubscriptionPriceChanges")
    suspend fun userSubscriptionPriceChangesGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserSubscriptionPriceChangesMultiResourceDataDocument>

    /**
     * Get decision relationship (\&quot;to-one\&quot;). Retrieves decision relationship. Responses:
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
     * @param id Price change id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: decision (optional)
     * @return [UserSubscriptionPriceChangesDecisionSingleRelationshipDataDocument]
     */
    @GET("userSubscriptionPriceChanges/{id}/relationships/decision")
    suspend fun userSubscriptionPriceChangesIdRelationshipsDecisionGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserSubscriptionPriceChangesDecisionSingleRelationshipDataDocument>
}
