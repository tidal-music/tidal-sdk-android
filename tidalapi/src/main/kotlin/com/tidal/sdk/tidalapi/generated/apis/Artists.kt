package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistsFollowersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsFollowingMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsFollowingRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistsFollowingRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsProfileArtRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtistsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsTrackProvidersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsUpdateOperationPayload
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Artists {
    /**
     * Get multiple artists. Retrieves multiple artists by available filters, or without if
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
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, biography, followers, following, owners, profileArt, radio,
     *   roles, similarArtists, trackProviders, tracks, videos (optional)
     * @param filterHandle Artist handle (optional)
     * @param filterId Artist id (optional)
     * @param filterOwnersId User id (optional)
     * @return [ArtistsMultiResourceDataDocument]
     */
    @GET("artists")
    suspend fun artistsGet(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[handle]")
        filterHandle: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsMultiResourceDataDocument>

    /**
     * Get single artist. Retrieves single artist by id. Responses:
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
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, biography, followers, following, owners, profileArt, radio,
     *   roles, similarArtists, trackProviders, tracks, videos (optional)
     * @return [ArtistsSingleResourceDataDocument]
     */
    @GET("artists/{id}")
    suspend fun artistsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsSingleResourceDataDocument>

    /**
     * Update single artist. Updates existing artist. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Artist id
     * @param artistsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artists/{id}")
    suspend fun artistsIdPatch(
        @Path("id") id: kotlin.String,
        @Body artistsUpdateOperationPayload: ArtistsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get albums relationship (\&quot;to-many\&quot;). Retrieves albums relationship. Responses:
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/albums")
    suspend fun artistsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get biography relationship (\&quot;to-one\&quot;). Retrieves biography relationship.
     * Responses:
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
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: biography (optional)
     * @return [ArtistsSingleRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/biography")
    suspend fun artistsIdRelationshipsBiographyGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsSingleRelationshipDataDocument>

    /**
     * Get followers relationship (\&quot;to-many\&quot;). Retrieves followers relationship.
     * Responses:
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
     * @param id Artist id
     * @param viewerContext (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: followers (optional)
     * @return [ArtistsFollowersMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/followers")
    suspend fun artistsIdRelationshipsFollowersGet(
        @Path("id") id: kotlin.String,
        @Query("viewerContext") viewerContext: kotlin.String? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsFollowersMultiRelationshipDataDocument>

    /**
     * Delete from following relationship (\&quot;to-many\&quot;). Deletes item(s) from following
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
     * @param id Artist id
     * @param artistsFollowingRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "artists/{id}/relationships/following", hasBody = true)
    suspend fun artistsIdRelationshipsFollowingDelete(
        @Path("id") id: kotlin.String,
        @Body
        artistsFollowingRelationshipRemoveOperationPayload:
            ArtistsFollowingRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get following relationship (\&quot;to-many\&quot;). Retrieves following relationship.
     * Responses:
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
     * @param id Artist id
     * @param viewerContext (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: following (optional)
     * @return [ArtistsFollowingMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/following")
    suspend fun artistsIdRelationshipsFollowingGet(
        @Path("id") id: kotlin.String,
        @Query("viewerContext") viewerContext: kotlin.String? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsFollowingMultiRelationshipDataDocument>

    /**
     * Add to following relationship (\&quot;to-many\&quot;). Adds item(s) to following
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
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param artistsFollowingRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("artists/{id}/relationships/following")
    suspend fun artistsIdRelationshipsFollowingPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        artistsFollowingRelationshipAddOperationPayload:
            ArtistsFollowingRelationshipAddOperationPayload? =
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
     * @param id Artist id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/owners")
    suspend fun artistsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get profileArt relationship (\&quot;to-many\&quot;). Retrieves profileArt relationship.
     * Responses:
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
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: profileArt (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/profileArt")
    suspend fun artistsIdRelationshipsProfileArtGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Update profileArt relationship (\&quot;to-many\&quot;). Updates profileArt relationship.
     * Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Artist id
     * @param artistsProfileArtRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artists/{id}/relationships/profileArt")
    suspend fun artistsIdRelationshipsProfileArtPatch(
        @Path("id") id: kotlin.String,
        @Body
        artistsProfileArtRelationshipUpdateOperationPayload:
            ArtistsProfileArtRelationshipUpdateOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get radio relationship (\&quot;to-many\&quot;). Retrieves radio relationship. Responses:
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: radio (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/radio")
    suspend fun artistsIdRelationshipsRadioGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get roles relationship (\&quot;to-many\&quot;). Retrieves roles relationship. Responses:
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
     * @param id Artist id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: roles (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/roles")
    suspend fun artistsIdRelationshipsRolesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get similarArtists relationship (\&quot;to-many\&quot;). Retrieves similarArtists
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: similarArtists (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/similarArtists")
    suspend fun artistsIdRelationshipsSimilarArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get trackProviders relationship (\&quot;to-many\&quot;). Retrieves trackProviders
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: trackProviders (optional)
     * @return [ArtistsTrackProvidersMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/trackProviders")
    suspend fun artistsIdRelationshipsTrackProvidersGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsTrackProvidersMultiRelationshipDataDocument>

    /** enum for parameter collapseBy */
    enum class CollapseByArtistsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "FINGERPRINT") FINGERPRINT("FINGERPRINT"),
        @SerialName(value = "NONE") NONE("NONE"),
    }

    /**
     * Get tracks relationship (\&quot;to-many\&quot;). Retrieves tracks relationship. Responses:
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
     * @param id Artist id
     * @param collapseBy Collapse by options for getting artist tracks. Available options:
     *   FINGERPRINT, ID. FINGERPRINT option might collapse similar tracks based entry fingerprints
     *   while collapsing by ID always returns all available items.
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/tracks")
    suspend fun artistsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("collapseBy") collapseBy: CollapseByArtistsIdRelationshipsTracksGet,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get videos relationship (\&quot;to-many\&quot;). Retrieves videos relationship. Responses:
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/videos")
    suspend fun artistsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Create single artist. Creates a new artist. Responses:
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
     * @param artistsCreateOperationPayload (optional)
     * @return [ArtistsSingleResourceDataDocument]
     */
    @POST("artists")
    suspend fun artistsPost(
        @Body artistsCreateOperationPayload: ArtistsCreateOperationPayload? = null
    ): Response<ArtistsSingleResourceDataDocument>
}
