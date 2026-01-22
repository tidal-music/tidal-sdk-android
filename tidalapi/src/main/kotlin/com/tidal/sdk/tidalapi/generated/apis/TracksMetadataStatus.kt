package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TracksMetadataStatusMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksMetadataStatusSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface TracksMetadataStatus {
    /**
     * Get multiple tracksMetadataStatus. Retrieves multiple tracksMetadataStatus by available
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
     * @param filterId Track id (optional)
     * @return [TracksMetadataStatusMultiResourceDataDocument]
     */
    @GET("tracksMetadataStatus")
    suspend fun tracksMetadataStatusGet(
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null
    ): Response<TracksMetadataStatusMultiResourceDataDocument>

    /**
     * Get single tracksMetadataStatu. Retrieves single tracksMetadataStatu by id. Responses:
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
     * @param id Tracks metadata status id
     * @return [TracksMetadataStatusSingleResourceDataDocument]
     */
    @GET("tracksMetadataStatus/{id}")
    suspend fun tracksMetadataStatusIdGet(
        @Path("id") id: kotlin.String
    ): Response<TracksMetadataStatusSingleResourceDataDocument>
}
