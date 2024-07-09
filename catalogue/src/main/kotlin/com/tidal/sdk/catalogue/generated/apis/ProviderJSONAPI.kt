package com.tidal.sdk.catalogue.generated.apis

import com.tidal.sdk.catalogue.generated.models.ProviderDataDocument
import com.tidal.sdk.catalogue.generated.models.ProvidersDataDocument
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProviderJSONAPI {
    /**
     * Get single provider
     * Retrieve provider details by TIDAL provider id.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL provider id
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @return [ProviderDataDocument]
     */
    @GET("providers/{id}")
    suspend fun getProvider(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderDataDocument>

    /**
     * Get multiple providers
     * Retrieve multiple provider details.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [ProvidersDataDocument]
     */
    @GET("providers")
    suspend fun getProviders(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProvidersDataDocument>
}
