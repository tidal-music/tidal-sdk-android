package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.UserDataExportsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.UserDataExportsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserDataExportsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserDataExportsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserDataExportsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface UserDataExports {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageUserDataExportsGet(val value: kotlin.String) {
        @SerialName(value = "owners") owners("owners")
    }

    /**
     * GET userDataExports Get multiple userDataExports. Lists the authenticated user&#39;s exports,
     * newest first. Responses:
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
     * @param filterOwnersId The authenticated user; exports of other users are never listed. Also
     *   accepts &#x60;me&#x60;.
     * @param include Include related resources. Available relationships: owners (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserDataExportsMultiResourceDataDocument]
     */
    @GET("userDataExports")
    suspend fun userDataExportsGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserDataExportsMultiResourceDataDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageUserDataExportsIdGet(val value: kotlin.String) {
        @SerialName(value = "owners") owners("owners")
    }

    /**
     * GET userDataExports/{id} Get single userDataExport. Retrieves single userDataExport by id.
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
     * @param id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [UserDataExportsSingleResourceDataDocument]
     */
    @GET("userDataExports/{id}")
    suspend fun userDataExportsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
    ): Response<UserDataExportsSingleResourceDataDocument>

    /**
     * GET userDataExports/{id}/relationships/owners Get owners relationship
     * (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserDataExportsOwnersMultiRelationshipDataDocument]
     */
    @GET("userDataExports/{id}/relationships/owners")
    suspend fun userDataExportsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserDataExportsOwnersMultiRelationshipDataDocument>

    /**
     * POST userDataExports Create single userDataExport. Requests an export of the selected data
     * types of the authenticated user&#39;s data, or returns the export already in progress as it
     * was requested. Responses:
     * - 201: Successful response
     * - 400: One or more of the selected data types are not available
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Another data export request is being handled; try again shortly; A data export was
     *   prepared recently; wait before requesting another; Request already in progress for this
     *   idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param userDataExportsCreateOperationPayload (optional)
     * @return [UserDataExportsCreateSingleResourceDataDocument]
     */
    @POST("userDataExports")
    suspend fun userDataExportsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body userDataExportsCreateOperationPayload: UserDataExportsCreateOperationPayload? = null,
    ): Response<UserDataExportsCreateSingleResourceDataDocument>
}
