package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.UsersArtistSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.UsersSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Users {
    /**
     * GET users/{id} Get single user. Retrieves single user by id. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [UsersSingleResourceDataDocument]
     */
    @GET("users/{id}")
    suspend fun usersIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UsersSingleResourceDataDocument>

    /**
     * GET users/{id}/relationships/artist Get artist relationship (\&quot;to-one\&quot;). Retrieves
     * artist relationship. Responses:
     * - 200: Successful response
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param id User id. Use &#x60;me&#x60; for the authenticated user&#39;s resource
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [UsersArtistSingleRelationshipDataDocument]
     */
    @GET("users/{id}/relationships/artist")
    suspend fun usersIdRelationshipsArtistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<UsersArtistSingleRelationshipDataDocument>
}
