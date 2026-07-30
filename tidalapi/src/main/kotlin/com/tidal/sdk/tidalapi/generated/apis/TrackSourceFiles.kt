package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface TrackSourceFiles {
    /**
     * Get single trackSourceFile. Retrieves single trackSourceFile by id. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [TrackSourceFilesSingleResourceDataDocument]
     */
    @GET("trackSourceFiles/{id}")
    suspend fun trackSourceFilesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackSourceFilesSingleResourceDataDocument>

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
     * @param id Track source file id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [TrackSourceFilesMultiRelationshipDataDocument]
     */
    @GET("trackSourceFiles/{id}/relationships/owners")
    suspend fun trackSourceFilesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TrackSourceFilesMultiRelationshipDataDocument>

    /**
     * Create single trackSourceFile. Create a track source file. &lt;p/&gt; The response contains a
     * upload link that must be used to upload the actual content.&lt;p/&gt; The headers in the
     * upload link response must be sent doing the actual upload. Responses:
     * - 201: Successful response
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
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param trackSourceFilesCreateOperationPayload (optional)
     * @return [TrackSourceFilesSingleResourceDataDocument]
     */
    @POST("trackSourceFiles")
    suspend fun trackSourceFilesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body trackSourceFilesCreateOperationPayload: TrackSourceFilesCreateOperationPayload? = null,
    ): Response<TrackSourceFilesSingleResourceDataDocument>
}
