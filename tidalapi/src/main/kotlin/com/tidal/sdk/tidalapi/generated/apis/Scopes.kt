package com.tidal.sdk.tidalapi.generated.apis

import com.tidal.sdk.tidalapi.generated.infrastructure.CollectionFormats.*
import com.tidal.sdk.tidalapi.generated.models.ScopesMultiResourceDataDocument
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.*

interface Scopes {

    /** enum for parameter filterRequiredAccessTier */
    @Serializable
    enum class FilterRequiredAccessTierScopesGet(val value: kotlin.String) {
        @SerialName(value = "THIRD_PARTY") THIRD_PARTY("THIRD_PARTY"),
        @SerialName(value = "THIRD_PARTY_PROD") THIRD_PARTY_PROD("THIRD_PARTY_PROD"),
        @SerialName(value = "PARTNER") PARTNER("PARTNER"),
        @SerialName(value = "INTERNAL") INTERNAL("INTERNAL"),
    }

    /** enum for parameter filterRequiredAccessTierSatisfiedBy */
    @Serializable
    enum class FilterRequiredAccessTierSatisfiedByScopesGet(val value: kotlin.String) {
        @SerialName(value = "THIRD_PARTY") THIRD_PARTY("THIRD_PARTY"),
        @SerialName(value = "THIRD_PARTY_PROD") THIRD_PARTY_PROD("THIRD_PARTY_PROD"),
        @SerialName(value = "PARTNER") PARTNER("PARTNER"),
        @SerialName(value = "INTERNAL") INTERNAL("INTERNAL"),
    }

    /**
     * GET scopes Get multiple scopes. Retrieves multiple scopes by available filters, or without if
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
     * @param filterRequiredAccessTier Filters scopes by their required client access tier. (e.g.
     *   &#x60;THIRD_PARTY&#x60;) (optional)
     * @param filterRequiredAccessTierSatisfiedBy Selects scopes whose required access tier is
     *   satisfied by the client tier. (e.g. &#x60;PARTNER&#x60;) (optional)
     * @return [ScopesMultiResourceDataDocument]
     */
    @GET("scopes")
    suspend fun scopesGet(
        @Query("filter[requiredAccessTier]")
        filterRequiredAccessTier: @JvmSuppressWildcards kotlin.collections.List<kotlin.String>? =
            null,
        @Query("filter[requiredAccessTier][satisfiedBy]")
        filterRequiredAccessTierSatisfiedBy: FilterRequiredAccessTierSatisfiedByScopesGet? = null,
    ): Response<ScopesMultiResourceDataDocument>
}
