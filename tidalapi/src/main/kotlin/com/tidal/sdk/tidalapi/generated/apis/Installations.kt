package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.InstallationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.InstallationsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.InstallationsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.InstallationsOfflineInventoryMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.InstallationsOfflineInventoryRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.InstallationsOfflineInventoryRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.InstallationsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.InstallationsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Installations {
    /**
     * Get multiple installations. Retrieves multiple installations by available filters, or without
     * if applicable. Responses:
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
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: offlineInventory, owners (optional)
     * @param filterClientProvidedInstallationId Client-provided installation identifier to filter
     *   by (e.g. &#x60;a468bee88def&#x60;) (optional)
     * @param filterOwnersId User ID to filter by. Use &#x60;me&#x60; for the authenticated user
     *   (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: offlineInventory (optional)
     * @return [InstallationsMultiResourceDataDocument]
     */
    @GET("installations")
    suspend fun installationsGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[clientProvidedInstallationId]")
        filterClientProvidedInstallationId:
            @JvmSuppressWildcards
            kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<InstallationsMultiResourceDataDocument>

    /**
     * Get single installation. Retrieves single installation by id. Responses:
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
     * @param id Installation id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: offlineInventory, owners (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: offlineInventory (optional)
     * @return [InstallationsSingleResourceDataDocument]
     */
    @GET("installations/{id}")
    suspend fun installationsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<InstallationsSingleResourceDataDocument>

    /**
     * Delete from offlineInventory relationship (\&quot;to-many\&quot;). Deletes item(s) from
     * offlineInventory relationship. Responses:
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
     * @param id Installation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param installationsOfflineInventoryRelationshipRemoveOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @HTTP(
        method = "DELETE",
        path = "installations/{id}/relationships/offlineInventory",
        hasBody = true,
    )
    suspend fun installationsIdRelationshipsOfflineInventoryDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        installationsOfflineInventoryRelationshipRemoveOperationPayload:
            InstallationsOfflineInventoryRelationshipRemoveOperationPayload? =
            null,
    ): Response<MutationResponseDocument>

    /** enum for parameter filterType */
    enum class FilterTypeInstallationsIdRelationshipsOfflineInventoryGet(val value: kotlin.String) {
        @SerialName(value = "tracks") tracks("tracks"),
        @SerialName(value = "videos") videos("videos"),
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "playlists") playlists("playlists"),
        @SerialName(value = "userCollectionTracks") userCollectionTracks("userCollectionTracks"),
    }

    /** enum for parameter filterState */
    enum class FilterStateInstallationsIdRelationshipsOfflineInventoryGet(
        val value: kotlin.String
    ) {
        @SerialName(value = "PENDING") PENDING("PENDING"),
        @SerialName(value = "STORED") STORED("STORED"),
        @SerialName(value = "FAILED") FAILED("FAILED"),
    }

    /**
     * Get offlineInventory relationship (\&quot;to-many\&quot;). Retrieves offlineInventory
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
     * @param id Installation id
     * @param filterType One of: tracks, videos, albums, playlists, userCollectionTracks (e.g.
     *   &#x60;tracks&#x60;)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: offlineInventory (optional)
     * @param filterId Offline item id (e.g. &#x60;1234&#x60;) (optional)
     * @param filterState One of: PENDING, STORED, FAILED (e.g. &#x60;PENDING&#x60;) (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: offlineInventory (optional)
     * @return [InstallationsOfflineInventoryMultiRelationshipDataDocument]
     */
    @GET("installations/{id}/relationships/offlineInventory")
    suspend fun installationsIdRelationshipsOfflineInventoryGet(
        @Path("id") id: kotlin.String,
        @Query("filter[type]")
        filterType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[state]")
        filterState: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<InstallationsOfflineInventoryMultiRelationshipDataDocument>

    /**
     * Add to offlineInventory relationship (\&quot;to-many\&quot;). Adds item(s) to
     * offlineInventory relationship. Responses:
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
     * @param id Installation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param installationsOfflineInventoryRelationshipAddOperationPayload (optional)
     * @return [MutationResponseDocument]
     */
    @POST("installations/{id}/relationships/offlineInventory")
    suspend fun installationsIdRelationshipsOfflineInventoryPost(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        installationsOfflineInventoryRelationshipAddOperationPayload:
            InstallationsOfflineInventoryRelationshipAddOperationPayload? =
            null,
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
     * @param id Installation id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [InstallationsOwnersMultiRelationshipDataDocument]
     */
    @GET("installations/{id}/relationships/owners")
    suspend fun installationsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<InstallationsOwnersMultiRelationshipDataDocument>

    /**
     * Create single installation. Creates a new installation. Responses:
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
     * @param installationsCreateOperationPayload (optional)
     * @return [InstallationsCreateSingleResourceDataDocument]
     */
    @POST("installations")
    suspend fun installationsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body installationsCreateOperationPayload: InstallationsCreateOperationPayload? = null,
    ): Response<InstallationsCreateSingleResourceDataDocument>
}
