package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.CreditsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.CreditsSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CreditsSingleResourceDataDocument
import retrofit2.Response
import retrofit2.http.*

interface Credits {
    /**
     * Get multiple credits. Retrieves multiple credits by available filters, or without if
     * applicable. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artist, category (optional)
     * @param filterId Credit id (optional)
     * @return [CreditsMultiResourceDataDocument]
     */
    @GET("credits")
    suspend fun creditsGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CreditsMultiResourceDataDocument>

    /**
     * Get single credit. Retrieves single credit by id. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Credit id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artist, category (optional)
     * @return [CreditsSingleResourceDataDocument]
     */
    @GET("credits/{id}")
    suspend fun creditsIdGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CreditsSingleResourceDataDocument>

    /**
     * Get artist relationship (\&quot;to-one\&quot;). Retrieves artist relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
     *
     * @param id Credit id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: artist (optional)
     * @return [CreditsSingleRelationshipDataDocument]
     */
    @GET("credits/{id}/relationships/artist")
    suspend fun creditsIdRelationshipsArtistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CreditsSingleRelationshipDataDocument>

    /**
     * Get category relationship (\&quot;to-one\&quot;). Retrieves category relationship. Responses:
     * - 200: Successful response
     * - 400: The request is malformed or invalid
     * - 404: The requested resource was not found
     * - 405: The HTTP method is not allowed for the requested resource
     * - 406: A response that satisfies the content negotiation headers cannot be produced
     * - 415: Unsupported request payload media type or content encoding
     * - 429: Rate limit exceeded
     * - 500: An unexpected error was encountered
     * - 503: Temporarily unavailable; please try again later
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
