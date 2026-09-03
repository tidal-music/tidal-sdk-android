package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesActivePlayerRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesActivePlayerSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesActivePlayerUpdateSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesAvailablePlayersAddMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesAvailablePlayersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesAvailablePlayersRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesAvailablePlayersRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesPlayQueueRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesPlayQueueSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesPlayQueueUpdateSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserPlaybackStatesUpdateSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserPlaybackStates {
    /**
     * Get single userPlaybackState. Retrieves single userPlaybackState by id. Responses:
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: activePlayer, availablePlayers, playQueue (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: activePlayer.offlineInventory (optional)
     * @return [UserPlaybackStatesSingleResourceDataDocument]
     */
    @GET("userPlaybackStates/{id}")
    suspend fun userPlaybackStatesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserPlaybackStatesSingleResourceDataDocument>

    /**
     * Update single userPlaybackState. Updates existing userPlaybackState. Responses:
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userPlaybackStatesUpdateOperationPayload (optional)
     * @return [UserPlaybackStatesUpdateSingleResourceDataDocument]
     */
    @PATCH("userPlaybackStates/{id}")
    suspend fun userPlaybackStatesIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userPlaybackStatesUpdateOperationPayload: UserPlaybackStatesUpdateOperationPayload? = null,
    ): Response<UserPlaybackStatesUpdateSingleResourceDataDocument>

    /**
     * Get activePlayer relationship (\&quot;to-one\&quot;). Retrieves activePlayer relationship.
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: activePlayer (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: activePlayer.offlineInventory (optional)
     * @return [UserPlaybackStatesActivePlayerSingleRelationshipDataDocument]
     */
    @GET("userPlaybackStates/{id}/relationships/activePlayer")
    suspend fun userPlaybackStatesIdRelationshipsActivePlayerGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserPlaybackStatesActivePlayerSingleRelationshipDataDocument>

    /**
     * Update activePlayer relationship (\&quot;to-one\&quot;). Updates activePlayer relationship.
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userPlaybackStatesActivePlayerRelationshipUpdateOperationPayload (optional)
     * @return [UserPlaybackStatesActivePlayerUpdateSingleRelationshipDataDocument]
     */
    @PATCH("userPlaybackStates/{id}/relationships/activePlayer")
    suspend fun userPlaybackStatesIdRelationshipsActivePlayerPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userPlaybackStatesActivePlayerRelationshipUpdateOperationPayload:
            UserPlaybackStatesActivePlayerRelationshipUpdateOperationPayload? =
            null,
    ): Response<UserPlaybackStatesActivePlayerUpdateSingleRelationshipDataDocument>

    /**
     * Delete from availablePlayers relationship (\&quot;to-many\&quot;). Deletes item(s) from
     * availablePlayers relationship. Responses:
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userPlaybackStatesAvailablePlayersRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(
        method = "DELETE",
        path = "userPlaybackStates/{id}/relationships/availablePlayers",
        hasBody = true,
    )
    suspend fun userPlaybackStatesIdRelationshipsAvailablePlayersDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userPlaybackStatesAvailablePlayersRelationshipRemoveOperationPayload:
            UserPlaybackStatesAvailablePlayersRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * Get availablePlayers relationship (\&quot;to-many\&quot;). Retrieves availablePlayers
     * relationship. Responses:
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: availablePlayers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: availablePlayers.offlineInventory (optional)
     * @return [UserPlaybackStatesAvailablePlayersMultiRelationshipDataDocument]
     */
    @GET("userPlaybackStates/{id}/relationships/availablePlayers")
    suspend fun userPlaybackStatesIdRelationshipsAvailablePlayersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserPlaybackStatesAvailablePlayersMultiRelationshipDataDocument>

    /**
     * Add to availablePlayers relationship (\&quot;to-many\&quot;). Adds item(s) to
     * availablePlayers relationship. Responses:
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userPlaybackStatesAvailablePlayersRelationshipAddOperationPayload (optional)
     * @return [UserPlaybackStatesAvailablePlayersAddMultiRelationshipDataDocument]
     */
    @POST("userPlaybackStates/{id}/relationships/availablePlayers")
    suspend fun userPlaybackStatesIdRelationshipsAvailablePlayersPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userPlaybackStatesAvailablePlayersRelationshipAddOperationPayload:
            UserPlaybackStatesAvailablePlayersRelationshipAddOperationPayload? =
            null,
    ): Response<UserPlaybackStatesAvailablePlayersAddMultiRelationshipDataDocument>

    /**
     * Get playQueue relationship (\&quot;to-one\&quot;). Retrieves playQueue relationship.
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: playQueue (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: playQueue.current (optional)
     * @return [UserPlaybackStatesPlayQueueSingleRelationshipDataDocument]
     */
    @GET("userPlaybackStates/{id}/relationships/playQueue")
    suspend fun userPlaybackStatesIdRelationshipsPlayQueueGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserPlaybackStatesPlayQueueSingleRelationshipDataDocument>

    /**
     * Update playQueue relationship (\&quot;to-one\&quot;). Updates playQueue relationship.
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
     * @param id User playback session id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param userPlaybackStatesPlayQueueRelationshipUpdateOperationPayload (optional)
     * @return [UserPlaybackStatesPlayQueueUpdateSingleRelationshipDataDocument]
     */
    @PATCH("userPlaybackStates/{id}/relationships/playQueue")
    suspend fun userPlaybackStatesIdRelationshipsPlayQueuePatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        userPlaybackStatesPlayQueueRelationshipUpdateOperationPayload:
            UserPlaybackStatesPlayQueueRelationshipUpdateOperationPayload? =
            null,
    ): Response<UserPlaybackStatesPlayQueueUpdateSingleRelationshipDataDocument>
}
