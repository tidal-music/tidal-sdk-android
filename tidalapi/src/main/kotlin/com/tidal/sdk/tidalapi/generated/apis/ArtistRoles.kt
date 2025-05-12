package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistRolesMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistRolesSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ArtistRoles {
    /**
     * Get multiple artistRoles. Retrieves multiple artistRoles by available filters, or without if
     * applicable. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param filterId Allows to filter the collection of resources based on id attribute value
     *   (optional)
     * @return [ArtistRolesMultiDataDocument]
     */
    @GET("artistRoles")
    suspend fun artistRolesGet(
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null
    ): Response<ArtistRolesMultiDataDocument>

    /**
     * Get single artistRole. Retrieves single artistRole by id. Responses:
     * - 200:
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist role id
     * @return [ArtistRolesSingleDataDocument]
     */
    @GET("artistRoles/{id}")
    suspend fun artistRolesIdGet(
        @Path("id") id: kotlin.String
    ): Response<ArtistRolesSingleDataDocument>
}
