package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.AddPayload
import com.tidal.sdk.tidalapi.generated.models.FolderCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FolderUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.RemovePayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionFoldersItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionFoldersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionFoldersMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionFoldersSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface UserCollectionFolders {
    /**
     * Get multiple userCollectionFolders. Retrieves multiple userCollectionFolders by available
     * filters, or without if applicable. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items, owners (optional)
     * @param filterId Folder Id (optional)
     * @return [UserCollectionFoldersMultiResourceDataDocument]
     */
    @GET("userCollectionFolders")
    suspend fun userCollectionFoldersGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionFoldersMultiResourceDataDocument>

    /**
     * Delete single userCollectionFolder. Deletes existing userCollectionFolder. Responses:
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
     * @param id Folder Id
     * @return [Unit]
     */
    @DELETE("userCollectionFolders/{id}")
    suspend fun userCollectionFoldersIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single userCollectionFolder. Retrieves single userCollectionFolder by id. Responses:
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
     * @param id Folder Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items, owners (optional)
     * @return [UserCollectionFoldersSingleResourceDataDocument]
     */
    @GET("userCollectionFolders/{id}")
    suspend fun userCollectionFoldersIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionFoldersSingleResourceDataDocument>

    /**
     * Update single userCollectionFolder. Updates existing userCollectionFolder. Responses:
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
     * @param id Folder Id
     * @param folderUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("userCollectionFolders/{id}")
    suspend fun userCollectionFoldersIdPatch(
        @Path("id") id: kotlin.String,
        @Body folderUpdateOperationPayload: FolderUpdateOperationPayload? = null,
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
     * @param removePayload (optional)
     * @return [Unit]
     */
    @HTTP(
        method = "DELETE",
        path = "userCollectionFolders/{id}/relationships/items",
        hasBody = true,
    )
    suspend fun userCollectionFoldersIdRelationshipsItemsDelete(
        @Body removePayload: RemovePayload? = null
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionFoldersIdRelationshipsItemsGet(val value: kotlin.String) {
        @SerialName(value = "items.addedAt") ItemsAddedAtAsc("items.addedAt"),
        @SerialName(value = "-items.addedAt") ItemsAddedAtDesc("-items.addedAt"),
        @SerialName(value = "items.lastModifiedAt") ItemsLastModifiedAtAsc("items.lastModifiedAt"),
        @SerialName(value = "-items.lastModifiedAt")
        ItemsLastModifiedAtDesc("-items.lastModifiedAt"),
        @SerialName(value = "items.name") ItemsNameAsc("items.name"),
        @SerialName(value = "-items.name") ItemsNameDesc("-items.name"),
    }

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
     * @param id Folder Id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @return [UserCollectionFoldersItemsMultiRelationshipDataDocument]
     */
    @GET("userCollectionFolders/{id}/relationships/items")
    suspend fun userCollectionFoldersIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionFoldersItemsMultiRelationshipDataDocument>

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
     * @param id Folder Id
     * @param addPayload (optional)
     * @return [Unit]
     */
    @POST("userCollectionFolders/{id}/relationships/items")
    suspend fun userCollectionFoldersIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Body addPayload: AddPayload? = null,
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
     * @param id Folder Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserCollectionFoldersMultiRelationshipDataDocument]
     */
    @GET("userCollectionFolders/{id}/relationships/owners")
    suspend fun userCollectionFoldersIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionFoldersMultiRelationshipDataDocument>

    /**
     * Create single userCollectionFolder. Creates a new userCollectionFolder. Responses:
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
     * @param folderCreateOperationPayload (optional)
     * @return [UserCollectionFoldersSingleResourceDataDocument]
     */
    @POST("userCollectionFolders")
    suspend fun userCollectionFoldersPost(
        @Body folderCreateOperationPayload: FolderCreateOperationPayload? = null
    ): Response<UserCollectionFoldersSingleResourceDataDocument>
}
