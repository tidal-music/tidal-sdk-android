package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.OfflineTasksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.OfflineTasksMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.OfflineTasksSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.OfflineTasksSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.OfflineTasksUpdateOperationPayload
import retrofit2.Response
import retrofit2.http.*

interface OfflineTasks {
    /**
     * Get multiple offlineTasks. Retrieves multiple offlineTasks by available filters, or without
     * if applicable. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: collection, item, owners (optional)
     * @param filterId Offline task id (e.g. &#x60;a468bee8-8def-4a1b-8c1e-123456789abc&#x60;)
     *   (optional)
     * @param filterInstallationId Installation id (e.g. &#x60;a468bee88def&#x60;) (optional)
     * @return [OfflineTasksMultiResourceDataDocument]
     */
    @GET("offlineTasks")
    suspend fun offlineTasksGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[installation.id]")
        filterInstallationId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<OfflineTasksMultiResourceDataDocument>

    /**
     * Get single offlineTask. Retrieves single offlineTask by id. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Offline task id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: collection, item, owners (optional)
     * @return [OfflineTasksSingleResourceDataDocument]
     */
    @GET("offlineTasks/{id}")
    suspend fun offlineTasksIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<OfflineTasksSingleResourceDataDocument>

    /**
     * Update single offlineTask. Updates existing offlineTask. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Offline task id
     * @param offlineTasksUpdateOperationPayload (optional)
     * @return [Unit]
     */
    @PATCH("offlineTasks/{id}")
    suspend fun offlineTasksIdPatch(
        @Path("id") id: kotlin.String,
        @Body offlineTasksUpdateOperationPayload: OfflineTasksUpdateOperationPayload? = null,
    ): Response<Unit>

    /**
     * Get collection relationship (\&quot;to-one\&quot;). Retrieves collection relationship.
     * Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Offline task id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: collection (optional)
     * @return [OfflineTasksSingleRelationshipDataDocument]
     */
    @GET("offlineTasks/{id}/relationships/collection")
    suspend fun offlineTasksIdRelationshipsCollectionGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<OfflineTasksSingleRelationshipDataDocument>

    /**
     * Get item relationship (\&quot;to-one\&quot;). Retrieves item relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Offline task id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: item (optional)
     * @return [OfflineTasksSingleRelationshipDataDocument]
     */
    @GET("offlineTasks/{id}/relationships/item")
    suspend fun offlineTasksIdRelationshipsItemGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<OfflineTasksSingleRelationshipDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Offline task id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [OfflineTasksMultiRelationshipDataDocument]
     */
    @GET("offlineTasks/{id}/relationships/owners")
    suspend fun offlineTasksIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<OfflineTasksMultiRelationshipDataDocument>
}
