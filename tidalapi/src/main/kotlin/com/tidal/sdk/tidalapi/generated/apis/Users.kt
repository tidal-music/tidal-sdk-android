package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UsersMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UsersSingleDataDocument
import com.tidal.sdk.tidalapi.generated.models.UsersSingletonDataRelationshipDocument
import retrofit2.Response
import retrofit2.http.*

interface Users {
    /**
     * Get all users
     * Retrieves all user details by available filters or without (if applicable).
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
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [UsersMultiDataDocument]
     */
    @GET("users")
    suspend fun usersGet(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<UsersMultiDataDocument>

    /**
     * Get single user
     * Retrieves user details by an unique id.
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
     * @param id User id
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @return [UsersSingleDataDocument]
     */
    @GET("users/{id}")
    suspend fun usersIdGet(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersSingleDataDocument>

    /**
     * Relationship: entitlements(read)
     * Retrieves entitlements relationship details of the related user resource.
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
     * @param id User id
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements (optional)
     * @return [UsersSingletonDataRelationshipDocument]
     */
    @GET("users/{id}/relationships/entitlements")
    suspend fun usersIdRelationshipsEntitlementsGet(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersSingletonDataRelationshipDocument>

    /**
     * Relationship: publicProfile(read)
     * Retrieves publicProfile relationship details of the related user resource.
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
     * @param id User id
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: publicProfile (optional)
     * @return [UsersSingletonDataRelationshipDocument]
     */
    @GET("users/{id}/relationships/publicProfile")
    suspend fun usersIdRelationshipsPublicProfileGet(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersSingletonDataRelationshipDocument>

    /**
     * Relationship: recommendations(read)
     * Retrieves recommendations relationship details of the related user resource.
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
     * @param id User id
     * @param include Allows the client to customize which related resources should be returned. Available options: recommendations (optional)
     * @return [UsersSingletonDataRelationshipDocument]
     */
    @GET("users/{id}/relationships/recommendations")
    suspend fun usersIdRelationshipsRecommendationsGet(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersSingletonDataRelationshipDocument>

    /**
     * Get current user&#39;s user data
     * Retrieves current user&#39;s user details.
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
     * @param include Allows the client to customize which related resources should be returned. Available options: entitlements, publicProfile, recommendations (optional)
     * @return [UsersSingleDataDocument]
     */
    @GET("users/me")
    suspend fun usersMeGet(
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UsersSingleDataDocument>
}
