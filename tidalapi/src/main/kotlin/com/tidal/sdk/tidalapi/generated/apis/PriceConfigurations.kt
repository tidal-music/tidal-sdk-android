package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.PriceConfigurationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PriceConfigurationsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PriceConfigurationsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface PriceConfigurations {
    /**
     * Get multiple priceConfigurations. Retrieves multiple priceConfigurations by available
     * filters, or without if applicable. Responses:
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
     * @param filterId List of price configurations IDs (e.g.
     *   &#x60;cHJpY2UtY29uZmlnLTEyMzpVUw&#x60;)
     * @return [PriceConfigurationsMultiResourceDataDocument]
     */
    @GET("priceConfigurations")
    suspend fun priceConfigurationsGet(
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>
    ): Response<PriceConfigurationsMultiResourceDataDocument>

    /**
     * Get single priceConfiguration. Retrieves single priceConfiguration by id. Responses:
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
     * @param id Price configuration id
     * @return [PriceConfigurationsSingleResourceDataDocument]
     */
    @GET("priceConfigurations/{id}")
    suspend fun priceConfigurationsIdGet(
        @Path("id") id: kotlin.String
    ): Response<PriceConfigurationsSingleResourceDataDocument>

    /**
     * Create single priceConfiguration. Creates a new priceConfiguration. Responses:
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
     * @param priceConfigurationsCreateOperationPayload (optional)
     * @return [PriceConfigurationsSingleResourceDataDocument]
     */
    @POST("priceConfigurations")
    suspend fun priceConfigurationsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        priceConfigurationsCreateOperationPayload: PriceConfigurationsCreateOperationPayload? = null,
    ): Response<PriceConfigurationsSingleResourceDataDocument>
}
