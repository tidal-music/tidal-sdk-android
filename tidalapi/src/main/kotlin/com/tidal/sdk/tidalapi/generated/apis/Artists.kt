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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, biography, claimStatus, followers, following, owners,
     *   profileArt, radio, roles, similarArtists, trackProviders, tracks, videos (optional)
     * @param filterHandle Artist handle (e.g. &#x60;jayz&#x60;) (optional)
     * @param filterId List of artist IDs (e.g. &#x60;1566&#x60;) (optional)
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
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
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsMultiResourceDataDocument>

    /**
     * Get single artist. Retrieves single artist by id. Responses:
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
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, biography, claimStatus, followers, following, owners,
     *   profileArt, radio, roles, similarArtists, trackProviders, tracks, videos (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @return [ArtistsSingleResourceDataDocument]
     */
    @GET("artists/{id}")
    suspend fun artistsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsSingleResourceDataDocument>

    /**
     * Update single artist. Updates existing artist. Responses:
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
     * @param id Artist id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artistsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artists/{id}")
    suspend fun artistsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body artistsUpdateOperationPayload: ArtistsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get albums relationship (\&quot;to-many\&quot;). Retrieves albums relationship. Responses:
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/albums")
    suspend fun artistsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get biography relationship (\&quot;to-one\&quot;). Retrieves biography relationship.
     * Responses:
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
     * Get claimStatus relationship (\&quot;to-one\&quot;). Retrieves claimStatus relationship.
     * Responses:
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
     * @param id Artist id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: claimStatus (optional)
     * @return [ArtistsSingleRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/claimStatus")
    suspend fun artistsIdRelationshipsClaimStatusGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsSingleRelationshipDataDocument>

    /**
     * Get followers relationship (\&quot;to-many\&quot;). Retrieves followers relationship.
     * Responses:
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
     * @param id Artist id
     * @param viewerContext (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: followers (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: followers.albums (optional)
     * @return [ArtistsFollowersMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/followers")
    suspend fun artistsIdRelationshipsFollowersGet(
        @Path("id") id: kotlin.String,
        @Query("viewerContext") viewerContext: kotlin.String? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsFollowersMultiRelationshipDataDocument>

    /**
     * Delete from following relationship (\&quot;to-many\&quot;). Deletes item(s) from following
     * relationship. Responses:
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
     * @param id Artist id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artistsFollowingRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "artists/{id}/relationships/following", hasBody = true)
    suspend fun artistsIdRelationshipsFollowingDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        artistsFollowingRelationshipRemoveOperationPayload:
            ArtistsFollowingRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get following relationship (\&quot;to-many\&quot;). Retrieves following relationship.
     * Responses:
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
     * @param id Artist id
     * @param viewerContext (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: following (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: following.albums (optional)
     * @return [ArtistsFollowingMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/following")
    suspend fun artistsIdRelationshipsFollowingGet(
        @Path("id") id: kotlin.String,
        @Query("viewerContext") viewerContext: kotlin.String? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsFollowingMultiRelationshipDataDocument>

    /**
     * Add to following relationship (\&quot;to-many\&quot;). Adds item(s) to following
     * relationship. Responses:
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
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artistsFollowingRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("artists/{id}/relationships/following")
    suspend fun artistsIdRelationshipsFollowingPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        artistsFollowingRelationshipAddOperationPayload:
            ArtistsFollowingRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
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
     * @param id Artist id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param artistsProfileArtRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("artists/{id}/relationships/profileArt")
    suspend fun artistsIdRelationshipsProfileArtPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        artistsProfileArtRelationshipUpdateOperationPayload:
            ArtistsProfileArtRelationshipUpdateOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get radio relationship (\&quot;to-many\&quot;). Retrieves radio relationship. Responses:
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: radio (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: radio.items (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/radio")
    suspend fun artistsIdRelationshipsRadioGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get roles relationship (\&quot;to-many\&quot;). Retrieves roles relationship. Responses:
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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: similarArtists (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: similarArtists.albums (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/similarArtists")
    suspend fun artistsIdRelationshipsSimilarArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get trackProviders relationship (\&quot;to-many\&quot;). Retrieves trackProviders
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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
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
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: tracks (optional)
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
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Get videos relationship (\&quot;to-many\&quot;). Retrieves videos relationship. Responses:
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
     * @param id Artist id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: videos (optional)
     * @return [ArtistsMultiRelationshipDataDocument]
     */
    @GET("artists/{id}/relationships/videos")
    suspend fun artistsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ArtistsMultiRelationshipDataDocument>

    /**
     * Create single artist. Creates a new artist. Responses:
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
     * @param artistsCreateOperationPayload (optional)
     * @return [ArtistsSingleResourceDataDocument]
     */
    @POST("artists")
    suspend fun artistsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body artistsCreateOperationPayload: ArtistsCreateOperationPayload? = null,
    ): Response<ArtistsSingleResourceDataDocument>
}
