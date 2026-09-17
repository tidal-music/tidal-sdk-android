package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsBaseGenerationSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsPlaylistSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsTrackPreferencesAddMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsTrackPreferencesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsTrackPreferencesRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsTrackPreferencesRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsTrackPreferencesRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsTrackPreferencesUpdateMultiRelationshipDataDocument
import retrofit2.Response
import retrofit2.http.*

interface PlaylistGenerations {
    /**
     * GET playlistGenerations Get multiple playlistGenerations. Retrieves multiple
     * playlistGenerations by available filters, or without if applicable. Responses:
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
     *   Available options: baseGeneration, playlist, trackPreferences (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: baseGeneration.trackPreferences (optional)
     * @return [PlaylistGenerationsMultiResourceDataDocument]
     */
    @GET("playlistGenerations")
    suspend fun playlistGenerationsGet(
        @Query("filter[playlist.id]")
        filterPlaylistId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationsMultiResourceDataDocument>

    /**
     * GET playlistGenerations/{id} Get single playlistGeneration. Retrieves single
     * playlistGeneration by id. Responses:
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
     * @param id Playlist generation id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: baseGeneration, playlist, trackPreferences (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: baseGeneration.trackPreferences (optional)
     * @return [PlaylistGenerationsSingleResourceDataDocument]
     */
    @GET("playlistGenerations/{id}")
    suspend fun playlistGenerationsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationsSingleResourceDataDocument>

    /**
     * GET playlistGenerations/{id}/relationships/baseGeneration Get baseGeneration relationship
     * (\&quot;to-one\&quot;). Retrieves baseGeneration relationship. Responses:
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
     * @param id Playlist generation id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: baseGeneration (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: baseGeneration.trackPreferences (optional)
     * @return [PlaylistGenerationsBaseGenerationSingleRelationshipDataDocument]
     */
    @GET("playlistGenerations/{id}/relationships/baseGeneration")
    suspend fun playlistGenerationsIdRelationshipsBaseGenerationGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationsBaseGenerationSingleRelationshipDataDocument>

    /**
     * GET playlistGenerations/{id}/relationships/playlist Get playlist relationship
     * (\&quot;to-one\&quot;). Retrieves playlist relationship. Responses:
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
     * @param id Playlist generation id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playlist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: playlist.items (optional)
     * @return [PlaylistGenerationsPlaylistSingleRelationshipDataDocument]
     */
    @GET("playlistGenerations/{id}/relationships/playlist")
    suspend fun playlistGenerationsIdRelationshipsPlaylistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationsPlaylistSingleRelationshipDataDocument>

    /**
     * DELETE playlistGenerations/{id}/relationships/trackPreferences Delete from trackPreferences
     * relationship (\&quot;to-many\&quot;). Removes feedback for one track without removing the
     * track from the playlist. An absent entry is unchanged. Returns an acknowledgement; read the
     * relationship again for its current preference version. Requires the current
     * meta.preferenceVersion as meta.expectedPreferenceVersion; stale versions return 409.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: The playlist state conflicts with the request: the preference version or base
     *   generation is stale, or the track preference selected for update does not exist. Refresh
     *   the current generation and track preferences before retrying.; Request already in progress
     *   for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Playlist generation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playlistGenerationsTrackPreferencesRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(
        method = "DELETE",
        path = "playlistGenerations/{id}/relationships/trackPreferences",
        hasBody = true,
    )
    suspend fun playlistGenerationsIdRelationshipsTrackPreferencesDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playlistGenerationsTrackPreferencesRelationshipRemoveOperationPayload:
            PlaylistGenerationsTrackPreferencesRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * GET playlistGenerations/{id}/relationships/trackPreferences Get trackPreferences relationship
     * (\&quot;to-many\&quot;). Retrieves trackPreferences relationship. Responses:
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
     * @param id Playlist generation id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: trackPreferences (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: trackPreferences (optional)
     * @return [PlaylistGenerationsTrackPreferencesMultiRelationshipDataDocument]
     */
    @GET("playlistGenerations/{id}/relationships/trackPreferences")
    suspend fun playlistGenerationsIdRelationshipsTrackPreferencesGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlaylistGenerationsTrackPreferencesMultiRelationshipDataDocument>

    /**
     * PATCH playlistGenerations/{id}/relationships/trackPreferences Update trackPreferences
     * relationship (\&quot;to-many\&quot;). Updates feedback for one existing track preference,
     * leaving other entries unchanged. A missing entry returns 409. Returns the complete preference
     * snapshot. Requires the current meta.preferenceVersion as meta.expectedPreferenceVersion;
     * stale versions return 409. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: The playlist state conflicts with the request: the preference version or base
     *   generation is stale, or the track preference selected for update does not exist. Refresh
     *   the current generation and track preferences before retrying.; Request already in progress
     *   for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Playlist generation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playlistGenerationsTrackPreferencesRelationshipUpdateOperationPayload (optional)
     * @return [PlaylistGenerationsTrackPreferencesUpdateMultiRelationshipDataDocument]
     */
    @PATCH("playlistGenerations/{id}/relationships/trackPreferences")
    suspend fun playlistGenerationsIdRelationshipsTrackPreferencesPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playlistGenerationsTrackPreferencesRelationshipUpdateOperationPayload:
            PlaylistGenerationsTrackPreferencesRelationshipUpdateOperationPayload? =
            null,
    ): Response<PlaylistGenerationsTrackPreferencesUpdateMultiRelationshipDataDocument>

    /**
     * POST playlistGenerations/{id}/relationships/trackPreferences Add to trackPreferences
     * relationship (\&quot;to-many\&quot;). Adds feedback for one track. An existing entry is
     * unchanged, including its preference. Returns the complete preference snapshot. All writes
     * require the current meta.preferenceVersion as meta.expectedPreferenceVersion; stale versions
     * return 409. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: The playlist state conflicts with the request: the preference version or base
     *   generation is stale, or the track preference selected for update does not exist. Refresh
     *   the current generation and track preferences before retrying.; Request already in progress
     *   for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Playlist generation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playlistGenerationsTrackPreferencesRelationshipAddOperationPayload (optional)
     * @return [PlaylistGenerationsTrackPreferencesAddMultiRelationshipDataDocument]
     */
    @POST("playlistGenerations/{id}/relationships/trackPreferences")
    suspend fun playlistGenerationsIdRelationshipsTrackPreferencesPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playlistGenerationsTrackPreferencesRelationshipAddOperationPayload:
            PlaylistGenerationsTrackPreferencesRelationshipAddOperationPayload? =
            null,
    ): Response<PlaylistGenerationsTrackPreferencesAddMultiRelationshipDataDocument>

    /**
     * POST playlistGenerations Create single playlistGeneration. Creates a new playlistGeneration.
     * Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: The playlist state conflicts with the request: the preference version or base
     *   generation is stale, or the track preference selected for update does not exist. Refresh
     *   the current generation and track preferences before retrying.; Request already in progress
     *   for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused with a different payload
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playlistGenerationsCreateOperationPayload (optional)
     * @return [PlaylistGenerationsCreateSingleResourceDataDocument]
     */
    @POST("playlistGenerations")
    suspend fun playlistGenerationsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playlistGenerationsCreateOperationPayload: PlaylistGenerationsCreateOperationPayload? = null,
    ): Response<PlaylistGenerationsCreateSingleResourceDataDocument>
}
