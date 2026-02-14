package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.InstallationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.InstallationsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.InstallationsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.InstallationsOfflineInventoryMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.InstallationsOfflineInventoryRelationshipAddOperationPayload
import com.tidal.sdk.tidalapi.generated.models.InstallationsOfflineInventoryRelationshipRemoveOperationPayload
import com.tidal.sdk.tidalapi.generated.models.InstallationsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Installations {
    /**
     * Get multiple installations. Retrieves multiple installations by available filters, or without
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
     *   Available options: offlineInventory, owners (optional)
     * @param filterClientProvidedInstallationId Client provided installation identifier (e.g.
     *   &#x60;a468bee88def&#x60;) (optional)
     * @param filterId Installation id (e.g. &#x60;a468bee88def&#x60;) (optional)
     * @param filterOwnersId User id (e.g. &#x60;123456&#x60;) (optional)
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
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<InstallationsMultiResourceDataDocument>

    /**
     * Get single installation. Retrieves single installation by id. Responses:
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
     * @param id Installation id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: offlineInventory, owners (optional)
     * @return [InstallationsSingleResourceDataDocument]
     */
    @GET("installations/{id}")
    suspend fun installationsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<InstallationsSingleResourceDataDocument>

    /**
     * Delete from offlineInventory relationship (\&quot;to-many\&quot;). Deletes item(s) from
     * offlineInventory relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Installation id
     * @param installationsOfflineInventoryRelationshipRemoveOperationPayload (optional)
     * @return [Unit]
     */
    @HTTP(
        method = "DELETE",
        path = "installations/{id}/relationships/offlineInventory",
        hasBody = true,
    )
    suspend fun installationsIdRelationshipsOfflineInventoryDelete(
        @Path("id") id: kotlin.String,
        @Body
        installationsOfflineInventoryRelationshipRemoveOperationPayload:
            InstallationsOfflineInventoryRelationshipRemoveOperationPayload? =
            null,
    ): Response<Unit>

    /** enum for parameter filterType */
    enum class FilterTypeInstallationsIdRelationshipsOfflineInventoryGet(val value: kotlin.String) {
        @SerialName(value = "tracks") tracks("tracks"),
        @SerialName(value = "videos") videos("videos"),
        @SerialName(value = "albums") albums("albums"),
        @SerialName(value = "playlists") playlists("playlists"),
    }

    /**
     * Get offlineInventory relationship (\&quot;to-many\&quot;). Retrieves offlineInventory
     * relationship. Responses:
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
     * @param id Installation id
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: offlineInventory (optional)
     * @param filterType One of: tracks, videos, albums, playlists (e.g. &#x60;tracks&#x60;)
     *   (optional)
     * @return [InstallationsOfflineInventoryMultiRelationshipDataDocument]
     */
    @GET("installations/{id}/relationships/offlineInventory")
    suspend fun installationsIdRelationshipsOfflineInventoryGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[type]")
        filterType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<InstallationsOfflineInventoryMultiRelationshipDataDocument>

    /**
     * Add to offlineInventory relationship (\&quot;to-many\&quot;). Adds item(s) to
     * offlineInventory relationship. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Installation id
     * @param installationsOfflineInventoryRelationshipAddOperationPayload (optional)
     * @return [Unit]
     */
    @POST("installations/{id}/relationships/offlineInventory")
    suspend fun installationsIdRelationshipsOfflineInventoryPost(
        @Path("id") id: kotlin.String,
        @Body
        installationsOfflineInventoryRelationshipAddOperationPayload:
            InstallationsOfflineInventoryRelationshipAddOperationPayload? =
            null,
    ): Response<Unit>

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
     * @param id Installation id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [InstallationsMultiRelationshipDataDocument]
     */
    @GET("installations/{id}/relationships/owners")
    suspend fun installationsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<InstallationsMultiRelationshipDataDocument>

    /**
     * Create single installation. Creates a new installation. Responses:
     * - 201: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param installationsCreateOperationPayload (optional)
     * @return [InstallationsSingleResourceDataDocument]
     */
    @POST("installations")
    suspend fun installationsPost(
        @Body installationsCreateOperationPayload: InstallationsCreateOperationPayload? = null
    ): Response<InstallationsSingleResourceDataDocument>
}
