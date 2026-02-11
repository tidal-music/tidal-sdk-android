package com.tidal.sdk.tidalapi.generated.apis

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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection tracks id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items, owners (optional)
     * @return [UserCollectionTracksSingleResourceDataDocument]
     */
    @GET("userCollectionTracks/{id}")
    suspend fun userCollectionTracksIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionTracksSingleResourceDataDocument>

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
     * @param id User collection tracks id
     * @param userCollectionTracksItemsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "userCollectionTracks/{id}/relationships/items", hasBody = true)
    suspend fun userCollectionTracksIdRelationshipsItemsDelete(
        @Path("id") id: kotlin.String,
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection tracks id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @return [UserCollectionTracksItemsMultiRelationshipDataDocument]
     */
    @GET("userCollectionTracks/{id}/relationships/items")
    suspend fun userCollectionTracksIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionTracksItemsMultiRelationshipDataDocument>

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
     * @param id User collection tracks id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionTracksItemsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollectionTracks/{id}/relationships/items")
    suspend fun userCollectionTracksIdRelationshipsItemsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionTracksItemsRelationshipAddOperationPayload:
            UserCollectionTracksItemsRelationshipAddOperationPayload? =
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
     * @param id User collection tracks id
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
