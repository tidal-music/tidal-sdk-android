package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.AlbumStatisticsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumStatisticsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumStatisticsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface AlbumStatistics {
    /**
     * Get multiple albumStatistics. Retrieves multiple albumStatistics by available filters, or
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
     * @param filterId List of album IDs (e.g. &#x60;251380836&#x60;) (optional)
     * @return [AlbumStatisticsMultiResourceDataDocument]
     */
    @GET("albumStatistics")
    suspend fun albumStatisticsGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<AlbumStatisticsMultiResourceDataDocument>

    /**
     * Get single albumStatistic. Retrieves single albumStatistic by id. Responses:
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
     * @param id Album statistic id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [AlbumStatisticsSingleResourceDataDocument]
     */
    @GET("albumStatistics/{id}")
    suspend fun albumStatisticsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<AlbumStatisticsSingleResourceDataDocument>

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
     * @param id Album statistic id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [AlbumStatisticsMultiRelationshipDataDocument]
     */
    @GET("albumStatistics/{id}/relationships/owners")
    suspend fun albumStatisticsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<AlbumStatisticsMultiRelationshipDataDocument>
}
