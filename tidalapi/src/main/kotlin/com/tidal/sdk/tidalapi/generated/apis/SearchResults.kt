package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchResultsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SearchResultsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface SearchResults {

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /**
     * Get single searchResult. Retrieves single searchResult by id. Responses:
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, playlists, topHits, tracks, videos (optional)
     * @return [SearchResultsSingleResourceDataDocument]
     */
    @GET("searchResults/{id}")
    suspend fun searchResultsIdGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchResultsIdGet? =
            ExplicitFilterSearchResultsIdGet.INCLUDE,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsSingleResourceDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/albums")
    suspend fun searchResultsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchResultsIdRelationshipsAlbumsGet? =
            ExplicitFilterSearchResultsIdRelationshipsAlbumsGet.INCLUDE,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsArtistsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/artists")
    suspend fun searchResultsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchResultsIdRelationshipsArtistsGet? =
            ExplicitFilterSearchResultsIdRelationshipsArtistsGet.INCLUDE,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /**
     * Get playlists relationship (\&quot;to-many\&quot;). Retrieves playlists relationship.
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlists (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/playlists")
    suspend fun searchResultsIdRelationshipsPlaylistsGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchResultsIdRelationshipsPlaylistsGet? =
            ExplicitFilterSearchResultsIdRelationshipsPlaylistsGet.INCLUDE,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsTopHitsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /**
     * Get topHits relationship (\&quot;to-many\&quot;). Retrieves topHits relationship. Responses:
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: topHits (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/topHits")
    suspend fun searchResultsIdRelationshipsTopHitsGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchResultsIdRelationshipsTopHitsGet? =
            ExplicitFilterSearchResultsIdRelationshipsTopHitsGet.INCLUDE,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/tracks")
    suspend fun searchResultsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchResultsIdRelationshipsTracksGet? =
            ExplicitFilterSearchResultsIdRelationshipsTracksGet.INCLUDE,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsVideosGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @return [SearchResultsMultiRelationshipDataDocument]
     */
    @GET("searchResults/{id}/relationships/videos")
    suspend fun searchResultsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchResultsIdRelationshipsVideosGet? =
            ExplicitFilterSearchResultsIdRelationshipsVideosGet.INCLUDE,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>
}
