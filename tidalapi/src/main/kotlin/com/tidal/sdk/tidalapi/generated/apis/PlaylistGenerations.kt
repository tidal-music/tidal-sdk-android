package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsPlaylistSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlaylistGenerationsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface PlaylistGenerations {
    /**
     * Get single playlistGeneration. Retrieves single playlistGeneration by id. Responses:
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
     * Create single playlistGeneration. Creates a new playlistGeneration. Responses:
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
