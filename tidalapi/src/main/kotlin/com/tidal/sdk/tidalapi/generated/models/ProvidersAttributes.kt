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
 * attributes object representing some of the resource's data
 *
 * @param name Provider name. Conditionally visible.
 */

@Serializable
data class ProvidersAttributes(

    // Provider name. Conditionally visible.

    @SerialName(value = "name")
    val name: kotlin.String,
)