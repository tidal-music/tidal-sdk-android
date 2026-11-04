package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.TracksAlbumsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksAlbumsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.TracksArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksArtistsRelationshipUpdateOperationPayload
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
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Tracks {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageTracksGet(val value: kotlin.String) {
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "artists") artists("artists"),
        @SerialName(value = "credits") credits("credits"),
        @SerialName(value = "download") download("download"),
        @SerialName(value = "genres") genres("genres"),
        @SerialName(value = "lyrics") lyrics("lyrics"),
        @SerialName(value = "metadataStatus") metadataStatus("metadataStatus"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "priceConfig") priceConfig("priceConfig"),
        @SerialName(value = "providers") providers("providers"),
        @SerialName(value = "radio") radio("radio"),
        @SerialName(value = "replacement") replacement("replacement"),
        @SerialName(value = "shares") shares("shares"),
        @SerialName(value = "similarTracks") similarTracks("similarTracks"),
        @SerialName(value = "sourceFile") sourceFile("sourceFile"),
        @SerialName(value = "suggestedTracks") suggestedTracks("suggestedTracks"),
        @SerialName(value = "trackStatistics") trackStatistics("trackStatistics"),
        @SerialName(value = "usageRules") usageRules("usageRules"),
    }

    /** enum for parameter sort */
    @Serializable
    enum class SortTracksGet(val value: kotlin.String) {
        @SerialName(value = "createdAt") CreatedAtAsc("createdAt"),
        @SerialName(value = "-createdAt") CreatedAtDesc("-createdAt"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
    }

    /**
     * GET tracks Get multiple tracks. Retrieves multiple tracks by available filters, or without if
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
     * @param filterId List of track IDs (e.g. &#x60;75413016&#x60;) (optional)
     * @param filterIsrc List of ISRCs. When a single ISRC is provided, pagination is supported and
     *   multiple tracks may be returned. When multiple ISRCs are provided, one track per ISRC is
     *   returned without pagination. (e.g. &#x60;QMJMT1701237&#x60;) (optional)
     * @param filterOwnersId User id. Also accepts &#x60;me&#x60;. (optional)
     * @param include Include related resources. Available relationships: albums, artists, credits,
     *   download, genres, lyrics, metadataStatus, owners, priceConfig, providers, radio,
     *   replacement, shares, similarTracks, sourceFile, suggestedTracks, trackStatistics,
     *   usageRules (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @return [TracksMultiResourceDataDocument]
     */
    @GET("tracks")
    suspend fun tracksGet(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[isrc]")
        filterIsrc: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TracksMultiResourceDataDocument>

    /**
     * DELETE tracks/{id} Delete single track. Deletes existing track. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Track id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("tracks/{id}")
    suspend fun tracksIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageTracksIdGet(val value: kotlin.String) {
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "artists") artists("artists"),
        @SerialName(value = "credits") credits("credits"),
        @SerialName(value = "download") download("download"),
        @SerialName(value = "genres") genres("genres"),
        @SerialName(value = "lyrics") lyrics("lyrics"),
        @SerialName(value = "metadataStatus") metadataStatus("metadataStatus"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "priceConfig") priceConfig("priceConfig"),
        @SerialName(value = "providers") providers("providers"),
        @SerialName(value = "radio") radio("radio"),
        @SerialName(value = "replacement") replacement("replacement"),
        @SerialName(value = "shares") shares("shares"),
        @SerialName(value = "similarTracks") similarTracks("similarTracks"),
        @SerialName(value = "sourceFile") sourceFile("sourceFile"),
        @SerialName(value = "suggestedTracks") suggestedTracks("suggestedTracks"),
        @SerialName(value = "trackStatistics") trackStatistics("trackStatistics"),
        @SerialName(value = "usageRules") usageRules("usageRules"),
    }

    /**
     * GET tracks/{id} Get single track. Retrieves single track by id. Responses:
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
     * @param include Include related resources. Available relationships: albums, artists, credits,
     *   download, genres, lyrics, metadataStatus, owners, priceConfig, providers, radio,
     *   replacement, shares, similarTracks, sourceFile, suggestedTracks, trackStatistics,
     *   usageRules (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TracksSingleResourceDataDocument]
     */
    @GET("tracks/{id}")
    suspend fun tracksIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSingleResourceDataDocument>

    /**
     * PATCH tracks/{id} Update single track. Updates existing track. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Track id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @param tracksUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("tracks/{id}")
    suspend fun tracksIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
        @Body tracksUpdateOperationPayload: TracksUpdateOperationPayload? = null,
    ): Response<MutationResponseDocument>

    /**
     * GET tracks/{id}/relationships/albums Get albums relationship (\&quot;to-many\&quot;).
     * Retrieves albums relationship. Responses:
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
     * @param include Include related resources. Available relationships: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: albums (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * PATCH tracks/{id}/relationships/albums Update albums relationship (\&quot;to-many\&quot;).
     * Moves the uploaded track to the supplied album, replacing its previous album association.
     * Exactly one album is required; empty data cannot clear it. Returns an acknowledgement.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Track id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @param tracksAlbumsRelationshipUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("tracks/{id}/relationships/albums")
    suspend fun tracksIdRelationshipsAlbumsPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
        @Body
        tracksAlbumsRelationshipUpdateOperationPayload:
            TracksAlbumsRelationshipUpdateOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * GET tracks/{id}/relationships/artists Get artists relationship (\&quot;to-many\&quot;).
     * Retrieves artists relationship. Responses:
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
     * @param include Include related resources. Available relationships: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artists.albums (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TracksArtistsMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/artists")
    suspend fun tracksIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksArtistsMultiRelationshipDataDocument>

    /**
     * PATCH tracks/{id}/relationships/artists Update artists relationship (\&quot;to-many\&quot;).
     * Sets the track&#39;s artists, replacing the current list. Artists are listed in the order
     * given. Provide 1 to 10 distinct artists. You can add your own artist profile and the profiles
     * of users who have owner or collaborator access to the track or its album. Only the
     * track&#39;s owner can change its artists. Returns an acknowledgement. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Track id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @param tracksArtistsRelationshipUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("tracks/{id}/relationships/artists")
    suspend fun tracksIdRelationshipsArtistsPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
        @Body
        tracksArtistsRelationshipUpdateOperationPayload:
            TracksArtistsRelationshipUpdateOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * GET tracks/{id}/relationships/credits Get credits relationship (\&quot;to-many\&quot;).
     * Retrieves credits relationship. Responses:
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
     * @param include Include related resources. Available relationships: credits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: credits.artist.albums (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TracksCreditsMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/credits")
    suspend fun tracksIdRelationshipsCreditsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksCreditsMultiRelationshipDataDocument>

    /**
     * GET tracks/{id}/relationships/download Get download relationship (\&quot;to-one\&quot;).
     * Retrieves download relationship. Responses:
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
     * @param include Include related resources. Available relationships: download (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/genres Get genres relationship (\&quot;to-many\&quot;).
     * Retrieves genres relationship. Responses:
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
     * @param include Include related resources. Available relationships: genres (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/lyrics Get lyrics relationship (\&quot;to-many\&quot;).
     * Retrieves lyrics relationship. Responses:
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
     * @param include Include related resources. Available relationships: lyrics (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: lyrics.track (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/metadataStatus Get metadataStatus relationship
     * (\&quot;to-one\&quot;). Retrieves metadataStatus relationship. Responses:
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
     * @param include Include related resources. Available relationships: metadataStatus (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
     * Retrieves owners relationship. Responses:
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
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TracksOwnersMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/owners")
    suspend fun tracksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksOwnersMultiRelationshipDataDocument>

    /**
     * GET tracks/{id}/relationships/priceConfig Get priceConfig relationship
     * (\&quot;to-one\&quot;). Retrieves priceConfig relationship. Responses:
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
     * @param include Include related resources. Available relationships: priceConfig (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/providers Get providers relationship (\&quot;to-many\&quot;).
     * Retrieves providers relationship. Responses:
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
     * @param include Include related resources. Available relationships: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/radio Get radio relationship (\&quot;to-many\&quot;). Retrieves
     * radio relationship. Responses:
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
     * @param include Include related resources. Available relationships: radio (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: radio.items (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/replacement Get replacement relationship
     * (\&quot;to-one\&quot;). Retrieves replacement relationship. Responses:
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
     * @param include Include related resources. Available relationships: replacement (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: replacement (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/shares Get shares relationship (\&quot;to-many\&quot;). The
     * track&#39;s share links, active and expired, newest first. Responses:
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
     * @param include Include related resources. Available relationships: shares (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: shares.sharedResources (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/similarTracks Get similarTracks relationship
     * (\&quot;to-many\&quot;). Retrieves similarTracks relationship. Responses:
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
     * @param include Include related resources. Available relationships: similarTracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: similarTracks (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TracksSimilarTracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/similarTracks")
    suspend fun tracksIdRelationshipsSimilarTracksGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSimilarTracksMultiRelationshipDataDocument>

    /**
     * GET tracks/{id}/relationships/sourceFile Get sourceFile relationship (\&quot;to-one\&quot;).
     * Retrieves sourceFile relationship. Responses:
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
     * @param include Include related resources. Available relationships: sourceFile (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/suggestedTracks Get suggestedTracks relationship
     * (\&quot;to-many\&quot;). Retrieves suggestedTracks relationship. Responses:
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
     * @param include Include related resources. Available relationships: suggestedTracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: suggestedTracks (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
     * @return [TracksSuggestedTracksMultiRelationshipDataDocument]
     */
    @GET("tracks/{id}/relationships/suggestedTracks")
    suspend fun tracksIdRelationshipsSuggestedTracksGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<TracksSuggestedTracksMultiRelationshipDataDocument>

    /**
     * GET tracks/{id}/relationships/trackStatistics Get trackStatistics relationship
     * (\&quot;to-one\&quot;). Retrieves trackStatistics relationship. Responses:
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
     * @param include Include related resources. Available relationships: trackStatistics (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * GET tracks/{id}/relationships/usageRules Get usageRules relationship (\&quot;to-one\&quot;).
     * Retrieves usageRules relationship. Responses:
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
     * @param include Include related resources. Available relationships: usageRules (optional)
     * @param shareCode Share code used as a bearer credential for the resource. (optional)
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
     * POST tracks Create single track. Creates a new track. Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param tracksCreateOperationPayload (optional)
     * @return [TracksCreateSingleResourceDataDocument]
     */
    @POST("tracks")
    suspend fun tracksPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body tracksCreateOperationPayload: TracksCreateOperationPayload? = null,
    ): Response<TracksCreateSingleResourceDataDocument>
}
