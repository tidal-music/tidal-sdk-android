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

import com.tidal.sdk.tidalapi.generated.models.PlaylistUpdateOperationPayloadDataAttributes

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Transient

/**
 * 
 *
 * @param id 
 * @param type 
 * @param attributes 
 */

@Serializable

data class PlaylistUpdateOperationPayloadData (

    
    @SerialName(value = "id")
    val id: kotlin.String,
    
    @SerialName(value = "type")
    val type: PlaylistUpdateOperationPayloadData.Type,
    
    @SerialName(value = "attributes")
    val attributes: PlaylistUpdateOperationPayloadDataAttributes
) {

    /**
     * 
     *
     * Values: playlists
     */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "playlists") playlists("playlists");
    }

}

