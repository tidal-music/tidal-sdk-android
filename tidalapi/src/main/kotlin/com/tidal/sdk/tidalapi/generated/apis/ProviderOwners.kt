package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProviderOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProviderOwnersMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProviderOwnersSingleRelationshipDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ProviderOwners {
    /**
     * Get multiple providerOwners. Retrieves multiple providerOwners by available filters, or
     * without if applicable. Responses:
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
     *   Available options: owners, provider (optional)
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user (optional)
     * @return [ProviderOwnersMultiResourceDataDocument]
     */
    @GET("providerOwners")
    suspend fun providerOwnersGet(
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderOwnersMultiResourceDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id Provider owner id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ProviderOwnersMultiRelationshipDataDocument]
     */
    @GET("providerOwners/{id}/relationships/owners")
    suspend fun providerOwnersIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ProviderOwnersMultiRelationshipDataDocument>

    /**
     * Get provider relationship (\&quot;to-one\&quot;). Retrieves provider relationship. Responses:
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
     * @param id Provider owner id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: provider (optional)
     * @return [ProviderOwnersSingleRelationshipDataDocument]
     */
    @GET("providerOwners/{id}/relationships/provider")
    suspend fun providerOwnersIdRelationshipsProviderGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderOwnersSingleRelationshipDataDocument>
}
