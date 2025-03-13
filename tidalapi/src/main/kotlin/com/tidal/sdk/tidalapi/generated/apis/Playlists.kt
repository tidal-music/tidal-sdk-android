package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ErrorDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsSingleDataDocument

interface Playlists {
    /**
     * Get current user&#39;s playlists
     * Get my playlists
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Playlists retrieved successfully
     *
     * @param include Allows the client to customize which related resources should be returned. Available options: items, owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [PlaylistsMultiDataDocument]
     */
    @GET("playlists/me")
    suspend fun getMyPlaylists(@Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<PlaylistsMultiDataDocument>

    /**
     * Get single playlist
     * Get playlist by id
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Playlist retrieved successfully
     *
     * @param id TIDAL playlist id
     * @param countryCode Country code (ISO 3166-1 alpha-2)
     * @param include Allows the client to customize which related resources should be returned. Available options: items, owners (optional)
     * @return [PlaylistsSingleDataDocument]
     */
    @GET("playlists/{id}")
    suspend fun getPlaylistById(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<PlaylistsSingleDataDocument>

    /**
     * Relationship: items
     * Get playlist items
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Playlist items retrieved successfully
     *
     * @param id TIDAL playlist id
     * @param countryCode Country code (ISO 3166-1 alpha-2)
     * @param include Allows the client to customize which related resources should be returned. Available options: items (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [PlaylistsMultiDataRelationshipDocument]
     */
    @GET("playlists/{id}/relationships/items")
    suspend fun getPlaylistItemsRelationship(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<PlaylistsMultiDataRelationshipDocument>

    /**
     * Relationship: owner
     * Get playlist owner
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Playlist owner retrieved successfully
     *
     * @param id TIDAL playlist id
     * @param countryCode Country code (ISO 3166-1 alpha-2)
     * @param include Allows the client to customize which related resources should be returned. Available options: owners (optional)
     * @return [PlaylistsMultiDataRelationshipDocument]
     */
    @GET("playlists/{id}/relationships/owners")
    suspend fun getPlaylistOwnersRelationship(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<PlaylistsMultiDataRelationshipDocument>

    /**
     * Get multiple playlists
     * Get user playlists
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Playlists retrieved successfully
     *
     * @param countryCode Country code (ISO 3166-1 alpha-2)
     * @param include Allows the client to customize which related resources should be returned. Available options: items, owners (optional)
     * @param filterId public.usercontent.getPlaylists.ids.descr (optional)
     * @return [PlaylistsMultiDataDocument]
     */
    @GET("playlists")
    suspend fun getPlaylistsByFilters(@Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<PlaylistsMultiDataDocument>

}
