package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import retrofit2.Response
import retrofit2.http.*

interface ArtistBiographies {
    /**
     * Get single artistBiographie. Retrieves single artistBiographie by id. Responses:
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
     * - 200: Successful response
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
     * @param id Artist biography id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artistBiographiesUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("artistBiographies/{id}")
    suspend fun artistBiographiesIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        artistBiographiesUpdateOperationPayload: ArtistBiographiesUpdateOperationPayload? = null,
    ): Response<MutationResponseDocument>

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
     * @param id Artist biography id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistBiographiesOwnersMultiRelationshipDataDocument]
     */
    @GET("artistBiographies/{id}/relationships/owners")
    suspend fun artistBiographiesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistBiographiesOwnersMultiRelationshipDataDocument>
}
