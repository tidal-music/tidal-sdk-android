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
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @param filterId Allows filtering by genre id(s). USER_SELECTABLE is special value used to
     *   return specific genres which users can select from (optional)
     * @return [GenresMultiResourceDataDocument]
     */
    @GET("genres")
    suspend fun genresGet(
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<GenresMultiResourceDataDocument>

    /**
     * Get single genre. Retrieves single genre by id. Responses:
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
     * @param id Genre id
     * @return [GenresSingleResourceDataDocument]
     */
    @GET("genres/{id}")
    suspend fun genresIdGet(
        @Path("id") id: kotlin.String
    ): Response<GenresSingleResourceDataDocument>
}
