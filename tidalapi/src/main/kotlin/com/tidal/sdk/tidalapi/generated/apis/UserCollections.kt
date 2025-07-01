package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionAlbumsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionAlbumsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionArtistsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionArtistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsAlbumsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsArtistsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsPlaylistsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserCollections {
    /**
     * Get single userCollection. Retrieves single userCollection by id. Responses:
     * - 200: Successful response
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
     * @param id User id
     * @param locale BCP 47 locale
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, owners, playlists (optional)
     * @return [UserCollectionsSingleDataDocument]
     */
    @GET("userCollections/{id}")
    suspend fun userCollectionsIdGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsSingleDataDocument>

    /**
     * Delete from albums relationship (\&quot;to-many\&quot;). Deletes item(s) from albums
     * relationship. Responses:
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
     * @param id User id
     * @param userCollectionAlbumsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionAlbumsRelationshipRemoveOperationPayload:
            UserCollectionAlbumsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get albums relationship (\&quot;to-many\&quot;). Retrieves albums relationship. Responses:
     * - 200: Successful response
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP 47 locale
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @return [UserCollectionsAlbumsMultiDataRelationshipDocument]
     */
    @GET("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsAlbumsMultiDataRelationshipDocument>

    /**
     * Add to albums relationship (\&quot;to-many\&quot;). Adds item(s) to albums relationship.
     * Responses:
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param userCollectionAlbumsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Body
        userCollectionAlbumsRelationshipAddOperationPayload:
            UserCollectionAlbumsRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from artists relationship (\&quot;to-many\&quot;). Deletes item(s) from artists
     * relationship. Responses:
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
     * @param id User id
     * @param userCollectionArtistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionArtistsRelationshipRemoveOperationPayload:
            UserCollectionArtistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Retrieves artists relationship. Responses:
     * - 200: Successful response
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP 47 locale
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @return [UserCollectionsArtistsMultiDataRelationshipDocument]
     */
    @GET("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsArtistsMultiDataRelationshipDocument>

    /**
     * Add to artists relationship (\&quot;to-many\&quot;). Adds item(s) to artists relationship.
     * Responses:
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param userCollectionArtistsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Body
        userCollectionArtistsRelationshipAddOperationPayload:
            UserCollectionArtistsRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
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
     * @param id User id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserCollectionsMultiDataRelationshipDocument]
     */
    @GET("userCollections/{id}/relationships/owners")
    suspend fun userCollectionsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionsMultiDataRelationshipDocument>

    /**
     * Delete from playlists relationship (\&quot;to-many\&quot;). Deletes item(s) from playlists
     * relationship. Responses:
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
     * @param id User id
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionPlaylistsRelationshipRemoveOperationPayload:
            UserCollectionPlaylistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get playlists relationship (\&quot;to-many\&quot;). Retrieves playlists relationship.
     * Responses:
     * - 200: Successful response
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlists (optional)
     * @return [UserCollectionsPlaylistsMultiDataRelationshipDocument]
     */
    @GET("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsPlaylistsMultiDataRelationshipDocument>

    /**
     * Add to playlists relationship (\&quot;to-many\&quot;). Adds item(s) to playlists
     * relationship. Responses:
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
     * @param id User id
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsPost(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionPlaylistsRelationshipRemoveOperationPayload:
            UserCollectionPlaylistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>
}
