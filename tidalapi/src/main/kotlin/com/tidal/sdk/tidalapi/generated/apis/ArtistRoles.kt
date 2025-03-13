package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ArtistRolesMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistRolesSingleDataDocument
import com.tidal.sdk.tidalapi.generated.models.ErrorDocument

interface ArtistRoles {
    /**
     * Get all artistRoles
     * Retrieves all artistRole details by available filters or without (if applicable).
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [ArtistRolesMultiDataDocument]
     */
    @GET("artistRoles")
    suspend fun artistRolesGet(@Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<ArtistRolesMultiDataDocument>

    /**
     * Get single artistRole
     * Retrieves artistRole details by an unique id.
     * Responses:
     *  - 200: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id TIDAL artist role id
     * @return [ArtistRolesSingleDataDocument]
     */
    @GET("artistRoles/{id}")
    suspend fun artistRolesIdGet(@Path("id") id: kotlin.String): Response<ArtistRolesSingleDataDocument>

}
