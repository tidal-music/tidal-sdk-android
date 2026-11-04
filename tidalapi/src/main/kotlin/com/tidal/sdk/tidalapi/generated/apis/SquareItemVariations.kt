package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.AtomicOperationsRequest
import com.tidal.sdk.tidalapi.generated.models.AtomicOperationsResponse
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsArtworksMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsArtworksRelationshipUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsArtworksUpdateMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemVariationsUpdateSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface SquareItemVariations {
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
     *   Square catalog; The site isn't the seller's selected Square Online site; select it to
     *   manage its items
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

    /**
     * DELETE squareItemVariations/{id} Delete single squareItemVariation. Deletes this variation by
     * ID without a revision precondition, preserving its siblings. The last variation cannot be
     * deleted; delete the item instead. Returns an acknowledgement; read the parent item for its
     * current revision. After a timeout or &#x60;5xx&#x60;, retry unchanged with the original
     * Idempotency-Key. Replay protection is bounded by receipt availability and Square&#39;s
     * idempotency retention. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Square item changed; reload it before changing its variations; An item must retain at least
     *   one variation; Another request is changing the Square catalog; Request already in progress
     *   for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square variation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @return [MutationResponseDocument]
     */
    @DELETE("squareItemVariations/{id}")
    suspend fun squareItemVariationsIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
    ): Response<MutationResponseDocument>

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageSquareItemVariationsIdGet(val value: kotlin.String) {
        @SerialName(value = "artworks") artworks("artworks"),
        @SerialName(value = "owners") owners("owners"),
    }

    /**
     * GET squareItemVariations/{id} Get single squareItemVariation. Retrieves single
     * squareItemVariation by id. Responses:
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
     * @param id Square variation id
     * @param include Include related resources. Available relationships: artworks, owners
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [SquareItemVariationsSingleResourceDataDocument]
     */
    @GET("squareItemVariations/{id}")
    suspend fun squareItemVariationsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
    ): Response<SquareItemVariationsSingleResourceDataDocument>

    /**
     * PATCH squareItemVariations/{id} Update single squareItemVariation. Updates the
     * variation&#39;s global name or its price and stock tracking at the selected site&#39;s
     * shipping location, with merchant-main fallback. Stock quantity is changed through
     * physical-count submissions. Price changes require existing positive fixed pricing at that
     * location; name and stock tracking preserve other pricing modes. After a timeout or
     * &#x60;5xx&#x60;, retry unchanged with the original Idempotency-Key. Replay protection is
     * bounded by receipt availability and Square&#39;s idempotency retention. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Square variation changed; reload it before editing; This variation's pricing cannot be
     *   edited here; edit it in Square Dashboard; Another request is changing the Square catalog;
     *   Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square variation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemVariationsUpdateOperationPayload (optional)
     * @return [SquareItemVariationsUpdateSingleResourceDataDocument]
     */
    @PATCH("squareItemVariations/{id}")
    suspend fun squareItemVariationsIdPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body
        squareItemVariationsUpdateOperationPayload: SquareItemVariationsUpdateOperationPayload? =
            null,
    ): Response<SquareItemVariationsUpdateSingleResourceDataDocument>

    /**
     * GET squareItemVariations/{id}/relationships/artworks Get artworks relationship
     * (\&quot;to-many\&quot;). Artworks of the variation&#39;s Square catalog images in
     * Square&#39;s display order. An image Square has no URL for stays in the list and its artwork
     * has no files. Returns the variation revision the list was read at. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Another request is changing the Square catalog
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square variation id
     * @param include Include related resources. Available relationships: artworks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareItemVariationsArtworksMultiRelationshipDataDocument]
     */
    @GET("squareItemVariations/{id}/relationships/artworks")
    suspend fun squareItemVariationsIdRelationshipsArtworksGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareItemVariationsArtworksMultiRelationshipDataDocument>

    /**
     * PATCH squareItemVariations/{id}/relationships/artworks Update artworks relationship
     * (\&quot;to-many\&quot;). Replaces the variation&#39;s photos with the complete ordered list.
     * Omitted photos are detached and stay in the seller&#39;s Square catalog; an empty list clears
     * them. The first artwork is the primary photo. Duplicate artwork IDs are rejected. Uploaded
     * TIDAL JPEG artworks, at most 15,000,000 bytes, are copied to Square and attached under new
     * artwork IDs; their originals stay unchanged. Existing Square artworks must belong to this
     * seller and are attached directly; they need not be the item&#39;s photos. Returns the
     * complete resulting linkage in display order and the committed variation revision. Copies may
     * remain in Square if the replacement fails. Retry unchanged with the original Idempotency-Key
     * after a timeout or 5xx. Recovery is bounded by Marketplace receipts and Square&#39;s
     * idempotency retention, and can be blocked if the variation is deleted or a source becomes
     * unavailable or ineligible. Read the variation again to reconcile its current state.
     * Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square variation changed; reload it before editing its artworks; Another request is
     *   changing the Square catalog; Square credential lacks the catalog scopes; run Square
     *   onboarding again to reconnect; Request already in progress for this idempotency key
     * - 413: Square images must be at most 15 MB (15,000,000 bytes)
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id Square variation id
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemVariationsArtworksRelationshipUpdateOperationPayload (optional)
     * @return [SquareItemVariationsArtworksUpdateMultiRelationshipDataDocument]
     */
    @PATCH("squareItemVariations/{id}/relationships/artworks")
    suspend fun squareItemVariationsIdRelationshipsArtworksPatch(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body
        squareItemVariationsArtworksRelationshipUpdateOperationPayload:
            SquareItemVariationsArtworksRelationshipUpdateOperationPayload? =
            null,
    ): Response<SquareItemVariationsArtworksUpdateMultiRelationshipDataDocument>

    /**
     * GET squareItemVariations/{id}/relationships/owners Get owners relationship
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
     * @param id Square variation id
     * @param include Include related resources. Available relationships: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SquareItemVariationsOwnersMultiRelationshipDataDocument]
     */
    @GET("squareItemVariations/{id}/relationships/owners")
    suspend fun squareItemVariationsIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SquareItemVariationsOwnersMultiRelationshipDataDocument>

    /**
     * POST squareItemVariations Create single squareItemVariation. Adds a fixed-price variation at
     * the end of the item&#39;s display order, preserving all existing variations and their pricing
     * modes. After a timeout or &#x60;5xx&#x60;, retry unchanged with the original Idempotency-Key.
     * Replay protection is bounded by receipt availability and Square&#39;s idempotency retention.
     * Responses:
     * - 201: Successful response
     * - 400: Invalid request
     * - 403: Latest terms and conditions must be accepted
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 409: Square credential lacks the catalog scopes; run Square onboarding again to reconnect;
     *   Square item changed; reload it before changing its variations; Another request is changing
     *   the Square catalog; Request already in progress for this idempotency key
     * - 415: Unsupported request media type or encoding
     * - 422: Idempotency key reused for a different request
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemVariationsCreateOperationPayload (optional)
     * @return [SquareItemVariationsCreateSingleResourceDataDocument]
     */
    @POST("squareItemVariations")
    suspend fun squareItemVariationsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body
        squareItemVariationsCreateOperationPayload: SquareItemVariationsCreateOperationPayload? =
            null,
    ): Response<SquareItemVariationsCreateSingleResourceDataDocument>
}
