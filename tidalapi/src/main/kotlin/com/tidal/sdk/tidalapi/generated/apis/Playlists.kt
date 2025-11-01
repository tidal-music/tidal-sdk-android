package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.PlaylistCoverArtRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistItemsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistItemsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistItemsRelationshipReorderOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistsItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Playlists {

    /** enum for parameter sort */
    enum class SortPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "createdAt") CreatedAtAsc("createdAt"),
        @SerialName(value = "-createdAt") CreatedAtDesc("-createdAt"),
        @SerialName(value = "lastModifiedAt") LastModifiedAtAsc("lastModifiedAt"),
        @SerialName(value = "-lastModifiedAt") LastModifiedAtDesc("-lastModifiedAt"),
        @SerialName(value = "name") NameAsc("name"),
        @SerialName(value = "-name") NameDesc("-name"),
    }

    /**
     * Get multiple playlists. Retrieves multiple playlists by available filters, or without if
     * applicable. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: coverArt, items, owners (optional)
     * @param filterOwnersId User id (optional)
     * @param filterId Playlist id (optional)
     * @return [PlaylistsMultiResourceDataDocument]
     */
    @GET("playlists")
    suspend fun playlistsGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlaylistsMultiResourceDataDocument>

    /**
     * Delete single playlist. Deletes existing playlist. Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @return [Unit]
     */
    @DELETE("playlists/{id}")
    suspend fun playlistsIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single playlist. Retrieves single playlist by id. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: coverArt, items, owners (optional)
     * @return [PlaylistsSingleResourceDataDocument]
     */
    @GET("playlists/{id}")
    suspend fun playlistsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlaylistsSingleResourceDataDocument>

    /**
     * Update single playlist. Updates existing playlist. Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param playlistUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("playlists/{id}")
    suspend fun playlistsIdPatch(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body playlistUpdateOperationPayload: PlaylistUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get coverArt relationship (\&quot;to-many\&quot;). Retrieves coverArt relationship.
     * Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: coverArt (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [PlaylistsMultiRelationshipDataDocument]
     */
    @GET("playlists/{id}/relationships/coverArt")
    suspend fun playlistsIdRelationshipsCoverArtGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlaylistsMultiRelationshipDataDocument>

    /**
     * Update coverArt relationship (\&quot;to-many\&quot;). Updates coverArt relationship.
     * Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param playlistCoverArtRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("playlists/{id}/relationships/coverArt")
    suspend fun playlistsIdRelationshipsCoverArtPatch(
        @Path("id") id: kotlin.String,
        @Body
        playlistCoverArtRelationshipUpdateOperationPayload:
            PlaylistCoverArtRelationshipUpdateOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from items relationship (\&quot;to-many\&quot;). Deletes item(s) from items
     * relationship. Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param playlistItemsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "playlists/{id}/relationships/items", hasBody = true)
    suspend fun playlistsIdRelationshipsItemsDelete(
        @Path("id") id: kotlin.String,
        @Body
        playlistItemsRelationshipRemoveOperationPayload:
            PlaylistItemsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get items relationship (\&quot;to-many\&quot;). Retrieves items relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @return [PlaylistsItemsMultiRelationshipDataDocument]
     */
    @GET("playlists/{id}/relationships/items")
    suspend fun playlistsIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlaylistsItemsMultiRelationshipDataDocument>

    /**
     * Update items relationship (\&quot;to-many\&quot;). Updates items relationship. Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param playlistItemsRelationshipReorderOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("playlists/{id}/relationships/items")
    suspend fun playlistsIdRelationshipsItemsPatch(
        @Path("id") id: kotlin.String,
        @Body
        playlistItemsRelationshipReorderOperationPayload:
            PlaylistItemsRelationshipReorderOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Add to items relationship (\&quot;to-many\&quot;). Adds item(s) to items relationship.
     * Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param playlistItemsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("playlists/{id}/relationships/items")
    suspend fun playlistsIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        playlistItemsRelationshipAddOperationPayload:
            PlaylistItemsRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Playlist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [PlaylistsMultiRelationshipDataDocument]
     */
    @GET("playlists/{id}/relationships/owners")
    suspend fun playlistsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlaylistsMultiRelationshipDataDocument>

    /**
     * Create single playlist. Creates a new playlist. Responses:
     * - 201: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param playlistCreateOperationPayload (optional)
     * @return [PlaylistsSingleResourceDataDocument]
     */
    @POST("playlists")
    suspend fun playlistsPost(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body playlistCreateOperationPayload: PlaylistCreateOperationPayload? = null,
    ): Response<PlaylistsSingleResourceDataDocument>
}
