package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtworkCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtworksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworksMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworksSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Artworks {
    /**
     * Get multiple artworks. Retrieves multiple artworks by available filters, or without if
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
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param filterId Artwork id (optional)
     * @return [ArtworksMultiResourceDataDocument]
     */
    @GET("artworks")
    suspend fun artworksGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtworksMultiResourceDataDocument>

    /**
     * Get single artwork. Retrieves single artwork by id. Responses:
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
     * @param id Artwork id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [ArtworksSingleResourceDataDocument]
     */
    @GET("artworks/{id}")
    suspend fun artworksIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtworksSingleResourceDataDocument>

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
     * @param id Artwork id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtworksMultiRelationshipDataDocument]
     */
    @GET("artworks/{id}/relationships/owners")
    suspend fun artworksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtworksMultiRelationshipDataDocument>

    /**
     * Create single artwork. Creates a new artwork. Responses:
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
     * @param artworkCreateOperationPayload (optional)
     * @return [ArtworksSingleResourceDataDocument]
     */
    @POST("artworks")
    suspend fun artworksPost(
        @Body artworkCreateOperationPayload: ArtworkCreateOperationPayload? = null
    ): Response<ArtworksSingleResourceDataDocument>
}
