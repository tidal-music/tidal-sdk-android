package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsSelectedSiteRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsSelectedSiteSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsSelectedSiteUpdateSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareConnectionsSitesMultiRelationshipDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SquareConnections {
    /**
     * Get single squareConnection. Retrieves single squareConnection by id. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square connection id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: selectedSite, sites (optional)
     * @return [SquareConnectionsSingleResourceDataDocument]
     */
    @GET("squareConnections/{id}")
    suspend fun squareConnectionsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SquareConnectionsSingleResourceDataDocument>

    /**
     * Get selectedSite relationship (\&quot;to-one\&quot;). Retrieves selectedSite relationship.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square connection id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: selectedSite (optional)
     * @return [SquareConnectionsSelectedSiteSingleRelationshipDataDocument]
     */
    @GET("squareConnections/{id}/relationships/selectedSite")
    suspend fun squareConnectionsIdRelationshipsSelectedSiteGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SquareConnectionsSelectedSiteSingleRelationshipDataDocument>

    /**
     * Update selectedSite relationship (\&quot;to-one\&quot;). Updates selectedSite relationship.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks required site scopes; run Square onboarding again; Request
     *   already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square connection id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param squareConnectionsSelectedSiteRelationshipUpdateOperationPayload (optional)
     * @return [SquareConnectionsSelectedSiteUpdateSingleRelationshipDataDocument]
     */
    @PATCH("squareConnections/{id}/relationships/selectedSite")
    suspend fun squareConnectionsIdRelationshipsSelectedSitePatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        squareConnectionsSelectedSiteRelationshipUpdateOperationPayload:
            SquareConnectionsSelectedSiteRelationshipUpdateOperationPayload? =
            null,
    ): Response<SquareConnectionsSelectedSiteUpdateSingleRelationshipDataDocument>

    /**
     * Get sites relationship (\&quot;to-many\&quot;). Retrieves sites relationship. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square connection id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: sites (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareConnectionsSitesMultiRelationshipDataDocument]
     */
    @GET("squareConnections/{id}/relationships/sites")
    suspend fun squareConnectionsIdRelationshipsSitesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareConnectionsSitesMultiRelationshipDataDocument>

    /**
     * Create single squareConnection. Creates a new squareConnection. Responses:
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
     * @param squareConnectionsCreateOperationPayload (optional)
     * @return [SquareConnectionsCreateSingleResourceDataDocument]
     */
    @POST("squareConnections")
    suspend fun squareConnectionsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        squareConnectionsCreateOperationPayload: SquareConnectionsCreateOperationPayload? = null,
    ): Response<SquareConnectionsCreateSingleResourceDataDocument>
}
