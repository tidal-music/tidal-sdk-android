package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistClaimAcceptedArtistsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistClaimsUpdateOperationPayload
import retrofit2.Response
import retrofit2.http.*

interface ArtistClaims {
    /**
     * Get single artistClaim. Retrieves single artistClaim by id. Responses:
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: acceptedArtists, owners, recommendedArtists (optional)
     * @return [ArtistClaimsSingleResourceDataDocument]
     */
    @GET("artistClaims/{id}")
    suspend fun artistClaimsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistClaimsSingleResourceDataDocument>

    /**
     * Update single artistClaim. Updates existing artistClaim. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Artist claim id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param artistClaimsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artistClaims/{id}")
    suspend fun artistClaimsIdPatch(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body artistClaimsUpdateOperationPayload: ArtistClaimsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get acceptedArtists relationship (\&quot;to-many\&quot;). Retrieves acceptedArtists
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: acceptedArtists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistClaimsMultiRelationshipDataDocument]
     */
    @GET("artistClaims/{id}/relationships/acceptedArtists")
    suspend fun artistClaimsIdRelationshipsAcceptedArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistClaimsMultiRelationshipDataDocument>

    /**
     * Update acceptedArtists relationship (\&quot;to-many\&quot;). Updates acceptedArtists
     * relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Artist claim id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param artistClaimAcceptedArtistsRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artistClaims/{id}/relationships/acceptedArtists")
    suspend fun artistClaimsIdRelationshipsAcceptedArtistsPatch(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        artistClaimAcceptedArtistsRelationshipUpdateOperationPayload:
            ArtistClaimAcceptedArtistsRelationshipUpdateOperationPayload? =
            null,
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistClaimsMultiRelationshipDataDocument]
     */
    @GET("artistClaims/{id}/relationships/owners")
    suspend fun artistClaimsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistClaimsMultiRelationshipDataDocument>

    /**
     * Get recommendedArtists relationship (\&quot;to-many\&quot;). Retrieves recommendedArtists
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
     * @param id Artist claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: recommendedArtists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistClaimsMultiRelationshipDataDocument]
     */
    @GET("artistClaims/{id}/relationships/recommendedArtists")
    suspend fun artistClaimsIdRelationshipsRecommendedArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistClaimsMultiRelationshipDataDocument>

    /**
     * Create single artistClaim. Creates a new artistClaim. Responses:
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
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param artistClaimsCreateOperationPayload (optional)
     * @return [ArtistClaimsSingleResourceDataDocument]
     */
    @POST("artistClaims")
    suspend fun artistClaimsPost(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body artistClaimsCreateOperationPayload: ArtistClaimsCreateOperationPayload? = null,
    ): Response<ArtistClaimsSingleResourceDataDocument>
}
