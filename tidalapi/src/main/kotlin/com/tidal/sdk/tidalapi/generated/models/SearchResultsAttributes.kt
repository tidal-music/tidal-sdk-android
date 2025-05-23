/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param trackingId search request unique tracking number
 * @param didYouMean 'did you mean' prompt
 */
@Serializable
data class SearchResultsAttributes(

    /* search request unique tracking number */

    @SerialName(value = "trackingId") val trackingId: kotlin.String,
    /* 'did you mean' prompt */

    @SerialName(value = "didYouMean") val didYouMean: kotlin.String? = null,
) {}
