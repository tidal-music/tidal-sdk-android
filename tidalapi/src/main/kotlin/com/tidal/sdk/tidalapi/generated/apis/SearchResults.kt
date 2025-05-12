package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchResultsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.SearchResultsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SearchResults {
    /**
     * Get single searchResult. Retrieves single searchResult by id. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, playlists, topHits, tracks, videos (optional)
     * @return [SearchResultsSingleDataDocument]
     */
    @GET("searchResults/{id}")
    suspend fun searchResultsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsSingleDataDocument>

    /**
     * Get albums relationship (\&quot;to-many\&quot;). Retrieves albums relationship. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiDataRelationshipDocument]
     */
    @GET("searchResults/{id}/relationships/albums")
    suspend fun searchResultsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiDataRelationshipDocument>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Retrieves artists relationship. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiDataRelationshipDocument]
     */
    @GET("searchResults/{id}/relationships/artists")
    suspend fun searchResultsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiDataRelationshipDocument>

    /**
     * Get playlists relationship (\&quot;to-many\&quot;). Retrieves playlists relationship.
     * Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiDataRelationshipDocument]
     */
    @GET("searchResults/{id}/relationships/playlists")
    suspend fun searchResultsIdRelationshipsPlaylistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiDataRelationshipDocument>

    /**
     * Get topHits relationship (\&quot;to-many\&quot;). Retrieves topHits relationship. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: topHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiDataRelationshipDocument]
     */
    @GET("searchResults/{id}/relationships/topHits")
    suspend fun searchResultsIdRelationshipsTopHitsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiDataRelationshipDocument>

    /**
     * Get tracks relationship (\&quot;to-many\&quot;). Retrieves tracks relationship. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiDataRelationshipDocument]
     */
    @GET("searchResults/{id}/relationships/tracks")
    suspend fun searchResultsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiDataRelationshipDocument>

    /**
     * Get videos relationship (\&quot;to-many\&quot;). Retrieves videos relationship. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchResultsMultiDataRelationshipDocument]
     */
    @GET("searchResults/{id}/relationships/videos")
    suspend fun searchResultsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchResultsMultiDataRelationshipDocument>
}
