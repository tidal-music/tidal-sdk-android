package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.PlayQueueAddFutureOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueueCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueueRemoveFutureOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueueUpdateCurrentOperationsPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueueUpdateFutureOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueueUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesFutureMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesPastMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.PlayQueuesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface PlayQueues {
    /**
     * Get multiple playQueues. Retrieves multiple playQueues by available filters, or without if
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
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: current, future, owners, past (optional)
     * @param filterOwnersId User id (optional)
     * @return [PlayQueuesMultiResourceDataDocument]
     */
    @GET("playQueues")
    suspend fun playQueuesGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlayQueuesMultiResourceDataDocument>

    /**
     * Delete single playQueue. Deletes existing playQueue. Responses:
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
     * @param id Play queue id
     * @return [Unit]
     */
    @DELETE("playQueues/{id}")
    suspend fun playQueuesIdDelete(@Path("id") id: kotlin.String): Response<Unit>

    /**
     * Get single playQueue. Retrieves single playQueue by id. Responses:
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
     * @param id Play queue id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: current, future, owners, past (optional)
     * @return [PlayQueuesSingleResourceDataDocument]
     */
    @GET("playQueues/{id}")
    suspend fun playQueuesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlayQueuesSingleResourceDataDocument>

    /**
     * Update single playQueue. Updates existing playQueue. Responses:
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
     * @param id Play queue id
     * @param playQueueUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("playQueues/{id}")
    suspend fun playQueuesIdPatch(
        @Path("id") id: kotlin.String,
        @Body playQueueUpdateOperationPayload: PlayQueueUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get current relationship (\&quot;to-one\&quot;). Retrieves current relationship. Responses:
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
     * @param id Play queue id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: current (optional)
     * @return [PlayQueuesSingleRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/current")
    suspend fun playQueuesIdRelationshipsCurrentGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlayQueuesSingleRelationshipDataDocument>

    /**
     * Update current relationship (\&quot;to-one\&quot;). Updates current relationship. Responses:
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
     * @param id Play queue id
     * @param playQueueUpdateCurrentOperationsPayload (optional)
     * @return [Unit]
     */
    @PATCH("playQueues/{id}/relationships/current")
    suspend fun playQueuesIdRelationshipsCurrentPatch(
        @Path("id") id: kotlin.String,
        @Body
        playQueueUpdateCurrentOperationsPayload: PlayQueueUpdateCurrentOperationsPayload? = null,
    ): Response<Unit>

    /**
     * Delete from future relationship (\&quot;to-many\&quot;). Deletes item(s) from future
     * relationship. Responses:
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
     * @param id Play queue id
     * @param playQueueRemoveFutureOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(method = "DELETE", path = "playQueues/{id}/relationships/future", hasBody = true)
    suspend fun playQueuesIdRelationshipsFutureDelete(
        @Path("id") id: kotlin.String,
        @Body playQueueRemoveFutureOperationPayload: PlayQueueRemoveFutureOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get future relationship (\&quot;to-many\&quot;). Retrieves future relationship. Responses:
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
     * @param id Play queue id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: future (optional)
     * @return [PlayQueuesFutureMultiRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/future")
    suspend fun playQueuesIdRelationshipsFutureGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlayQueuesFutureMultiRelationshipDataDocument>

    /**
     * Update future relationship (\&quot;to-many\&quot;). Updates future relationship. Responses:
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
     * @param id Play queue id
     * @param playQueueUpdateFutureOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("playQueues/{id}/relationships/future")
    suspend fun playQueuesIdRelationshipsFuturePatch(
        @Path("id") id: kotlin.String,
        @Body playQueueUpdateFutureOperationPayload: PlayQueueUpdateFutureOperationPayload? = null,
    ): Response<Unit>

    /**
     * Add to future relationship (\&quot;to-many\&quot;). Adds item(s) to future relationship.
     * Responses:
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
     * @param id Play queue id
     * @param playQueueAddFutureOperationPayload (optional)
     * @return [Unit]
     */
    @POST("playQueues/{id}/relationships/future")
    suspend fun playQueuesIdRelationshipsFuturePost(
        @Path("id") id: kotlin.String,
        @Body playQueueAddFutureOperationPayload: PlayQueueAddFutureOperationPayload? = null,
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
     * @param id Play queue id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [PlayQueuesMultiRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/owners")
    suspend fun playQueuesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlayQueuesMultiRelationshipDataDocument>

    /**
     * Get past relationship (\&quot;to-many\&quot;). Retrieves past relationship. Responses:
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
     * @param id Play queue id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: past (optional)
     * @return [PlayQueuesPastMultiRelationshipDataDocument]
     */
    @GET("playQueues/{id}/relationships/past")
    suspend fun playQueuesIdRelationshipsPastGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<PlayQueuesPastMultiRelationshipDataDocument>

    /**
     * Create single playQueue. Creates a new playQueue. Responses:
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
     * @param playQueueCreateOperationPayload (optional)
     * @return [PlayQueuesSingleResourceDataDocument]
     */
    @POST("playQueues")
    suspend fun playQueuesPost(
        @Body playQueueCreateOperationPayload: PlayQueueCreateOperationPayload? = null
    ): Response<PlayQueuesSingleResourceDataDocument>
}
