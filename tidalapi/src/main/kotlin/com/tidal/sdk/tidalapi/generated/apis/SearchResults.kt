package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchResultsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SearchResultsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SearchResults {
    /**
     * Get single searchResult. Retrieves single searchResult by id. Responses:
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
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, playlists, topHits, tracks, videos (optional)
     * @return [SearchResultsSingleResourceDataDocument]
     */
    @GET("searchResults/{id}")
    suspend fun searchResultsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsSingleResourceDataDocument>

    /**
     * Get albums relationship (\&quot;to-many\&quot;). Retrieves albums relationship. Responses:
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
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/albums")
    suspend fun searchResultsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Retrieves artists relationship. Responses:
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
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/artists")
    suspend fun searchResultsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /**
     * Get playlists relationship (\&quot;to-many\&quot;). Retrieves playlists relationship.
     * Responses:
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
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/playlists")
    suspend fun searchResultsIdRelationshipsPlaylistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /**
     * Get topHits relationship (\&quot;to-many\&quot;). Retrieves topHits relationship. Responses:
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
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: topHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/topHits")
    suspend fun searchResultsIdRelationshipsTopHitsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /**
     * Get tracks relationship (\&quot;to-many\&quot;). Retrieves tracks relationship. Responses:
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
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/tracks")
    suspend fun searchResultsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /**
     * Get videos relationship (\&quot;to-many\&quot;). Retrieves videos relationship. Responses:
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
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/videos")
    suspend fun searchResultsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>
}
