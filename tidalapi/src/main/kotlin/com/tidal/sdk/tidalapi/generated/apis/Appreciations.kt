package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.AppreciationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AppreciationsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Appreciations {
    /**
     * Create single appreciation. Creates a new appreciation. Responses:
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
     * @param appreciationsCreateOperationPayload (optional)
     * @return [AppreciationsSingleResourceDataDocument]
     */
    @POST("appreciations")
    suspend fun appreciationsPost(
        @Body appreciationsCreateOperationPayload: AppreciationsCreateOperationPayload? = null
    ): Response<AppreciationsSingleResourceDataDocument>
}
