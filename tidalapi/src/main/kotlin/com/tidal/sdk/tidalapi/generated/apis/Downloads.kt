package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.DownloadsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.DownloadsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.DownloadsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Downloads {
    /**
     * Get multiple downloads. Retrieves multiple downloads by available filters, or without if
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
     * @param filterId Download id (e.g. &#x60;VFJBQ0tTOjEyMzQ1&#x60;)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [DownloadsMultiResourceDataDocument]
     */
    @GET("downloads")
    suspend fun downloadsGet(
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DownloadsMultiResourceDataDocument>

    /**
     * Get single download. Retrieves single download by id. Responses:
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
     * @param id Download id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [DownloadsSingleResourceDataDocument]
     */
    @GET("downloads/{id}")
    suspend fun downloadsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<DownloadsSingleResourceDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id Download id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [DownloadsOwnersMultiRelationshipDataDocument]
     */
    @GET("downloads/{id}/relationships/owners")
    suspend fun downloadsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<DownloadsOwnersMultiRelationshipDataDocument>
}
