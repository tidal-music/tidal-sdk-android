package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistUpdateBody
import com.tidal.sdk.tidalapi.generated.models.ArtistsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Artists {
    /**
     * Get all artists
     * Retrieves all artist details by available filters or without (if applicable).
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, radio, roles, similarArtists, trackProviders, tracks, videos (optional)
     * @param filterHandle Allows to filter the collection of resources based on handle attribute value (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [ArtistsMultiDataDocument]
     */
    @GET("artists")
    suspend fun artistsGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[handle]") filterHandle:
        @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<ArtistsMultiDataDocument>

    /**
     * Get single artist
     * Retrieves artist details by an unique id.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, radio, roles, similarArtists, trackProviders, tracks, videos (optional)
     * @return [ArtistsSingleDataDocument]
     */
    @GET("artists/{id}")
    suspend fun artistsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsSingleDataDocument>

    /**
     * Update single artist
     * Updates the existing instance of artist&#39;s resource.
     * Responses:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param artistUpdateBody  (optional)
     * @return [Unit]
     */
    @PATCH("artists/{id}")
    suspend fun artistsIdPatch(
        @Path("id") id: kotlin.String,
        @Body artistUpdateBody: ArtistUpdateBody? = null,
    ): Response<Unit>

    /**
     * Relationship: albums
     * Retrieves albums relationship details of the related artist resource.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/albums")
    suspend fun artistsIdRelationshipsAlbumsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: radio
     * Retrieves radio relationship details of the related artist resource.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: radio (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/radio")
    suspend fun artistsIdRelationshipsRadioGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: roles
     * Retrieves roles relationship details of the related artist resource.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param include Allows the client to customize which related resources should be returned. Available options: roles (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/roles")
    suspend fun artistsIdRelationshipsRolesGet(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: similarArtists
     * Retrieves similarArtists relationship details of the related artist resource.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: similarArtists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/similarArtists")
    suspend fun artistsIdRelationshipsSimilarArtistsGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: trackProviders
     * Retrieves trackProviders relationship details of the related artist resource.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param include Allows the client to customize which related resources should be returned. Available options: trackProviders (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/trackProviders")
    suspend fun artistsIdRelationshipsTrackProvidersGet(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: tracks
     * Retrieves tracks relationship details of the related artist resource.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param collapseBy Collapse by options for getting artist tracks. Available options: FINGERPRINT, ID. FINGERPRINT option might collapse similar tracks based entry fingerprints while collapsing by ID always returns all available items.
     * @param include Allows the client to customize which related resources should be returned. Available options: tracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/tracks")
    suspend fun artistsIdRelationshipsTracksGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("collapseBy") collapseBy: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: videos
     * Retrieves videos relationship details of the related artist resource.
     * Responses:
     *  - 200:
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param id Artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: videos (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/videos")
    suspend fun artistsIdRelationshipsVideosGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>
}
