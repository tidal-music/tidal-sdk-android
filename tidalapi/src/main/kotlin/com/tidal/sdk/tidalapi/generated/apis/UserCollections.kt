package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionAlbumsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionAlbumsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionArtistsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionArtistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserCollections {
    /**
     * Get all userCollections
     * Retrieves all userCollection details by available filters or without (if applicable).
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
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, artists, playlists (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [UserCollectionsMultiDataDocument]
     */
    @GET("userCollections")
    suspend fun userCollectionsGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<UserCollectionsMultiDataDocument>

    /**
     * Get single userCollection
     * Retrieves userCollection details by an unique id.
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, artists, playlists (optional)
     * @return [UserCollectionsSingleDataDocument]
     */
    @GET("userCollections/{id}")
    suspend fun userCollectionsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsSingleDataDocument>

    /**
     * Relationship: albums (remove)
     * Removes items from albums relationship of the related userCollection resource.
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
     * @param id User collection id
     * @param userCollectionAlbumsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsDelete(
        @Path("id") id: kotlin.String,
        @Body userCollectionAlbumsRelationshipRemoveOperationPayload:
        UserCollectionAlbumsRelationshipRemoveOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: albums
     * Retrieves albums relationship details of the related userCollection resource.
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserCollectionsMultiDataRelationshipDocument]
     */
    @GET("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionsMultiDataRelationshipDocument>

    /**
     * Relationship: albums (add)
     * Adds items to albums relationship of the related userCollection resource.
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param userCollectionAlbumsRelationshipAddOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Body userCollectionAlbumsRelationshipAddOperationPayload:
        UserCollectionAlbumsRelationshipAddOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: artists (remove)
     * Removes items from artists relationship of the related userCollection resource.
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
     * @param id User collection id
     * @param userCollectionArtistsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsDelete(
        @Path("id") id: kotlin.String,
        @Body userCollectionArtistsRelationshipRemoveOperationPayload:
        UserCollectionArtistsRelationshipRemoveOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: artists
     * Retrieves artists relationship details of the related userCollection resource.
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserCollectionsMultiDataRelationshipDocument]
     */
    @GET("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionsMultiDataRelationshipDocument>

    /**
     * Relationship: artists (add)
     * Adds items to artists relationship of the related userCollection resource.
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param userCollectionArtistsRelationshipAddOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Body userCollectionArtistsRelationshipAddOperationPayload:
        UserCollectionArtistsRelationshipAddOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: playlists (remove)
     * Removes items from playlists relationship of the related userCollection resource.
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
     * @param id User collection id
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsDelete(
        @Path("id") id: kotlin.String,
        @Body userCollectionPlaylistsRelationshipRemoveOperationPayload:
        UserCollectionPlaylistsRelationshipRemoveOperationPayload? = null,
    ): Response<Unit>

    /**
     * Relationship: playlists
     * Retrieves playlists relationship details of the related userCollection resource.
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: playlists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserCollectionsMultiDataRelationshipDocument]
     */
    @GET("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionsMultiDataRelationshipDocument>

    /**
     * Relationship: playlists (add)
     * Adds items to playlists relationship of the related userCollection resource.
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Body userCollectionPlaylistsRelationshipRemoveOperationPayload:
        UserCollectionPlaylistsRelationshipRemoveOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get current user&#39;s userCollection data
     * Retrieves current user&#39;s userCollection details.
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
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, artists, playlists (optional)
     * @return [UserCollectionsSingleDataDocument]
     */
    @GET("userCollections/me")
    suspend fun userCollectionsMeGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsSingleDataDocument>
}
