package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.models.ScopesMultiResourceDataDocument
import kotlinx.serialization.SerialName
import retrofit2.Response
import retrofit2.http.*

interface Scopes {

    /** enum for parameter filterRequiredAccessTier */
    enum class FilterRequiredAccessTierScopesGet(val value: kotlin.String) {
        @SerialName(value = "THIRD_PARTY") THIRD_PARTY("THIRD_PARTY"),
        @SerialName(value = "THIRD_PARTY_PROD") THIRD_PARTY_PROD("THIRD_PARTY_PROD"),
        @SerialName(value = "PARTNER") PARTNER("PARTNER"),
        @SerialName(value = "INTERNAL") INTERNAL("INTERNAL"),
    }

    /**
     * Get multiple scopes. Retrieves multiple scopes by available filters, or without if
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
     * @param filterRequiredAccessTier Filters scopes by their &#x60;requiredAccessTier&#x60;. (e.g.
     *   &#x60;THIRD_PARTY&#x60;) (optional)
     * @return [ScopesMultiResourceDataDocument]
     */
    @GET("scopes")
    suspend fun scopesGet(
        @Query("filter[requiredAccessTier]")
        filterRequiredAccessTier: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null
    ): Response<ScopesMultiResourceDataDocument>
}
