package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UsageRulesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UsageRulesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UsageRules {
    /**
     * Get single usageRule. Retrieves single usageRule by id. Responses:
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
     * @param id Usage rules id
     * @return [UsageRulesSingleResourceDataDocument]
     */
    @GET("usageRules/{id}")
    suspend fun usageRulesIdGet(
        @Path("id") id: kotlin.String
    ): Response<UsageRulesSingleResourceDataDocument>

    /**
     * Create single usageRule. Creates a new usageRule. Responses:
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
     * @param usageRulesCreateOperationPayload (optional)
     * @return [UsageRulesSingleResourceDataDocument]
     */
    @POST("usageRules")
    suspend fun usageRulesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body usageRulesCreateOperationPayload: UsageRulesCreateOperationPayload? = null,
    ): Response<UsageRulesSingleResourceDataDocument>
}
