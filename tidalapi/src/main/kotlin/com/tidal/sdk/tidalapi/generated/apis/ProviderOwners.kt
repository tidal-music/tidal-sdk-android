package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProviderOwnersMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProviderOwnersOwnersMultiRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProviderOwnersProviderSingleRelationshipDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ProviderOwners {
    /**
     * Get multiple providerOwners. Retrieves multiple providerOwners by available filters, or
     * without if applicable. Responses:
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
     * @param filterOwnersId User id. Use &#x60;me&#x60; for the authenticated user
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners, provider (optional)
     * @return [ProviderOwnersMultiResourceDataDocument]
     */
    @GET("providerOwners")
    suspend fun providerOwnersGet(
        @Query("filter[owners.id]")
        filterOwnersId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderOwnersMultiResourceDataDocument>

    /**
     * Get owners relationship (\&quot;to-many\&quot;). Retrieves owners relationship. Responses:
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
     * @param id Provider owner id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: owners (optional)
     * @param pageCursor Server-generated cursor value pointing a certain page of items. Optional,
     *   targets first page if not specified (optional)
     * @return [ProviderOwnersOwnersMultiRelationshipDataDocument]
     */
    @GET("providerOwners/{id}/relationships/owners")
    suspend fun providerOwnersIdRelationshipsOwnersGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("page[cursor]") pageCursor: kotlin.String? = null,
    ): Response<ProviderOwnersOwnersMultiRelationshipDataDocument>

    /**
     * Get provider relationship (\&quot;to-one\&quot;). Retrieves provider relationship. Responses:
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
     * @param id Provider owner id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: provider (optional)
     * @return [ProviderOwnersProviderSingleRelationshipDataDocument]
     */
    @GET("providerOwners/{id}/relationships/provider")
    suspend fun providerOwnersIdRelationshipsProviderGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderOwnersProviderSingleRelationshipDataDocument>
}
