package com.tidal.sdk.catalogue.generated.apis

import com.tidal.sdk.catalogue.generated.models.TrackDataDocument
import com.tidal.sdk.catalogue.generated.models.TrackRelationshipsDocument
import com.tidal.sdk.catalogue.generated.models.TracksDataDocument
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackJSONAPI {
    /**
     * Relationship: similar tracks
     * This endpoint can be used to retrieve a list of tracks similar to the given track.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the track
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: similarTracks (optional)
     * @return [TrackRelationshipsDocument]
     */
    @GET("tracks/{id}/relationships/similarTracks")
    suspend fun getSimilarAlbums(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackRelationshipsDocument>

    /**
     * Get single track
     * Retrieve track details by TIDAL track id.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL track id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks (optional)
     * @return [TrackDataDocument]
     */
    @GET("tracks/{id}")
    suspend fun getTrack(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackDataDocument>

    /**
     * Relationship: albums
     * Retrieve album details of the related track.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL track id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @return [TrackRelationshipsDocument]
     */
    @GET("tracks/{id}/relationships/albums")
    suspend fun getTrackAlbums(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackRelationshipsDocument>

    /**
     * Relationship: artists
     * Retrieve artist details of the related track.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL track id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @return [TrackRelationshipsDocument]
     */
    @GET("tracks/{id}/relationships/artists")
    suspend fun getTrackArtists(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackRelationshipsDocument>

    /**
     * Relationship: providers
     * This endpoint can be used to retrieve a list of track&#39;s related providers.
     * Responses:
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the track
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: providers (optional)
     * @return [TrackRelationshipsDocument]
     */
    @GET("tracks/{id}/relationships/providers")
    suspend fun getTrackProviders(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TrackRelationshipsDocument>

    /**
     * Get multiple tracks
     * Retrieve multiple track details.
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
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, albums, providers, similarTracks (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @param filterIsrc Allows to filter the collection of resources based on isrc attribute value (optional)
     * @return [TracksDataDocument]
     */
    @GET("tracks")
    suspend fun getTracks(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[isrc]") filterIsrc: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TracksDataDocument>
}
