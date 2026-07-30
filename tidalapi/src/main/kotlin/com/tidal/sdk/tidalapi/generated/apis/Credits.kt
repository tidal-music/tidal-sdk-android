package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.CreditsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CreditsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Credits {
    /**
     * Get single credit. Retrieves single credit by id. Responses:
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
     * @param id Credit id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artist, category (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [CreditsSingleResourceDataDocument]
     */
    @GET("credits/{id}")
    suspend fun creditsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<CreditsSingleResourceDataDocument>

    /**
     * Get artist relationship (\&quot;to-one\&quot;). Retrieves artist relationship. Responses:
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
     * @param id Credit id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [CreditsSingleRelationshipDataDocument]
     */
    @GET("credits/{id}/relationships/artist")
    suspend fun creditsIdRelationshipsArtistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<CreditsSingleRelationshipDataDocument>

    /**
     * Get category relationship (\&quot;to-one\&quot;). Retrieves category relationship. Responses:
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
     * @param id Credit id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: category (optional)
     * @return [CreditsSingleRelationshipDataDocument]
     */
    @GET("credits/{id}/relationships/category")
    suspend fun creditsIdRelationshipsCategoryGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CreditsSingleRelationshipDataDocument>
}
