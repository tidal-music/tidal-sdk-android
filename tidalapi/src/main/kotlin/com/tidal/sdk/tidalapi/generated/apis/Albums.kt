package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.AlbumsCoverArtRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AlbumsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AlbumsItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsItemsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.AlbumsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSuggestedCoverArtsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsUpdateOperationPayload
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Albums {

    /** enum for parameter sort */
    enum class SortAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "createdAt") CreatedAtAsc("createdAt"),
        @SerialName(value = "-createdAt") CreatedAtDesc("-createdAt"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
    }

    /**
     * Get multiple albums. Retrieves multiple albums by available filters, or without if
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
     *   Available options: albumStatistics, artists, coverArt, genres, items, owners, priceConfig,
     *   providers, replacement, similarAlbums, suggestedCoverArts, usageRules (optional)
     * @param filterBarcodeId List of barcode IDs (EAN-13 or UPC-A). NOTE: Supplying more than one
     *   barcode ID will currently only return one album per barcode ID. (e.g.
     *   &#x60;196589525444&#x60;) (optional)
     * @param filterId Album id (e.g. &#x60;251380836&#x60;) (optional)
     * @param filterOwnersId User id (e.g. &#x60;123456&#x60;) (optional)
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
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiResourceDataDocument>

    /**
     * Delete single album. Deletes existing album. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Album id
     * @return [Unit]
     */
    @DELETE("albums/{id}") suspend fun albumsIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single album. Retrieves single album by id. Responses:
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albumStatistics, artists, coverArt, genres, items, owners, priceConfig,
     *   providers, replacement, similarAlbums, suggestedCoverArts, usageRules (optional)
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
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSingleResourceDataDocument>

    /**
     * Update single album. Updates existing album. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Album id
     * @param albumsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("albums/{id}")
    suspend fun albumsIdPatch(
        @Path("id") id: kotlin.String,
        @Body albumsUpdateOperationPayload: AlbumsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get albumStatistics relationship (\&quot;to-one\&quot;). Retrieves albumStatistics
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
     * @param id Album id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albumStatistics (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/albumStatistics")
    suspend fun albumsIdRelationshipsAlbumStatisticsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSingleRelationshipDataDocument>

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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/artists")
    suspend fun albumsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiRelationshipDataDocument>

    /**
     * Get coverArt relationship (\&quot;to-many\&quot;). Retrieves coverArt relationship.
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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: coverArt (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/coverArt")
    suspend fun albumsIdRelationshipsCoverArtGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiRelationshipDataDocument>

    /**
     * Update coverArt relationship (\&quot;to-many\&quot;). Updates coverArt relationship.
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
     * @param id Album id
     * @param albumsCoverArtRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("albums/{id}/relationships/coverArt")
    suspend fun albumsIdRelationshipsCoverArtPatch(
        @Path("id") id: kotlin.String,
        @Body
        albumsCoverArtRelationshipUpdateOperationPayload:
            AlbumsCoverArtRelationshipUpdateOperationPayload? =
            null,
    ): Response<Unit>

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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: genres (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/genres")
    suspend fun albumsIdRelationshipsGenresGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiRelationshipDataDocument>

    /**
     * Get items relationship (\&quot;to-many\&quot;). Retrieves items relationship. Responses:
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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: items (optional)
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
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsItemsMultiRelationshipDataDocument>

    /**
     * Update items relationship (\&quot;to-many\&quot;). Updates items relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Album id
     * @param albumsItemsRelationshipUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("albums/{id}/relationships/items")
    suspend fun albumsIdRelationshipsItemsPatch(
        @Path("id") id: kotlin.String,
        @Body
        albumsItemsRelationshipUpdateOperationPayload:
            AlbumsItemsRelationshipUpdateOperationPayload? =
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
     * @param id Album id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/owners")
    suspend fun albumsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiRelationshipDataDocument>

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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: priceConfig (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/priceConfig")
    suspend fun albumsIdRelationshipsPriceConfigGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSingleRelationshipDataDocument>

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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/providers")
    suspend fun albumsIdRelationshipsProvidersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiRelationshipDataDocument>

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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: replacement (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/replacement")
    suspend fun albumsIdRelationshipsReplacementGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSingleRelationshipDataDocument>

    /**
     * Get similarAlbums relationship (\&quot;to-many\&quot;). Retrieves similarAlbums relationship.
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
     * @param id Album id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: similarAlbums (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsMultiRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/similarAlbums")
    suspend fun albumsIdRelationshipsSimilarAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsMultiRelationshipDataDocument>

    /**
     * Get suggestedCoverArts relationship (\&quot;to-many\&quot;). Retrieves suggestedCoverArts
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
     * @param id Album id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: usageRules (optional)
     * @param shareCode Share code that grants access to UNLISTED resources. When provided, allows
     *   non-owners to access resources that would otherwise be restricted. (optional)
     * @return [AlbumsSingleRelationshipDataDocument]
     */
    @GET("albums/{id}/relationships/usageRules")
    suspend fun albumsIdRelationshipsUsageRulesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("shareCode") shareCode: kotlin.String? = null,
    ): Response<AlbumsSingleRelationshipDataDocument>

    /**
     * Create single album. Creates a new album. Responses:
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
     * @param albumsCreateOperationPayload (optional)
     * @return [AlbumsSingleResourceDataDocument]
     */
    @POST("albums")
    suspend fun albumsPost(
        @Body albumsCreateOperationPayload: AlbumsCreateOperationPayload? = null
    ): Response<AlbumsSingleResourceDataDocument>
}
