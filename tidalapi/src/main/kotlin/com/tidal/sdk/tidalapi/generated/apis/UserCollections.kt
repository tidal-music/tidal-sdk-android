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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
