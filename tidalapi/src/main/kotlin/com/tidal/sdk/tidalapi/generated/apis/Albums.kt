package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.AlbumsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.AlbumsSingleDataDocument
import com.tidal.sdk.tidalapi.generated.models.ErrorDocument

interface Albums {
    /**
     * Get all albums
     * Retrieves all album details by available filters or without (if applicable).
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
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums (optional)
     * @param filterBarcodeId Allows to filter the collection of resources based on barcodeId attribute value (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [AlbumsMultiDataDocument]
     */
    @GET("albums")
    suspend fun albumsGet(@Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("filter[barcodeId]") filterBarcodeId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<AlbumsMultiDataDocument>

    /**
     * Get single album
     * Retrieves album details by an unique id.
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
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists, items, providers, similarAlbums (optional)
     * @return [AlbumsSingleDataDocument]
     */
    @GET("albums/{id}")
    suspend fun albumsIdGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<AlbumsSingleDataDocument>

    /**
     * Relationship: artists
     * Retrieves artists relationship details of the related album resource.
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
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: artists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [AlbumsMultiDataRelationshipDocument]
     */
    @GET("albums/{id}/relationships/artists")
    suspend fun albumsIdRelationshipsArtistsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<AlbumsMultiDataRelationshipDocument>

    /**
     * Relationship: items
     * Retrieves items relationship details of the related album resource.
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
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: items (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [AlbumsMultiDataRelationshipDocument]
     */
    @GET("albums/{id}/relationships/items")
    suspend fun albumsIdRelationshipsItemsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<AlbumsMultiDataRelationshipDocument>

    /**
     * Relationship: providers
     * Retrieves providers relationship details of the related album resource.
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
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: providers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [AlbumsMultiDataRelationshipDocument]
     */
    @GET("albums/{id}/relationships/providers")
    suspend fun albumsIdRelationshipsProvidersGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<AlbumsMultiDataRelationshipDocument>

    /**
     * Relationship: similarAlbums
     * Retrieves similarAlbums relationship details of the related album resource.
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
     * @param id TIDAL album id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: similarAlbums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [AlbumsMultiDataRelationshipDocument]
     */
    @GET("albums/{id}/relationships/similarAlbums")
    suspend fun albumsIdRelationshipsSimilarAlbumsGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("page[cursor]") pageCursor: kotlin.String? = null): Response<AlbumsMultiDataRelationshipDocument>

}
