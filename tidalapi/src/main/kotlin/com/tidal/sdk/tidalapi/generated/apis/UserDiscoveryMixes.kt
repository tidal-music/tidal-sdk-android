package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.UserDiscoveryMixesItemsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserDiscoveryMixesSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface UserDiscoveryMixes {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageUserDiscoveryMixesIdGet(val value: kotlin.String) {
        @SerialName(value = "items") items("items")
    }

    /**
     * GET userDiscoveryMixes/{id} Get single userDiscoveryMixe. Retrieves single userDiscoveryMixe
     * by id. Responses:
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
     * @param id User discovery mixes id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Include related resources. Available relationships: items (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items.items (optional)
     * @return [UserDiscoveryMixesSingleResourceDataDocument]
     */
    @GET("userDiscoveryMixes/{id}")
    suspend fun userDiscoveryMixesIdGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserDiscoveryMixesSingleResourceDataDocument>

    /**
     * GET userDiscoveryMixes/{id}/relationships/items Get items relationship
     * (\&quot;to-many\&quot;). Retrieves items relationship. Responses:
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
     * @param id User discovery mixes id. Use &#x60;me&#x60; for the authenticated user&#39;s
     *   resource
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param include Include related resources. Available relationships: items (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: items.items (optional)
     * @return [UserDiscoveryMixesItemsMultiRelationshipDataDocument]
     */
    @GET("userDiscoveryMixes/{id}/relationships/items")
    suspend fun userDiscoveryMixesIdRelationshipsItemsGet(
        @Path("id") id: kotlin.String,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UserDiscoveryMixesItemsMultiRelationshipDataDocument>
}
