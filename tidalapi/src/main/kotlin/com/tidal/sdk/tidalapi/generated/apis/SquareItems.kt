package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.AtomicOperationsRequest
import com.tidal.sdk.tidalapi.generated.models.AtomicOperationsResponse
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsArtworksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsArtworksRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemsArtworksUpdateMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemsUpdateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsVariationsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsVariationsRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemsVariationsUpdateMultiRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface SquareItems {
    /**
     * POST operations Commit atomic resource operations Commits a supported group of resource
     * creations together. Only add operations without ref or href are supported. Local linkage must
     * reference an earlier creation. Each resource&#39;s mutation policy applies. After a timeout
     * or server error, retry unchanged with the original Idempotency-Key. Replay is bounded by
     * downstream receipt and idempotency retention. Responses:
     * - 200: Committed results, in the order of the requested operations
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Request already in progress for this idempotency key; Square credential lacks the
     *   catalog scopes; run Square onboarding again to reconnect; Another request is changing the
     *   Square catalog
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Retry an unchanged request with the original key after a timeout or
     *   server error. Replay is bounded by downstream receipt and idempotency retention.
     * @param atomicOperationsRequest
     * @return [AtomicOperationsResponse]
     */
    @POST("operations")
    suspend fun operationsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body atomicOperationsRequest: AtomicOperationsRequest,
    ): Response<AtomicOperationsResponse>

    /** enum for parameter filterOrigin */
    @Serializable
    enum class FilterOriginSquareItemsGet(val value: kotlin.String) {
        @SerialName(value = "TIDAL") TIDAL("TIDAL"),
        @SerialName(value = "OTHER") OTHER("OTHER"),
    }

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageSquareItemsGet(val value: kotlin.String) {
        @SerialName(value = "artworks") artworks("artworks"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "variations") variations("variations"),
    }

    /**
     * GET squareItems Get multiple squareItems. Retrieves multiple squareItems by available
     * filters, or without if applicable. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param filterOwnersId User id. Also accepts &#x60;me&#x60;.
     * @param filterOrigin Only items with this origin (e.g. &#x60;TIDAL&#x60;) (optional)
     * @param filterQuery Word-prefix match on item titles, descriptions and abbreviations, and on
     *   variation names, SKUs and UPCs (e.g. &#x60;tee&#x60;) (optional)
     * @param include Include related resources. Available relationships: artworks, owners,
     *   variations (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareItemsMultiResourceDataDocument]
     */
    @GET("squareItems")
    suspend fun squareItemsGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("filter[origin]") filterOrigin: FilterOriginSquareItemsGet? = null,
        @Query("filter[query]") filterQuery: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareItemsMultiResourceDataDocument>

    /**
     * DELETE squareItems/{id} Delete single squareItem. Deletes existing squareItem. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Another request is changing the Square catalog; Request already in progress for this
     *   idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned. (optional)
     * @return [MutationResponseDocument]
     */
    @DELETE("squareItems/{id}")
    suspend fun squareItemsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageSquareItemsIdGet(val value: kotlin.String) {
        @SerialName(value = "artworks") artworks("artworks"),
        @SerialName(value = "owners") owners("owners"),
        @SerialName(value = "variations") variations("variations"),
    }

    /**
     * GET squareItems/{id} Get single squareItem. Retrieves single squareItem by id. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square item id
     * @param include Include related resources. Available relationships: artworks, owners,
     *   variations (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [SquareItemsSingleResourceDataDocument]
     */
    @GET("squareItems/{id}")
    suspend fun squareItemsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
    ): Response<SquareItemsSingleResourceDataDocument>

    /**
     * PATCH squareItems/{id} Update single squareItem. Updates existing squareItem. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Another request is changing the Square catalog; Square catalog item changed; reload it
     *   before editing; Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemsUpdateOperationPayload (optional)
     * @return [SquareItemsUpdateSingleResourceDataDocument]
     */
    @PATCH("squareItems/{id}")
    suspend fun squareItemsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body squareItemsUpdateOperationPayload: SquareItemsUpdateOperationPayload? = null,
    ): Response<SquareItemsUpdateSingleResourceDataDocument>

    /**
     * GET squareItems/{id}/relationships/artworks Get artworks relationship
     * (\&quot;to-many\&quot;). A photo Square has no URL for stays in the list and its artwork has
     * no files. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square item id
     * @param include Include related resources. Available relationships: artworks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareItemsArtworksMultiRelationshipDataDocument]
     */
    @GET("squareItems/{id}/relationships/artworks")
    suspend fun squareItemsIdRelationshipsArtworksGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareItemsArtworksMultiRelationshipDataDocument>

    /**
     * PATCH squareItems/{id}/relationships/artworks Update artworks relationship
     * (\&quot;to-many\&quot;). Appends images without moving existing photos. Duplicate artwork IDs
     * are rejected. Uploaded TIDAL JPEG artworks, at most 15,000,000 bytes, are copied to Square
     * and attached under new artwork IDs; their originals stay unchanged. Existing Square artworks
     * must belong to this seller and are attached directly. Returns only the affected Square
     * artwork linkage, in the item&#39;s display order, and the committed item revision. Copies may
     * remain in Square if attachment fails. Retry unchanged with the original Idempotency-Key after
     * a timeout or 5xx. Recovery is bounded by Marketplace receipts and Square&#39;s idempotency
     * retention, and can be blocked if the item is deleted or a source becomes unavailable or
     * ineligible. Read the item again to reconcile its current state. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square catalog item changed; reload it before editing; A Square item can have at most
     *   20 images; Another request is changing the Square catalog; Square credential lacks the
     *   catalog scopes; run Square onboarding again to reconnect; Request already in progress for
     *   this idempotency key
     * - 413: Square images must be at most 15 MB (15,000,000 bytes)
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemsArtworksRelationshipUpdateOperationPayload (optional)
     * @return [SquareItemsArtworksUpdateMultiRelationshipDataDocument]
     */
    @PATCH("squareItems/{id}/relationships/artworks")
    suspend fun squareItemsIdRelationshipsArtworksPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body
        squareItemsArtworksRelationshipUpdateOperationPayload:
            SquareItemsArtworksRelationshipUpdateOperationPayload? =
            null,
    ): Response<SquareItemsArtworksUpdateMultiRelationshipDataDocument>

    /**
     * GET squareItems/{id}/relationships/owners Get owners relationship (\&quot;to-many\&quot;).
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
     * @param id Square item id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareItemsOwnersMultiRelationshipDataDocument]
     */
    @GET("squareItems/{id}/relationships/owners")
    suspend fun squareItemsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareItemsOwnersMultiRelationshipDataDocument>

    /**
     * GET squareItems/{id}/relationships/variations Get variations relationship
     * (\&quot;to-many\&quot;). Variations in Square&#39;s display order. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square item id
     * @param include Include related resources. Available relationships: variations (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareItemsVariationsMultiRelationshipDataDocument]
     */
    @GET("squareItems/{id}/relationships/variations")
    suspend fun squareItemsIdRelationshipsVariationsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareItemsVariationsMultiRelationshipDataDocument>

    /**
     * PATCH squareItems/{id}/relationships/variations Update variations relationship
     * (\&quot;to-many\&quot;). Incrementally moves the selected variations as an ordered block
     * before positionBefore, or to the end when omitted. Memberships are preserved; unselected
     * variations retain their metadata and relative order. Empty data is rejected. Returns the
     * affected linkage only and the updated item revision. After a timeout or &#x60;5xx&#x60;,
     * retry unchanged with the original Idempotency-Key. Replay protection is bounded by receipt
     * availability and Square&#39;s idempotency retention. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Square catalog item changed; reload it before editing; Another request is changing the
     *   Square catalog; Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square item id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemsVariationsRelationshipUpdateOperationPayload (optional)
     * @return [SquareItemsVariationsUpdateMultiRelationshipDataDocument]
     */
    @PATCH("squareItems/{id}/relationships/variations")
    suspend fun squareItemsIdRelationshipsVariationsPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body
        squareItemsVariationsRelationshipUpdateOperationPayload:
            SquareItemsVariationsRelationshipUpdateOperationPayload? =
            null,
    ): Response<SquareItemsVariationsUpdateMultiRelationshipDataDocument>
}
