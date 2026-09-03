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
        @SerialName(value = "MERCH_GUIDELINES") MERCH_GUIDELINES("MERCH_GUIDELINES"),
    }

    /**
     * Get multiple terms. Retrieves multiple terms by available filters, or without if applicable.
     * Responses:
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
     * @param filterTermsType One of: DEVELOPER, UPLOAD_MARKETPLACE, MERCH_GUIDELINES (e.g.
     *   &#x60;DEVELOPER&#x60;)
     * @param filterCountryCode Selects the country-specific terms variant when it has an effective
     *   version; otherwise falls back to the worldwide (WW) variant. Combine with
     *   &#x60;filter[isLatestVersion]&#x3D;true&#x60; to return the currently acceptable terms.
     *   (e.g. &#x60;US&#x60;) (optional)
     * @param filterIsLatestVersion Filter by isLatestVersion (optional)
     * @return [TermsMultiResourceDataDocument]
     */
    @GET("terms")
    suspend fun termsGet(
        @Query("filter[termsType]")
        filterTermsType: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>,
        @Query("filter[countryCode]")
        filterCountryCode: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
        @Query("filter[isLatestVersion]")
        filterIsLatestVersion: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? = null,
    ): Response<TermsMultiResourceDataDocument>

    /**
     * Get single term. Retrieves single term by id. Responses:
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
     * @param id Terms id
     * @return [TermsSingleResourceDataDocument]
     */
    @GET("terms/{id}")
    suspend fun termsIdGet(@Path("id") id: kotlin.String): Response<TermsSingleResourceDataDocument>
}
