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

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 *
 * @param id resource unique identifier
 * @param type resource unique type
 * @param attributes
 * @param relationships
 * @param links
 */

@Serializable
data class ResourceObjectObject(

    // resource unique identifier

    @SerialName(value = "id")
    val id: kotlin.String,
    // resource unique type

    @SerialName(value = "type")
    val type: kotlin.String,

    @Contextual @SerialName(value = "attributes")
    val attributes: kotlin.Any? = null,

    @Contextual @SerialName(value = "relationships")
    val relationships: kotlin.Any? = null,

    @SerialName(value = "links")
    val links: Links? = null,
)