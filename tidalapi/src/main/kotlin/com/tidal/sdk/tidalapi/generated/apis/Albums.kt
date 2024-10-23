package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.AlbumsItemsRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSingleDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.ProvidersRelationshipDocument
import retrofit2.Response
import retrofit2.http.*

interface Albums {
    /**
     * Relationship: artists
     * Retrieve artist details of the related album.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsRelationshipDocument]
     */
    @GET("albums/{id}/relationships/artists")
    suspend fun getAlbumArtistsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsRelationshipDocument>

    /**
     * Get single album
     * Retrieve album details by TIDAL album id.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums (optional)
     * @return [AlbumsSingleDataDocument]
     */
    @GET("albums/{id}")
    suspend fun getAlbumById(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<AlbumsSingleDataDocument>

    /**
     * Relationship: items
     * Retrieve album item details.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: items (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [AlbumsItemsRelationshipDocument]
     */
    @GET("albums/{id}/relationships/items")
    suspend fun getAlbumItemsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<AlbumsItemsRelationshipDocument>

    /**
     * Relationship: providers
     * This endpoint can be used to retrieve a list of album&#39;s related providers.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the album
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ProvidersRelationshipDocument]
     */
    @GET("albums/{id}/relationships/providers")
    suspend fun getAlbumProvidersRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ProvidersRelationshipDocument>

    /**
     * Relationship: similar albums
     * This endpoint can be used to retrieve a list of albums similar to the given album.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the album
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: similarAlbums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [AlbumsRelationshipDocument]
     */
    @GET("albums/{id}/relationships/similarAlbums")
    suspend fun getAlbumSimilarAlbumsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<AlbumsRelationshipDocument>

    /**
     * Get multiple albums
     * Retrieve multiple album details.
     * Responses:
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 200: Successfully executed request.
     *
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @param filterBarcodeId Allows to filter the collection of resources based on barcodeId attribute value (optional)
     * @return [AlbumsMultiDataDocument]
     */
    @GET("albums")
    suspend fun getAlbumsByFilters(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[barcodeId]") filterBarcodeId:
        @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<AlbumsMultiDataDocument>
}
