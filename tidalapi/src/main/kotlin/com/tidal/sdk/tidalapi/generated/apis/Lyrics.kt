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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, track (optional)
     * @param filterId Lyrics Id (e.g. &#x60;nejMcAhh5N8S3EQ4LaqysVdI0cZZ&#x60;) (optional)
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Lyrics Id
     * @return [Unit]
     */
    @DELETE("lyrics/{id}") suspend fun lyricsIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single lyric. Retrieves single lyric by id. Responses:
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Lyrics Id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [LyricsMultiRelationshipDataDocument]
     */
    @GET("lyrics/{id}/relationships/owners")
    suspend fun lyricsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<LyricsMultiRelationshipDataDocument>

    /**
     * Get track relationship (\&quot;to-one\&quot;). Retrieves track relationship. Responses:
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
     * @param id Lyrics Id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: track (optional)
     * @return [LyricsSingleRelationshipDataDocument]
     */
    @GET("lyrics/{id}/relationships/track")
    suspend fun lyricsIdRelationshipsTrackGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<LyricsSingleRelationshipDataDocument>

    /**
     * Create single lyric. Creates a new lyric. Responses:
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
     * @param lyricsCreateOperationPayload (optional)
     * @return [LyricsSingleResourceDataDocument]
     */
    @POST("lyrics")
    suspend fun lyricsPost(
        @Body lyricsCreateOperationPayload: LyricsCreateOperationPayload? = null
    ): Response<LyricsSingleResourceDataDocument>
}
