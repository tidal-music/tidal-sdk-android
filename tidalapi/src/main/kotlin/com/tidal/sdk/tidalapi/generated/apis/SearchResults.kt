package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ErrorDocument
import com.tidal.sdk.tidalapi.generated.models.SearchresultsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.SearchresultsSingleDataDocument

interface Searchresults {
    /**
     * Get single searchresult
     * Retrieves searchresult details by an unique id.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, artists, playlists, topHits, tracks, videos (optional)
     * @return [SearchresultsSingleDataDocument]
     */
    @GET("searchresults/{id}")
    suspend fun searchresultsIdGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<SearchresultsSingleDataDocument>

    /**
     * Relationship: albums
     * Retrieves albums relationship details of the related searchresult resource.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{id}/relationships/albums")
    suspend fun searchresultsIdRelationshipsAlbumsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: artists
     * Retrieves artists relationship details of the related searchresult resource.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{id}/relationships/artists")
    suspend fun searchresultsIdRelationshipsArtistsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: playlists
     * Retrieves playlists relationship details of the related searchresult resource.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: playlists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{id}/relationships/playlists")
    suspend fun searchresultsIdRelationshipsPlaylistsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: topHits
     * Retrieves topHits relationship details of the related searchresult resource.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: topHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{id}/relationships/topHits")
    suspend fun searchresultsIdRelationshipsTopHitsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: tracks
     * Retrieves tracks relationship details of the related searchresult resource.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: tracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{id}/relationships/tracks")
    suspend fun searchresultsIdRelationshipsTracksGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<SearchresultsMultiDataRelationshipDocument>

    /**
     * Relationship: videos
     * Retrieves videos relationship details of the related searchresult resource.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Search query
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: videos (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [SearchresultsMultiDataRelationshipDocument]
     */
    @GET("searchresults/{id}/relationships/videos")
    suspend fun searchresultsIdRelationshipsVideosGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<SearchresultsMultiDataRelationshipDocument>

}
