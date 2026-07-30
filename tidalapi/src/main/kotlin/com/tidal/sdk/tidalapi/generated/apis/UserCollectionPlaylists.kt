package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsItemsAddMultiRelationshipDataDocument
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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User collection playlists id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items, owners (optional)
     * @return [UserCollectionPlaylistsSingleResourceDataDocument]
     */
    @GET("userCollectionPlaylists/{id}")
    suspend fun userCollectionPlaylistsIdGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionPlaylistsSingleResourceDataDocument>

    /**
     * Delete from items relationship (\&quot;to-many\&quot;). Deletes item(s) from items
     * relationship. Responses:
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User collection playlists id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
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
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userCollectionPlaylistsItemsRelationshipRemoveOperationPayload:
            UserCollectionPlaylistsItemsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter collectionView */
    enum class CollectionViewUserCollectionPlaylistsIdRelationshipsItemsGet(
        val value: kotlin.String
    ) {
        @SerialName(value = "FLAT") FLAT("FLAT"),
        @SerialName(value = "FOLDERS") FOLDERS("FOLDERS"),
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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User collection playlists id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
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
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Collection item limit reached; Collection already contains one or more items; Request
     *   already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User collection playlists id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userCollectionPlaylistsItemsRelationshipAddOperationPayload (optional)
     * @return [UserCollectionPlaylistsItemsAddMultiRelationshipDataDocument]
     */
    @POST("userCollectionPlaylists/{id}/relationships/items")
    suspend fun userCollectionPlaylistsIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userCollectionPlaylistsItemsRelationshipAddOperationPayload:
            UserCollectionPlaylistsItemsRelationshipAddOperationPayload? =
            null,
    ): Response<UserCollectionPlaylistsItemsAddMultiRelationshipDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User collection playlists id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
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
