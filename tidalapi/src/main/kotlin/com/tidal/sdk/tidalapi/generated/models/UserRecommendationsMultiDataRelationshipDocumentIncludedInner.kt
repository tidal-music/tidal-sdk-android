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

import com.tidal.sdk.tidalapi.generated.models.PlaylistsResource
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsRelationships
import com.tidal.sdk.tidalapi.generated.models.UserRecommendationsResource

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
 * @param relationships 
 */
@Serializable
@Polymorphic
sealed interface UserRecommendationsMultiDataRelationshipDocumentIncludedInner

