package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.SearchHistoryEntriesMultiResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface SearchHistoryEntries {
    /**
     * Get multiple searchHistoryEntries. Retrieves multiple searchHistoryEntries by available
     * filters, or without if applicable. Responses:
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
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param filterId Opaque identifier for a search history entry (e.g.
     *   &#x60;MjcyNjg5OTAjamF5&#x60;) (optional)
     * @return [SearchHistoryEntriesMultiResourceDataDocument]
     */
    @GET("searchHistoryEntries")
    suspend fun searchHistoryEntriesGet(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<SearchHistoryEntriesMultiResourceDataDocument>

    /**
     * Delete single searchHistoryEntrie. Deletes existing searchHistoryEntrie. Responses:
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 409: A request with this idempotency key is currently being processed
     * - 415: Unsupported request payload media type or content encoding
     * - 422: Idempotency key was already used with a different request payload
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Opaque identifier for a search history entry
     * @param idempotencyKey Unique idempotency key for safe retry of mutation requests. If a
     *   duplicate key is sent with the same payload, the original response is replayed. If the
     *   payload differs, a 422 error is returned. (optional)
     * @return [Unit]
     */
    @DELETE("searchHistoryEntries/{id}")
    suspend fun searchHistoryEntriesIdDelete(
        @Path("id") id: kotlin.String,
        @Header("Idempotency-Key") idempotencyKey: kotlin.String? = null,
    ): Response<Unit>
}
