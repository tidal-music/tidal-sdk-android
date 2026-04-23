package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.TermsMultiResourceDataDocument
import com.tidal.sdk.tidalapi.generated.models.TermsSingleResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Terms {

    /** enum for parameter filterTermsType */
    enum class FilterTermsTypeTermsGet(val value: kotlin.String) {
        @SerialName(value = "DEVELOPER") DEVELOPER("DEVELOPER"),
        @SerialName(value = "UPLOAD_MARKETPLACE") UPLOAD_MARKETPLACE("UPLOAD_MARKETPLACE"),
    }

    /**
     * Get multiple terms. Retrieves multiple terms by available filters, or without if applicable.
     * Responses:
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
     * @param filterCountryCode Filter by countryCode (optional)
     * @param filterId Terms id (e.g. &#x60;a468bee88def&#x60;) (optional)
     * @param filterIsLatestVersion Filter by isLatestVersion (optional)
     * @param filterTermsType One of: DEVELOPER, UPLOAD_MARKETPLACE (e.g. &#x60;DEVELOPER&#x60;)
     *   (optional)
     * @return [TermsMultiResourceDataDocument]
     */
    @GET("terms")
    suspend fun termsGet(
        @Query("filter[countryCode]")
        filterCountryCode: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[id]")
        filterId: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[isLatestVersion]")
        filterIsLatestVersion: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[termsType]")
        filterTermsType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TermsMultiResourceDataDocument>

    /**
     * Get single term. Retrieves single term by id. Responses:
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
     * @param id Terms id
     * @return [TermsSingleResourceDataDocument]
     */
    @GET("terms/{id}")
    suspend fun termsIdGet(@Path("id") id: kotlin.String): Response<TermsSingleResourceDataDocument>
}
