package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationPhysicalCountsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationPhysicalCountsCreateSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SquareItemVariationPhysicalCounts {
    /**
     * POST squareItemVariationPhysicalCounts Create single squareItemVariationPhysicalCount.
     * Records a whole-unit physical stock count at occurredAt. Requires stock tracking at the
     * selected site&#39;s shipping location (merchant-main fallback). While its replay receipt
     * remains available, exact retries retain the original location and event time. Catalog
     * revisions do not guard inventory counts. Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog and inventory scopes; reconnect Square; Square
     *   inventory cannot accept this physical count in its current state; Request already in
     *   progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemVariationPhysicalCountsCreateOperationPayload (optional)
     * @return [SquareItemVariationPhysicalCountsCreateSingleResourceDataDocument]
     */
    @POST("squareItemVariationPhysicalCounts")
    suspend fun squareItemVariationPhysicalCountsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body
        squareItemVariationPhysicalCountsCreateOperationPayload:
            SquareItemVariationPhysicalCountsCreateOperationPayload? =
            null,
    ): Response<SquareItemVariationPhysicalCountsCreateSingleResourceDataDocument>
}
