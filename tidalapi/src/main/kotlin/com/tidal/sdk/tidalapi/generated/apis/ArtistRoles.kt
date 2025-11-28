package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistRolesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistRolesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ArtistRoles {
    /**
     * Get multiple artistRoles. Retrieves multiple artistRoles by available filters, or without if
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
     * @param filterId Allows to filter the collection of resources based on id attribute value
     *   (optional)
     * @return [ArtistRolesMultiResourceDataDocument]
     */
    @GET("artistRoles")
    suspend fun artistRolesGet(
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null
    ): Response<ArtistRolesMultiResourceDataDocument>

    /**
     * Get single artistRole. Retrieves single artistRole by id. Responses:
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
     * @param id Artist role id
     * @return [ArtistRolesSingleResourceDataDocument]
     */
    @GET("artistRoles/{id}")
    suspend fun artistRolesIdGet(
        @Path("id") id: kotlin.String
    ): Response<ArtistRolesSingleResourceDataDocument>
}
