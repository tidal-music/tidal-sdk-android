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

import com.tidal.sdk.tidalapi.generated.models.ErrorObject
import com.tidal.sdk.tidalapi.generated.models.Links

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Transient

/**
 * JSON:API error document object
 *
 * @param errors array of error objects
 * @param links 
 */

@Serializable

data class ErrorDocument (

    /* array of error objects */
    
    @SerialName(value = "errors")
    val errors: kotlin.collections.List<ErrorObject>? = null,
    
    @SerialName(value = "links")
    val links: Links? = null
) {


}

