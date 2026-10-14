package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesChangeEventStreamSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesCurrentRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesCurrentSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesCurrentUpdateSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesFutureAddMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesFutureMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesFutureRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesFutureRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesFutureRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesFutureUpdateMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesPastMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesUpdateSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface PlayQueues {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkagePlayQueuesGet(val value: kotlin.String) {
        @SerialName(value = "changeEventStream") changeEventStream("changeEventStream"),
        @SerialName(value = "current") current("current"),
        @SerialName(value = "future") future("future"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "past") past("past"),
    }

    /**
     * GET playQueues Get multiple playQueues. Retrieves multiple playQueues by available filters,
     * or without if applicable. Responses:
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
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: changeEventStream,
     *   current, future, owners, past (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: current (optional)
     * @return [PlayQueuesMultiResourceDataDocument]
     */
    @GET("playQueues")
    suspend fun playQueuesGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlayQueuesMultiResourceDataDocument>

    /**
     * DELETE playQueues/{id} Delete single playQueue. Deletes existing playQueue. Responses:
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
     * @param id Play queue id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("playQueues/{id}")
    suspend fun playQueuesIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkagePlayQueuesIdGet(val value: kotlin.String) {
        @SerialName(value = "changeEventStream") changeEventStream("changeEventStream"),
        @SerialName(value = "current") current("current"),
        @SerialName(value = "future") future("future"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "past") past("past"),
    }

    /**
     * GET playQueues/{id} Get single playQueue. Retrieves single playQueue by id. Responses:
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
     * @param id Play queue id
     * @param include Include related resources. Available relationships: changeEventStream,
     *   current, future, owners, past (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: current (optional)
     * @return [PlayQueuesSingleResourceDataDocument]
     */
    @GET("playQueues/{id}")
    suspend fun playQueuesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlayQueuesSingleResourceDataDocument>

    /**
     * PATCH playQueues/{id} Update single playQueue. Updates existing playQueue. Responses:
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
     * @param id Play queue id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playQueuesUpdateOperationPayload (optional)
     * @return [PlayQueuesUpdateSingleResourceDataDocument]
     */
    @PATCH("playQueues/{id}")
    suspend fun playQueuesIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body playQueuesUpdateOperationPayload: PlayQueuesUpdateOperationPayload? = null,
    ): Response<PlayQueuesUpdateSingleResourceDataDocument>

    /**
     * GET playQueues/{id}/relationships/changeEventStream Get changeEventStream relationship
     * (\&quot;to-one\&quot;). Retrieves changeEventStream relationship. Responses:
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
     * @param include Include related resources. Available relationships: changeEventStream
     *   (optional)
     * @return [PlayQueuesChangeEventStreamSingleRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/changeEventStream")
    suspend fun playQueuesIdRelationshipsChangeEventStreamGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlayQueuesChangeEventStreamSingleRelationshipDataDocument>

    /**
     * GET playQueues/{id}/relationships/current Get current relationship (\&quot;to-one\&quot;).
     * Retrieves current relationship. Responses:
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
     * @param id Play queue id
     * @param include Include related resources. Available relationships: current (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: current (optional)
     * @return [PlayQueuesCurrentSingleRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/current")
    suspend fun playQueuesIdRelationshipsCurrentGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlayQueuesCurrentSingleRelationshipDataDocument>

    /**
     * PATCH playQueues/{id}/relationships/current Update current relationship
     * (\&quot;to-one\&quot;). Updates current relationship. Responses:
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
     * @param id Play queue id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playQueuesCurrentRelationshipUpdateOperationPayload (optional)
     * @return [PlayQueuesCurrentUpdateSingleRelationshipDataDocument]
     */
    @PATCH("playQueues/{id}/relationships/current")
    suspend fun playQueuesIdRelationshipsCurrentPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playQueuesCurrentRelationshipUpdateOperationPayload:
            PlayQueuesCurrentRelationshipUpdateOperationPayload? =
            null,
    ): Response<PlayQueuesCurrentUpdateSingleRelationshipDataDocument>

    /**
     * DELETE playQueues/{id}/relationships/future Delete from future relationship
     * (\&quot;to-many\&quot;). Deletes item(s) from future relationship. Responses:
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
     * @param id Play queue id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playQueuesFutureRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(method = "DELETE", path = "playQueues/{id}/relationships/future", hasBody = true)
    suspend fun playQueuesIdRelationshipsFutureDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playQueuesFutureRelationshipRemoveOperationPayload:
            PlayQueuesFutureRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /**
     * GET playQueues/{id}/relationships/future Get future relationship (\&quot;to-many\&quot;).
     * Retrieves future relationship. Responses:
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
     * @param id Play queue id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: future (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: future (optional)
     * @return [PlayQueuesFutureMultiRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/future")
    suspend fun playQueuesIdRelationshipsFutureGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlayQueuesFutureMultiRelationshipDataDocument>

    /**
     * PATCH playQueues/{id}/relationships/future Update future relationship
     * (\&quot;to-many\&quot;). Returns empty data and meta.revision as an acknowledgement.
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
     * @param id Play queue id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playQueuesFutureRelationshipUpdateOperationPayload (optional)
     * @return [PlayQueuesFutureUpdateMultiRelationshipDataDocument]
     */
    @PATCH("playQueues/{id}/relationships/future")
    suspend fun playQueuesIdRelationshipsFuturePatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playQueuesFutureRelationshipUpdateOperationPayload:
            PlayQueuesFutureRelationshipUpdateOperationPayload? =
            null,
    ): Response<PlayQueuesFutureUpdateMultiRelationshipDataDocument>

    /**
     * POST playQueues/{id}/relationships/future Add to future relationship (\&quot;to-many\&quot;).
     * With meta.source, startIndex selects an entry in the first page&#39;s data before unsupported
     * types are skipped. Tracks and videos are added in page order, retaining duplicates; other
     * types are skipped without expansion. Reaching 1000 added items or 100 pages queues the
     * collected prefix. Invalid sources or indexes, a suffix with no playable items, repeated
     * pages, and read failures encountered before a cap leave the queue unchanged. Returns empty
     * data and meta.revision as an acknowledgement. Responses:
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
     * @param id Play queue id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param playQueuesFutureRelationshipAddOperationPayload (optional)
     * @return [PlayQueuesFutureAddMultiRelationshipDataDocument]
     */
    @POST("playQueues/{id}/relationships/future")
    suspend fun playQueuesIdRelationshipsFuturePost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        playQueuesFutureRelationshipAddOperationPayload:
            PlayQueuesFutureRelationshipAddOperationPayload? =
            null,
    ): Response<PlayQueuesFutureAddMultiRelationshipDataDocument>

    /**
     * GET playQueues/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
     * Retrieves owners relationship. Responses:
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
     * @param id Play queue id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [PlayQueuesOwnersMultiRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/owners")
    suspend fun playQueuesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlayQueuesOwnersMultiRelationshipDataDocument>

    /**
     * GET playQueues/{id}/relationships/past Get past relationship (\&quot;to-many\&quot;).
     * Retrieves past relationship. Responses:
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
     * @param id Play queue id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: past (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: past (optional)
     * @return [PlayQueuesPastMultiRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/past")
    suspend fun playQueuesIdRelationshipsPastGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<PlayQueuesPastMultiRelationshipDataDocument>

    /**
     * POST playQueues Create single playQueue. Creates a new playQueue. Responses:
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
     * @param playQueuesCreateOperationPayload (optional)
     * @return [PlayQueuesCreateSingleResourceDataDocument]
     */
    @POST("playQueues")
    suspend fun playQueuesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body playQueuesCreateOperationPayload: PlayQueuesCreateOperationPayload? = null,
    ): Response<PlayQueuesCreateSingleResourceDataDocument>
}
