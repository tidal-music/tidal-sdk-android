package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UsageRulesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UsageRulesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UsageRulesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UsageRules {
    /**
     * Get multiple usageRules. Retrieves multiple usageRules by available filters, or without if
     * applicable. Responses:
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
     * @param filterId List of usage rules IDs (e.g. &#x60;VFJBQ0tTOjEyMzpOTw&#x60;) (optional)
     * @return [UsageRulesMultiResourceDataDocument]
     */
    @GET("usageRules")
    suspend fun usageRulesGet(
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null
    ): Response<UsageRulesMultiResourceDataDocument>

    /**
     * Get single usageRule. Retrieves single usageRule by id. Responses:
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param usageRulesCreateOperationPayload (optional)
     * @return [UsageRulesSingleResourceDataDocument]
     */
    @POST("usageRules")
    suspend fun usageRulesPost(
        @Body usageRulesCreateOperationPayload: UsageRulesCreateOperationPayload? = null
    ): Response<UsageRulesSingleResourceDataDocument>
}
