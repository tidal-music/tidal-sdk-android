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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Artist claim id
     * @param artistClaimsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artistClaims/{id}")
    suspend fun artistClaimsIdPatch(
        @Path("id") id: kotlin.String,
        @Body artistClaimsUpdateOperationPayload: ArtistClaimsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get acceptedArtists relationship (\&quot;to-many\&quot;). Retrieves acceptedArtists
     * relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Artist claim id
     * @param artistClaimAcceptedArtistsRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artistClaims/{id}/relationships/acceptedArtists")
    suspend fun artistClaimsIdRelationshipsAcceptedArtistsPatch(
        @Path("id") id: kotlin.String,
        @Body
        artistClaimAcceptedArtistsRelationshipUpdateOperationPayload:
            ArtistClaimAcceptedArtistsRelationshipUpdateOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param artistClaimsCreateOperationPayload (optional)
     * @return [ArtistClaimsSingleResourceDataDocument]
     */
    @POST("artistClaims")
    suspend fun artistClaimsPost(
        @Query("countryCode") countryCode: kotlin.String = "US",
        @Body artistClaimsCreateOperationPayload: ArtistClaimsCreateOperationPayload? = null,
    ): Response<ArtistClaimsSingleResourceDataDocument>
}
