package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.LyricsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.LyricsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsUpdateOperationPayload
import retrofit2.Response
import retrofit2.http.*

interface Lyrics {
    /**
     * Delete single lyric. Deletes existing lyric. Responses:
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
     * @param id Lyrics Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [Unit]
     */
    @DELETE("lyrics/{id}")
    suspend fun lyricsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<Unit>

    /**
     * Get single lyric. Retrieves single lyric by id. Responses:
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
     * @param id Lyrics Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param lyricsUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("lyrics/{id}")
    suspend fun lyricsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body lyricsUpdateOperationPayload: LyricsUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
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
     * @param lyricsCreateOperationPayload (optional)
     * @return [LyricsSingleResourceDataDocument]
     */
    @POST("lyrics")
    suspend fun lyricsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body lyricsCreateOperationPayload: LyricsCreateOperationPayload? = null,
    ): Response<LyricsSingleResourceDataDocument>
}
