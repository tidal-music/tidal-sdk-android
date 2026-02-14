package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TrackStatisticsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackStatisticsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackStatisticsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface TrackStatistics {
    /**
     * Get multiple trackStatistics. Retrieves multiple trackStatistics by available filters, or
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
     * @param filterId Track id (e.g. &#x60;75413016&#x60;) (optional)
     * @return [TrackStatisticsMultiResourceDataDocument]
     */
    @GET("trackStatistics")
    suspend fun trackStatisticsGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackStatisticsMultiResourceDataDocument>

    /**
     * Get single trackStatistic. Retrieves single trackStatistic by id. Responses:
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
     * @param id Track statistic id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [TrackStatisticsSingleResourceDataDocument]
     */
    @GET("trackStatistics/{id}")
    suspend fun trackStatisticsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackStatisticsSingleResourceDataDocument>

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
     * @param id Track statistic id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [TrackStatisticsMultiRelationshipDataDocument]
     */
    @GET("trackStatistics/{id}/relationships/owners")
    suspend fun trackStatisticsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TrackStatisticsMultiRelationshipDataDocument>
}
