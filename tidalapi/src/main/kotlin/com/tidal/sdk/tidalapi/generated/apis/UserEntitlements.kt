package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserEntitlementsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserEntitlements {
    /**
     * Get single userEntitlement. Retrieves single userEntitlement by id. Responses:
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
     * @param id User id
     * @return [UserEntitlementsSingleResourceDataDocument]
     */
    @GET("userEntitlements/{id}")
    suspend fun userEntitlementsIdGet(
        @Path("id") id: kotlin.String
    ): Response<UserEntitlementsSingleResourceDataDocument>
}
