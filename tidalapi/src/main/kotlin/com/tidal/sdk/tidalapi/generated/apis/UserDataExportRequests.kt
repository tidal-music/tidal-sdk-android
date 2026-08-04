package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserDataExportRequestsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserDataExportRequestsCreateSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserDataExportRequests {
    /**
     * Create single userDataExportRequest. Creates a new userDataExportRequest. Responses:
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
     * @param userDataExportRequestsCreateOperationPayload (optional)
     * @return [UserDataExportRequestsCreateSingleResourceDataDocument]
     */
    @POST("userDataExportRequests")
    suspend fun userDataExportRequestsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userDataExportRequestsCreateOperationPayload:
            UserDataExportRequestsCreateOperationPayload? =
            null,
    ): Response<UserDataExportRequestsCreateSingleResourceDataDocument>
}
