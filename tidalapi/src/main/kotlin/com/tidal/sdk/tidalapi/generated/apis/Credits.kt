package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.CreditsArtistSingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CreditsCategorySingleRelationshipDataDocument
import com.tidal.sdk.tidalapi.generated.models.CreditsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Credits {

    /** enum for parameter includeLinkage */
    @Serializable
    enum class IncludeLinkageCreditsIdGet(val value: kotlin.String) {
        @SerialName(value = "artist") artist("artist"),
        @SerialName(value = "category") category("category"),
    }

    /**
     * GET credits/{id} Get single credit. Retrieves single credit by id. Responses:
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
     * @param include Include related resources. Available relationships: artist, category
     *   (optional)
     * @param includeLinkage Comma-separated direct relationships to return as linkage only, without
     *   related content. (optional)
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
        @Query("includeLinkage") includeLinkage: CSVParams? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<CreditsSingleResourceDataDocument>

    /**
     * GET credits/{id}/relationships/artist Get artist relationship (\&quot;to-one\&quot;).
     * Retrieves artist relationship. Responses:
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
     * @param include Include related resources. Available relationships: artist (optional)
     * @param replaceMedia Applies context-dependent replacements to media resource identifiers in
     *   selected relationships without changing stored data. Paths are comma-separated and follow
     *   &#x60;include&#x60; syntax. Example: artist.albums (optional)
     * @return [CreditsArtistSingleRelationshipDataDocument]
     */
    @GET("credits/{id}/relationships/artist")
    suspend fun creditsIdRelationshipsArtistGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("replaceMedia") replaceMedia: kotlin.String? = null,
    ): Response<CreditsArtistSingleRelationshipDataDocument>

    /**
     * GET credits/{id}/relationships/category Get category relationship (\&quot;to-one\&quot;).
     * Retrieves category relationship. Responses:
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
     * @param include Include related resources. Available relationships: category (optional)
     * @return [CreditsCategorySingleRelationshipDataDocument]
     */
    @GET("credits/{id}/relationships/category")
    suspend fun creditsIdRelationshipsCategoryGet(
        @Path("id") id: kotlin.String,
        @Query("include")
        include: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<CreditsCategorySingleRelationshipDataDocument>
}
