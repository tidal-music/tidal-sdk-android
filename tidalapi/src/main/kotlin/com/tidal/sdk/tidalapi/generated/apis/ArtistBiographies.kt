package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesUpdateOperationPayload
import retrofit2.Response
import retrofit2.http.*

interface ArtistBiographies {
    /**
     * Get multiple artistBiographies. Retrieves multiple artistBiographies by available filters, or
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
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param filterId Artist id (e.g. &#x60;1566&#x60;) (optional)
     * @return [ArtistBiographiesMultiResourceDataDocument]
     */
    @GET("artistBiographies")
    suspend fun artistBiographiesGet(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistBiographiesMultiResourceDataDocument>

    /**
     * Get single artistBiographie. Retrieves single artistBiographie by id. Responses:
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
     * @param id Artist biography id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [ArtistBiographiesSingleResourceDataDocument]
     */
    @GET("artistBiographies/{id}")
    suspend fun artistBiographiesIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistBiographiesSingleResourceDataDocument>

    /**
     * Update single artistBiographie. Updates existing artistBiographie. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Artist biography id
     * @param artistBiographiesUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artistBiographies/{id}")
    suspend fun artistBiographiesIdPatch(
        @Path("id") id: kotlin.String,
        @Body
        artistBiographiesUpdateOperationPayload: ArtistBiographiesUpdateOperationPayload? = null,
    ): Response<Unit>

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
     * @param id Artist biography id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistBiographiesMultiRelationshipDataDocument]
     */
    @GET("artistBiographies/{id}/relationships/owners")
    suspend fun artistBiographiesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistBiographiesMultiRelationshipDataDocument>
}
