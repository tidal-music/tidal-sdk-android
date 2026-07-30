package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.StripeDashboardLinksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.StripeDashboardLinksMultiResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface StripeDashboardLinks {
    /**
     * Get multiple stripeDashboardLinks. Retrieves multiple stripeDashboardLinks by available
     * filters, or without if applicable. Responses:
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
     * @return [StripeDashboardLinksMultiResourceDataDocument]
     */
    @GET("stripeDashboardLinks")
    suspend fun stripeDashboardLinksGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<StripeDashboardLinksMultiResourceDataDocument>

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
     * @param id Stripe dashboard link id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [StripeDashboardLinksMultiRelationshipDataDocument]
     */
    @GET("stripeDashboardLinks/{id}/relationships/owners")
    suspend fun stripeDashboardLinksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<StripeDashboardLinksMultiRelationshipDataDocument>
}
