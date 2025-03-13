package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilePicksMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilePicksSingletonDataRelationshipDocument
import retrofit2.Response
import retrofit2.http.*

interface UserPublicProfilePicks {
    /**
     * Get all userPublicProfilePicks
     * Retrieves all userPublicProfilePick details by available filters or without (if applicable).
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
     * @param include Allows the client to customize which related resources should be returned. Available options: item, userPublicProfile (optional)
     * @param filterUserPublicProfileId Allows to filter the collection of resources based on userPublicProfile.id attribute value (optional)
     * @param filterId Allows to filter the collection of resources based on id attribute value (optional)
     * @return [UserPublicProfilePicksMultiDataDocument]
     */
    @GET("userPublicProfilePicks")
    suspend fun userPublicProfilePicksGet(
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[userPublicProfile.id]") filterUserPublicProfileId:
        @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<UserPublicProfilePicksMultiDataDocument>

    /**
     * Relationship: item(read)
     * Retrieves item relationship details of the related userPublicProfilePick resource.
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
     * @param id User public profile pick id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: item (optional)
     * @return [UserPublicProfilePicksSingletonDataRelationshipDocument]
     */
    @GET("userPublicProfilePicks/{id}/relationships/item")
    suspend fun userPublicProfilePicksIdRelationshipsItemGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilePicksSingletonDataRelationshipDocument>

    /**
     * Relationship: userPublicProfile(read)
     * Retrieves userPublicProfile relationship details of the related userPublicProfilePick resource.
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
     * @param id User public profile pick id
     * @param include Allows the client to customize which related resources should be returned. Available options: userPublicProfile (optional)
     * @return [UserPublicProfilePicksSingletonDataRelationshipDocument]
     */
    @GET("userPublicProfilePicks/{id}/relationships/userPublicProfile")
    suspend fun userPublicProfilePicksIdRelationshipsUserPublicProfileGet(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilePicksSingletonDataRelationshipDocument>

    /**
     * Get current user&#39;s userPublicProfilePick data
     * Retrieves current user&#39;s userPublicProfilePick details.
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
     * @param id User public profile pick id
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param locale BCP47 locale code
     * @param include Allows the client to customize which related resources should be returned. Available options: item, userPublicProfile (optional)
     * @return [UserPublicProfilePicksMultiDataDocument]
     */
    @GET("userPublicProfilePicks/me")
    suspend fun userPublicProfilePicksMeGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilePicksMultiDataDocument>
}
