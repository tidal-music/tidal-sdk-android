package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.SearchSuggestionsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SearchSuggestions {
    /**
     * Get single searchSuggestion. Retrieves single searchSuggestion by id. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not allowed for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
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
