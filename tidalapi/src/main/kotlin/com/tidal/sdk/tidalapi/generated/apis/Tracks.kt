package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TracksAlbumsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TracksCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksUpdateOperationPayload
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
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
     * @param filterId Track id (e.g. &#x60;75413016&#x60;) (optional)
     * @param filterIsrc List of ISRCs. When a single ISRC is provided, pagination is supported and
     *   multiple tracks may be returned. When multiple ISRCs are provided, one track per ISRC is
     *   returned without pagination. (e.g. &#x60;QMJMT1701237&#x60;) (optional)
     * @param filterOwnersId User id (e.g. &#x60;123456&#x60;) (optional)
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
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiResourceDataDocument>

    /**
     * Delete single track. Deletes existing track. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Track id
     * @return [Unit]
     */
    @DELETE("tracks/{id}") suspend fun tracksIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single track. Retrieves single track by id. Responses:
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, credits, download, genres, lyrics, metadataStatus,
     *   owners, priceConfig, providers, radio, replacement, shares, similarTracks, sourceFile,
     *   suggestedTracks, trackStatistics, usageRules (optional)
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
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleResourceDataDocument>

    /**
     * Update single track. Updates existing track. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Track id
     * @param tracksUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("tracks/{id}")
    suspend fun tracksIdPatch(
        @Path("id") id: kotlin.String,
        @Body tracksUpdateOperationPayload: TracksUpdateOperationPayload? = null,
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/albums")
    suspend fun tracksIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Update albums relationship (\&quot;to-many\&quot;). Updates albums relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Track id
     * @param tracksAlbumsRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("tracks/{id}/relationships/albums")
    suspend fun tracksIdRelationshipsAlbumsPatch(
        @Path("id") id: kotlin.String,
        @Body
        tracksAlbumsRelationshipUpdateOperationPayload:
            TracksAlbumsRelationshipUpdateOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Retrieves artists relationship. Responses:
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/artists")
    suspend fun tracksIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get credits relationship (\&quot;to-many\&quot;). Retrieves credits relationship. Responses:
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: credits (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/credits")
    suspend fun tracksIdRelationshipsCreditsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get download relationship (\&quot;to-one\&quot;). Retrieves download relationship. Responses:
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: download (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/download")
    suspend fun tracksIdRelationshipsDownloadGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleRelationshipDataDocument>

    /**
     * Get genres relationship (\&quot;to-many\&quot;). Retrieves genres relationship. Responses:
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: genres (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/genres")
    suspend fun tracksIdRelationshipsGenresGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get lyrics relationship (\&quot;to-many\&quot;). Retrieves lyrics relationship. Responses:
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: lyrics (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/lyrics")
    suspend fun tracksIdRelationshipsLyricsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get metadataStatus relationship (\&quot;to-one\&quot;). Retrieves metadataStatus
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: metadataStatus (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/metadataStatus")
    suspend fun tracksIdRelationshipsMetadataStatusGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleRelationshipDataDocument>

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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/owners")
    suspend fun tracksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get priceConfig relationship (\&quot;to-one\&quot;). Retrieves priceConfig relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: priceConfig (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/priceConfig")
    suspend fun tracksIdRelationshipsPriceConfigGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleRelationshipDataDocument>

    /**
     * Get providers relationship (\&quot;to-many\&quot;). Retrieves providers relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/providers")
    suspend fun tracksIdRelationshipsProvidersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: radio (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/radio")
    suspend fun tracksIdRelationshipsRadioGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get replacement relationship (\&quot;to-one\&quot;). Retrieves replacement relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: replacement (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/replacement")
    suspend fun tracksIdRelationshipsReplacementGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleRelationshipDataDocument>

    /**
     * Get shares relationship (\&quot;to-many\&quot;). Retrieves shares relationship. Responses:
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: shares (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/shares")
    suspend fun tracksIdRelationshipsSharesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get similarTracks relationship (\&quot;to-many\&quot;). Retrieves similarTracks relationship.
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: similarTracks (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/similarTracks")
    suspend fun tracksIdRelationshipsSimilarTracksGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get sourceFile relationship (\&quot;to-one\&quot;). Retrieves sourceFile relationship.
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: sourceFile (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/sourceFile")
    suspend fun tracksIdRelationshipsSourceFileGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleRelationshipDataDocument>

    /**
     * Get suggestedTracks relationship (\&quot;to-many\&quot;). Retrieves suggestedTracks
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
     * @param id Track id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: suggestedTracks (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/suggestedTracks")
    suspend fun tracksIdRelationshipsSuggestedTracksGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksMultiRelationshipDataDocument>

    /**
     * Get trackStatistics relationship (\&quot;to-one\&quot;). Retrieves trackStatistics
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
     * @param id Track id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: trackStatistics (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/trackStatistics")
    suspend fun tracksIdRelationshipsTrackStatisticsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleRelationshipDataDocument>

    /**
     * Get usageRules relationship (\&quot;to-one\&quot;). Retrieves usageRules relationship.
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
     * @param id Track id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: usageRules (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [TracksSingleRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/usageRules")
    suspend fun tracksIdRelationshipsUsageRulesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleRelationshipDataDocument>

    /**
     * Create single track. Creates a new track. Responses:
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
     * @param tracksCreateOperationPayload (optional)
     * @return [TracksSingleResourceDataDocument]
     */
    @POST("tracks")
    suspend fun tracksPost(
        @Body tracksCreateOperationPayload: TracksCreateOperationPayload? = null
    ): Response<TracksSingleResourceDataDocument>
}
