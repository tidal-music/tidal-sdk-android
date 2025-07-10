package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProvidersMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProvidersSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Providers {
    /**
     * Get multiple providers. Retrieves multiple providers by available filters, or without if
     * applicable. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param filterId Allows to filter the collection of resources based on id attribute value
     *   (optional)
     * @return [ProvidersMultiDataDocument]
     */
    @GET("providers")
    suspend fun providersGet(
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null
    ): Response<ProvidersMultiDataDocument>

    /**
     * Get single provider. Retrieves single provider by id. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Provider id
     * @return [ProvidersSingleDataDocument]
     */
    @GET("providers/{id}")
    suspend fun providersIdGet(@Path("id") id: kotlin.String): Response<ProvidersSingleDataDocument>
}
