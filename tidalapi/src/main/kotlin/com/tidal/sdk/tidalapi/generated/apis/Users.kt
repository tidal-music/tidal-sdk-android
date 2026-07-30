package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UsersSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Users {
    /**
     * Get single user. Retrieves single user by id. Responses:
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
     * @param id User id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @return [UsersSingleResourceDataDocument]
     */
    @GET("users/{id}")
    suspend fun usersIdGet(@Path("id") id: kotlin.String): Response<UsersSingleResourceDataDocument>
}
