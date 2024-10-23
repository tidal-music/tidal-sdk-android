package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.PlaylistsRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserRecommendations {
    /**
     * Get the current users recommendations
     * Get the current users recommendations
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned. Available options: myMixes, discoveryMixes, newArrivalMixes (optional)
     * @return [UserRecommendationsSingleDataDocument]
     */
    @GET("userRecommendations/me")
    suspend fun getMyUserRecommendations(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationsSingleDataDocument>

    /**
     * Get recommendations for users in batch
     * Get recommendations for users in batch
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned. Available options: myMixes, discoveryMixes, newArrivalMixes (optional)
     * @param filterId User recommendations id (optional)
     * @return [UserRecommendationsMultiDataDocument]
     */
    @GET("userRecommendations")
    suspend fun getUserRecommendationsByFilters(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<UserRecommendationsMultiDataDocument>

    /**
     * Get user recommendations for user
     * Get user recommendations for user
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User recommendations id
     * @param include Allows the client to customize which related resources should be returned. Available options: myMixes, discoveryMixes, newArrivalMixes (optional)
     * @return [UserRecommendationsSingleDataDocument]
     */
    @GET("userRecommendations/{id}")
    suspend fun getUserRecommendationsById(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationsSingleDataDocument>

    /**
     * Relationship: discovery mixes
     * Get discovery mixes relationship
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User recommendations id
     * @param include Allows the client to customize which related resources should be returned. Available options: discoveryMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [PlaylistsRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/discoveryMixes")
    suspend fun getUserRecommendationsDiscoveryMixesRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlaylistsRelationshipDocument>

    /**
     * Relationship: my mixes
     * Get my mixes relationship
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User recommendations id
     * @param include Allows the client to customize which related resources should be returned. Available options: myMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [PlaylistsRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/myMixes")
    suspend fun getUserRecommendationsMyMixesRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlaylistsRelationshipDocument>

    /**
     * Relationship: new arrivals mixes
     * Get new arrival mixes relationship
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User recommendations id
     * @param include Allows the client to customize which related resources should be returned. Available options: newArrivalMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [PlaylistsRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/newArrivalMixes")
    suspend fun getUserRecommendationsNewArrivalMixesRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<PlaylistsRelationshipDocument>
}
