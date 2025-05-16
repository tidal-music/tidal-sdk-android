package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchresultsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.SearchresultsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Searchresults {
    /**
     * Get single searchresult. This operation is deprecated and will be removed shortly. Please
     * switch to the renamed /searchResults/xyz endpoint. Retrieves single searchresult by id.
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, playlists, topHits, tracks, videos (optional)
     * @return [SearchresultsSingleDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("searchresults/{id}")
    suspend fun searchresultsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchresultsSingleDataDocument>

    /**
     * Get albums relationship (\&quot;to-many\&quot;). This operation is deprecated and will be
     * removed shortly. Please switch to the renamed /searchResults/xyz endpoint. Retrieves albums
     * relationship. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("searchresults/{id}/relationships/albums")
    suspend fun searchresultsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). This operation is deprecated and will be
     * removed shortly. Please switch to the renamed /searchResults/xyz endpoint. Retrieves artists
     * relationship. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("searchresults/{id}/relationships/artists")
    suspend fun searchresultsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Get playlists relationship (\&quot;to-many\&quot;). This operation is deprecated and will be
     * removed shortly. Please switch to the renamed /searchResults/xyz endpoint. Retrieves
     * playlists relationship. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("searchresults/{id}/relationships/playlists")
    suspend fun searchresultsIdRelationshipsPlaylistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Get topHits relationship (\&quot;to-many\&quot;). This operation is deprecated and will be
     * removed shortly. Please switch to the renamed /searchResults/xyz endpoint. Retrieves topHits
     * relationship. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: topHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("searchresults/{id}/relationships/topHits")
    suspend fun searchresultsIdRelationshipsTopHitsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Get tracks relationship (\&quot;to-many\&quot;). This operation is deprecated and will be
     * removed shortly. Please switch to the renamed /searchResults/xyz endpoint. Retrieves tracks
     * relationship. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("searchresults/{id}/relationships/tracks")
    suspend fun searchresultsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Get videos relationship (\&quot;to-many\&quot;). This operation is deprecated and will be
     * removed shortly. Please switch to the renamed /searchResults/xyz endpoint. Retrieves videos
     * relationship. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("searchresults/{id}/relationships/videos")
    suspend fun searchresultsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchresultsMultiDataRelationshipDocument>
}
