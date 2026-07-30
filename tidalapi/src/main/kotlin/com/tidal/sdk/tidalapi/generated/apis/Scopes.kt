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
     * - 400: Invalid request
     * - 404: Resource not found
     * - 405: HTTP method not allowed
     * - 406: No acceptable response media type
     * - 415: Unsupported request media type or encoding
     * - 429: Rate limit exceeded
     * - 500: Internal server error
     * - 503: Service temporarily unavailable
     *
     * @param filterRequiredAccessTier Filters scopes by their &#x60;requiredAccessTier&#x60;. (e.g.
     *   &#x60;THIRD_PARTY&#x60;)
     * @return [ScopesMultiResourceDataDocument]
     */
    @GET("scopes")
    suspend fun scopesGet(
        @Query("filter[requiredAccessTier]")
        filterRequiredAccessTier: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>
    ): Response<ScopesMultiResourceDataDocument>
}
