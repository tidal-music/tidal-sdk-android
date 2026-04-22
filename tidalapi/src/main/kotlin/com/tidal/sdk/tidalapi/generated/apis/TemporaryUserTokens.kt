package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TemporaryUserTokensCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TemporaryUserTokensMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TemporaryUserTokensSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface TemporaryUserTokens {
    /**
     * Get single temporaryUserToken. Retrieves single temporaryUserToken by id. Responses:
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Temporary user token id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [TemporaryUserTokensMultiRelationshipDataDocument]
     */
    @GET("temporaryUserTokens/{id}/relationships/owners")
    suspend fun temporaryUserTokensIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TemporaryUserTokensMultiRelationshipDataDocument>

    /**
     * Create single temporaryUserToken. Creates a new temporaryUserToken. Responses:
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
     * @param temporaryUserTokensCreateOperationPayload (optional)
     * @return [TemporaryUserTokensSingleResourceDataDocument]
     */
    @POST("temporaryUserTokens")
    suspend fun temporaryUserTokensPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        temporaryUserTokensCreateOperationPayload: TemporaryUserTokensCreateOperationPayload? = null,
    ): Response<TemporaryUserTokensSingleResourceDataDocument>
}
