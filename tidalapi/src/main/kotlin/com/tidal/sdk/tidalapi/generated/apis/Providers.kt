package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProvidersMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProvidersSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Providers {
    /**
     * Get single provider
     * Retrieve provider details by TIDAL provider id.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL provider id
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @return [ProvidersSingleDataDocument]
     */
    @GET("providers/{id}")
    suspend fun getProviderById(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProvidersSingleDataDocument>

    /**
     * Get multiple providers
     * Retrieve multiple provider details.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @param filterId provider id (optional)
     * @return [ProvidersMultiDataDocument]
     */
    @GET("providers")
    suspend fun getProvidersByFilters(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<ProvidersMultiDataDocument>
}
