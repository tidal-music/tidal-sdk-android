package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtworksCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtworksCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworksMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworksOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworksSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Artworks {
    /**
     * Get multiple artworks. Retrieves multiple artworks by available filters, or without if
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
     * @param filterId Artwork id (e.g. &#x60;a468bee88def&#x60;)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [ArtworksMultiResourceDataDocument]
     */
    @GET("artworks")
    suspend fun artworksGet(
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtworksMultiResourceDataDocument>

    /**
     * Get single artwork. Retrieves single artwork by id. Responses:
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
     * @param id Artwork id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [ArtworksSingleResourceDataDocument]
     */
    @GET("artworks/{id}")
    suspend fun artworksIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtworksSingleResourceDataDocument>

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
     * @param id Artwork id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtworksOwnersMultiRelationshipDataDocument]
     */
    @GET("artworks/{id}/relationships/owners")
    suspend fun artworksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtworksOwnersMultiRelationshipDataDocument>

    /**
     * Create single artwork. Creates a new artwork. Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artworksCreateOperationPayload (optional)
     * @return [ArtworksCreateSingleResourceDataDocument]
     */
    @POST("artworks")
    suspend fun artworksPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body artworksCreateOperationPayload: ArtworksCreateOperationPayload? = null,
    ): Response<ArtworksCreateSingleResourceDataDocument>
}
