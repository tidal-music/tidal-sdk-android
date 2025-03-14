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
import kotlinx.serialization.Transient

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
@SerialName(value = "userPublicProfiles")
data class UserPublicProfilesResource(

    // resource unique identifier

    @SerialName(value = "id")
    val id: kotlin.String,
    // resource unique type
    @Transient
    @SerialName(value = "type")
    val type: kotlin.String = "",

    @SerialName(value = "attributes")
    val attributes: UserPublicProfilesAttributes? = null,

    @SerialName(value = "relationships")
    val relationships: UserPublicProfilesRelationships? = null,

    @SerialName(value = "links")
    val links: Links? = null,
) : UserPublicProfilesMultiDataRelationshipDocumentIncludedInner, UsersMultiDataDocumentIncludedInner, UsersSingletonDataRelationshipDocumentIncludedInner, UserPublicProfilePicksSingletonDataRelationshipDocumentIncludedInner, UserPublicProfilePicksMultiDataDocumentIncludedInner
