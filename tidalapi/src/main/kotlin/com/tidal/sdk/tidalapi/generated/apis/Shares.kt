package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SharesCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SharesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SharesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SharesSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Shares {
    /**
     * Get multiple shares. Retrieves multiple shares by available filters, or without if
     * applicable. Responses:
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
     * @param filterCode A share code (e.g. &#x60;xyz&#x60;)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, sharedResources (optional)
     * @return [SharesMultiResourceDataDocument]
     */
    @GET("shares")
    suspend fun sharesGet(
        @Query("filter[code]")
        filterCode: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SharesMultiResourceDataDocument>

    /**
     * Get single share. Retrieves single share by id. Responses:
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
     * @param id User share id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, sharedResources (optional)
     * @return [SharesSingleResourceDataDocument]
     */
    @GET("shares/{id}")
    suspend fun sharesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SharesSingleResourceDataDocument>

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
     * @param id User share id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SharesMultiRelationshipDataDocument]
     */
    @GET("shares/{id}/relationships/owners")
    suspend fun sharesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SharesMultiRelationshipDataDocument>

    /**
     * Get sharedResources relationship (\&quot;to-many\&quot;). Retrieves sharedResources
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
     * @param id User share id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: sharedResources (optional)
     * @return [SharesMultiRelationshipDataDocument]
     */
    @GET("shares/{id}/relationships/sharedResources")
    suspend fun sharesIdRelationshipsSharedResourcesGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SharesMultiRelationshipDataDocument>

    /**
     * Create single share. Creates a new share. Responses:
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
     * @param sharesCreateOperationPayload (optional)
     * @return [SharesSingleResourceDataDocument]
     */
    @POST("shares")
    suspend fun sharesPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body sharesCreateOperationPayload: SharesCreateOperationPayload? = null,
    ): Response<SharesSingleResourceDataDocument>
}
