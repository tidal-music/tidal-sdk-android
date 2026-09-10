package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.AlbumsAlbumStatisticsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsCoverArtMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsCoverArtRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AlbumsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AlbumsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsGenresMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsItemsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AlbumsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsPriceConfigSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsProvidersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsReplacementSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSharesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSimilarAlbumsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSuggestedCoverArtsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AlbumsUsageRulesSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Albums {

    /** enum for parameter sort */
    @Serializable
    enum class SortAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "createdAt") CreatedAtAsc("createdAt"),
        @SerialName(value = "-createdAt") CreatedAtDesc("-createdAt"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
    }

    /**
     * GET albums Get multiple albums. Retrieves multiple albums by available filters, or without if
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
     *   Available options: albumStatistics, artists, coverArt, genres, items, owners, priceConfig,
     *   providers, replacement, shares, similarAlbums, suggestedCoverArts, usageRules (optional)
     * @param filterBarcodeId List of barcode IDs (EAN-13 or UPC-A). NOTE: Supplying more than one
     *   barcode ID will currently only return one album per barcode ID. (e.g.
     *   &#x60;196589525444&#x60;) (optional)
     * @param filterId List of album IDs (e.g. &#x60;251380836&#x60;) (optional)
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artists.albums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsMultiResourceDataDocument]
     */
    @GET("albums")
    suspend fun albumsGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[barcodeId]")
        filterBarcodeId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiResourceDataDocument>

    /**
     * DELETE albums/{id} Delete single album. Deletes existing album. Responses:
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
     * @param id Album id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("albums/{id}")
    suspend fun albumsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /**
     * GET albums/{id} Get single album. Retrieves single album by id. Responses:
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albumStatistics, artists, coverArt, genres, items, owners, priceConfig,
     *   providers, replacement, shares, similarAlbums, suggestedCoverArts, usageRules (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artists.albums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSingleResourceDataDocument]
     */
    @GET("albums/{id}")
    suspend fun albumsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSingleResourceDataDocument>

    /**
     * PATCH albums/{id} Update single album. Updates existing album. Responses:
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
     * @param id Album id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param albumsUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("albums/{id}")
    suspend fun albumsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body albumsUpdateOperationPayload: AlbumsUpdateOperationPayload? = null,
    ): Response<MutationResponseDocument>

    /**
     * GET albums/{id}/relationships/albumStatistics Get albumStatistics relationship
     * (\&quot;to-one\&quot;). Retrieves albumStatistics relationship. Responses:
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
     * @param id Album id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albumStatistics (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsAlbumStatisticsSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/albumStatistics")
    suspend fun albumsIdRelationshipsAlbumStatisticsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsAlbumStatisticsSingleRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/artists Get artists relationship (\&quot;to-many\&quot;).
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
     * @param id Album id
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
     * @return [AlbumsArtistsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/artists")
    suspend fun albumsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsArtistsMultiRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/coverArt Get coverArt relationship (\&quot;to-many\&quot;).
     * Retrieves coverArt relationship. Responses:
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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: coverArt (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsCoverArtMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/coverArt")
    suspend fun albumsIdRelationshipsCoverArtGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsCoverArtMultiRelationshipDataDocument>

    /**
     * PATCH albums/{id}/relationships/coverArt Update coverArt relationship
     * (\&quot;to-many\&quot;). Updates coverArt relationship. Responses:
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
     * @param id Album id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param albumsCoverArtRelationshipUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("albums/{id}/relationships/coverArt")
    suspend fun albumsIdRelationshipsCoverArtPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        albumsCoverArtRelationshipUpdateOperationPayload:
            AlbumsCoverArtRelationshipUpdateOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * GET albums/{id}/relationships/genres Get genres relationship (\&quot;to-many\&quot;).
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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: genres (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsGenresMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/genres")
    suspend fun albumsIdRelationshipsGenresGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsGenresMultiRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/items Get items relationship (\&quot;to-many\&quot;). Retrieves
     * items relationship. Responses:
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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsItemsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/items")
    suspend fun albumsIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsItemsMultiRelationshipDataDocument>

    /**
     * PATCH albums/{id}/relationships/items Update items relationship (\&quot;to-many\&quot;).
     * Updates items relationship. Responses:
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
     * @param id Album id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param albumsItemsRelationshipUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("albums/{id}/relationships/items")
    suspend fun albumsIdRelationshipsItemsPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        albumsItemsRelationshipUpdateOperationPayload:
            AlbumsItemsRelationshipUpdateOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * GET albums/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsOwnersMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/owners")
    suspend fun albumsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsOwnersMultiRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/priceConfig Get priceConfig relationship
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: priceConfig (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsPriceConfigSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/priceConfig")
    suspend fun albumsIdRelationshipsPriceConfigGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsPriceConfigSingleRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/providers Get providers relationship (\&quot;to-many\&quot;).
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsProvidersMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/providers")
    suspend fun albumsIdRelationshipsProvidersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsProvidersMultiRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/replacement Get replacement relationship
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: replacement (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: replacement (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsReplacementSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/replacement")
    suspend fun albumsIdRelationshipsReplacementGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsReplacementSingleRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/shares Get shares relationship (\&quot;to-many\&quot;).
     * Retrieves shares relationship. Responses:
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
     * @param id Album id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: shares (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: shares.sharedResources (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSharesMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/shares")
    suspend fun albumsIdRelationshipsSharesGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSharesMultiRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/similarAlbums Get similarAlbums relationship
     * (\&quot;to-many\&quot;). Retrieves similarAlbums relationship. Responses:
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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: similarAlbums (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: similarAlbums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSimilarAlbumsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/similarAlbums")
    suspend fun albumsIdRelationshipsSimilarAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSimilarAlbumsMultiRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/suggestedCoverArts Get suggestedCoverArts relationship
     * (\&quot;to-many\&quot;). Retrieves suggestedCoverArts relationship. Responses:
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
     * @param id Album id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: suggestedCoverArts (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSuggestedCoverArtsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/suggestedCoverArts")
    suspend fun albumsIdRelationshipsSuggestedCoverArtsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSuggestedCoverArtsMultiRelationshipDataDocument>

    /**
     * GET albums/{id}/relationships/usageRules Get usageRules relationship (\&quot;to-one\&quot;).
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: usageRules (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsUsageRulesSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/usageRules")
    suspend fun albumsIdRelationshipsUsageRulesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsUsageRulesSingleRelationshipDataDocument>

    /**
     * POST albums Create single album. Creates a new album. Responses:
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
     * @param albumsCreateOperationPayload (optional)
     * @return [AlbumsCreateSingleResourceDataDocument]
     */
    @POST("albums")
    suspend fun albumsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body albumsCreateOperationPayload: AlbumsCreateOperationPayload? = null,
    ): Response<AlbumsCreateSingleResourceDataDocument>
}
