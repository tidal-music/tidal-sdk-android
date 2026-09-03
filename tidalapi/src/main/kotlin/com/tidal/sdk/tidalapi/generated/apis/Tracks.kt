package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.TracksAlbumsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksAlbumsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TracksArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TracksCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksCreditsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksDownloadSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksGenresMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksLyricsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksMetadataStatusSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksPriceConfigSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksProvidersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksRadioMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksReplacementSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSharesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSimilarTracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSourceFileSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSuggestedTracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksTrackStatisticsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TracksUsageRulesSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Tracks {

    /** enum for parameter sort */
    enum class SortTracksGet(val value: kotlin.String) {
        @SerialName(value = "createdAt") CreatedAtAsc("createdAt"),
        @SerialName(value = "-createdAt") CreatedAtDesc("-createdAt"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
    }

    /**
     * Get multiple tracks. Retrieves multiple tracks by available filters, or without if
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
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, credits, download, genres, lyrics, metadataStatus,
     *   owners, priceConfig, providers, radio, replacement, shares, similarTracks, sourceFile,
     *   suggestedTracks, trackStatistics, usageRules (optional)
     * @param filterId List of track IDs (e.g. &#x60;75413016&#x60;) (optional)
     * @param filterIsrc List of ISRCs. When a single ISRC is provided, pagination is supported and
     *   multiple tracks may be returned. When multiple ISRCs are provided, one track per ISRC is
     *   returned without pagination. (e.g. &#x60;QMJMT1701237&#x60;) (optional)
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiResourceDataDocument]
     */
    @GET("tracks")
    suspend fun tracksGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[isrc]")
        filterIsrc: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiResourceDataDocument>

    /**
     * Delete single track. Deletes existing track. Responses:
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
     * @param id Track id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("tracks/{id}")
    suspend fun tracksIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /**
     * Get single track. Retrieves single track by id. Responses:
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, credits, download, genres, lyrics, metadataStatus,
     *   owners, priceConfig, providers, radio, replacement, shares, similarTracks, sourceFile,
     *   suggestedTracks, trackStatistics, usageRules (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleResourceDataDocument]
     */
    @GET("tracks/{id}")
    suspend fun tracksIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleResourceDataDocument>

    /**
     * Update single track. Updates existing track. Responses:
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
     * @param id Track id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param tracksUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("tracks/{id}")
    suspend fun tracksIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body tracksUpdateOperationPayload: TracksUpdateOperationPayload? = null,
    ): Response<MutationResponseDocument>

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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksAlbumsMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/albums")
    suspend fun tracksIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksAlbumsMultiRelationshipDataDocument>

    /**
     * Update albums relationship (\&quot;to-many\&quot;). Updates albums relationship. Responses:
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
     * @param id Track id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param tracksAlbumsRelationshipUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("tracks/{id}/relationships/albums")
    suspend fun tracksIdRelationshipsAlbumsPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        tracksAlbumsRelationshipUpdateOperationPayload:
            TracksAlbumsRelationshipUpdateOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Retrieves artists relationship. Responses:
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artists.albums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksArtistsMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/artists")
    suspend fun tracksIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksArtistsMultiRelationshipDataDocument>

    /**
     * Get credits relationship (\&quot;to-many\&quot;). Retrieves credits relationship. Responses:
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: credits (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: credits.artist.albums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksCreditsMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/credits")
    suspend fun tracksIdRelationshipsCreditsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksCreditsMultiRelationshipDataDocument>

    /**
     * Get download relationship (\&quot;to-one\&quot;). Retrieves download relationship. Responses:
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: download (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksDownloadSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/download")
    suspend fun tracksIdRelationshipsDownloadGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksDownloadSingleRelationshipDataDocument>

    /**
     * Get genres relationship (\&quot;to-many\&quot;). Retrieves genres relationship. Responses:
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: genres (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksGenresMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/genres")
    suspend fun tracksIdRelationshipsGenresGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksGenresMultiRelationshipDataDocument>

    /**
     * Get lyrics relationship (\&quot;to-many\&quot;). Retrieves lyrics relationship. Responses:
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: lyrics (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: lyrics.track (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksLyricsMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/lyrics")
    suspend fun tracksIdRelationshipsLyricsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksLyricsMultiRelationshipDataDocument>

    /**
     * Get metadataStatus relationship (\&quot;to-one\&quot;). Retrieves metadataStatus
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: metadataStatus (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMetadataStatusSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/metadataStatus")
    suspend fun tracksIdRelationshipsMetadataStatusGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMetadataStatusSingleRelationshipDataDocument>

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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksOwnersMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/owners")
    suspend fun tracksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksOwnersMultiRelationshipDataDocument>

    /**
     * Get priceConfig relationship (\&quot;to-one\&quot;). Retrieves priceConfig relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: priceConfig (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksPriceConfigSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/priceConfig")
    suspend fun tracksIdRelationshipsPriceConfigGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksPriceConfigSingleRelationshipDataDocument>

    /**
     * Get providers relationship (\&quot;to-many\&quot;). Retrieves providers relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksProvidersMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/providers")
    suspend fun tracksIdRelationshipsProvidersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksProvidersMultiRelationshipDataDocument>

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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: radio (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: radio.items (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksRadioMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/radio")
    suspend fun tracksIdRelationshipsRadioGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksRadioMultiRelationshipDataDocument>

    /**
     * Get replacement relationship (\&quot;to-one\&quot;). Retrieves replacement relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: replacement (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: replacement (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksReplacementSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/replacement")
    suspend fun tracksIdRelationshipsReplacementGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksReplacementSingleRelationshipDataDocument>

    /**
     * Get shares relationship (\&quot;to-many\&quot;). Retrieves shares relationship. Responses:
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: shares (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: shares.sharedResources (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSharesMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/shares")
    suspend fun tracksIdRelationshipsSharesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSharesMultiRelationshipDataDocument>

    /**
     * Get similarTracks relationship (\&quot;to-many\&quot;). Retrieves similarTracks relationship.
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: similarTracks (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: similarTracks (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSimilarTracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/similarTracks")
    suspend fun tracksIdRelationshipsSimilarTracksGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSimilarTracksMultiRelationshipDataDocument>

    /**
     * Get sourceFile relationship (\&quot;to-one\&quot;). Retrieves sourceFile relationship.
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: sourceFile (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSourceFileSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/sourceFile")
    suspend fun tracksIdRelationshipsSourceFileGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSourceFileSingleRelationshipDataDocument>

    /**
     * Get suggestedTracks relationship (\&quot;to-many\&quot;). Retrieves suggestedTracks
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: suggestedTracks (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: suggestedTracks (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSuggestedTracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/suggestedTracks")
    suspend fun tracksIdRelationshipsSuggestedTracksGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSuggestedTracksMultiRelationshipDataDocument>

    /**
     * Get trackStatistics relationship (\&quot;to-one\&quot;). Retrieves trackStatistics
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: trackStatistics (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksTrackStatisticsSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/trackStatistics")
    suspend fun tracksIdRelationshipsTrackStatisticsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksTrackStatisticsSingleRelationshipDataDocument>

    /**
     * Get usageRules relationship (\&quot;to-one\&quot;). Retrieves usageRules relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: usageRules (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksUsageRulesSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/usageRules")
    suspend fun tracksIdRelationshipsUsageRulesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksUsageRulesSingleRelationshipDataDocument>

    /**
     * Create single track. Creates a new track. Responses:
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
     * @param tracksCreateOperationPayload (optional)
     * @return [TracksCreateSingleResourceDataDocument]
     */
    @POST("tracks")
    suspend fun tracksPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body tracksCreateOperationPayload: TracksCreateOperationPayload? = null,
    ): Response<TracksCreateSingleResourceDataDocument>
}
