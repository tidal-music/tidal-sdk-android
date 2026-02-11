package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ContentClaimsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ContentClaimsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ContentClaimsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ContentClaimsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ContentClaimsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ContentClaims {
    /**
     * Get multiple contentClaims. Retrieves multiple contentClaims by available filters, or without
     * if applicable. Responses:
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
     *   Available options: claimedResource, claimingArtist, owners (optional)
     * @param filterOwnersId User id (optional)
     * @return [ContentClaimsMultiResourceDataDocument]
     */
    @GET("contentClaims")
    suspend fun contentClaimsGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ContentClaimsMultiResourceDataDocument>

    /**
     * Get single contentClaim. Retrieves single contentClaim by id. Responses:
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
     * @param id Content claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: claimedResource, claimingArtist, owners (optional)
     * @return [ContentClaimsSingleResourceDataDocument]
     */
    @GET("contentClaims/{id}")
    suspend fun contentClaimsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ContentClaimsSingleResourceDataDocument>

    /**
     * Get claimedResource relationship (\&quot;to-one\&quot;). Retrieves claimedResource
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
     * @param id Content claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: claimedResource (optional)
     * @return [ContentClaimsSingleRelationshipDataDocument]
     */
    @GET("contentClaims/{id}/relationships/claimedResource")
    suspend fun contentClaimsIdRelationshipsClaimedResourceGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ContentClaimsSingleRelationshipDataDocument>

    /**
     * Get claimingArtist relationship (\&quot;to-one\&quot;). Retrieves claimingArtist
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
     * @param id Content claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: claimingArtist (optional)
     * @return [ContentClaimsSingleRelationshipDataDocument]
     */
    @GET("contentClaims/{id}/relationships/claimingArtist")
    suspend fun contentClaimsIdRelationshipsClaimingArtistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ContentClaimsSingleRelationshipDataDocument>

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
     * @param id Content claim id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ContentClaimsMultiRelationshipDataDocument]
     */
    @GET("contentClaims/{id}/relationships/owners")
    suspend fun contentClaimsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ContentClaimsMultiRelationshipDataDocument>

    /**
     * Create single contentClaim. Creates a new contentClaim. Responses:
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
     * @param contentClaimsCreateOperationPayload (optional)
     * @return [ContentClaimsSingleResourceDataDocument]
     */
    @POST("contentClaims")
    suspend fun contentClaimsPost(
        @Body contentClaimsCreateOperationPayload: ContentClaimsCreateOperationPayload? = null
    ): Response<ContentClaimsSingleResourceDataDocument>
}
