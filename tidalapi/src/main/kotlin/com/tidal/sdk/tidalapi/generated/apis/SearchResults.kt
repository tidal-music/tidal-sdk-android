package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchresultsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.SearchresultsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SearchResults {
    /**
     * Relationship: albums
     * Search for albums by a query.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param query Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{query}/relationships/albums")
    suspend fun getSearchResultsAlbumsRelationship(
        @Path("query") query: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: artists
     * Search for artists by a query.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param query Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{query}/relationships/artists")
    suspend fun getSearchResultsArtistsRelationship(
        @Path("query") query: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Search for music metadata by a query
     * Search for music: albums, artists, tracks, etc.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param query Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, tracks, videos, playlists, topHits (optional)
     * @return [SearchresultsSingleDataDocument]
     */
    @GET("searchresults/{query}")
    suspend fun getSearchResultsByQuery(
        @Path("query") query: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchresultsSingleDataDocument>

    /**
     * Relationship: playlists
     * Search for playlists by a query.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param query Searh query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: playlists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{query}/relationships/playlists")
    suspend fun getSearchResultsPlaylistsRelationship(
        @Path("query") query: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: topHits
     * Search for top hits by a query: artists, albums, tracks, videos.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param query Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: topHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{query}/relationships/topHits")
    suspend fun getSearchResultsTopHitsRelationship(
        @Path("query") query: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: tracks
     * Search for tracks by a query.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param query Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: tracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{query}/relationships/tracks")
    suspend fun getSearchResultsTracksRelationship(
        @Path("query") query: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: videos
     * Search for videos by a query.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param query Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: videos (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{query}/relationships/videos")
    suspend fun getSearchResultsVideosRelationship(
        @Path("query") query: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>
}
