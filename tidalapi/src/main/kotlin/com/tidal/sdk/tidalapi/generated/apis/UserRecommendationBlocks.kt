package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksArtistsAddMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksArtistsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksArtistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksTracksAddMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksTracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksTracksRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksTracksRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationBlocksVideosAddMultiRelationshipDataDocument
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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists, owners, tracks, videos (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artists.albums (optional)
     * @return [UserRecommendationBlocksSingleResourceDataDocument]
     */
    @GET("userRecommendationBlocks/{id}")
    suspend fun userRecommendationBlocksIdGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserRecommendationBlocksSingleResourceDataDocument>

    /**
     * Delete from artists relationship (\&quot;to-many\&quot;). Deletes item(s) from artists
     * relationship. Responses:
     * - 200: Successful response
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksArtistsRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(
        method = "DELETE",
        path = "userRecommendationBlocks/{id}/relationships/artists",
        hasBody = true,
    )
    suspend fun userRecommendationBlocksIdRelationshipsArtistsDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksArtistsRelationshipRemoveOperationPayload:
            UserRecommendationBlocksArtistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Retrieves artists relationship. Responses:
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artists.albums (optional)
     * @return [UserRecommendationBlocksArtistsMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/artists")
    suspend fun userRecommendationBlocksIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserRecommendationBlocksArtistsMultiRelationshipDataDocument>

    /**
     * Add to artists relationship (\&quot;to-many\&quot;). Adds item(s) to artists relationship.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Recommendation block limit reached; Request already in progress for this idempotency
     *   key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksArtistsRelationshipAddOperationPayload (optional)
     * @return [UserRecommendationBlocksArtistsAddMultiRelationshipDataDocument]
     */
    @POST("userRecommendationBlocks/{id}/relationships/artists")
    suspend fun userRecommendationBlocksIdRelationshipsArtistsPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksArtistsRelationshipAddOperationPayload:
            UserRecommendationBlocksArtistsRelationshipAddOperationPayload? =
            null,
    ): Response<UserRecommendationBlocksArtistsAddMultiRelationshipDataDocument>

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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserRecommendationBlocksOwnersMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/owners")
    suspend fun userRecommendationBlocksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationBlocksOwnersMultiRelationshipDataDocument>

    /**
     * Delete from tracks relationship (\&quot;to-many\&quot;). Deletes item(s) from tracks
     * relationship. Responses:
     * - 200: Successful response
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksTracksRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(
        method = "DELETE",
        path = "userRecommendationBlocks/{id}/relationships/tracks",
        hasBody = true,
    )
    suspend fun userRecommendationBlocksIdRelationshipsTracksDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksTracksRelationshipRemoveOperationPayload:
            UserRecommendationBlocksTracksRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * Get tracks relationship (\&quot;to-many\&quot;). Retrieves tracks relationship. Responses:
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: tracks (optional)
     * @return [UserRecommendationBlocksTracksMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/tracks")
    suspend fun userRecommendationBlocksIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserRecommendationBlocksTracksMultiRelationshipDataDocument>

    /**
     * Add to tracks relationship (\&quot;to-many\&quot;). Adds item(s) to tracks relationship.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Recommendation block limit reached; Request already in progress for this idempotency
     *   key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksTracksRelationshipAddOperationPayload (optional)
     * @return [UserRecommendationBlocksTracksAddMultiRelationshipDataDocument]
     */
    @POST("userRecommendationBlocks/{id}/relationships/tracks")
    suspend fun userRecommendationBlocksIdRelationshipsTracksPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksTracksRelationshipAddOperationPayload:
            UserRecommendationBlocksTracksRelationshipAddOperationPayload? =
            null,
    ): Response<UserRecommendationBlocksTracksAddMultiRelationshipDataDocument>

    /**
     * Delete from videos relationship (\&quot;to-many\&quot;). Deletes item(s) from videos
     * relationship. Responses:
     * - 200: Successful response
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksVideosRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(
        method = "DELETE",
        path = "userRecommendationBlocks/{id}/relationships/videos",
        hasBody = true,
    )
    suspend fun userRecommendationBlocksIdRelationshipsVideosDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksVideosRelationshipRemoveOperationPayload:
            UserRecommendationBlocksVideosRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * Get videos relationship (\&quot;to-many\&quot;). Retrieves videos relationship. Responses:
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
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: videos (optional)
     * @return [UserRecommendationBlocksVideosMultiRelationshipDataDocument]
     */
    @GET("userRecommendationBlocks/{id}/relationships/videos")
    suspend fun userRecommendationBlocksIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserRecommendationBlocksVideosMultiRelationshipDataDocument>

    /**
     * Add to videos relationship (\&quot;to-many\&quot;). Adds item(s) to videos relationship.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Recommendation block limit reached; Request already in progress for this idempotency
     *   key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User recommendation blocks id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userRecommendationBlocksVideosRelationshipAddOperationPayload (optional)
     * @return [UserRecommendationBlocksVideosAddMultiRelationshipDataDocument]
     */
    @POST("userRecommendationBlocks/{id}/relationships/videos")
    suspend fun userRecommendationBlocksIdRelationshipsVideosPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userRecommendationBlocksVideosRelationshipAddOperationPayload:
            UserRecommendationBlocksVideosRelationshipAddOperationPayload? =
            null,
    ): Response<UserRecommendationBlocksVideosAddMultiRelationshipDataDocument>
}
