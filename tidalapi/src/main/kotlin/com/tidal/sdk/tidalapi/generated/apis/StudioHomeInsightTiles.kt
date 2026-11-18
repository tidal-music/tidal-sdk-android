package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.StudioHomeInsightTilesItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.StudioHomeInsightTilesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface StudioHomeInsightTiles {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageStudioHomeInsightTilesIdGet(val value: kotlin.String) {
        @SerialName(value = "items") items("items")
    }

    /**
     * GET studioHomeInsightTiles/{id} Get single studioHomeInsightTile. Retrieves single
     * studioHomeInsightTile by id. Responses:
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
     * @param id Studio Home insight tile id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Include related resources. Available relationships: items (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items.topTracks (optional)
     * @return [StudioHomeInsightTilesSingleResourceDataDocument]
     */
    @GET("studioHomeInsightTiles/{id}")
    suspend fun studioHomeInsightTilesIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<StudioHomeInsightTilesSingleResourceDataDocument>

    /**
     * GET studioHomeInsightTiles/{id}/relationships/items Get items relationship
     * (\&quot;to-many\&quot;). The streams statistics of the user&#39;s artist over the last 28
     * days. Responses:
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
     * @param id Studio Home insight tile id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param include Include related resources. Available relationships: items (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items.topTracks (optional)
     * @return [StudioHomeInsightTilesItemsMultiRelationshipDataDocument]
     */
    @GET("studioHomeInsightTiles/{id}/relationships/items")
    suspend fun studioHomeInsightTilesIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<StudioHomeInsightTilesItemsMultiRelationshipDataDocument>
}
