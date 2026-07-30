package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistClaimStatusesMultiResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ArtistClaimStatuses {
    /**
     * Get multiple artistClaimStatuses. Retrieves multiple artistClaimStatuses by available
     * filters, or without if applicable. Responses:
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
     * @param filterId List of artist claim status IDs (e.g. &#x60;QVJUSVNUUzoxNTY2&#x60;)
     * @return [ArtistClaimStatusesMultiResourceDataDocument]
     */
    @GET("artistClaimStatuses")
    suspend fun artistClaimStatusesGet(
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>
    ): Response<ArtistClaimStatusesMultiResourceDataDocument>
}
