package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.AppreciationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import retrofit2.Response
import retrofit2.http.*

interface Appreciations {
    /**
     * POST appreciations Create single appreciation. Creates a new appreciation. Responses:
     * - 200: Successful dry run
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
     * @param appreciationsCreateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @POST("appreciations")
    suspend fun appreciationsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body appreciationsCreateOperationPayload: AppreciationsCreateOperationPayload? = null,
    ): Response<MutationResponseDocument>
}
