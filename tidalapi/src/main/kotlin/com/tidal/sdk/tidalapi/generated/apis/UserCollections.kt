package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserCollectionAlbumsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionAlbumsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionArtistsRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionArtistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionPlaylistsRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionTracksRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionVideosRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionVideosRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsAlbumsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsArtistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsPlaylistsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsTracksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserCollectionsVideosMultiRelationshipDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface UserCollections {
    /**
     * Get single userCollection. Retrieves single userCollection by id. Responses:
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
     * @param id User id
     * @param locale BCP 47 locale (default to "en-US")
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums, artists, owners, playlists, tracks, videos (optional)
     * @return [UserCollectionsSingleResourceDataDocument]
     */
    @GET("userCollections/{id}")
    suspend fun userCollectionsIdGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String = "en-US",
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsSingleResourceDataDocument>

    /**
     * Delete from albums relationship (\&quot;to-many\&quot;). Deletes item(s) from albums
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
     * @param id User id
     * @param userCollectionAlbumsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/albums", hasBody = true)
    suspend fun userCollectionsIdRelationshipsAlbumsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionAlbumsRelationshipRemoveOperationPayload:
            UserCollectionAlbumsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "albums.addedAt") AlbumsAddedAtAsc("albums.addedAt"),
        @SerialName(value = "-albums.addedAt") AlbumsAddedAtDesc("-albums.addedAt"),
        @SerialName(value = "albums.artists.name") AlbumsArtistsNameAsc("albums.artists.name"),
        @SerialName(value = "-albums.artists.name") AlbumsArtistsNameDesc("-albums.artists.name"),
        @SerialName(value = "albums.releaseDate") AlbumsReleaseDateAsc("albums.releaseDate"),
        @SerialName(value = "-albums.releaseDate") AlbumsReleaseDateDesc("-albums.releaseDate"),
        @SerialName(value = "albums.title") AlbumsTitleAsc("albums.title"),
        @SerialName(value = "-albums.title") AlbumsTitleDesc("-albums.title"),
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
     * @param id User id
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: albums (optional)
     * @return [UserCollectionsAlbumsMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String = "en-US",
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsAlbumsMultiRelationshipDataDocument>

    /**
     * Add to albums relationship (\&quot;to-many\&quot;). Adds item(s) to albums relationship.
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionAlbumsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionAlbumsRelationshipAddOperationPayload:
            UserCollectionAlbumsRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from artists relationship (\&quot;to-many\&quot;). Deletes item(s) from artists
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
     * @param id User id
     * @param userCollectionArtistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/artists", hasBody = true)
    suspend fun userCollectionsIdRelationshipsArtistsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionArtistsRelationshipRemoveOperationPayload:
            UserCollectionArtistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsArtistsGet(val value: kotlin.String) {
        @SerialName(value = "artists.addedAt") ArtistsAddedAtAsc("artists.addedAt"),
        @SerialName(value = "-artists.addedAt") ArtistsAddedAtDesc("-artists.addedAt"),
        @SerialName(value = "artists.name") ArtistsNameAsc("artists.name"),
        @SerialName(value = "-artists.name") ArtistsNameDesc("-artists.name"),
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
     * @param id User id
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artists (optional)
     * @return [UserCollectionsArtistsMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String = "en-US",
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsArtistsMultiRelationshipDataDocument>

    /**
     * Add to artists relationship (\&quot;to-many\&quot;). Adds item(s) to artists relationship.
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionArtistsRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionArtistsRelationshipAddOperationPayload:
            UserCollectionArtistsRelationshipAddOperationPayload? =
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
     * @param id User id
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
     * Delete from playlists relationship (\&quot;to-many\&quot;). Deletes item(s) from playlists
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
     * @param id User id
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/playlists", hasBody = true)
    suspend fun userCollectionsIdRelationshipsPlaylistsDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionPlaylistsRelationshipRemoveOperationPayload:
            UserCollectionPlaylistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter collectionView */
    enum class CollectionViewUserCollectionsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "FOLDERS") FOLDERS("FOLDERS")
    }

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "playlists.addedAt") PlaylistsAddedAtAsc("playlists.addedAt"),
        @SerialName(value = "-playlists.addedAt") PlaylistsAddedAtDesc("-playlists.addedAt"),
        @SerialName(value = "playlists.lastUpdatedAt")
        PlaylistsLastUpdatedAtAsc("playlists.lastUpdatedAt"),
        @SerialName(value = "-playlists.lastUpdatedAt")
        PlaylistsLastUpdatedAtDesc("-playlists.lastUpdatedAt"),
        @SerialName(value = "playlists.name") PlaylistsNameAsc("playlists.name"),
        @SerialName(value = "-playlists.name") PlaylistsNameDesc("-playlists.name"),
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
     * @param id User id
     * @param collectionView (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlists (optional)
     * @return [UserCollectionsPlaylistsMultiRelationshipDataDocument]
     */
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
     * Add to playlists relationship (\&quot;to-many\&quot;). Adds item(s) to playlists
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
     * @param id User id
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsPost(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionPlaylistsRelationshipRemoveOperationPayload:
            UserCollectionPlaylistsRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from tracks relationship (\&quot;to-many\&quot;). Deletes item(s) from tracks
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
     * @param id User id
     * @param userCollectionTracksRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/tracks", hasBody = true)
    suspend fun userCollectionsIdRelationshipsTracksDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionTracksRelationshipRemoveOperationPayload:
            UserCollectionTracksRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "tracks.addedAt") TracksAddedAtAsc("tracks.addedAt"),
        @SerialName(value = "-tracks.addedAt") TracksAddedAtDesc("-tracks.addedAt"),
        @SerialName(value = "tracks.albums.title") TracksAlbumsTitleAsc("tracks.albums.title"),
        @SerialName(value = "-tracks.albums.title") TracksAlbumsTitleDesc("-tracks.albums.title"),
        @SerialName(value = "tracks.artists.name") TracksArtistsNameAsc("tracks.artists.name"),
        @SerialName(value = "-tracks.artists.name") TracksArtistsNameDesc("-tracks.artists.name"),
        @SerialName(value = "tracks.duration") TracksDurationAsc("tracks.duration"),
        @SerialName(value = "-tracks.duration") TracksDurationDesc("-tracks.duration"),
        @SerialName(value = "tracks.title") TracksTitleAsc("tracks.title"),
        @SerialName(value = "-tracks.title") TracksTitleDesc("-tracks.title"),
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
     * @param id User id
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: tracks (optional)
     * @return [UserCollectionsTracksMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/tracks")
    suspend fun userCollectionsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String = "en-US",
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsTracksMultiRelationshipDataDocument>

    /**
     * Add to tracks relationship (\&quot;to-many\&quot;). Adds item(s) to tracks relationship.
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionTracksRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/tracks")
    suspend fun userCollectionsIdRelationshipsTracksPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionTracksRelationshipAddOperationPayload:
            UserCollectionTracksRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

    /**
     * Delete from videos relationship (\&quot;to-many\&quot;). Deletes item(s) from videos
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
     * @param id User id
     * @param userCollectionVideosRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "userCollections/{id}/relationships/videos", hasBody = true)
    suspend fun userCollectionsIdRelationshipsVideosDelete(
        @Path("id") id: kotlin.String,
        @Body
        userCollectionVideosRelationshipRemoveOperationPayload:
            UserCollectionVideosRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter sort */
    enum class SortUserCollectionsIdRelationshipsVideosGet(val value: kotlin.String) {
        @SerialName(value = "videos.addedAt") VideosAddedAtAsc("videos.addedAt"),
        @SerialName(value = "-videos.addedAt") VideosAddedAtDesc("-videos.addedAt"),
        @SerialName(value = "videos.artists.name") VideosArtistsNameAsc("videos.artists.name"),
        @SerialName(value = "-videos.artists.name") VideosArtistsNameDesc("-videos.artists.name"),
        @SerialName(value = "videos.duration") VideosDurationAsc("videos.duration"),
        @SerialName(value = "-videos.duration") VideosDurationDesc("-videos.duration"),
        @SerialName(value = "videos.title") VideosTitleAsc("videos.title"),
        @SerialName(value = "-videos.title") VideosTitleDesc("-videos.title"),
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
     * @param id User id
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are
     *   sorted ascending. (optional)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: videos (optional)
     * @return [UserCollectionsVideosMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/videos")
    suspend fun userCollectionsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String = "en-US",
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserCollectionsVideosMultiRelationshipDataDocument>

    /**
     * Add to videos relationship (\&quot;to-many\&quot;). Adds item(s) to videos relationship.
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
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param userCollectionVideosRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/videos")
    suspend fun userCollectionsIdRelationshipsVideosPost(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Body
        userCollectionVideosRelationshipAddOperationPayload:
            UserCollectionVideosRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>
}
