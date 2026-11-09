package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.CommentsAuthorSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsCommentSummarySingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.CommentsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsReactionSummarySingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsRootSubjectSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsSubjectSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CommentsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Comments {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageCommentsGet(val value: kotlin.String) {
        @SerialName(value = "author") author("author"),
        @SerialName(value = "commentSummary") commentSummary("commentSummary"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "reactionSummary") reactionSummary("reactionSummary"),
        @SerialName(value = "rootSubject") rootSubject("rootSubject"),
        @SerialName(value = "subject") subject("subject"),
    }

    /** enum for parameter sort */
    @Serializable
    enum class SortCommentsGet(val value: kotlin.String) {
        @SerialName(value = "createdAt") CreatedAtAsc("createdAt"),
        @SerialName(value = "-createdAt") CreatedAtDesc("-createdAt"),
    }

    /**
     * GET comments Get multiple comments. Returns root comments on a resource, or replies to a
     * comment. Both support createdAt in either direction, newest first by default. Responses:
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
     * @param filterSubject The immediate subject whose comments to return.
     * @param include Include related resources. Available relationships: author, commentSummary,
     *   owners, reactionSummary, rootSubject, subject (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: author.albums (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @return [CommentsMultiResourceDataDocument]
     */
    @GET("comments")
    suspend fun commentsGet(
        @Query("filter[subject]") filterSubject: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CommentsMultiResourceDataDocument>

    /**
     * DELETE comments/{id} Delete single comment. Deletes existing comment. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Comment Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("comments/{id}")
    suspend fun commentsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageCommentsIdGet(val value: kotlin.String) {
        @SerialName(value = "author") author("author"),
        @SerialName(value = "commentSummary") commentSummary("commentSummary"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "reactionSummary") reactionSummary("reactionSummary"),
        @SerialName(value = "rootSubject") rootSubject("rootSubject"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET comments/{id} Get single comment. Retrieves single comment by id. Responses:
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
     * @param id Comment Id
     * @param include Include related resources. Available relationships: author, commentSummary,
     *   owners, reactionSummary, rootSubject, subject (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: author.albums (optional)
     * @return [CommentsSingleResourceDataDocument]
     */
    @GET("comments/{id}")
    suspend fun commentsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<CommentsSingleResourceDataDocument>

    /**
     * PATCH comments/{id} Update single comment. Updates existing comment. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Comment Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param commentsUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("comments/{id}")
    suspend fun commentsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body commentsUpdateOperationPayload: CommentsUpdateOperationPayload? = null,
    ): Response<MutationResponseDocument>

    /**
     * GET comments/{id}/relationships/author Get author relationship (\&quot;to-one\&quot;). The
     * artist who wrote the comment. Responses:
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
     * @param id Comment Id
     * @param include Include related resources. Available relationships: author (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: author.albums (optional)
     * @return [CommentsAuthorSingleRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/author")
    suspend fun commentsIdRelationshipsAuthorGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<CommentsAuthorSingleRelationshipDataDocument>

    /**
     * GET comments/{id}/relationships/commentSummary Get commentSummary relationship
     * (\&quot;to-one\&quot;). Retrieves commentSummary relationship. Responses:
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
     * @param id Comment Id
     * @param include Include related resources. Available relationships: commentSummary (optional)
     * @return [CommentsCommentSummarySingleRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/commentSummary")
    suspend fun commentsIdRelationshipsCommentSummaryGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CommentsCommentSummarySingleRelationshipDataDocument>

    /**
     * GET comments/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
     * Retrieves owners relationship. Responses:
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
     * @param id Comment Id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [CommentsOwnersMultiRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/owners")
    suspend fun commentsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<CommentsOwnersMultiRelationshipDataDocument>

    /**
     * GET comments/{id}/relationships/reactionSummary Get reactionSummary relationship
     * (\&quot;to-one\&quot;). Retrieves reactionSummary relationship. Responses:
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
     * @param id Comment Id
     * @param include Include related resources. Available relationships: reactionSummary (optional)
     * @return [CommentsReactionSummarySingleRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/reactionSummary")
    suspend fun commentsIdRelationshipsReactionSummaryGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CommentsReactionSummarySingleRelationshipDataDocument>

    /**
     * GET comments/{id}/relationships/rootSubject Get rootSubject relationship
     * (\&quot;to-one\&quot;). Retrieves rootSubject relationship. Responses:
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
     * @param id Comment Id
     * @param include Include related resources. Available relationships: rootSubject (optional)
     * @return [CommentsRootSubjectSingleRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/rootSubject")
    suspend fun commentsIdRelationshipsRootSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CommentsRootSubjectSingleRelationshipDataDocument>

    /**
     * GET comments/{id}/relationships/subject Get subject relationship (\&quot;to-one\&quot;).
     * Retrieves subject relationship. Responses:
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
     * @param id Comment Id
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject.author.albums (optional)
     * @return [CommentsSubjectSingleRelationshipDataDocument]
     */
    @GET("comments/{id}/relationships/subject")
    suspend fun commentsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<CommentsSubjectSingleRelationshipDataDocument>

    /**
     * POST comments Create single comment. Creates a root comment on a resource or a reply whose
     * subject is a root comment. Replies to replies are rejected. Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param commentsCreateOperationPayload (optional)
     * @return [CommentsCreateSingleResourceDataDocument]
     */
    @POST("comments")
    suspend fun commentsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body commentsCreateOperationPayload: CommentsCreateOperationPayload? = null,
    ): Response<CommentsCreateSingleResourceDataDocument>
}
