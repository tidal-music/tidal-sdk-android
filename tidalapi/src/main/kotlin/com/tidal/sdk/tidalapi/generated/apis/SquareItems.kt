package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.MutationResponseDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemsCreateSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.SquareItemsUpdateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.SquareItemsUpdateSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface SquareItems {

    /** enum for parameter filterOrigin */
    @Serializable
    enum class FilterOriginSquareItemsGet(val value: kotlin.String) {
        @SerialName(value = "TIDAL") TIDAL("TIDAL"),
        @SerialName(value = "OTHER") OTHER("OTHER"),
    }

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageSquareItemsGet(val value: kotlin.String) {
        @SerialName(value = "owners") owners("owners")
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
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param include Include related resources. Available relationships: owners (optional)
     * @param filterOrigin Only items with this origin (e.g. &#x60;TIDAL&#x60;) (optional)
     * @param filterQuery Word-prefix match on item titles, descriptions and abbreviations, and on
     *   variation names, SKUs and UPCs (e.g. &#x60;tee&#x60;) (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [SquareItemsMultiResourceDataDocument]
     */
    @GET("squareItems")
    suspend fun squareItemsGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[origin]") filterOrigin: FilterOriginSquareItemsGet? = null,
        @Query("filter[query]") filterQuery: kotlin.String? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
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
        @SerialName(value = "owners") owners("owners")
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
     * @param include Include related resources. Available relationships: owners (optional)
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
     *   Another request is changing the Square catalog; Variation editing requires positive fixed
     *   prices for every existing variation; edit this item's variations in Square Dashboard;
     *   Square catalog item changed; reload it before editing; Request already in progress for this
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
     * POST squareItems Create single squareItem. Creates a new squareItem. Responses:
     * - 201: Successful response
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
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If the same
     *   caller repeats a key with the same method, path, query and payload, the original response
     *   is replayed. If the request differs, a 422 error is returned.
     * @param squareItemsCreateOperationPayload (optional)
     * @return [SquareItemsCreateSingleResourceDataDocument]
     */
    @POST("squareItems")
    suspend fun squareItemsPost(
        @Header("Idempotency-Key") idempotencyKey: kotlin.String,
        @Body squareItemsCreateOperationPayload: SquareItemsCreateOperationPayload? = null,
    ): Response<SquareItemsCreateSingleResourceDataDocument>
}
