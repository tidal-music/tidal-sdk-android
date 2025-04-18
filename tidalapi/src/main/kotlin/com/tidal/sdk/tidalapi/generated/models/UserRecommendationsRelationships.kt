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

import com.tidal.sdk.tidalapi.generated.models.MultiDataRelationshipDoc

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Transient

/**
 * 
 *
 * @param discoveryMixes 
 * @param newArrivalMixes 
 * @param myMixes 
 */

@Serializable

data class UserRecommendationsRelationships (

    
    @SerialName(value = "discoveryMixes")
    val discoveryMixes: MultiDataRelationshipDoc,
    
    @SerialName(value = "newArrivalMixes")
    val newArrivalMixes: MultiDataRelationshipDoc,
    
    @SerialName(value = "myMixes")
    val myMixes: MultiDataRelationshipDoc
) {


}

