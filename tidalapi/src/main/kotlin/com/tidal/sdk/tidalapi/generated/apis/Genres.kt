package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.GenresMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.GenresSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Genres {
    /**
     * Get multiple genres. Retrieves multiple genres by available filters, or without if
     * applicable. Responses:
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
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @param filterId Allows filtering by genre id(s). USER_SELECTABLE is special value used to
     *   return specific genres which users can select from (e.g. &#x60;&#39;1,2,3&#39; or
     *   &#39;USER_SELECTABLE&#39;&#x60;) (optional)
     * @return [GenresMultiResourceDataDocument]
     */
    @GET("genres")
    suspend fun genresGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("locale") locale: kotlin.String? = "en-US",
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<GenresMultiResourceDataDocument>

    /**
     * Get single genre. Retrieves single genre by id. Responses:
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
     * @param id Genre id
     * @param locale BCP 47 locale (e.g., en-US, nb-NO, pt-BR). Defaults to en-US if not provided or
     *   unsupported. (optional, default to "en-US")
     * @return [GenresSingleResourceDataDocument]
     */
    @GET("genres/{id}")
    suspend fun genresIdGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String? = "en-US",
    ): Response<GenresSingleResourceDataDocument>
}
