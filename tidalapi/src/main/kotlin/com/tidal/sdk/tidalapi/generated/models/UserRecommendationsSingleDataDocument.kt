/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 *
 * @param `data`
 * @param links
 * @param included
 */

@Serializable
data class UserRecommendationsSingleDataDocument(

    @SerialName(value = "data")
    val `data`: UserRecommendationsResource? = null,

    @SerialName(value = "links")
    val links: Links? = null,

    @SerialName(value = "included")
    val included: kotlin.collections.List<UserRecommendationsMultiDataDocumentIncludedInner>? = null,
)