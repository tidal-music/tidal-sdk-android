package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.SavedSharesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SavedSharesCreateSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SavedShares {
    /**
     * POST savedShares Create single savedShare. Redeem an active share with VOUCHER access mode
     * for its offered role. A share without VOUCHER mode returns 403. A password-protected share
     * needs its password unless the caller owns or already saved it; attempts are rate limited.
     * Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 403: This share does not permit redemption; This share can no longer be accepted; The share
     *   is password protected; send its password; The share password is incorrect
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Too many share password attempts; try again later
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param savedSharesCreateOperationPayload (optional)
     * @return [SavedSharesCreateSingleResourceDataDocument]
     */
    @POST("savedShares")
    suspend fun savedSharesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body savedSharesCreateOperationPayload: SavedSharesCreateOperationPayload? = null,
    ): Response<SavedSharesCreateSingleResourceDataDocument>
}
