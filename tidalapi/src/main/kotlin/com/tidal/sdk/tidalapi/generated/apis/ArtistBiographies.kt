package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographiesSingleResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistBiographyUpdateBody
import retrofit2.Response
import retrofit2.http.*

interface ArtistBiographies {
    /**
     * Get multiple artistBiographies. Retrieves multiple artistBiographies by available filters, or
     * without if applicable. Responses:
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
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param filterId Artist id (optional)
     * @return [ArtistBiographiesMultiResourceDataDocument]
     */
    @GET("artistBiographies")
    suspend fun artistBiographiesGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistBiographiesMultiResourceDataDocument>

    /**
     * Get single artistBiographie. Retrieves single artistBiographie by id. Responses:
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
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @return [ArtistBiographiesSingleResourceDataDocument]
     */
    @GET("artistBiographies/{id}")
    suspend fun artistBiographiesIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistBiographiesSingleResourceDataDocument>

    /**
     * Update single artistBiographie. Updates existing artistBiographie. Responses:
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
     * @param id Artist id
     * @param artistBiographyUpdateBody (optional)
     * @return [Unit]
     */
    @PATCH("artistBiographies/{id}")
    suspend fun artistBiographiesIdPatch(
        @Path("id") id: kotlin.String,
        @Body artistBiographyUpdateBody: ArtistBiographyUpdateBody? = null,
    ): Response<Unit>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id Artist id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ArtistBiographiesMultiRelationshipDataDocument]
     */
    @GET("artistBiographies/{id}/relationships/owners")
    suspend fun artistBiographiesIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistBiographiesMultiRelationshipDataDocument>
}
