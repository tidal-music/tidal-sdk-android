package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.LyricsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.LyricsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsTrackSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.LyricsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import retrofit2.Response
import retrofit2.http.*

interface Lyrics {
    /**
     * Delete single lyric. Deletes existing lyric. Responses:
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
     * @param id Lyrics Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("lyrics/{id}")
    suspend fun lyricsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

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
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: track (optional)
     * @return [LyricsSingleResourceDataDocument]
     */
    @GET("lyrics/{id}")
    suspend fun lyricsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<LyricsSingleResourceDataDocument>

    /**
     * Update single lyric. Updates existing lyric. Responses:
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
     * @param id Lyrics Id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param lyricsUpdateOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @PATCH("lyrics/{id}")
    suspend fun lyricsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body lyricsUpdateOperationPayload: LyricsUpdateOperationPayload? = null,
    ): Response<MutationResponseDocument>

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
     * @return [LyricsOwnersMultiRelationshipDataDocument]
     */
    @GET("lyrics/{id}/relationships/owners")
    suspend fun lyricsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<LyricsOwnersMultiRelationshipDataDocument>

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
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: track (optional)
     * @return [LyricsTrackSingleRelationshipDataDocument]
     */
    @GET("lyrics/{id}/relationships/track")
    suspend fun lyricsIdRelationshipsTrackGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<LyricsTrackSingleRelationshipDataDocument>

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
     * @return [LyricsCreateSingleResourceDataDocument]
     */
    @POST("lyrics")
    suspend fun lyricsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body lyricsCreateOperationPayload: LyricsCreateOperationPayload? = null,
    ): Response<LyricsCreateSingleResourceDataDocument>
}
