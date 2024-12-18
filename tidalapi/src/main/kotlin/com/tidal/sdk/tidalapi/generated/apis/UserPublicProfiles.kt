package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UpdateUserProfileBody
import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilesMultiDataDocument
import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilesMultiDataRelationshipDocument
import com.tidal.sdk.tidalapi.generated.models.UserPublicProfilesSingleDataDocument
import retrofit2.Response
import retrofit2.http.*

interface UserPublicProfiles {
    /**
     * Get my user profile
     * Retrieve the logged-in user&#39;s public profile details.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: User profile retrieved successfully
     *
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: publicPlaylists, publicPicks, followers, following (optional)
     * @return [UserPublicProfilesSingleDataDocument]
     */
    @GET("userPublicProfiles/me")
    suspend fun getMyUserPublicProfile(
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilesSingleDataDocument>

    /**
     * Get user public profile by id
     * Retrieve user public profile details by TIDAL user id.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: User profile retrieved successfully
     *
     * @param id TIDAL user id
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: publicPlaylists, publicPicks, followers, following (optional)
     * @return [UserPublicProfilesSingleDataDocument]
     */
    @GET("userPublicProfiles/{id}")
    suspend fun getUserPublicProfileById(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilesSingleDataDocument>

    /**
     * Relationship: followers
     * Retrieve user&#39;s public followers
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Public followers retrieved successfully
     *
     * @param id TIDAL user id
     * @param include Allows the client to customize which related resources should be returned. Available options: followers (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserPublicProfilesMultiDataRelationshipDocument]
     */
    @GET("userPublicProfiles/{id}/relationships/followers")
    suspend fun getUserPublicProfileFollowersRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserPublicProfilesMultiDataRelationshipDocument>

    /**
     * Relationship: following
     * Retrieve user&#39;s public followings
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Public following retrieved successfully
     *
     * @param id TIDAL user id
     * @param include Allows the client to customize which related resources should be returned. Available options: following (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserPublicProfilesMultiDataRelationshipDocument]
     */
    @GET("userPublicProfiles/{id}/relationships/following")
    suspend fun getUserPublicProfileFollowingRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserPublicProfilesMultiDataRelationshipDocument>

    /**
     * Relationship: picks
     * Retrieve user&#39;s public picks.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Public picks retrieved successfully
     *
     * @param id TIDAL user id
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: publicPicks (optional)
     * @return [UserPublicProfilesMultiDataRelationshipDocument]
     */
    @GET("userPublicProfiles/{id}/relationships/publicPicks")
    suspend fun getUserPublicProfilePublicPicksRelationship(
        @Path("id") id: kotlin.String,
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<UserPublicProfilesMultiDataRelationshipDocument>

    /**
     * Relationship: playlists
     * Retrieves user&#39;s public playlists.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Public playlists retrieved successfully
     *
     * @param id TIDAL user id
     * @param include Allows the client to customize which related resources should be returned. Available options: publicPlaylists (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional, targets first page if not specified (optional)
     * @return [UserPublicProfilesMultiDataRelationshipDocument]
     */
    @GET("userPublicProfiles/{id}/relationships/publicPlaylists")
    suspend fun getUserPublicProfilePublicPlaylistsRelationship(
        @Path("id") id: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<UserPublicProfilesMultiDataRelationshipDocument>

    /**
     * Get user public profiles
     * Reads user public profile details by TIDAL user ids.
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: User profile retrieved successfully
     *
     * @param locale Locale language tag (IETF BCP 47 Language Tag)
     * @param include Allows the client to customize which related resources should be returned. Available options: publicPlaylists, publicPicks, followers, following (optional)
     * @param filterId TIDAL user id (optional)
     * @return [UserPublicProfilesMultiDataDocument]
     */
    @GET("userPublicProfiles")
    suspend fun getUserPublicProfilesByFilters(
        @Query("locale") locale: kotlin.String,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]") filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
    ): Response<UserPublicProfilesMultiDataDocument>

    /**
     * Update user public profile
     * Update user public profile
     * Responses:
     *  - 404: Resource not found. The requested resource is not found.
     *  - 500: Internal Server Error. Something went wrong on the server party.
     *  - 405: Method not supported. Ensure a proper HTTP method for an HTTP request is used.
     *  - 400: Bad request on client party. Ensure the proper HTTP request is sent (query parameters, request body, etc.).
     *  - 415: Unsupported Media Type. The API is using content negotiation. Ensure the proper media type is set into Content-Type header.
     *  - 406: Not acceptable. The server doesn't support any of the requested by client acceptable content types.
     *  - 200: Updated Public Profile successfully
     *
     * @param id ${public.usercontent.updateProfile.id.descr}
     * @param updateUserProfileBody
     * @param include Allows the client to customize which related resources should be returned (optional)
     * @return [kotlin.Any]
     */
    @PATCH("userPublicProfiles/{id}")
    suspend fun updateMyUserProfile(
        @Path("id") id: kotlin.String,
        @Body updateUserProfileBody: UpdateUserProfileBody,
        @Query("include") include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<kotlin.Any>
}
