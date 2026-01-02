package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface SearchSuggestions {

    /** enum for parameter explicitFilter */
    enum class ExplicitFilterSearchSuggestionsIdGet(val value: kotlin.String) {
        @SerialName(value = "INCLUDE") INCLUDE("INCLUDE"),
        @SerialName(value = "EXCLUDE") EXCLUDE("EXCLUDE"),
    }

    /**
     * Get single searchSuggestion. Retrieves single searchSuggestion by id. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: directHits (optional)
     * @return [SearchSuggestionsSingleResourceDataDocument]
     */
    @GET("searchSuggestions/{id}")
    suspend fun searchSuggestionsIdGet(
        @Path("id") id: kotlin.String,
        @Query("explicitFilter")
        explicitFilter: ExplicitFilterSearchSuggestionsIdGet? =
            ExplicitFilterSearchSuggestionsIdGet.INCLUDE,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchSuggestionsSingleResourceDataDocument>

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
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Search query string used as the resource identifier
     * @param explicitFilter Explicit filter (optional, default to INCLUDE)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: directHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchSuggestionsMultiRelationshipDataDocument]
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
    ): Response<SearchSuggestionsMultiRelationshipDataDocument>
}
