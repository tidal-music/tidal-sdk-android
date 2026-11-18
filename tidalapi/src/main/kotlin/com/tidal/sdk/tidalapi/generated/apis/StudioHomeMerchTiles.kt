package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.StudioHomeMerchTilesItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.StudioHomeMerchTilesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface StudioHomeMerchTiles {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageStudioHomeMerchTilesIdGet(val value: kotlin.String) {
        @SerialName(value = "items") items("items")
    }

    /**
     * GET studioHomeMerchTiles/{id} Get single studioHomeMerchTile. Retrieves single
     * studioHomeMerchTile by id. Responses:
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
     * @param id Studio Home merch tile id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Include related resources. Available relationships: items (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @return [StudioHomeMerchTilesSingleResourceDataDocument]
     */
    @GET("studioHomeMerchTiles/{id}")
    suspend fun studioHomeMerchTilesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
    ): Response<StudioHomeMerchTilesSingleResourceDataDocument>

    /**
     * GET studioHomeMerchTiles/{id}/relationships/items Get items relationship
     * (\&quot;to-many\&quot;). The seller&#39;s catalog items, in title order; empty until the
     * latest merch guidelines are accepted. A page can be short or empty and still have a next
     * cursor. Responses:
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
     * @param id Studio Home merch tile id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Include related resources. Available relationships: items (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [StudioHomeMerchTilesItemsMultiRelationshipDataDocument]
     */
    @GET("studioHomeMerchTiles/{id}/relationships/items")
    suspend fun studioHomeMerchTilesIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<StudioHomeMerchTilesItemsMultiRelationshipDataDocument>
}
