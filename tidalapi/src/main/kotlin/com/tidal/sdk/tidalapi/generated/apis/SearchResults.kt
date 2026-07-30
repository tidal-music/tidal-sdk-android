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

    /** enum for parameter deviceType */
    enum class DeviceTypeSearchResultsIdGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeSearchResultsIdGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get single searchResult. Retrieves single searchResult by id. Responses:
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param deviceType The type of device making the request (optional)
     * @param systemType The system type of the device making the request (optional)
     * @param clientVersion Client version number (optional)
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
        @Query("deviceType") deviceType: DeviceTypeSearchResultsIdGet? = null,
        @Query("systemType") systemType: SystemTypeSearchResultsIdGet? = null,
        @Query("clientVersion") clientVersion: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsSingleResourceDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /** enum for parameter deviceType */
    enum class DeviceTypeSearchResultsIdRelationshipsAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeSearchResultsIdRelationshipsAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param deviceType The type of device making the request (optional)
     * @param systemType The system type of the device making the request (optional)
     * @param clientVersion Client version number (optional)
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
        @Query("deviceType") deviceType: DeviceTypeSearchResultsIdRelationshipsAlbumsGet? = null,
        @Query("systemType") systemType: SystemTypeSearchResultsIdRelationshipsAlbumsGet? = null,
        @Query("clientVersion") clientVersion: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsArtistsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /** enum for parameter deviceType */
    enum class DeviceTypeSearchResultsIdRelationshipsArtistsGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeSearchResultsIdRelationshipsArtistsGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param deviceType The type of device making the request (optional)
     * @param systemType The system type of the device making the request (optional)
     * @param clientVersion Client version number (optional)
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
        @Query("deviceType") deviceType: DeviceTypeSearchResultsIdRelationshipsArtistsGet? = null,
        @Query("systemType") systemType: SystemTypeSearchResultsIdRelationshipsArtistsGet? = null,
        @Query("clientVersion") clientVersion: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /** enum for parameter deviceType */
    enum class DeviceTypeSearchResultsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeSearchResultsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get playlists relationship (\&quot;to-many\&quot;). Retrieves playlists relationship.
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param deviceType The type of device making the request (optional)
     * @param systemType The system type of the device making the request (optional)
     * @param clientVersion Client version number (optional)
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
        @Query("deviceType") deviceType: DeviceTypeSearchResultsIdRelationshipsPlaylistsGet? = null,
        @Query("systemType") systemType: SystemTypeSearchResultsIdRelationshipsPlaylistsGet? = null,
        @Query("clientVersion") clientVersion: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsTopHitsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /** enum for parameter deviceType */
    enum class DeviceTypeSearchResultsIdRelationshipsTopHitsGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeSearchResultsIdRelationshipsTopHitsGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

    /**
     * Get topHits relationship (\&quot;to-many\&quot;). Retrieves topHits relationship. Responses:
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param deviceType The type of device making the request (optional)
     * @param systemType The system type of the device making the request (optional)
     * @param clientVersion Client version number (optional)
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
        @Query("deviceType") deviceType: DeviceTypeSearchResultsIdRelationshipsTopHitsGet? = null,
        @Query("systemType") systemType: SystemTypeSearchResultsIdRelationshipsTopHitsGet? = null,
        @Query("clientVersion") clientVersion: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /** enum for parameter deviceType */
    enum class DeviceTypeSearchResultsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeSearchResultsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param deviceType The type of device making the request (optional)
     * @param systemType The system type of the device making the request (optional)
     * @param clientVersion Client version number (optional)
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
        @Query("deviceType") deviceType: DeviceTypeSearchResultsIdRelationshipsTracksGet? = null,
        @Query("systemType") systemType: SystemTypeSearchResultsIdRelationshipsTracksGet? = null,
        @Query("clientVersion") clientVersion: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchResultsIdRelationshipsVideosGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /** enum for parameter deviceType */
    enum class DeviceTypeSearchResultsIdRelationshipsVideosGet(val value: kotlin.String) {
        @SerialName(value = "BROWSER") BROWSER("BROWSER"),
        @SerialName(value = "CAR") CAR("CAR"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "PHONE") PHONE("PHONE"),
        @SerialName(value = "TABLET") TABLET("TABLET"),
        @SerialName(value = "TV") TV("TV"),
    }

    /** enum for parameter systemType */
    enum class SystemTypeSearchResultsIdRelationshipsVideosGet(val value: kotlin.String) {
        @SerialName(value = "ANDROID") ANDROID("ANDROID"),
        @SerialName(value = "DESKTOP") DESKTOP("DESKTOP"),
        @SerialName(value = "TESLA") TESLA("TESLA"),
        @SerialName(value = "IOS") IOS("IOS"),
        @SerialName(value = "WEB") WEB("WEB"),
    }

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
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param deviceType The type of device making the request (optional)
     * @param systemType The system type of the device making the request (optional)
     * @param clientVersion Client version number (optional)
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
        @Query("deviceType") deviceType: DeviceTypeSearchResultsIdRelationshipsVideosGet? = null,
        @Query("systemType") systemType: SystemTypeSearchResultsIdRelationshipsVideosGet? = null,
        @Query("clientVersion") clientVersion: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchResultsMultiRelationshipDataDocument>
}
