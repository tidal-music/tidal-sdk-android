package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SquareConnectionsApi {
    /**
     * Get single squareConnection. Retrieves single squareConnection by id. Responses:
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
     * @param id Square connection id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @return [SquareConnectionsSingleResourceDataDocument]
     */
    @GET("squareConnections/{id}")
    suspend fun squareConnectionsIdGet(
        @Path("id") id: kotlin.String
    ): Response<SquareConnectionsSingleResourceDataDocument>

    /**
     * Create single squareConnection. Creates a new squareConnection. Responses:
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
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param squareConnectionsCreateOperationPayload (optional)
     * @return [SquareConnectionsSingleResourceDataDocument]
     */
    @POST("squareConnections")
    suspend fun squareConnectionsPost(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        squareConnectionsCreateOperationPayload: SquareConnectionsCreateOperationPayload? = null,
    ): Response<SquareConnectionsSingleResourceDataDocument>
}
