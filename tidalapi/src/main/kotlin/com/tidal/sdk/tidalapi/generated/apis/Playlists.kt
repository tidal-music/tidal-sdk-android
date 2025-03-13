package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.PlaylistCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistItemsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistItemsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistItemsRelationshipReorderOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Playlists {
    /**
     * Get all playlists
     * Retrieves all playlist details by available filters or without (if applicable).
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
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: items, owners (optional)
     * @param filterId Playlist id (optional)
     * @return [PlaylistsMultiDataDocument]
     */
    @GET("playlists")
    suspend fun playlistsGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<PlaylistsMultiDataDocument>

    /**
     * Delete single playlist
     * Deletes the existing instance of playlist&#39;s resource.
     * Responses:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Playlist id
     * @return [Unit]
     */
    @DELETE("playlists/{id}")
    suspend fun playlistsIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single playlist
     * Retrieves playlist details by an unique id.
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
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: items, owners (optional)
     * @return [PlaylistsSingleDataDocument]
     */
    @GET("playlists/{id}")
    suspend fun playlistsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlaylistsSingleDataDocument>

    /**
     * Update single playlist
     * Updates the existing instance of playlist&#39;s resource.
     * Responses:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Playlist id
     * @param playlistUpdateOperationPayload  (optional)
     * @return [Unit]
     */
    @PATCH("playlists/{id}")
    suspend fun playlistsIdPatch(
        @Path("id") id: kotlin.String,
        @Body playlistUpdateOperationPayload: PlaylistUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: items (remove)
     * Removes items from items relationship of the related playlist resource.
     * Responses:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Playlist id
     * @param playlistItemsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("playlists/{id}/relationships/items")
    suspend fun playlistsIdRelationshipsItemsDelete(
        @Path("id") id: kotlin.String,
        @Body playlistItemsRelationshipRemoveOperationPayload:
        PlaylistItemsRelationshipRemoveOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: items
     * Retrieves items relationship details of the related playlist resource.
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
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: items (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [PlaylistsMultiDataRelationshipDocument]
     */
    @GET("playlists/{id}/relationships/items")
    suspend fun playlistsIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlaylistsMultiDataRelationshipDocument>

    /**
     * Relationship: items (create/update/delete)
     * Manages items relationship details of the related playlist resource. Use this operation if you need to create, update or delete relationship&#39;s linkage.
     * Responses:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param playlistItemsRelationshipReorderOperationPayload  (optional)
     * @return [Unit]
     */
    @PATCH("playlists/{id}/relationships/items")
    suspend fun playlistsIdRelationshipsItemsPatch(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Body playlistItemsRelationshipReorderOperationPayload:
        PlaylistItemsRelationshipReorderOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: items (add)
     * Adds items to items relationship of the related playlist resource.
     * Responses:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param playlistItemsRelationshipAddOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("playlists/{id}/relationships/items")
    suspend fun playlistsIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Body playlistItemsRelationshipAddOperationPayload:
        PlaylistItemsRelationshipAddOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: owners
     * Retrieves owners relationship details of the related playlist resource.
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
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [PlaylistsMultiDataRelationshipDocument]
     */
    @GET("playlists/{id}/relationships/owners")
    suspend fun playlistsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlaylistsMultiDataRelationshipDocument>

    /**
     * Get current user&#39;s playlist data
     * Retrieves current user&#39;s playlist details.
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
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: items, owners (optional)
     * @return [PlaylistsMultiDataDocument]
     */
    @GET("playlists/me")
    suspend fun playlistsMeGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlaylistsMultiDataDocument>

    /**
     * Create single playlist
     * Creates a new instance of playlist&#39;s resource.
     * Responses:
     *  - 201:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param playlistCreateOperationPayload  (optional)
     * @return [PlaylistsSingleDataDocument]
     */
    @POST("playlists")
    suspend fun playlistsPost(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Body playlistCreateOperationPayload: PlaylistCreateOperationPayload? = null,
    ): Response<PlaylistsSingleDataDocument>
}
