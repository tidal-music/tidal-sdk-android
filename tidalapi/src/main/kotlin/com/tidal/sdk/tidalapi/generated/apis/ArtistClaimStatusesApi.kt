package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistClaimStatusesMultiResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ArtistClaimStatusesApi {
    /**
     * Get multiple artistClaimStatuses. Retrieves multiple artistClaimStatuses by available
     * filters, or without if applicable. Responses:
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
     * @param filterId List of artist claim status IDs (e.g. &#x60;QVJUSVNUUzoxNTY2&#x60;)
     * @return [ArtistClaimStatusesMultiResourceDataDocument]
     */
    @GET("artistClaimStatuses")
    suspend fun artistClaimStatusesGet(
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>
    ): Response<ArtistClaimStatusesMultiResourceDataDocument>
}
