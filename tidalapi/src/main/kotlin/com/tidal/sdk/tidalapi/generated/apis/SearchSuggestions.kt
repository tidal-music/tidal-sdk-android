package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsDirectHitsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsHistoryMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsMultiResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface SearchSuggestions {

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchSuggestionsGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /**
     * Get search suggestions by query. Searches for a query and returns a collection containing
     * exactly one search suggestions resource. Responses:
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
     * @param filterQuery Search query (e.g. &#x60;hello&#x60;)
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: directHits, history (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: directHits (optional)
     * @return [SearchSuggestionsMultiResourceDataDocument]
     */
    @GET("searchSuggestions")
    suspend fun searchSuggestionsGet(
        @Query("filter[query]") filterQuery: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchSuggestionsGet? =
            ExplicitFilterSearchSuggestionsGet.INCLUDE,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<SearchSuggestionsMultiResourceDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchSuggestionsIdRelationshipsDirectHitsGet(
        val value: kotlin.String
    ) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /**
     * Get directHits relationship (\&quot;to-many\&quot;). Retrieves directHits relationship.
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
     * @param id An opaque search suggestions identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: directHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: directHits (optional)
     * @return [SearchSuggestionsDirectHitsMultiRelationshipDataDocument]
     */
    @GET("searchSuggestions/{id}/relationships/directHits")
    suspend fun searchSuggestionsIdRelationshipsDirectHitsGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchSuggestionsIdRelationshipsDirectHitsGet? =
            ExplicitFilterSearchSuggestionsIdRelationshipsDirectHitsGet.INCLUDE,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<SearchSuggestionsDirectHitsMultiRelationshipDataDocument>

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchSuggestionsIdRelationshipsHistoryGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /**
     * Get history relationship (\&quot;to-many\&quot;). Retrieves history relationship. Responses:
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
     * @param id An opaque search suggestions identifier
     * @param explicitFilter Explicit filter. Valid values: INCLUDE or EXCLUDE (optional, default to
     *   INCLUDE)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: history (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchSuggestionsHistoryMultiRelationshipDataDocument]
     */
    @GET("searchSuggestions/{id}/relationships/history")
    suspend fun searchSuggestionsIdRelationshipsHistoryGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchSuggestionsIdRelationshipsHistoryGet? =
            ExplicitFilterSearchSuggestionsIdRelationshipsHistoryGet.INCLUDE,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchSuggestionsHistoryMultiRelationshipDataDocument>
}
