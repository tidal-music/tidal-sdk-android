package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsItemsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsItemsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface UserCollectionPlaylists {
    /**
     * Get single userCollectionPlaylist. Retrieves single userCollectionPlaylist by id. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection playlists id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items, owners (optional)
     * @return [UserCollectionPlaylistsSingleResourceDataDocument]
     */
    @GET("userCollectionPlaylists/{id}")
    suspend fun userCollectionPlaylistsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionPlaylistsSingleResourceDataDocument>

    /**
     * Delete from items relationship (\&quot;to-many\&quot;). Deletes item(s) from items
     * relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection playlists id
     * @param userCollectionPlaylistsItemsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(
        method = "DELETE",
        path = "userCollectionPlaylists/{id}/relationships/items",
        hasBody = true,
    )
    suspend fun userCollectionPlaylistsIdRelationshipsItemsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionPlaylistsItemsRelationshipRemoveOperationPayload:
            UserCollectionPlaylistsItemsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter collectionView */
    enum class CollectionViewUserCollectionPlaylistsIdRelationshipsItemsGet(
        val value: kotlin.String
    ) {
        @SerialName(value = "FOLDERS") FOLDERS("FOLDERS")
    }

    /** enum for parameter sort */
    enum class SortUserCollectionPlaylistsIdRelationshipsItemsGet(val value: kotlin.String) {
        @SerialName(value = "addedAt") AddedAtAsc("addedAt"),
        @SerialName(value = "-addedAt") AddedAtDesc("-addedAt"),
        @SerialName(value = "lastModifiedAt") LastModifiedAtAsc("lastModifiedAt"),
        @SerialName(value = "-lastModifiedAt") LastModifiedAtDesc("-lastModifiedAt"),
        @SerialName(value = "name") NameAsc("name"),
        @SerialName(value = "-name") NameDesc("-name"),
    }

    /**
     * Get items relationship (\&quot;to-many\&quot;). Retrieves items relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection playlists id
     * @param collectionView (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @return [UserCollectionPlaylistsItemsMultiRelationshipDataDocument]
     */
    @GET("userCollectionPlaylists/{id}/relationships/items")
    suspend fun userCollectionPlaylistsIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("collectionView")
        collectionView: CollectionViewUserCollectionPlaylistsIdRelationshipsItemsGet? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionPlaylistsItemsMultiRelationshipDataDocument>

    /**
     * Add to items relationship (\&quot;to-many\&quot;). Adds item(s) to items relationship.
     * Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of items allowed for this collection. Please
     *   remove some items before adding more.; One or more items you are trying to add are already
     *   in your favorites.
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection playlists id
     * @param userCollectionPlaylistsItemsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollectionPlaylists/{id}/relationships/items")
    suspend fun userCollectionPlaylistsIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionPlaylistsItemsRelationshipAddOperationPayload:
            UserCollectionPlaylistsItemsRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection playlists id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserCollectionPlaylistsMultiRelationshipDataDocument]
     */
    @GET("userCollectionPlaylists/{id}/relationships/owners")
    suspend fun userCollectionPlaylistsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionPlaylistsMultiRelationshipDataDocument>
}
