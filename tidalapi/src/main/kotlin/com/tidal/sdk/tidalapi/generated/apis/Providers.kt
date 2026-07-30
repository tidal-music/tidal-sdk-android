package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProvidersSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Providers {
    /**
     * Get single provider. Retrieves single provider by id. Responses:
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
     * @param id Provider ID
     * @return [ProvidersSingleResourceDataDocument]
     */
    @GET("providers/{id}")
    suspend fun providersIdGet(
        @Path("id") id: kotlin.String
    ): Response<ProvidersSingleResourceDataDocument>
}
