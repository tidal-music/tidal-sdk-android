package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationSchedulesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationSchedulesCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationSchedulesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationSchedulesPlaylistSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationSchedulesSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationSchedulesUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationSchedulesUpdateSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface PlaylistGenerationSchedules {
    /**
     * Get multiple playlistGenerationSchedules. Retrieves multiple playlistGenerationSchedules by
     * available filters, or without if applicable. Responses:
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
     * @param filterPlaylistId Playlist id (e.g. &#x60;550e8400-e29b-41d4-a716-446655440000&#x60;)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: playlist.items (optional)
     * @return [PlaylistGenerationSchedulesMultiResourceDataDocument]
     */
    @GET("playlistGenerationSchedules")
    suspend fun playlistGenerationSchedulesGet(
        @Query("filter[playlist.id]")
        filterPlaylistId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationSchedulesMultiResourceDataDocument>

    /**
     * Delete single playlistGenerationSchedule. Deletes existing playlistGenerationSchedule.
     * Responses:
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
     * @param id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("playlistGenerationSchedules/{id}")
    suspend fun playlistGenerationSchedulesIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /**
     * Get single playlistGenerationSchedule. Retrieves single playlistGenerationSchedule by id.
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
     * @param id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: playlist.items (optional)
     * @return [PlaylistGenerationSchedulesSingleResourceDataDocument]
     */
    @GET("playlistGenerationSchedules/{id}")
    suspend fun playlistGenerationSchedulesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationSchedulesSingleResourceDataDocument>

    /**
     * Update single playlistGenerationSchedule. Updates existing playlistGenerationSchedule.
     * Responses:
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
     * @param id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playlistGenerationSchedulesUpdateOperationPayload (optional)
     * @return [PlaylistGenerationSchedulesUpdateSingleResourceDataDocument]
     */
    @PATCH("playlistGenerationSchedules/{id}")
    suspend fun playlistGenerationSchedulesIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playlistGenerationSchedulesUpdateOperationPayload:
            PlaylistGenerationSchedulesUpdateOperationPayload? =
            null,
    ): Response<PlaylistGenerationSchedulesUpdateSingleResourceDataDocument>

    /**
     * Get playlist relationship (\&quot;to-one\&quot;). Retrieves playlist relationship. Responses:
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
     * @param id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: playlist.items (optional)
     * @return [PlaylistGenerationSchedulesPlaylistSingleRelationshipDataDocument]
     */
    @GET("playlistGenerationSchedules/{id}/relationships/playlist")
    suspend fun playlistGenerationSchedulesIdRelationshipsPlaylistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationSchedulesPlaylistSingleRelationshipDataDocument>

    /**
     * Create single playlistGenerationSchedule. Creates a new playlistGenerationSchedule.
     * Responses:
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
     * @param playlistGenerationSchedulesCreateOperationPayload (optional)
     * @return [PlaylistGenerationSchedulesCreateSingleResourceDataDocument]
     */
    @POST("playlistGenerationSchedules")
    suspend fun playlistGenerationSchedulesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playlistGenerationSchedulesCreateOperationPayload:
            PlaylistGenerationSchedulesCreateOperationPayload? =
            null,
    ): Response<PlaylistGenerationSchedulesCreateSingleResourceDataDocument>
}
