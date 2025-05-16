package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ArtworkCreateOperationPayload
import com.tidal.sdk.tidalapi.generated.models.ArtworksMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtworksSingleDataDocument
import com.tidal.sdk.tidalapi.generated.models.ErrorDocument

interface Artworks {
    /**
     * Get multiple artworks.
     * Retrieves multiple artworks by available filters, or without if applicable.
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
     * @param filterId Artwork id (optional)
     * @return [ArtworksMultiDataDocument]
     */
    @GET("artworks")
    suspend fun artworksGet(@Query("countryCode") countryCode: kotlin.String, @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<ArtworksMultiDataDocument>

    /**
     * Get single artwork.
     * Retrieves single artwork by id.
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
     * @param id Artwork id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @return [ArtworksSingleDataDocument]
     */
    @GET("artworks/{id}")
    suspend fun artworksIdGet(@Path("id") id: kotlin.String, @Query("countryCode") countryCode: kotlin.String): Response<ArtworksSingleDataDocument>

    /**
     * Create single artwork.
     * Creates a new artwork.
     * Responses:
     *  - 201: 
     *  - 451: Unavailable For Legal Reasons
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 429: Too many HTTP requests have been made within the allowed time.
     *
     * @param artworkCreateOperationPayload  (optional)
     * @return [ArtworksSingleDataDocument]
     */
    @POST("artworks")
    suspend fun artworksPost(@Body artworkCreateOperationPayload: ArtworkCreateOperationPayload? = null): Response<ArtworksSingleDataDocument>

}
