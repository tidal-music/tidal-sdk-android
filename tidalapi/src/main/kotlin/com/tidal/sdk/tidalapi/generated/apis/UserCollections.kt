package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionsAlbumsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsAlbumsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsAlbumsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsArtistsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsArtistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsPlaylistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsPlaylistsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsPlaylistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsTracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsTracksRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsTracksRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsVideosMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsVideosRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsVideosRelationshipRemoveOperationPayload
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface UserCollections {
    /**
     * Get single userCollection. Deprecated. Use the dedicated collection resources instead:
     * userCollectionAlbums, userCollectionArtists, userCollectionTracks, userCollectionVideos, or
     * userCollectionPlaylists. Responses:
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
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, owners, playlists, tracks, videos (optional)
     * @return [UserCollectionsSingleResourceDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("userCollections/{id}")
    suspend fun userCollectionsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsSingleResourceDataDocument>

    /**
     * Delete from albums relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionAlbums resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param userCollectionsAlbumsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/albums", hasBody = true)
    suspend fun userCollectionsIdRelationshipsAlbumsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionsAlbumsRelationshipRemoveOperationPayload:
            UserCollectionsAlbumsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "addedAt") AddedAtAsc("addedAt"),
        @SerialName(value = "-addedAt") AddedAtDesc("-addedAt"),
        @SerialName(value = "artists.name") ArtistsNameAsc("artists.name"),
        @SerialName(value = "-artists.name") ArtistsNameDesc("-artists.name"),
        @SerialName(value = "releaseDate") ReleaseDateAsc("releaseDate"),
        @SerialName(value = "-releaseDate") ReleaseDateDesc("-releaseDate"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
    }

    /**
     * Get albums relationship (\&quot;to-many\&quot;). Deprecated. Use the userCollectionAlbums
     * resource and its items relationship instead. Responses:
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
     * @param id User collection id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @return [UserCollectionsAlbumsMultiRelationshipDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsAlbumsMultiRelationshipDataDocument>

    /**
     * Add to albums relationship (\&quot;to-many\&quot;). Deprecated. Use the userCollectionAlbums
     * resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of items allowed for this collection. Please
     *   remove some items before adding more.; One or more items you are trying to add are already
     *   in your favorites.
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionsAlbumsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @POST("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionsAlbumsRelationshipAddOperationPayload:
            UserCollectionsAlbumsRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from artists relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionArtists resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param userCollectionsArtistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/artists", hasBody = true)
    suspend fun userCollectionsIdRelationshipsArtistsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionsArtistsRelationshipRemoveOperationPayload:
            UserCollectionsArtistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsArtistsGet(val value: kotlin.String) {
        @SerialName(value = "addedAt") AddedAtAsc("addedAt"),
        @SerialName(value = "-addedAt") AddedAtDesc("-addedAt"),
        @SerialName(value = "name") NameAsc("name"),
        @SerialName(value = "-name") NameDesc("-name"),
    }

    /**
     * Get artists relationship (\&quot;to-many\&quot;). Deprecated. Use the userCollectionArtists
     * resource and its items relationship instead. Responses:
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
     * @param id User collection id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @return [UserCollectionsArtistsMultiRelationshipDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsArtistsMultiRelationshipDataDocument>

    /**
     * Add to artists relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionArtists resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of items allowed for this collection. Please
     *   remove some items before adding more.; One or more items you are trying to add are already
     *   in your favorites.
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionsArtistsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @POST("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionsArtistsRelationshipAddOperationPayload:
            UserCollectionsArtistsRelationshipAddOperationPayload? =
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
     * @param id User collection id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserCollectionsMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/owners")
    suspend fun userCollectionsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserCollectionsMultiRelationshipDataDocument>

    /**
     * Delete from playlists relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionPlaylists resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param userCollectionsPlaylistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/playlists", hasBody = true)
    suspend fun userCollectionsIdRelationshipsPlaylistsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionsPlaylistsRelationshipRemoveOperationPayload:
            UserCollectionsPlaylistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter collectionView */
    enum class CollectionViewUserCollectionsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "FOLDERS") FOLDERS("FOLDERS")
    }

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "addedAt") AddedAtAsc("addedAt"),
        @SerialName(value = "-addedAt") AddedAtDesc("-addedAt"),
        @SerialName(value = "lastModifiedAt") LastModifiedAtAsc("lastModifiedAt"),
        @SerialName(value = "-lastModifiedAt") LastModifiedAtDesc("-lastModifiedAt"),
        @SerialName(value = "name") NameAsc("name"),
        @SerialName(value = "-name") NameDesc("-name"),
    }

    /**
     * Get playlists relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionPlaylists resource and its items relationship instead. Responses:
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
     * @param id User collection id
     * @param collectionView (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlists (optional)
     * @return [UserCollectionsPlaylistsMultiRelationshipDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsGet(
        @Path("id") id: kotlin.String,
        @Query("collectionView")
        collectionView: CollectionViewUserCollectionsIdRelationshipsPlaylistsGet? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsPlaylistsMultiRelationshipDataDocument>

    /**
     * Add to playlists relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionPlaylists resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of items allowed for this collection. Please
     *   remove some items before adding more.; One or more items you are trying to add are already
     *   in your favorites.
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param userCollectionsPlaylistsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @POST("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsPost(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionsPlaylistsRelationshipAddOperationPayload:
            UserCollectionsPlaylistsRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from tracks relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionTracks resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param userCollectionsTracksRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/tracks", hasBody = true)
    suspend fun userCollectionsIdRelationshipsTracksDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionsTracksRelationshipRemoveOperationPayload:
            UserCollectionsTracksRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "addedAt") AddedAtAsc("addedAt"),
        @SerialName(value = "-addedAt") AddedAtDesc("-addedAt"),
        @SerialName(value = "albums.title") AlbumsTitleAsc("albums.title"),
        @SerialName(value = "-albums.title") AlbumsTitleDesc("-albums.title"),
        @SerialName(value = "artists.name") ArtistsNameAsc("artists.name"),
        @SerialName(value = "-artists.name") ArtistsNameDesc("-artists.name"),
        @SerialName(value = "duration") DurationAsc("duration"),
        @SerialName(value = "-duration") DurationDesc("-duration"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
    }

    /**
     * Get tracks relationship (\&quot;to-many\&quot;). Deprecated. Use the userCollectionTracks
     * resource and its items relationship instead. Responses:
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
     * @param id User collection id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @return [UserCollectionsTracksMultiRelationshipDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("userCollections/{id}/relationships/tracks")
    suspend fun userCollectionsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsTracksMultiRelationshipDataDocument>

    /**
     * Add to tracks relationship (\&quot;to-many\&quot;). Deprecated. Use the userCollectionTracks
     * resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of items allowed for this collection. Please
     *   remove some items before adding more.; One or more items you are trying to add are already
     *   in your favorites.
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionsTracksRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @POST("userCollections/{id}/relationships/tracks")
    suspend fun userCollectionsIdRelationshipsTracksPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionsTracksRelationshipAddOperationPayload:
            UserCollectionsTracksRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from videos relationship (\&quot;to-many\&quot;). Deprecated. Use the
     * userCollectionVideos resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param userCollectionsVideosRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/videos", hasBody = true)
    suspend fun userCollectionsIdRelationshipsVideosDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionsVideosRelationshipRemoveOperationPayload:
            UserCollectionsVideosRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsVideosGet(val value: kotlin.String) {
        @SerialName(value = "addedAt") AddedAtAsc("addedAt"),
        @SerialName(value = "-addedAt") AddedAtDesc("-addedAt"),
        @SerialName(value = "artists.name") ArtistsNameAsc("artists.name"),
        @SerialName(value = "-artists.name") ArtistsNameDesc("-artists.name"),
        @SerialName(value = "duration") DurationAsc("duration"),
        @SerialName(value = "-duration") DurationDesc("-duration"),
        @SerialName(value = "title") TitleAsc("title"),
        @SerialName(value = "-title") TitleDesc("-title"),
    }

    /**
     * Get videos relationship (\&quot;to-many\&quot;). Deprecated. Use the userCollectionVideos
     * resource and its items relationship instead. Responses:
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
     * @param id User collection id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @return [UserCollectionsVideosMultiRelationshipDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("userCollections/{id}/relationships/videos")
    suspend fun userCollectionsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsVideosMultiRelationshipDataDocument>

    /**
     * Add to videos relationship (\&quot;to-many\&quot;). Deprecated. Use the userCollectionVideos
     * resource and its items relationship instead. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: You have reached the maximum number of items allowed for this collection. Please
     *   remove some items before adding more.; One or more items you are trying to add are already
     *   in your favorites.
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id User collection id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionsVideosRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @Deprecated("This api was deprecated")
    @POST("userCollections/{id}/relationships/videos")
    suspend fun userCollectionsIdRelationshipsVideosPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionsVideosRelationshipAddOperationPayload:
            UserCollectionsVideosRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>
}
