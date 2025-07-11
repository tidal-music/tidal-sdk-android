/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param id
 * @param meta
 * @param type
 */
@Serializable
data class PlaylistItemsRelationshipReorderOperationPayloadData(
    @SerialName(value = "id") val id: kotlin.String,
    @SerialName(value = "meta") val meta: PlaylistItemsRelationshipReorderOperationPayloadDataMeta,
    @SerialName(value = "type") val type: PlaylistItemsRelationshipReorderOperationPayloadData.Type,
) {

    /** Values: tracks,videos */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "tracks") tracks("tracks"),
        @SerialName(value = "videos") videos("videos"),
    }
}
