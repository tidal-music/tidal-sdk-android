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

package com.tidal.sdk.catalogue.generated.models

import com.tidal.sdk.catalogue.generated.models.Links
import com.tidal.sdk.catalogue.generated.models.Relationship
import com.tidal.sdk.catalogue.generated.models.VideoRelationship

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * array of resource objects that are related to the primary data and/or each other
 *
 * @param id resource unique identifier
 * @param type resource unique type
 * @param attributes 
 * @param relationships relationships object describing relationships between the resource and other resources
 * @param links 
 */
@Serializable

data class VideoRelationshipResource (

    /* resource unique identifier */
    @SerialName(value = "id")
    val id: kotlin.String,

    /* resource unique type */
    @SerialName(value = "type")
    val type: kotlin.String,

    @SerialName(value = "attributes")
    val attributes: VideoRelationship? = null,

    /* relationships object describing relationships between the resource and other resources */
    @Contextual @SerialName(value = "relationships")
    val relationships: kotlin.collections.Map<kotlin.String, Relationship>? = null,

    @SerialName(value = "links")
    val links: Links? = null

)
