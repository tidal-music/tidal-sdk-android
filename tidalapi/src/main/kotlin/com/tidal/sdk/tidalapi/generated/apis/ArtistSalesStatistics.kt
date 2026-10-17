package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.ArtistSalesStatisticsArtistSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistSalesStatisticsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistSalesStatisticsTopSellersMultiRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ArtistSalesStatistics {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageArtistSalesStatisticsGet(val value: kotlin.String) {
        @SerialName(value = "artist") artist("artist"),
        @SerialName(value = "topSellers") topSellers("topSellers"),
    }

    /**
     * GET artistSalesStatistics Get multiple artistSalesStatistics. Retrieves multiple
     * artistSalesStatistics by available filters, or without if applicable. Responses:
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
     * @param filterArtistId Artist ID. Every published range of the artist is returned (e.g.
     *   &#x60;1566&#x60;)
     * @param include Include related resources. Available relationships: artist, topSellers
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [ArtistSalesStatisticsMultiResourceDataDocument]
     */
    @GET("artistSalesStatistics")
    suspend fun artistSalesStatisticsGet(
        @Query("filter[artist.id]")
        filterArtistId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistSalesStatisticsMultiResourceDataDocument>

    /**
     * GET artistSalesStatistics/{id}/relationships/artist Get artist relationship
     * (\&quot;to-one\&quot;). Retrieves artist relationship. Responses:
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
     * @param id Artist sales statistics id
     * @param include Include related resources. Available relationships: artist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [ArtistSalesStatisticsArtistSingleRelationshipDataDocument]
     */
    @GET("artistSalesStatistics/{id}/relationships/artist")
    suspend fun artistSalesStatisticsIdRelationshipsArtistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistSalesStatisticsArtistSingleRelationshipDataDocument>

    /**
     * GET artistSalesStatistics/{id}/relationships/topSellers Get topSellers relationship
     * (\&quot;to-many\&quot;). Retrieves topSellers relationship. Responses:
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
     * @param id Artist sales statistics id
     * @param include Include related resources. Available relationships: topSellers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: topSellers (optional)
     * @return [ArtistSalesStatisticsTopSellersMultiRelationshipDataDocument]
     */
    @GET("artistSalesStatistics/{id}/relationships/topSellers")
    suspend fun artistSalesStatisticsIdRelationshipsTopSellersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistSalesStatisticsTopSellersMultiRelationshipDataDocument>
}
