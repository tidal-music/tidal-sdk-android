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
 * @param type
 * @param id
 */

@Serializable
data class UpdatePickRelationshipBodyData(

    @SerialName(value = "type")
    val type: kotlin.String? = null,

    @SerialName(value = "id")
    val id: kotlin.String? = null,
)
