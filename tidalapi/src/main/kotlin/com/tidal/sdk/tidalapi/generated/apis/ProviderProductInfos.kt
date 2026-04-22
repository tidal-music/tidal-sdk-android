package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProviderProductInfosMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProviderProductInfosSingleRelationshipDataDocument
import retrofit2.Response
import retrofit2.http.*

interface ProviderProductInfos {
    /**
     * Get multiple providerProductInfos. Retrieves multiple providerProductInfos by available
     * filters, or without if applicable. Responses:
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
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: provider, subject (optional)
     * @param filterBarcodeId List of barcode IDs (EAN-13 or UPC-A) (e.g.
     *   &#x60;00602527336510&#x60;) (optional)
     * @param filterProviderId Content provider ID (e.g. &#x60;50&#x60;) (optional)
     * @return [ProviderProductInfosMultiResourceDataDocument]
     */
    @GET("providerProductInfos")
    suspend fun providerProductInfosGet(
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[barcodeId]")
        filterBarcodeId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[provider.id]")
        filterProviderId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderProductInfosMultiResourceDataDocument>

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
     * @param id Provider product info id
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: provider (optional)
     * @return [ProviderProductInfosSingleRelationshipDataDocument]
     */
    @GET("providerProductInfos/{id}/relationships/provider")
    suspend fun providerProductInfosIdRelationshipsProviderGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderProductInfosSingleRelationshipDataDocument>

    /**
     * Get subject relationship (\&quot;to-one\&quot;). Retrieves subject relationship. Responses:
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
     * @param id Provider product info id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Allows the client to customize which related resources should be returned.
     *   Available options: subject (optional)
     * @return [ProviderProductInfosSingleRelationshipDataDocument]
     */
    @GET("providerProductInfos/{id}/relationships/subject")
    suspend fun providerProductInfosIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderProductInfosSingleRelationshipDataDocument>
}
