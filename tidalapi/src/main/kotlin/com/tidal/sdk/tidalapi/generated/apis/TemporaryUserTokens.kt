package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TemporaryUserTokensCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TemporaryUserTokensCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TemporaryUserTokensOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TemporaryUserTokensSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface TemporaryUserTokens {
    /**
     * Get single temporaryUserToken. Retrieves single temporaryUserToken by id. Responses:
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
     * @param id Temporary user token id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [TemporaryUserTokensSingleResourceDataDocument]
     */
    @GET("temporaryUserTokens/{id}")
    suspend fun temporaryUserTokensIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TemporaryUserTokensSingleResourceDataDocument>

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
     * @param id Temporary user token id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [TemporaryUserTokensOwnersMultiRelationshipDataDocument]
     */
    @GET("temporaryUserTokens/{id}/relationships/owners")
    suspend fun temporaryUserTokensIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TemporaryUserTokensOwnersMultiRelationshipDataDocument>

    /**
     * Create single temporaryUserToken. Creates a new temporaryUserToken. Responses:
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
     * @param temporaryUserTokensCreateOperationPayload (optional)
     * @return [TemporaryUserTokensCreateSingleResourceDataDocument]
     */
    @POST("temporaryUserTokens")
    suspend fun temporaryUserTokensPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        temporaryUserTokensCreateOperationPayload: TemporaryUserTokensCreateOperationPayload? = null,
    ): Response<TemporaryUserTokensCreateSingleResourceDataDocument>
}
