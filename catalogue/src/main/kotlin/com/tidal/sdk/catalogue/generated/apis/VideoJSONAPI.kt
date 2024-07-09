package com.tidal.sdk.catalogue.generated.apis

import com.tidal.sdk.catalogue.generated.models.VideoDataDocument
import com.tidal.sdk.catalogue.generated.models.VideoRelationshipsDocument
import com.tidal.sdk.catalogue.generated.models.VideosDataDocument
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoJSONAPI {
    /**
     * Get single video
     * Retrieve video details by TIDAL video id.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL video id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers (optional)
     * @return [VideoDataDocument]
     */
    @GET("videos/{id}")
    suspend fun getVideo(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideoDataDocument>

    /**
     * Relationship: albums
     * Retrieve album details of the related video.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL video id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @return [VideoRelationshipsDocument]
     */
    @GET("videos/{id}/relationships/albums")
    suspend fun getVideoAlbums(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideoRelationshipsDocument>

    /**
     * Relationship: artists
     * Retrieve artist details of the related video.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL video id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @return [VideoRelationshipsDocument]
     */
    @GET("videos/{id}/relationships/artists")
    suspend fun getVideoArtists(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideoRelationshipsDocument>

    /**
     * Relationship: providers
     * This endpoint can be used to retrieve a list of video&#39;s related providers.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the video
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: providers (optional)
     * @return [VideoRelationshipsDocument]
     */
    @GET("videos/{id}/relationships/providers")
    suspend fun getVideoProviders(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideoRelationshipsDocument>

    /**
     * Get multiple videos
     * Retrieve multiple video details.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @param filterIsrc Allows to filter the collection of resources based on isrc attribute value (optional)
     * @return [VideosDataDocument]
     */
    @GET("videos")
    suspend fun getVideos(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[isrc]") filterIsrc: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<VideosDataDocument>
}
