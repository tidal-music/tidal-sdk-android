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
 * primary resource data
 *
 * @param id resource unique identifier
 * @param type resource unique type
 * @param attributes
 * @param relationships
 * @param links
 */

@Serializable
data class SearchResultsResource(

    // resource unique identifier

    @SerialName(value = "id")
    val id: kotlin.String,
    // resource unique type

    @SerialName(value = "type")
    val type: kotlin.String,

    @SerialName(value = "attributes")
    val attributes: SearchResultsAttributes? = null,

    @SerialName(value = "relationships")
    val relationships: SearchResultsRelationships? = null,

    @SerialName(value = "links")
    val links: Links? = null,
)