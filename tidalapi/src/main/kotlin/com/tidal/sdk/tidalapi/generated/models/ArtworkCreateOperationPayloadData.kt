/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param type
 * @param attributes
 */
@Serializable
data class ArtworkCreateOperationPayloadData(
    @SerialName(value = "type") val type: ArtworkCreateOperationPayloadData.Type,
    @SerialName(value = "attributes") val attributes: ArtworkCreateOperationPayloadDataAttributes,
) {

    /** Values: artworks */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "artworks") artworks("artworks")
    }
}
