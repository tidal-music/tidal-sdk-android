package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.LyricsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.LyricsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsUpdateOperationPayload
import retrofit2.Response
import retrofit2.http.*

interface Lyrics {
    /**
     * Get multiple lyrics. Retrieves multiple lyrics by available filters, or without if
     * applicable. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, track (optional)
     * @param filterId Lyrics Id (optional)
     * @return [LyricsMultiResourceDataDocument]
     */
    @GET("lyrics")
    suspend fun lyricsGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<LyricsMultiResourceDataDocument>

    /**
     * Delete single lyric. Deletes existing lyric. Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Lyrics Id
     * @return [Unit]
     */
    @DELETE("lyrics/{id}") suspend fun lyricsIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single lyric. Retrieves single lyric by id. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Lyrics Id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, track (optional)
     * @return [LyricsSingleResourceDataDocument]
     */
    @GET("lyrics/{id}")
    suspend fun lyricsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<LyricsSingleResourceDataDocument>

    /**
     * Update single lyric. Updates existing lyric. Responses:
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Lyrics Id
     * @param lyricsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("lyrics/{id}")
    suspend fun lyricsIdPatch(
        @Path("id") id: kotlin.String,
        @Body lyricsUpdateOperationPayload: LyricsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Lyrics Id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [LyricsMultiRelationshipDataDocument]
     */
    @GET("lyrics/{id}/relationships/owners")
    suspend fun lyricsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String = "US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<LyricsMultiRelationshipDataDocument>

    /**
     * Get track relationship (\&quot;to-one\&quot;). Retrieves track relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id Lyrics Id
     * @param countryCode ISO 3166-1 alpha-2 country code (default to "US")
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: track (optional)
     * @return [LyricsSingleRelationshipDataDocument]
     */
    @GET("lyrics/{id}/relationships/track")
    suspend fun lyricsIdRelationshipsTrackGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String = "US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<LyricsSingleRelationshipDataDocument>

    /**
     * Create single lyric. Creates a new lyric. Responses:
     * - 201: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param lyricsCreateOperationPayload (optional)
     * @return [LyricsSingleResourceDataDocument]
     */
    @POST("lyrics")
    suspend fun lyricsPost(
        @Body lyricsCreateOperationPayload: LyricsCreateOperationPayload? = null
    ): Response<LyricsSingleResourceDataDocument>
}
