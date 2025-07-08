package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserRecommendations {
    /**
     * Get single userRecommendation. Retrieves single userRecommendation by id. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: discoveryMixes, myMixes, newArrivalMixes (optional)
     * @return [UserRecommendationsSingleDataDocument]
     */
    @GET("userRecommendations/{id}")
    suspend fun userRecommendationsIdGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationsSingleDataDocument>

    /**
     * Get discoveryMixes relationship (\&quot;to-many\&quot;). Retrieves discoveryMixes
     * relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: discoveryMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserRecommendationsMultiDataRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/discoveryMixes")
    suspend fun userRecommendationsIdRelationshipsDiscoveryMixesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationsMultiDataRelationshipDocument>

    /**
     * Get myMixes relationship (\&quot;to-many\&quot;). Retrieves myMixes relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: myMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserRecommendationsMultiDataRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/myMixes")
    suspend fun userRecommendationsIdRelationshipsMyMixesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationsMultiDataRelationshipDocument>

    /**
     * Get newArrivalMixes relationship (\&quot;to-many\&quot;). Retrieves newArrivalMixes
     * relationship. Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param id User id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: newArrivalMixes (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [UserRecommendationsMultiDataRelationshipDocument]
     */
    @GET("userRecommendations/{id}/relationships/newArrivalMixes")
    suspend fun userRecommendationsIdRelationshipsNewArrivalMixesGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserRecommendationsMultiDataRelationshipDocument>

    /**
     * Get current user&#39;s userRecommendation(s). This operation is deprecated and will be
     * removed shortly. Please switch to the equivalent /userRecommendations/{userId} endpoint. You
     * can find your user id by calling /users/me. Retrieves current user&#39;s
     * userRecommendation(s). Responses:
     * - 200: Successful response
     * - 400: Bad request - The request could not be understood by the server due to malformed
     *   syntax or invalid parameters
     * - 404: Not found - The requested resource could not be found
     * - 405: Method not allowed - The request method is not supported for the requested resource
     * - 406: Not acceptable - The requested resource is capable of generating only content not
     *   acceptable according to the Accept headers sent in the request
     * - 415: Unsupported media type - The request entity has a media type which the server or
     *   resource does not support
     * - 429: Too many requests - The user has sent too many requests in a given amount of time
     * - 451: Unavailable for legal reasons - The resource is unavailable due to legal restrictions
     * - 500: Internal server error - The server encountered an unexpected condition that prevented
     *   it from fulfilling the request
     *
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: discoveryMixes, myMixes, newArrivalMixes (optional)
     * @return [UserRecommendationsSingleDataDocument]
     */
    @Deprecated("This api was deprecated")
    @GET("userRecommendations/me")
    suspend fun userRecommendationsMeGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserRecommendationsSingleDataDocument>
}
