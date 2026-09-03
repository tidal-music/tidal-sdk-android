package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsAcceptedArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsAcceptedArtistsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsRecommendedArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import retrofit2.Response
import retrofit2.http.*

interface ArtistClaims {
    /**
     * Get multiple artistClaims. Retrieves multiple artistClaims by available filters, or without
     * if applicable. Responses:
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
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: acceptedArtists, owners, recommendedArtists (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: acceptedArtists.albums (optional)
     * @return [ArtistClaimsMultiResourceDataDocument]
     */
    @GET("artistClaims")
    suspend fun artistClaimsGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistClaimsMultiResourceDataDocument>

    /**
     * Delete single artistClaim. Deletes existing artistClaim. Responses:
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
     * @param id Artist claim id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("artistClaims/{id}")
    suspend fun artistClaimsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /**
     * Get single artistClaim. Retrieves single artistClaim by id. Responses:
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: acceptedArtists, owners, recommendedArtists (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: acceptedArtists.albums (optional)
     * @return [ArtistClaimsSingleResourceDataDocument]
     */
    @GET("artistClaims/{id}")
    suspend fun artistClaimsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistClaimsSingleResourceDataDocument>

    /**
     * Update single artistClaim. Updates existing artistClaim. Responses:
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
     * @param id Artist claim id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artistClaimsUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("artistClaims/{id}")
    suspend fun artistClaimsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body artistClaimsUpdateOperationPayload: ArtistClaimsUpdateOperationPayload? = null,
    ): Response<MutationResponseDocument>

    /**
     * Get acceptedArtists relationship (\&quot;to-many\&quot;). Retrieves acceptedArtists
     * relationship. Responses:
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: acceptedArtists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: acceptedArtists.albums (optional)
     * @return [ArtistClaimsAcceptedArtistsMultiRelationshipDataDocument]
     */
    @GET("artistClaims/{id}/relationships/acceptedArtists")
    suspend fun artistClaimsIdRelationshipsAcceptedArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistClaimsAcceptedArtistsMultiRelationshipDataDocument>

    /**
     * Update acceptedArtists relationship (\&quot;to-many\&quot;). Updates acceptedArtists
     * relationship. Responses:
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
     * @param id Artist claim id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artistClaimsAcceptedArtistsRelationshipUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("artistClaims/{id}/relationships/acceptedArtists")
    suspend fun artistClaimsIdRelationshipsAcceptedArtistsPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        artistClaimsAcceptedArtistsRelationshipUpdateOperationPayload:
            ArtistClaimsAcceptedArtistsRelationshipUpdateOperationPayload? =
            null,
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistClaimsOwnersMultiRelationshipDataDocument]
     */
    @GET("artistClaims/{id}/relationships/owners")
    suspend fun artistClaimsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistClaimsOwnersMultiRelationshipDataDocument>

    /**
     * Get recommendedArtists relationship (\&quot;to-many\&quot;). Retrieves recommendedArtists
     * relationship. Responses:
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: recommendedArtists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: recommendedArtists.albums (optional)
     * @return [ArtistClaimsRecommendedArtistsMultiRelationshipDataDocument]
     */
    @GET("artistClaims/{id}/relationships/recommendedArtists")
    suspend fun artistClaimsIdRelationshipsRecommendedArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistClaimsRecommendedArtistsMultiRelationshipDataDocument>

    /**
     * Create single artistClaim. Creates a new artistClaim. Responses:
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
     * @param artistClaimsCreateOperationPayload (optional)
     * @return [ArtistClaimsCreateSingleResourceDataDocument]
     */
    @POST("artistClaims")
    suspend fun artistClaimsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body artistClaimsCreateOperationPayload: ArtistClaimsCreateOperationPayload? = null,
    ): Response<ArtistClaimsCreateSingleResourceDataDocument>
}
