package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SharesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SharesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SharesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SharesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Shares {
    /**
     * Get multiple shares. Retrieves multiple shares by available filters, or without if
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
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, sharedResources (optional)
     * @param filterCode A share code (e.g. &#x60;xyz&#x60;) (optional)
     * @param filterId List of shares IDs (e.g. &#x60;a468bee88def&#x60;) (optional)
     * @return [SharesMultiResourceDataDocument]
     */
    @GET("shares")
    suspend fun sharesGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[code]")
        filterCode: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SharesMultiResourceDataDocument>

    /**
     * Get single share. Retrieves single share by id. Responses:
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
     * @param id User share id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, sharedResources (optional)
     * @return [SharesSingleResourceDataDocument]
     */
    @GET("shares/{id}")
    suspend fun sharesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SharesSingleResourceDataDocument>

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
     * @param id User share id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SharesMultiRelationshipDataDocument]
     */
    @GET("shares/{id}/relationships/owners")
    suspend fun sharesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SharesMultiRelationshipDataDocument>

    /**
     * Get sharedResources relationship (\&quot;to-many\&quot;). Retrieves sharedResources
     * relationship. Responses:
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
     * @param id User share id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: sharedResources (optional)
     * @return [SharesMultiRelationshipDataDocument]
     */
    @GET("shares/{id}/relationships/sharedResources")
    suspend fun sharesIdRelationshipsSharedResourcesGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SharesMultiRelationshipDataDocument>

    /**
     * Create single share. Creates a new share. Responses:
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
     * @param sharesCreateOperationPayload (optional)
     * @return [SharesSingleResourceDataDocument]
     */
    @POST("shares")
    suspend fun sharesPost(
        @Body sharesCreateOperationPayload: SharesCreateOperationPayload? = null
    ): Response<SharesSingleResourceDataDocument>
}
