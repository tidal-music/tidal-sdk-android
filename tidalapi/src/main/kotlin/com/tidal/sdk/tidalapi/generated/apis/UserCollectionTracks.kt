package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksItemsAddMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksItemsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksItemsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface UserCollectionTracks {
    /**
     * Get single userCollectionTrack. Retrieves single userCollectionTrack by id. Responses:
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
     * @param id User collection tracks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items, owners (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items (optional)
     * @return [UserCollectionTracksSingleResourceDataDocument]
     */
    @GET("userCollectionTracks/{id}")
    suspend fun userCollectionTracksIdGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserCollectionTracksSingleResourceDataDocument>

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
     * @param id User collection tracks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userCollectionTracksItemsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "userCollectionTracks/{id}/relationships/items", hasBody = true)
    suspend fun userCollectionTracksIdRelationshipsItemsDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userCollectionTracksItemsRelationshipRemoveOperationPayload:
            UserCollectionTracksItemsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionTracksIdRelationshipsItemsGet(val value: kotlin.String) {
        @SerialName(value = "addedAt") AddedAtAsc("addedAt"),
        @SerialName(value = "-addedAt") AddedAtDesc("-addedAt"),
        @SerialName(value = "albums.title") AlbumsTitleAsc("albums.title"),
        @SerialName(value = "-albums.title") AlbumsTitleDesc("-albums.title"),
        @SerialName(value = "artists.name") ArtistsNameAsc("artists.name"),
        @SerialName(value = "-artists.name") ArtistsNameDesc("-artists.name"),
        @SerialName(value = "duration") DurationAsc("duration"),
        @SerialName(value = "-duration") DurationDesc("-duration"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
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
     * @param id User collection tracks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items (optional)
     * @return [UserCollectionTracksItemsMultiRelationshipDataDocument]
     */
    @GET("userCollectionTracks/{id}/relationships/items")
    suspend fun userCollectionTracksIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserCollectionTracksItemsMultiRelationshipDataDocument>

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
     * @param id User collection tracks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userCollectionTracksItemsRelationshipAddOperationPayload (optional)
     * @return [UserCollectionTracksItemsAddMultiRelationshipDataDocument]
     */
    @POST("userCollectionTracks/{id}/relationships/items")
    suspend fun userCollectionTracksIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userCollectionTracksItemsRelationshipAddOperationPayload:
            UserCollectionTracksItemsRelationshipAddOperationPayload? =
            null,
    ): Response<UserCollectionTracksItemsAddMultiRelationshipDataDocument>

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
     * @param id User collection tracks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserCollectionTracksMultiRelationshipDataDocument]
     */
    @GET("userCollectionTracks/{id}/relationships/owners")
    suspend fun userCollectionTracksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionTracksMultiRelationshipDataDocument>
}
