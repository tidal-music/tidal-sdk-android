package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TrackSourceFilesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface TrackSourceFiles {
    /**
     * Get multiple trackSourceFiles. Retrieves multiple trackSourceFiles by available filters, or
     * without if applicable. Responses:
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param filterId Track source file id (e.g. &#x60;a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11&#x60;)
     *   (optional)
     * @return [TrackSourceFilesMultiResourceDataDocument]
     */
    @GET("trackSourceFiles")
    suspend fun trackSourceFilesGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackSourceFilesMultiResourceDataDocument>

    /**
     * Get single trackSourceFile. Retrieves single trackSourceFile by id. Responses:
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
     * @param id Track source file id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [TrackSourceFilesSingleResourceDataDocument]
     */
    @GET("trackSourceFiles/{id}")
    suspend fun trackSourceFilesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackSourceFilesSingleResourceDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id Track source file id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [TrackSourceFilesMultiRelationshipDataDocument]
     */
    @GET("trackSourceFiles/{id}/relationships/owners")
    suspend fun trackSourceFilesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TrackSourceFilesMultiRelationshipDataDocument>

    /**
     * Create single trackSourceFile. Create a track source file. &lt;p/&gt; The response contains a
     * upload link that must be used to upload the actual content.&lt;p/&gt; The headers in the
     * upload link response must be sent doing the actual upload. Responses:
     * - 201: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param trackSourceFilesCreateOperationPayload (optional)
     * @return [TrackSourceFilesSingleResourceDataDocument]
     */
    @POST("trackSourceFiles")
    suspend fun trackSourceFilesPost(
        @Body trackSourceFilesCreateOperationPayload: TrackSourceFilesCreateOperationPayload? = null
    ): Response<TrackSourceFilesSingleResourceDataDocument>
}
