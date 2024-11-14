package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ArtistsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsSingleDataDocument
import com.tidal.sdk.tidalapi.generated.models.ArtistsTrackProvidersMultiDataRelationshipDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Artists {
    /**
     * Relationship: albums
     * Retrieve album details of the related artist.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/albums")
    suspend fun getArtistAlbumsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Get single artist
     * Retrieve artist details by TIDAL artist id.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, tracks, videos, similarArtists, trackProviders, radio (optional)
     * @return [ArtistsSingleDataDocument]
     */
    @GET("artists/{id}")
    suspend fun getArtistById(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ArtistsSingleDataDocument>

    /**
     * Relationship: radio
     * This endpoint can be used to retrieve a list of radios for the given artist.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the artist
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: radio (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/radio")
    suspend fun getArtistRadioRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: similar artists
     * This endpoint can be used to retrieve a list of artists similar to the given artist.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the artist
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: similarArtists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/similarArtists")
    suspend fun getArtistSimilarArtistsRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: track providers
     * Retrieve providers that have released tracks for this artist
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL id of the artist
     * @param include Allows the client to customize which related resources should be returned. Available options: trackProviders (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsTrackProvidersMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/trackProviders")
    suspend fun getArtistTrackProvidersRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsTrackProvidersMultiDataRelationshipDocument>

    /**
     * enum for parameter collapseBy
     */
    enum class CollapseByGetArtistTracksRelationship(val value: kotlin.String) {
        @SerialName(value = "FINGERPRINT")
        FINGERPRINT("FINGERPRINT"),

        @SerialName(value = "NONE")
        NONE("NONE"),
    }

    /**
     * Relationship: tracks
     * Retrieve track details by related artist.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param collapseBy Collapse by options for getting artist tracks. Available options: FINGERPRINT, ID. FINGERPRINT option might collapse similar tracks based item fingerprints while collapsing by ID always returns all available items. (optional, default to FINGERPRINT)
     * @param include Allows the client to customize which related resources should be returned. Available options: tracks (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/tracks")
    suspend fun getArtistTracksRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("collapseBy") collapseBy: CollapseByGetArtistTracksRelationship? = CollapseByGetArtistTracksRelationship.FINGERPRINT,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Relationship: videos
     * Retrieve video details by related artist.
     * Responses:
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL artist id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param include Allows the client to customize which related resources should be returned. Available options: videos (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [ArtistsMultiDataRelationshipDocument]
     */
    @GET("artists/{id}/relationships/videos")
    suspend fun getArtistVideosRelationship(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ArtistsMultiDataRelationshipDocument>

    /**
     * Get multiple artists
     * Retrieve multiple artist details.
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
     * @param include Allows the client to customize which related resources should be returned. Available options: albums, tracks, videos, similarArtists, trackProviders, radio (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [ArtistsMultiDataDocument]
     */
    @GET("artists")
    suspend fun getArtistsByFilters(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<ArtistsMultiDataDocument>
}
