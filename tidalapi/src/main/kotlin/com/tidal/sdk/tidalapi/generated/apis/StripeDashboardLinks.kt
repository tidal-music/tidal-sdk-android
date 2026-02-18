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
     * @param filterOwnersId User id (e.g. &#x60;123456&#x60;) (optional)
     * @return [StripeDashboardLinksMultiResourceDataDocument]
     */
    @GET("stripeDashboardLinks")
    suspend fun stripeDashboardLinksGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<StripeDashboardLinksMultiResourceDataDocument>

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
     * @param id Stripe dashboard link id (same as user id)
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
