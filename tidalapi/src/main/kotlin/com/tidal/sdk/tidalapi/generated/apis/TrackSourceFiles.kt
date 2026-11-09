package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesCommentSummarySingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesReactionSummarySingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface TrackSourceFiles {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageTrackSourceFilesIdGet(val value: kotlin.String) {
        @SerialName(value = "commentSummary") commentSummary("commentSummary"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "reactionSummary") reactionSummary("reactionSummary"),
    }

    /**
     * GET trackSourceFiles/{id} Get single trackSourceFile. Retrieves single trackSourceFile by id.
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
     * @param id Track source file id
     * @param include Include related resources. Available relationships: commentSummary, owners,
     *   reactionSummary (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TrackSourceFilesSingleResourceDataDocument]
     */
    @GET("trackSourceFiles/{id}")
    suspend fun trackSourceFilesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TrackSourceFilesSingleResourceDataDocument>

    /**
     * GET trackSourceFiles/{id}/relationships/commentSummary Get commentSummary relationship
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
     * @param id Track source file id
     * @param include Include related resources. Available relationships: commentSummary (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TrackSourceFilesCommentSummarySingleRelationshipDataDocument]
     */
    @GET("trackSourceFiles/{id}/relationships/commentSummary")
    suspend fun trackSourceFilesIdRelationshipsCommentSummaryGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TrackSourceFilesCommentSummarySingleRelationshipDataDocument>

    /**
     * GET trackSourceFiles/{id}/relationships/owners Get owners relationship
     * (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id Track source file id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TrackSourceFilesOwnersMultiRelationshipDataDocument]
     */
    @GET("trackSourceFiles/{id}/relationships/owners")
    suspend fun trackSourceFilesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TrackSourceFilesOwnersMultiRelationshipDataDocument>

    /**
     * GET trackSourceFiles/{id}/relationships/reactionSummary Get reactionSummary relationship
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
     * @param id Track source file id
     * @param include Include related resources. Available relationships: reactionSummary (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TrackSourceFilesReactionSummarySingleRelationshipDataDocument]
     */
    @GET("trackSourceFiles/{id}/relationships/reactionSummary")
    suspend fun trackSourceFilesIdRelationshipsReactionSummaryGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TrackSourceFilesReactionSummarySingleRelationshipDataDocument>

    /**
     * POST trackSourceFiles Create single trackSourceFile. Create a track source file. &lt;p/&gt;
     * The response contains a upload link that must be used to upload the actual content.&lt;p/&gt;
     * The headers in the upload link response must be sent doing the actual upload. Responses:
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
     * @param trackSourceFilesCreateOperationPayload (optional)
     * @return [TrackSourceFilesCreateSingleResourceDataDocument]
     */
    @POST("trackSourceFiles")
    suspend fun trackSourceFilesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body trackSourceFilesCreateOperationPayload: TrackSourceFilesCreateOperationPayload? = null,
    ): Response<TrackSourceFilesCreateSingleResourceDataDocument>
}
