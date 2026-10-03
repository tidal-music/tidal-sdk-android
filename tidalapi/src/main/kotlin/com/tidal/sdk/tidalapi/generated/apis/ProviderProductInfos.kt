package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ProviderProductInfosMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProviderProductInfosProviderSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.ProviderProductInfosSubjectSingleRelationshipDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface ProviderProductInfos {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageProviderProductInfosGet(val value: kotlin.String) {
        @SerialName(value = "provider") provider("provider"),
        @SerialName(value = "subject") subject("subject"),
    }

    /**
     * GET providerProductInfos Get multiple providerProductInfos. Retrieves multiple
     * providerProductInfos by available filters, or without if applicable. Responses:
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
     * @param filterProviderId Content provider ID (e.g. &#x60;50&#x60;)
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: provider, subject
     *   (optional)
     * @param filterBarcodeId List of barcode IDs (EAN-13 or UPC-A) (e.g.
     *   &#x60;00602527336510&#x60;) (optional)
     * @param filterGrid List of GRIDs (Global Release Identifier, ISO 7064) (e.g.
     *   &#x60;A10302B0013941653J&#x60;) (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ProviderProductInfosMultiResourceDataDocument]
     */
    @GET("providerProductInfos")
    suspend fun providerProductInfosGet(
        @Query("filter[provider.id]")
        filterProviderId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[barcodeId]")
        filterBarcodeId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[grid]")
        filterGrid: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ProviderProductInfosMultiResourceDataDocument>

    /**
     * GET providerProductInfos/{id}/relationships/provider Get provider relationship
     * (\&quot;to-one\&quot;). Retrieves provider relationship. Responses:
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
     * @param id Provider product info id
     * @param include Include related resources. Available relationships: provider (optional)
     * @return [ProviderProductInfosProviderSingleRelationshipDataDocument]
     */
    @GET("providerProductInfos/{id}/relationships/provider")
    suspend fun providerProductInfosIdRelationshipsProviderGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<ProviderProductInfosProviderSingleRelationshipDataDocument>

    /**
     * GET providerProductInfos/{id}/relationships/subject Get subject relationship
     * (\&quot;to-one\&quot;). Retrieves subject relationship. Responses:
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
     * @param id Provider product info id
     * @param countryCode ISO 3166-1 alpha-2 country code (optional)
     * @param include Include related resources. Available relationships: subject (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: subject (optional)
     * @return [ProviderProductInfosSubjectSingleRelationshipDataDocument]
     */
    @GET("providerProductInfos/{id}/relationships/subject")
    suspend fun providerProductInfosIdRelationshipsSubjectGet(
        @Path("id") id: kotlin.String,
        @Query("countryCode") countryCode: kotlin.String? = null,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<ProviderProductInfosSubjectSingleRelationshipDataDocument>
}
