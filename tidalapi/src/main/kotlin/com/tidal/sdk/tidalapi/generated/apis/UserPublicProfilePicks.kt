package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UpdatePickRelationshipBody
import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilePicksMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilePicksSingletonDataRelationshipDocument
import retrofit2.Response
import retrofit2.http.*

interface UserPublicProfilePicks {
    /**
     * Get my picks
     * Retrieves picks for the logged-in user.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: item, userPublicProfile (optional)
     * @return [UserPublicProfilePicksMultiDataDocument]
     */
    @GET("userPublicProfilePicks/me")
    suspend fun getMyUserPublicProfilePicks(
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilePicksMultiDataDocument>

    /**
     * Relationship: item (read)
     * Retrieves a picks item relationship
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL pick id
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: item (optional)
     * @return [UserPublicProfilePicksSingletonDataRelationshipDocument]
     */
    @GET("userPublicProfilePicks/{id}/relationships/item")
    suspend fun getUserPublicProfilePickItemRelationship(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilePicksSingletonDataRelationshipDocument>

    /**
     * Relationship: user public profile
     * Retrieves a picks owner public profile
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param id TIDAL pick id
     * @param include Allows the client to customize which related resources should be returned. Available options: userPublicProfile (optional)
     * @return [UserPublicProfilePicksSingletonDataRelationshipDocument]
     */
    @GET("userPublicProfilePicks/{id}/relationships/userPublicProfile")
    suspend fun getUserPublicProfilePickUserPublicProfileRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilePicksSingletonDataRelationshipDocument>

    /**
     * Get picks
     * Retrieves a filtered collection of user&#39;s public picks.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Successfully executed request.
     *
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: item, userPublicProfile (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @param filterUserPublicProfileId Allows to filter the collection of resources based on userPublicProfile.id attribute value (optional)
     * @return [UserPublicProfilePicksMultiDataDocument]
     */
    @GET("userPublicProfilePicks")
    suspend fun getUserPublicProfilePicksByFilters(
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[userPublicProfile.id]") filterUserPublicProfileId:
        @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilePicksMultiDataDocument>

    /**
     * Relationship: item (update)
     * Updates a picks item relationship, e.g. sets a &#39;track&#39;, &#39;album&#39; or &#39;artist&#39; reference.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 202: Successfully executed request.
     *
     * @param id TIDAL pick id
     * @param updatePickRelationshipBody
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @return [kotlin.Any]
     */
    @PATCH("userPublicProfilePicks/{id}/relationships/item")
    suspend fun setUserPublicProfilePickItemRelationship(
        @Path("id") id: kotlin.String,
        @Body updatePickRelationshipBody: UpdatePickRelationshipBody,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<kotlin.Any>
}
