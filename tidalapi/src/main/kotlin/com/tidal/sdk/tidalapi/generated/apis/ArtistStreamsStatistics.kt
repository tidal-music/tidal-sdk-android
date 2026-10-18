package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.ArtistStreamsStatisticsArtistSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistStreamsStatisticsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistStreamsStatisticsTopTracksMultiRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ArtistStreamsStatistics {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageArtistStreamsStatisticsGet(val value: kotlin.String) {
        @SerialName(value = "artist") artist("artist"),
        @SerialName(value = "topTracks") topTracks("topTracks"),
    }

    /**
     * GET artistStreamsStatistics Get multiple artistStreamsStatistics. Retrieves multiple
     * artistStreamsStatistics by available filters, or without if applicable. Responses:
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
     * @param include Include related resources. Available relationships: artist, topTracks
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [ArtistStreamsStatisticsMultiResourceDataDocument]
     */
    @GET("artistStreamsStatistics")
    suspend fun artistStreamsStatisticsGet(
        @Query("filter[artist.id]")
        filterArtistId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistStreamsStatisticsMultiResourceDataDocument>

    /**
     * GET artistStreamsStatistics/{id}/relationships/artist Get artist relationship
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
     * @param id Artist streams statistics id
     * @param include Include related resources. Available relationships: artist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [ArtistStreamsStatisticsArtistSingleRelationshipDataDocument]
     */
    @GET("artistStreamsStatistics/{id}/relationships/artist")
    suspend fun artistStreamsStatisticsIdRelationshipsArtistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistStreamsStatisticsArtistSingleRelationshipDataDocument>

    /**
     * GET artistStreamsStatistics/{id}/relationships/topTracks Get topTracks relationship
     * (\&quot;to-many\&quot;). Retrieves topTracks relationship. Responses:
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
     * @param id Artist streams statistics id
     * @param include Include related resources. Available relationships: topTracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: topTracks (optional)
     * @return [ArtistStreamsStatisticsTopTracksMultiRelationshipDataDocument]
     */
    @GET("artistStreamsStatistics/{id}/relationships/topTracks")
    suspend fun artistStreamsStatisticsIdRelationshipsTopTracksGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistStreamsStatisticsTopTracksMultiRelationshipDataDocument>
}
