package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SearchSuggestions {
    /**
     * Get single searchSuggestion. Retrieves single searchSuggestion by id. Responses:
     * - 200: Successful response
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: directHits (optional)
     * @return [SearchSuggestionsSingleDataDocument]
     */
    @GET("searchSuggestions/{id}")
    suspend fun searchSuggestionsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchSuggestionsSingleDataDocument>

    /**
     * Get directHits relationship (\&quot;to-many\&quot;). Retrieves directHits relationship.
     * Responses:
     * - 200: Successful response
     * - 451: Unavailable For Legal Reasons
     * - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters,
     *   request body, etc.).
     * - 500: Internal Server Error. Something went wrong on the server party.
     * - 404: Resource not found. The requested resource is not found.
     * - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media
     *   type is set into Content-Type header.
     * - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     * - 406: Not acceptable. The server doesn't support any of the requested by client acceptable
     *   content types.
     * - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param explicitFilter Explicit filter (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: directHits (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [SearchSuggestionsMultiDataRelationshipDocument]
     */
    @GET("searchSuggestions/{id}/relationships/directHits")
    suspend fun searchSuggestionsIdRelationshipsDirectHitsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("explicitFilter") explicitFilter: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<SearchSuggestionsMultiDataRelationshipDocument>
}
