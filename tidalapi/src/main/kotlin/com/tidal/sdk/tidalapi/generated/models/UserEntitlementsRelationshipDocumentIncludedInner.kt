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

import com.tidal.sdk.tidalapi.generated.models.Links
import com.tidal.sdk.tidalapi.generated.models.UserEntitlementsAttributes
import com.tidal.sdk.tidalapi.generated.models.UserEntitlementsResource

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Transient

/**
 * 
 *
 * @param id resource unique identifier
 * @param type resource unique type
 * @param attributes 
 * @param relationships relationships object describing relationships between the resource and other resources
 * @param links 
 */
@Serializable
@Polymorphic
sealed interface UserEntitlementsRelationshipDocumentIncludedInner

