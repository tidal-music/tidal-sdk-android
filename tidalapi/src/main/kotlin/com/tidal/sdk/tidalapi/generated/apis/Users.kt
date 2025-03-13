package com.tidal.sdk.tidalapi.generated.apis

import retrofit2.http.*
import retrofit2.Response
import okhttp3.RequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.tidal.sdk.tidalapi.generated.models.ErrorDocument
import com.tidal.sdk.tidalapi.generated.models.UsersMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UsersSingleDataDocument
import com.tidal.sdk.tidalapi.generated.models.UsersSingletonDataRelationshipDocument

interface Users {
    /**
     * Get the current user
     * Get the current user
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @return [UsersSingleDataDocument]
     */
    @GET("users/me")
    suspend fun getMyUser(@Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UsersSingleDataDocument>

    /**
     * Get a single user by id
     * Get a single user by id
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @return [UsersSingleDataDocument]
     */
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UsersSingleDataDocument>

    /**
     * Relationship: entitlements
     * Get user entitlements relationship
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements (optional)
     * @return [UsersSingletonDataRelationshipDocument]
     */
    @GET("users/{id}/relationships/entitlements")
    suspend fun getUserEntitlementsRelationship(@Path("id") id: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UsersSingletonDataRelationshipDocument>

    /**
     * Relationship: public profile
     * Get user public profile
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: publicProfile (optional)
     * @return [UsersSingletonDataRelationshipDocument]
     */
    @GET("users/{id}/relationships/publicProfile")
    suspend fun getUserPublicProfileRelationship(@Path("id") id: kotlin.String, @Query("locale") locale: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UsersSingletonDataRelationshipDocument>

    /**
     * Relationship: user recommendations
     * Get user recommendations
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id User Id
     * @param include Allows the client to customize which related resources should be returned. Available options: recommendations (optional)
     * @return [UsersSingletonDataRelationshipDocument]
     */
    @GET("users/{id}/relationships/recommendations")
    suspend fun getUserRecommendationsRelationship(@Path("id") id: kotlin.String, @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UsersSingletonDataRelationshipDocument>

    /**
     * Get multiple users by id
     * Get multiple users by id
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [UsersMultiDataDocument]
     */
    @GET("users")
    suspend fun getUsersByFilters(@Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null, @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null): Response<UsersMultiDataDocument>

}
