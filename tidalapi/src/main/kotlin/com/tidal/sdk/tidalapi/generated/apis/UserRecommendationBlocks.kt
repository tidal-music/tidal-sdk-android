package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksArtistsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksArtistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksTracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksTracksRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksTracksRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksVideosMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksVideosRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksVideosRelationshipRemoveOperationPayload
import retrofit2.Response
import retrofit2.http.*

interface UserRecommendationBlocks {
    /**
     * Get single userRecommendationBlock. Retrieves single userRecommendationBlock by id.
     * Responses:
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists, owners, tracks, videos (optional)
     * @return [UserRecommendationBlocksSingleResourceDataDocument]
     */
    @GET("userRecommendationBlocks/{id}")
    suspend fun userRecommendationBlocksIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationBlocksSingleResourceDataDocument>

    /**
     * Delete from artists relationship (\&quot;to-many\&quot;). Deletes item(s) from artists
     * relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: A request with this idempotency key is currently being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksArtistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(
        method = "DELETE",
        path = "userRecommendationBlocks/{id}/relationships/artists",
        hasBody = true,
    )
    suspend fun userRecommendationBlocksIdRelationshipsArtistsDelete(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksArtistsRelationshipRemoveOperationPayload:
            UserRecommendationBlocksArtistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Retrieves artists relationship. Responses:
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @return [UserRecommendationBlocksArtistsMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/artists")
    suspend fun userRecommendationBlocksIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationBlocksArtistsMultiRelationshipDataDocument>

    /**
     * Add to artists relationship (\&quot;to-many\&quot;). Adds item(s) to artists relationship.
     * Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of blocked recommendation items. Please remove
     *   some blocked items before adding more.; A request with this idempotency key is currently
     *   being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksArtistsRelationshipAddOperationPayload (optional)
     * @return [UserRecommendationBlocksArtistsMultiRelationshipDataDocument]
     */
    @POST("userRecommendationBlocks/{id}/relationships/artists")
    suspend fun userRecommendationBlocksIdRelationshipsArtistsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksArtistsRelationshipAddOperationPayload:
            UserRecommendationBlocksArtistsRelationshipAddOperationPayload? =
            null,
    ): Response<UserRecommendationBlocksArtistsMultiRelationshipDataDocument>

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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserRecommendationBlocksMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/owners")
    suspend fun userRecommendationBlocksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationBlocksMultiRelationshipDataDocument>

    /**
     * Delete from tracks relationship (\&quot;to-many\&quot;). Deletes item(s) from tracks
     * relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: A request with this idempotency key is currently being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksTracksRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(
        method = "DELETE",
        path = "userRecommendationBlocks/{id}/relationships/tracks",
        hasBody = true,
    )
    suspend fun userRecommendationBlocksIdRelationshipsTracksDelete(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksTracksRelationshipRemoveOperationPayload:
            UserRecommendationBlocksTracksRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get tracks relationship (\&quot;to-many\&quot;). Retrieves tracks relationship. Responses:
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @return [UserRecommendationBlocksTracksMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/tracks")
    suspend fun userRecommendationBlocksIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationBlocksTracksMultiRelationshipDataDocument>

    /**
     * Add to tracks relationship (\&quot;to-many\&quot;). Adds item(s) to tracks relationship.
     * Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of blocked recommendation items. Please remove
     *   some blocked items before adding more.; A request with this idempotency key is currently
     *   being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksTracksRelationshipAddOperationPayload (optional)
     * @return [UserRecommendationBlocksTracksMultiRelationshipDataDocument]
     */
    @POST("userRecommendationBlocks/{id}/relationships/tracks")
    suspend fun userRecommendationBlocksIdRelationshipsTracksPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksTracksRelationshipAddOperationPayload:
            UserRecommendationBlocksTracksRelationshipAddOperationPayload? =
            null,
    ): Response<UserRecommendationBlocksTracksMultiRelationshipDataDocument>

    /**
     * Delete from videos relationship (\&quot;to-many\&quot;). Deletes item(s) from videos
     * relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: A request with this idempotency key is currently being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksVideosRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(
        method = "DELETE",
        path = "userRecommendationBlocks/{id}/relationships/videos",
        hasBody = true,
    )
    suspend fun userRecommendationBlocksIdRelationshipsVideosDelete(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksVideosRelationshipRemoveOperationPayload:
            UserRecommendationBlocksVideosRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get videos relationship (\&quot;to-many\&quot;). Retrieves videos relationship. Responses:
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @return [UserRecommendationBlocksVideosMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/videos")
    suspend fun userRecommendationBlocksIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationBlocksVideosMultiRelationshipDataDocument>

    /**
     * Add to videos relationship (\&quot;to-many\&quot;). Adds item(s) to videos relationship.
     * Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of blocked recommendation items. Please remove
     *   some blocked items before adding more.; A request with this idempotency key is currently
     *   being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksVideosRelationshipAddOperationPayload (optional)
     * @return [UserRecommendationBlocksVideosMultiRelationshipDataDocument]
     */
    @POST("userRecommendationBlocks/{id}/relationships/videos")
    suspend fun userRecommendationBlocksIdRelationshipsVideosPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksVideosRelationshipAddOperationPayload:
            UserRecommendationBlocksVideosRelationshipAddOperationPayload? =
            null,
    ): Response<UserRecommendationBlocksVideosMultiRelationshipDataDocument>
}
