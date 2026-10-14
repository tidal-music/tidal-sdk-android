package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.GroupsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.GroupsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Groups {
    /**
     * GET groups Get multiple groups. Retrieves multiple groups by available filters, or without if
     * applicable. Responses:
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
     * @param filterName Exact group name (case-sensitive) (e.g. &#x60;Everyone&#x60;)
     * @return [GroupsMultiResourceDataDocument]
     */
    @GET("groups")
    suspend fun groupsGet(
        @Query("filter[name]") filterName: kotlin.String
    ): Response<GroupsMultiResourceDataDocument>

    /**
     * GET groups/{id} Get single group. Retrieves single group by id. Responses:
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
     * @param id Group ID
     * @return [GroupsSingleResourceDataDocument]
     */
    @GET("groups/{id}")
    suspend fun groupsIdGet(
        @Path("id") id: kotlin.String
    ): Response<GroupsSingleResourceDataDocument>
}
