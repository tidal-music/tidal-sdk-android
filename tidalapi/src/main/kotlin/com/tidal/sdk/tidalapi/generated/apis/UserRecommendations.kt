package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserRecommendations {
    /**
     * Get all userRecommendations
     * Retrieves all userRecommendation details by available filters or without (if applicable).
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
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: discoveryMixes, myMixes, newArrivalMixes (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [UserRecommendationsMultiDataDocument]
     */
    @GET("userRecommendations")
    suspend fun userRecommendationsGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<UserRecommendationsMultiDataDocument>

    /**
     * Get single userRecommendation
     * Retrieves userRecommendation details by an unique id.
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
     * @param id User recommendations id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: discoveryMixes, myMixes, newArrivalMixes (optional)
     * @return [UserRecommendationsSingleDataDocument]
     */
    @GET("userRecommendations/{id}")
    suspend fun userRecommendationsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationsSingleDataDocument>

    /**
     * Relationship: discoveryMixes
     * Retrieves discoveryMixes relationship details of the related userRecommendation resource.
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
     * @param id User recommendations id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: discoveryMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserRecommendationsMultiDataRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/discoveryMixes")
    suspend fun userRecommendationsIdRelationshipsDiscoveryMixesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationsMultiDataRelationshipDocument>

    /**
     * Relationship: myMixes
     * Retrieves myMixes relationship details of the related userRecommendation resource.
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
     * @param id User recommendations id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: myMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserRecommendationsMultiDataRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/myMixes")
    suspend fun userRecommendationsIdRelationshipsMyMixesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationsMultiDataRelationshipDocument>

    /**
     * Relationship: newArrivalMixes
     * Retrieves newArrivalMixes relationship details of the related userRecommendation resource.
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
     * @param id User recommendations id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: newArrivalMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserRecommendationsMultiDataRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/newArrivalMixes")
    suspend fun userRecommendationsIdRelationshipsNewArrivalMixesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationsMultiDataRelationshipDocument>

    /**
     * Get current user&#39;s userRecommendation data
     * Retrieves current user&#39;s userRecommendation details.
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
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: discoveryMixes, myMixes, newArrivalMixes (optional)
     * @return [UserRecommendationsSingleDataDocument]
     */
    @GET("userRecommendations/me")
    suspend fun userRecommendationsMeGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationsSingleDataDocument>
}
