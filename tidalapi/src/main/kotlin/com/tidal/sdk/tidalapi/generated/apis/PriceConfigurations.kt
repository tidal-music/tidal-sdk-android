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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param filterId Price configuration id (optional)
     * @return [PriceConfigurationsMultiResourceDataDocument]
     */
    @GET("priceConfigurations")
    suspend fun priceConfigurationsGet(
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null
    ): Response<PriceConfigurationsMultiResourceDataDocument>

    /**
     * Get single priceConfiguration. Retrieves single priceConfiguration by id. Responses:
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param priceConfigurationsCreateOperationPayload (optional)
     * @return [PriceConfigurationsSingleResourceDataDocument]
     */
    @POST("priceConfigurations")
    suspend fun priceConfigurationsPost(
        @Body
        priceConfigurationsCreateOperationPayload: PriceConfigurationsCreateOperationPayload? = null
    ): Response<PriceConfigurationsSingleResourceDataDocument>
}
