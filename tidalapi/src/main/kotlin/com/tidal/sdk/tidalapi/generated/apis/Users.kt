package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UsersSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Users {
    /**
     * Get single user. Retrieves single user by id. Responses:
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
     * @param id User id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @return [UsersSingleResourceDataDocument]
     */
    @GET("users/{id}")
    suspend fun usersIdGet(@Path("id") id: kotlin.String): Response<UsersSingleResourceDataDocument>
}
