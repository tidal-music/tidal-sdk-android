package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ErrorsDocument
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

interface UserCollections {
    /**
     * Get single userCollection.
     * Retrieves single userCollection by id.
     * Responses:
     *  - 200: Successful response
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param locale BCP 47 locale (default to "en-US")
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, artists, owners, playlists, tracks, videos (optional)
     * @return [UserCollectionsSingleResourceDataDocument]
     */
    @GET("userCollections/{id}")
    suspend fun userCollectionsIdGet(@Path("id") id: kotlin.String, @Query("locale") locale: kotlin.String = "en-US", @Query("countryCode") countryCode: kotlin.String = "US", @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserCollectionsSingleResourceDataDocument>

    /**
     * Delete from albums relationship (\&quot;to-many\&quot;).
     * Deletes item(s) from albums relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param userCollectionAlbumsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsDelete(@Path("id") id: kotlin.String, @Body userCollectionAlbumsRelationshipRemoveOperationPayload: UserCollectionAlbumsRelationshipRemoveOperationPayload? = null): Response<Unit>


    /**
    * enum for parameter sort
    */
    enum class SortUserCollectionsIdRelationshipsAlbumsGet(val value: kotlin.String) {
        @SerialName(value = "albums.addedAt") albums.addedAtAsc("albums.addedAt"),
        @SerialName(value = "-albums.addedAt") albums.addedAtDesc("-albums.addedAt"),
        @SerialName(value = "albums.artists.name") albums.artists.nameAsc("albums.artists.name"),
        @SerialName(value = "-albums.artists.name") albums.artists.nameDesc("-albums.artists.name"),
        @SerialName(value = "albums.releaseDate") albums.releaseDateAsc("albums.releaseDate"),
        @SerialName(value = "-albums.releaseDate") albums.releaseDateDesc("-albums.releaseDate"),
        @SerialName(value = "albums.title") albums.titleAsc("albums.title"),
        @SerialName(value = "-albums.title") albums.titleDesc("-albums.title")
    }

    /**
     * Get albums relationship (\&quot;to-many\&quot;).
     * Retrieves albums relationship.
     * Responses:
     *  - 200: Successful response
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @return [UserCollectionsAlbumsMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Query("locale") locale: kotlin.String = "en-US", @Query("page[cursor]") pageCursor: kotlin.String? = null, @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserCollectionsAlbumsMultiRelationshipDataDocument>

    /**
     * Add to albums relationship (\&quot;to-many\&quot;).
     * Adds item(s) to albums relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param userCollectionAlbumsRelationshipAddOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/albums")
    suspend fun userCollectionsIdRelationshipsAlbumsPost(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Body userCollectionAlbumsRelationshipAddOperationPayload: UserCollectionAlbumsRelationshipAddOperationPayload? = null): Response<Unit>

    /**
     * Delete from artists relationship (\&quot;to-many\&quot;).
     * Deletes item(s) from artists relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param userCollectionArtistsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsDelete(@Path("id") id: kotlin.String, @Body userCollectionArtistsRelationshipRemoveOperationPayload: UserCollectionArtistsRelationshipRemoveOperationPayload? = null): Response<Unit>


    /**
    * enum for parameter sort
    */
    enum class SortUserCollectionsIdRelationshipsArtistsGet(val value: kotlin.String) {
        @SerialName(value = "artists.addedAt") artists.addedAtAsc("artists.addedAt"),
        @SerialName(value = "-artists.addedAt") artists.addedAtDesc("-artists.addedAt"),
        @SerialName(value = "artists.name") artists.nameAsc("artists.name"),
        @SerialName(value = "-artists.name") artists.nameDesc("-artists.name")
    }

    /**
     * Get artists relationship (\&quot;to-many\&quot;).
     * Retrieves artists relationship.
     * Responses:
     *  - 200: Successful response
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @return [UserCollectionsArtistsMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Query("locale") locale: kotlin.String = "en-US", @Query("page[cursor]") pageCursor: kotlin.String? = null, @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserCollectionsArtistsMultiRelationshipDataDocument>

    /**
     * Add to artists relationship (\&quot;to-many\&quot;).
     * Adds item(s) to artists relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param userCollectionArtistsRelationshipAddOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/artists")
    suspend fun userCollectionsIdRelationshipsArtistsPost(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Body userCollectionArtistsRelationshipAddOperationPayload: UserCollectionArtistsRelationshipAddOperationPayload? = null): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;).
     * Retrieves owners relationship.
     * Responses:
     *  - 200: Successful response
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param include Allows the client to customize which related resources should be returned. Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserCollectionsMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/owners")
    suspend fun userCollectionsIdRelationshipsOwnersGet(@Path("id") id: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<UserCollectionsMultiRelationshipDataDocument>

    /**
     * Delete from playlists relationship (\&quot;to-many\&quot;).
     * Deletes item(s) from playlists relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsDelete(@Path("id") id: kotlin.String, @Body userCollectionPlaylistsRelationshipRemoveOperationPayload: UserCollectionPlaylistsRelationshipRemoveOperationPayload? = null): Response<Unit>


    /**
    * enum for parameter sort
    */
    enum class SortUserCollectionsIdRelationshipsPlaylistsGet(val value: kotlin.String) {
        @SerialName(value = "playlists.addedAt") playlists.addedAtAsc("playlists.addedAt"),
        @SerialName(value = "-playlists.addedAt") playlists.addedAtDesc("-playlists.addedAt"),
        @SerialName(value = "playlists.lastUpdatedAt") playlists.lastUpdatedAtAsc("playlists.lastUpdatedAt"),
        @SerialName(value = "-playlists.lastUpdatedAt") playlists.lastUpdatedAtDesc("-playlists.lastUpdatedAt"),
        @SerialName(value = "playlists.name") playlists.nameAsc("playlists.name"),
        @SerialName(value = "-playlists.name") playlists.nameDesc("-playlists.name")
    }

    /**
     * Get playlists relationship (\&quot;to-many\&quot;).
     * Retrieves playlists relationship.
     * Responses:
     *  - 200: Successful response
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned. Available options: playlists (optional)
     * @return [UserCollectionsPlaylistsMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsGet(@Path("id") id: kotlin.String, @Query("page[cursor]") pageCursor: kotlin.String? = null, @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserCollectionsPlaylistsMultiRelationshipDataDocument>

    /**
     * Add to playlists relationship (\&quot;to-many\&quot;).
     * Adds item(s) to playlists relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param userCollectionPlaylistsRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/playlists")
    suspend fun userCollectionsIdRelationshipsPlaylistsPost(@Path("id") id: kotlin.String, @Body userCollectionPlaylistsRelationshipRemoveOperationPayload: UserCollectionPlaylistsRelationshipRemoveOperationPayload? = null): Response<Unit>

    /**
     * Delete from tracks relationship (\&quot;to-many\&quot;).
     * Deletes item(s) from tracks relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param userCollectionTracksRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/tracks")
    suspend fun userCollectionsIdRelationshipsTracksDelete(@Path("id") id: kotlin.String, @Body userCollectionTracksRelationshipRemoveOperationPayload: UserCollectionTracksRelationshipRemoveOperationPayload? = null): Response<Unit>


    /**
    * enum for parameter sort
    */
    enum class SortUserCollectionsIdRelationshipsTracksGet(val value: kotlin.String) {
        @SerialName(value = "tracks.addedAt") tracks.addedAtAsc("tracks.addedAt"),
        @SerialName(value = "-tracks.addedAt") tracks.addedAtDesc("-tracks.addedAt"),
        @SerialName(value = "tracks.albums.title") tracks.albums.titleAsc("tracks.albums.title"),
        @SerialName(value = "-tracks.albums.title") tracks.albums.titleDesc("-tracks.albums.title"),
        @SerialName(value = "tracks.artists.name") tracks.artists.nameAsc("tracks.artists.name"),
        @SerialName(value = "-tracks.artists.name") tracks.artists.nameDesc("-tracks.artists.name"),
        @SerialName(value = "tracks.duration") tracks.durationAsc("tracks.duration"),
        @SerialName(value = "-tracks.duration") tracks.durationDesc("-tracks.duration"),
        @SerialName(value = "tracks.title") tracks.titleAsc("tracks.title"),
        @SerialName(value = "-tracks.title") tracks.titleDesc("-tracks.title")
    }

    /**
     * Get tracks relationship (\&quot;to-many\&quot;).
     * Retrieves tracks relationship.
     * Responses:
     *  - 200: Successful response
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned. Available options: tracks (optional)
     * @return [UserCollectionsTracksMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/tracks")
    suspend fun userCollectionsIdRelationshipsTracksGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Query("locale") locale: kotlin.String = "en-US", @Query("page[cursor]") pageCursor: kotlin.String? = null, @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserCollectionsTracksMultiRelationshipDataDocument>

    /**
     * Add to tracks relationship (\&quot;to-many\&quot;).
     * Adds item(s) to tracks relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param userCollectionTracksRelationshipAddOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/tracks")
    suspend fun userCollectionsIdRelationshipsTracksPost(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Body userCollectionTracksRelationshipAddOperationPayload: UserCollectionTracksRelationshipAddOperationPayload? = null): Response<Unit>

    /**
     * Delete from videos relationship (\&quot;to-many\&quot;).
     * Deletes item(s) from videos relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param userCollectionVideosRelationshipRemoveOperationPayload  (optional)
     * @return [Unit]
     */
    @DELETE("userCollections/{id}/relationships/videos")
    suspend fun userCollectionsIdRelationshipsVideosDelete(@Path("id") id: kotlin.String, @Body userCollectionVideosRelationshipRemoveOperationPayload: UserCollectionVideosRelationshipRemoveOperationPayload? = null): Response<Unit>


    /**
    * enum for parameter sort
    */
    enum class SortUserCollectionsIdRelationshipsVideosGet(val value: kotlin.String) {
        @SerialName(value = "videos.addedAt") videos.addedAtAsc("videos.addedAt"),
        @SerialName(value = "-videos.addedAt") videos.addedAtDesc("-videos.addedAt"),
        @SerialName(value = "videos.artists.name") videos.artists.nameAsc("videos.artists.name"),
        @SerialName(value = "-videos.artists.name") videos.artists.nameDesc("-videos.artists.name"),
        @SerialName(value = "videos.duration") videos.durationAsc("videos.duration"),
        @SerialName(value = "-videos.duration") videos.durationDesc("-videos.duration"),
        @SerialName(value = "videos.title") videos.titleAsc("videos.title"),
        @SerialName(value = "-videos.title") videos.titleDesc("-videos.title")
    }

    /**
     * Get videos relationship (\&quot;to-many\&quot;).
     * Retrieves videos relationship.
     * Responses:
     *  - 200: Successful response
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param locale BCP 47 locale (default to "en-US")
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @param sort Values prefixed with \&quot;-\&quot; are sorted descending; values without it are sorted ascending. (optional)
     * @param include Allows the client to customize which related resources should be returned. Available options: videos (optional)
     * @return [UserCollectionsVideosMultiRelationshipDataDocument]
     */
    @GET("userCollections/{id}/relationships/videos")
    suspend fun userCollectionsIdRelationshipsVideosGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Query("locale") locale: kotlin.String = "en-US", @Query("page[cursor]") pageCursor: kotlin.String? = null, @Query("sort") sort: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UserCollectionsVideosMultiRelationshipDataDocument>

    /**
     * Add to videos relationship (\&quot;to-many\&quot;).
     * Adds item(s) to videos relationship.
     * Responses:
     *  - 400: Bad request - The request could not be understood by the server due to malformed syntax or invalid parameters
     *  - 404: Not found - The requested resource could not be found
     *  - 405: Method not allowed - The request method is not allowed for the requested resource
     *  - 406: Not acceptable - The requested resource is capable of generating only content not acceptable according to the Accept headers sent in the request
     *  - 415: Unsupported media type - The request entity has a media type which the server or resource does not support
     *  - 429: Too many requests - The user has sent too many requests in a given amount of time
     *  - 500: Internal server error - The server encountered an unexpected condition that prevented it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param userCollectionVideosRelationshipAddOperationPayload  (optional)
     * @return [Unit]
     */
    @POST("userCollections/{id}/relationships/videos")
    suspend fun userCollectionsIdRelationshipsVideosPost(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String = "US", @Body userCollectionVideosRelationshipAddOperationPayload: UserCollectionVideosRelationshipAddOperationPayload? = null): Response<Unit>

}
