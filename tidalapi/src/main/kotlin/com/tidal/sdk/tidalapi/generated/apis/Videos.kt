package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.VideosMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.VideosMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.VideosSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Videos {
    /**
     * Relationship: albums
     * Retrieve album details of the related video.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL video id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [VideosMultiDataRelationshipDocument]
     */
    @GET("videos/{id}/relationships/albums")
    suspend fun getVideoAlbumsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<VideosMultiDataRelationshipDocument>

    /**
     * Relationship: artists
     * Retrieve artist details of the related video.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL video id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [VideosMultiDataRelationshipDocument]
     */
    @GET("videos/{id}/relationships/artists")
    suspend fun getVideoArtistsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<VideosMultiDataRelationshipDocument>

    /**
     * Get single video
     * Retrieve video details by TIDAL video id.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL video id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers (optional)
     * @return [VideosSingleDataDocument]
     */
    @GET("videos/{id}")
    suspend fun getVideoById(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideosSingleDataDocument>

    /**
     * Relationship: providers
     * This endpoint can be used to retrieve a list of video&#39;s related providers.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the video
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [VideosMultiDataRelationshipDocument]
     */
    @GET("videos/{id}/relationships/providers")
    suspend fun getVideoProvidersRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<VideosMultiDataRelationshipDocument>

    /**
     * Get multiple videos
     * Retrieve multiple video details.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @param filterIsrc Allows to filter the collection of resources based on isrc attribute value (optional)
     * @return [VideosMultiDataDocument]
     */
    @GET("videos")
    suspend fun getVideosByFilters(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[isrc]") filterIsrc:
        @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideosMultiDataDocument>
}
