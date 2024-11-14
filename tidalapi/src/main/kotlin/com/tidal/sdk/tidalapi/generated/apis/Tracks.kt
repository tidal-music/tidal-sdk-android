package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TracksMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.TracksMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.TracksSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Tracks {
    /**
     * Relationship: albums
     * Retrieve album details of the related track.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL track id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [TracksMultiDataRelationshipDocument]
     */
    @GET("tracks/{id}/relationships/albums")
    suspend fun getTrackAlbumsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TracksMultiDataRelationshipDocument>

    /**
     * Relationship: artists
     * Retrieve artist details of the related track.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL track id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [TracksMultiDataRelationshipDocument]
     */
    @GET("tracks/{id}/relationships/artists")
    suspend fun getTrackArtistsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TracksMultiDataRelationshipDocument>

    /**
     * Get single track
     * Retrieve track details by TIDAL track id.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL track id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks, radio (optional)
     * @return [TracksSingleDataDocument]
     */
    @GET("tracks/{id}")
    suspend fun getTrackById(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TracksSingleDataDocument>

    /**
     * Relationship: providers
     * This endpoint can be used to retrieve a list of track&#39;s related providers.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the track
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [TracksMultiDataRelationshipDocument]
     */
    @GET("tracks/{id}/relationships/providers")
    suspend fun getTrackProvidersRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TracksMultiDataRelationshipDocument>

    /**
     * Relationship: radio
     * This endpoint can be used to retrieve a list of radios for the given track.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the track
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: radio (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [TracksMultiDataRelationshipDocument]
     */
    @GET("tracks/{id}/relationships/radio")
    suspend fun getTrackRadioRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TracksMultiDataRelationshipDocument>

    /**
     * Relationship: similar tracks
     * This endpoint can be used to retrieve a list of tracks similar to the given track.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the track
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: similarTracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [TracksMultiDataRelationshipDocument]
     */
    @GET("tracks/{id}/relationships/similarTracks")
    suspend fun getTrackSimilarTracksRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<TracksMultiDataRelationshipDocument>

    /**
     * Get multiple tracks
     * Retrieve multiple track details.
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
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks, radio (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @param filterIsrc Allows to filter the collection of resources based on isrc attribute value (optional)
     * @return [TracksMultiDataDocument]
     */
    @GET("tracks")
    suspend fun getTracksByFilters(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[isrc]") filterIsrc:
        @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TracksMultiDataDocument>
}
