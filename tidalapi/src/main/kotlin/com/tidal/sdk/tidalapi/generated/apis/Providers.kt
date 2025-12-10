package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProvidersMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProvidersSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Providers {
    /**
     * Get multiple providers. Retrieves multiple providers by available filters, or without if
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
     * @param filterId Provider ID (optional)
     * @return [ProvidersMultiResourceDataDocument]
     */
    @GET("providers")
    suspend fun providersGet(
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null
    ): Response<ProvidersMultiResourceDataDocument>

    /**
     * Get single provider. Retrieves single provider by id. Responses:
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
     * @param id Provider ID
     * @return [ProvidersSingleResourceDataDocument]
     */
    @GET("providers/{id}")
    suspend fun providersIdGet(
        @Path("id") id: kotlin.String
    ): Response<ProvidersSingleResourceDataDocument>
}
