package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.FolderItemsBreadcrumbMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FolderItemsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsParentRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.FolderItemsParentSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsParentUpdateSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsSubjectSharingSettingsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.FolderItemsSubjectSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface FolderItems {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageFolderItemsGet(val value: kotlin.String) {
        @SerialName(value = "breadcrumb") breadcrumb("breadcrumb"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "parent") parent("parent"),
        @SerialName(value = "subject") subject("subject"),
        @SerialName(value = "subjectSharingSettings")
        subjectSharingSettings("subjectSharingSettings"),
    }

    /**
     * GET folderItems Get multiple folderItems. Lists items in a folder, newest placement first.
     * Select a folder with parent.id, or select your root with parent.isRoot&#x3D;true and
     * parent.owners.id&#x3D;me. These forms cannot be combined; no other parent-filter combinations
     * are supported. To search every folder in your tree instead, use query,
     * subject.albums:published or both with parent.owners.id&#x3D;me alone. Responses:
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
     * @param filterParentId Folder id (e.g. &#x60;e3226624-355b-48f8-aa93-db42532caa66&#x60;). Also
     *   accepts &#x60;me&#x60;. (optional)
     * @param filterParentIsRoot Filter by parent.isRoot (optional)
     * @param filterParentOwnersId Owner of the folder an item is in; must be the authenticated
     *   user. With parent.isRoot&#x3D;true it selects your root, and with query or
     *   subject.albums:published your whole tree. Also accepts &#x60;me&#x60;. (optional)
     * @param filterQuery Searches every folder in your tree, selected with
     *   parent.owners.id&#x3D;me, and lists the matching items in one flat list. An item matches
     *   when each word given starts a word in its folder&#39;s name, its album&#39;s title or the
     *   title of one of the album&#39;s tracks, ignoring case. An item shared with you matches by
     *   its subject, and only while you can open it. (e.g. &#x60;demo&#x60;) (optional)
     * @param filterSubjectAlbumsPublished Searches every folder in your tree, selected with
     *   parent.owners.id&#x3D;me, for albums that are (true) or are not (false) published, and
     *   lists them in one flat list. Other items never match. Combines with query. (e.g.
     *   &#x60;true&#x60;) (optional)
     * @param filterSubjectOwnersId In your folders, &#x60;&#x3D;me&#x60; selects owned placements
     *   and &#x60;[ne]&#x3D;me&#x60; selects shared placements. In someone else&#39;s folder,
     *   &#x60;&#x3D;me&#x60; returns no items and &#x60;[ne]&#x3D;me&#x60; returns all items.
     *   Accepts only &#x60;me&#x60; or your own user ID. (optional)
     * @param filterSubjectOwnersIdNe In your folders, &#x60;&#x3D;me&#x60; selects owned placements
     *   and &#x60;[ne]&#x3D;me&#x60; selects shared placements. In someone else&#39;s folder,
     *   &#x60;&#x3D;me&#x60; returns no items and &#x60;[ne]&#x3D;me&#x60; returns all items.
     *   Accepts only &#x60;me&#x60; or your own user ID. (optional)
     * @param include Include related resources. Available relationships: breadcrumb, owners,
     *   parent, subject, subjectSharingSettings (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: breadcrumb.children.subject (optional)
     * @return [FolderItemsMultiResourceDataDocument]
     */
    @GET("folderItems")
    suspend fun folderItemsGet(
        @Query("filter[parent.id]") filterParentId: kotlin.String? = null,
        @Query("filter[parent.isRoot]") filterParentIsRoot: kotlin.String? = null,
        @Query("filter[parent.owners.id]") filterParentOwnersId: kotlin.String? = null,
        @Query("filter[query]") filterQuery: kotlin.String? = null,
        @Query("filter[subject.albums:published]")
        filterSubjectAlbumsPublished: kotlin.String? = null,
        @Query("filter[subject.owners.id]") filterSubjectOwnersId: kotlin.String? = null,
        @Query("filter[subject.owners.id][ne]") filterSubjectOwnersIdNe: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsMultiResourceDataDocument>

    /**
     * DELETE folderItems/{id} Delete single folderItem. Deletes existing folderItem. Responses:
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
     * @param id Folder item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("folderItems/{id}")
    suspend fun folderItemsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageFolderItemsIdGet(val value: kotlin.String) {
        @SerialName(value = "breadcrumb") breadcrumb("breadcrumb"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "parent") parent("parent"),
        @SerialName(value = "subject") subject("subject"),
        @SerialName(value = "subjectSharingSettings")
        subjectSharingSettings("subjectSharingSettings"),
    }

    /**
     * GET folderItems/{id} Get single folderItem. Retrieves single folderItem by id. Responses:
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
     * @param id Folder item id
     * @param include Include related resources. Available relationships: breadcrumb, owners,
     *   parent, subject, subjectSharingSettings (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: breadcrumb.children.subject (optional)
     * @return [FolderItemsSingleResourceDataDocument]
     */
    @GET("folderItems/{id}")
    suspend fun folderItemsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsSingleResourceDataDocument>

    /**
     * GET folderItems/{id}/relationships/breadcrumb Get breadcrumb relationship
     * (\&quot;to-many\&quot;). The folders from the root to this item&#39;s parent; only for the
     * tree&#39;s owner, empty otherwise. Responses:
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
     * @param id Folder item id
     * @param include Include related resources. Available relationships: breadcrumb (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: breadcrumb.children.subject (optional)
     * @return [FolderItemsBreadcrumbMultiRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/breadcrumb")
    suspend fun folderItemsIdRelationshipsBreadcrumbGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsBreadcrumbMultiRelationshipDataDocument>

    /**
     * GET folderItems/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
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
     * @param id Folder item id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [FolderItemsOwnersMultiRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/owners")
    suspend fun folderItemsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<FolderItemsOwnersMultiRelationshipDataDocument>

    /**
     * GET folderItems/{id}/relationships/parent Get parent relationship (\&quot;to-one\&quot;).
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
     * @param id Folder item id
     * @param include Include related resources. Available relationships: parent (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: parent.children.subject (optional)
     * @return [FolderItemsParentSingleRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/parent")
    suspend fun folderItemsIdRelationshipsParentGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsParentSingleRelationshipDataDocument>

    /**
     * PATCH folderItems/{id}/relationships/parent Update parent relationship
     * (\&quot;to-one\&quot;). Moves the item to the supplied parent folder, replacing its previous
     * parent. To move to the top level, supply the root folder explicitly; null data is rejected.
     * Returns the new parent linkage. Responses:
     * - 200: Successful response
     * - 400: A folder cannot be placed inside itself or one of its descendants
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
     * @param id Folder item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param folderItemsParentRelationshipUpdateOperationPayload (optional)
     * @return [FolderItemsParentUpdateSingleRelationshipDataDocument]
     */
    @PATCH("folderItems/{id}/relationships/parent")
    suspend fun folderItemsIdRelationshipsParentPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body
        folderItemsParentRelationshipUpdateOperationPayload:
            FolderItemsParentRelationshipUpdateOperationPayload? =
            null,
    ): Response<FolderItemsParentUpdateSingleRelationshipDataDocument>

    /**
     * GET folderItems/{id}/relationships/subject Get subject relationship (\&quot;to-one\&quot;).
     * Retrieves subject relationship. Responses:
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
     * @param id Folder item id
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [FolderItemsSubjectSingleRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/subject")
    suspend fun folderItemsIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsSubjectSingleRelationshipDataDocument>

    /**
     * GET folderItems/{id}/relationships/subjectSharingSettings Get subjectSharingSettings
     * relationship (\&quot;to-one\&quot;). Retrieves subjectSharingSettings relationship.
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
     * @param id Folder item id
     * @param include Include related resources. Available relationships: subjectSharingSettings
     *   (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subjectSharingSettings.subject (optional)
     * @return [FolderItemsSubjectSharingSettingsSingleRelationshipDataDocument]
     */
    @GET("folderItems/{id}/relationships/subjectSharingSettings")
    suspend fun folderItemsIdRelationshipsSubjectSharingSettingsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<FolderItemsSubjectSharingSettingsSingleRelationshipDataDocument>

    /**
     * POST folderItems Create single folderItem. Creates a new folderItem. Responses:
     * - 201: Successful response
     * - 400: A folder cannot be placed inside itself or one of its descendants
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
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @param folderItemsCreateOperationPayload (optional)
     * @return [FolderItemsCreateSingleResourceDataDocument]
     */
    @POST("folderItems")
    suspend fun folderItemsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
        @Body folderItemsCreateOperationPayload: FolderItemsCreateOperationPayload? = null,
    ): Response<FolderItemsCreateSingleResourceDataDocument>
}
