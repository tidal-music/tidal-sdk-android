package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.FoldersChildrenMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FoldersCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersParentSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersPreviewMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FoldersUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FoldersUpdateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Folders {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageFoldersGet(val value: kotlin.String) {
        @SerialName(value = "children") children("children"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "parent") parent("parent"),
        @SerialName(value = "preview") preview("preview"),
    }

    /**
     * GET folders Get multiple folders. Retrieves multiple folders by available filters, or without
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
     * @param filterId Folder id (e.g. &#x60;e3226624-355b-48f8-aa93-db42532caa66&#x60;)
     * @param include Include related resources. Available relationships: children, owners, parent,
     *   preview (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: children.subject (optional)
     * @return [FoldersMultiResourceDataDocument]
     */
    @GET("folders")
    suspend fun foldersGet(
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FoldersMultiResourceDataDocument>

    /**
     * DELETE folders/{id} Delete single folder. Deletes existing folder. Responses:
     * - 200: Successful response
     * - 400: The root folder cannot be renamed or deleted
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("folders/{id}")
    suspend fun foldersIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageFoldersIdGet(val value: kotlin.String) {
        @SerialName(value = "children") children("children"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "parent") parent("parent"),
        @SerialName(value = "preview") preview("preview"),
    }

    /**
     * GET folders/{id} Get single folder. Retrieves single folder by id. Responses:
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Include related resources. Available relationships: children, owners, parent,
     *   preview (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: children.subject (optional)
     * @return [FoldersSingleResourceDataDocument]
     */
    @GET("folders/{id}")
    suspend fun foldersIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FoldersSingleResourceDataDocument>

    /**
     * PATCH folders/{id} Update single folder. Updates existing folder. Responses:
     * - 200: Successful response
     * - 400: The root folder cannot be renamed or deleted
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @param foldersUpdateOperationPayload (optional)
     * @return [FoldersUpdateSingleResourceDataDocument]
     */
    @PATCH("folders/{id}")
    suspend fun foldersIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body foldersUpdateOperationPayload: FoldersUpdateOperationPayload? = null,
    ): Response<FoldersUpdateSingleResourceDataDocument>

    /**
     * GET folders/{id}/relationships/children Get children relationship (\&quot;to-many\&quot;).
     * Lists folder items newest first by placement timestamp, with ID as a stable tie-breaker.
     * Moving to another parent resets the timestamp; renaming preserves it. Responses:
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: children (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: children.subject (optional)
     * @return [FoldersChildrenMultiRelationshipDataDocument]
     */
    @GET("folders/{id}/relationships/children")
    suspend fun foldersIdRelationshipsChildrenGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FoldersChildrenMultiRelationshipDataDocument>

    /**
     * GET folders/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [FoldersOwnersMultiRelationshipDataDocument]
     */
    @GET("folders/{id}/relationships/owners")
    suspend fun foldersIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<FoldersOwnersMultiRelationshipDataDocument>

    /**
     * GET folders/{id}/relationships/parent Get parent relationship (\&quot;to-one\&quot;).
     * Retrieves parent relationship. Responses:
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Include related resources. Available relationships: parent (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: parent.children.subject (optional)
     * @return [FoldersParentSingleRelationshipDataDocument]
     */
    @GET("folders/{id}/relationships/parent")
    suspend fun foldersIdRelationshipsParentGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FoldersParentSingleRelationshipDataDocument>

    /**
     * GET folders/{id}/relationships/preview Get preview relationship (\&quot;to-many\&quot;). The
     * first four items of this folder, as &#x60;children&#x60; lists them. Responses:
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
     * @param id Folder id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Include related resources. Available relationships: preview (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: preview.subject (optional)
     * @return [FoldersPreviewMultiRelationshipDataDocument]
     */
    @GET("folders/{id}/relationships/preview")
    suspend fun foldersIdRelationshipsPreviewGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FoldersPreviewMultiRelationshipDataDocument>

    /**
     * POST folders Create single folder. Creates a new folder. Responses:
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
     * @param foldersCreateOperationPayload (optional)
     * @return [FoldersCreateSingleResourceDataDocument]
     */
    @POST("folders")
    suspend fun foldersPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body foldersCreateOperationPayload: FoldersCreateOperationPayload? = null,
    ): Response<FoldersCreateSingleResourceDataDocument>
}
