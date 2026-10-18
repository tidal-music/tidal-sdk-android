package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.UserFeedsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserFeedsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserFeedsUpdateSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserFeeds {
    /**
     * GET userFeeds/{id} Get single userFeed. Retrieves single userFeed by id. Responses:
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
     * @param id Opaque identifier of a user&#39;s feed state. Use &#x60;me&#x60; for the
     *   authenticated user&#39;s resource
     * @return [UserFeedsSingleResourceDataDocument]
     */
    @GET("userFeeds/{id}")
    suspend fun userFeedsIdGet(
        @Path("id") id: kotlin.String
    ): Response<UserFeedsSingleResourceDataDocument>

    /**
     * PATCH userFeeds/{id} Update single userFeed. Advances the supplied seen positions and returns
     * the updated feed state. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Opaque identifier of a user&#39;s feed state. Use &#x60;me&#x60; for the
     *   authenticated user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param userFeedsUpdateOperationPayload (optional)
     * @return [UserFeedsUpdateSingleResourceDataDocument]
     */
    @PATCH("userFeeds/{id}")
    suspend fun userFeedsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body userFeedsUpdateOperationPayload: UserFeedsUpdateOperationPayload? = null,
    ): Response<UserFeedsUpdateSingleResourceDataDocument>
}
