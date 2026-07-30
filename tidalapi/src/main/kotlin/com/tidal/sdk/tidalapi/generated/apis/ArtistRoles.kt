package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistRolesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ArtistRoles {
    /**
     * Get single artistRole. Retrieves single artistRole by id. Responses:
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
     * @param id Artist role id
     * @return [ArtistRolesSingleResourceDataDocument]
     */
    @GET("artistRoles/{id}")
    suspend fun artistRolesIdGet(
        @Path("id") id: kotlin.String
    ): Response<ArtistRolesSingleResourceDataDocument>
}
