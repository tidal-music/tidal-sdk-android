package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.CommentsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.CommentsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsUpdateOperationPayload
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Comments {

    /** enum for parameter sort */
    enum class SortCommentsGet(val value: kotlin.String) {
        @SerialName(value = "createdAt") CreatedAtAsc("createdAt"),
        @SerialName(value = "-createdAt") CreatedAtDesc("-createdAt"),
        @SerialName(value = "likeCount") LikeCountAsc("likeCount"),
        @SerialName(value = "-likeCount") LikeCountDesc("-likeCount"),
        @SerialName(value = "replyCount") ReplyCountAsc("replyCount"),
        @SerialName(value = "-replyCount") ReplyCountDesc("-replyCount"),
        @SerialName(value = "startTime") StartTimeAsc("startTime"),
        @SerialName(value = "-startTime") StartTimeDesc("-startTime"),
    }

    /** enum for parameter filterSubjectType */
    enum class FilterSubjectTypeCommentsGet(val value: kotlin.String) {
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "tracks") tracks("tracks"),
    }

    /**
     * Get multiple comments. Retrieves multiple comments by available filters, or without if
     * applicable. Responses:
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
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: ownerProfiles, owners, parentComment (optional)
     * @param filterParentCommentId Filter by parent comment ID to get replies (e.g.
     *   &#x60;550e8400-e29b-41d4-a716-446655440000&#x60;) (optional)
     * @param filterSubjectId Filter by subject resource ID (e.g. &#x60;12345&#x60;) (optional)
     * @param filterSubjectType Filter by subject resource type (e.g. &#x60;albums&#x60;) (optional)
     * @return [CommentsMultiResourceDataDocument]
     */
    @GET("comments")
    suspend fun commentsGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[parentComment.id]")
        filterParentCommentId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject.id]")
        filterSubjectId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[subject.type]")
        filterSubjectType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CommentsMultiResourceDataDocument>

    /**
     * Delete single comment. Deletes existing comment. Responses:
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
     * @param id Comment Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [Unit]
     */
    @DELETE("comments/{id}")
    suspend fun commentsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<Unit>

    /**
     * Get single comment. Retrieves single comment by id. Responses:
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
     * @param id Comment Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: ownerProfiles, owners, parentComment (optional)
     * @return [CommentsSingleResourceDataDocument]
     */
    @GET("comments/{id}")
    suspend fun commentsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CommentsSingleResourceDataDocument>

    /**
     * Update single comment. Updates existing comment. Responses:
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
     * @param id Comment Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param commentsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("comments/{id}")
    suspend fun commentsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body commentsUpdateOperationPayload: CommentsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get ownerProfiles relationship (\&quot;to-many\&quot;). Retrieves ownerProfiles relationship.
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
     * @param id Comment Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: ownerProfiles (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [CommentsMultiRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/ownerProfiles")
    suspend fun commentsIdRelationshipsOwnerProfilesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<CommentsMultiRelationshipDataDocument>

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
     * @param id Comment Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [CommentsMultiRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/owners")
    suspend fun commentsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<CommentsMultiRelationshipDataDocument>

    /**
     * Get parentComment relationship (\&quot;to-one\&quot;). Retrieves parentComment relationship.
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
     * @param id Comment Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: parentComment (optional)
     * @return [CommentsSingleRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/parentComment")
    suspend fun commentsIdRelationshipsParentCommentGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CommentsSingleRelationshipDataDocument>

    /**
     * Create single comment. Creates a new comment. Responses:
     * - 201: Successful response
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
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param commentsCreateOperationPayload (optional)
     * @return [CommentsSingleResourceDataDocument]
     */
    @POST("comments")
    suspend fun commentsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body commentsCreateOperationPayload: CommentsCreateOperationPayload? = null,
    ): Response<CommentsSingleResourceDataDocument>
}
