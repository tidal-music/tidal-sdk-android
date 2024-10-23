package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserEntitlementsRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilesRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UsersMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UsersRecommendationsRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UsersSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Users {
    /**
     * Get the current user
     * Get the current user
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @return [UsersSingleDataDocument]
     */
    @GET("users/me")
    suspend fun getMyUser(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersSingleDataDocument>

    /**
     * Get a single user by id
     * Get a single user by id
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @return [UsersSingleDataDocument]
     */
    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersSingleDataDocument>

    /**
     * Relationship: entitlements
     * Get user entitlements relationship
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements (optional)
     * @return [UserEntitlementsRelationshipDocument]
     */
    @GET("users/{id}/relationships/entitlements")
    suspend fun getUserEntitlementsRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserEntitlementsRelationshipDocument>

    /**
     * Relationship: public profile
     * Get user public profile
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: publicProfile (optional)
     * @return [UserPublicProfilesRelationshipDocument]
     */
    @GET("users/{id}/relationships/publicProfile")
    suspend fun getUserPublicProfileRelationship(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilesRelationshipDocument>

    /**
     * Relationship: user recommendations
     * Get user recommendations
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param include Allows the client to customize which related resources should be returned. Available options: recommendations (optional)
     * @return [UsersRecommendationsRelationshipDocument]
     */
    @GET("users/{id}/relationships/recommendations")
    suspend fun getUserRecommendationsRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersRecommendationsRelationshipDocument>

    /**
     * Get multiple users by id
     * Get multiple users by id
     * Responses:
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 404: Resource not found. The requested resource is not found.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [UsersMultiDataDocument]
     */
    @GET("users")
    suspend fun getUsersByFilters(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<UsersMultiDataDocument>
}
