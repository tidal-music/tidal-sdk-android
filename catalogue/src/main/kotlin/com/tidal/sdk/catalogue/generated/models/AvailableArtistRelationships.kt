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

import com.tidal.sdk.catalogue.generated.models.ResourceRelationship

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual

/**
 * Available artist relationships
 *
 * @param similarArtists 
 * @param albums 
 * @param videos 
 * @param tracks 
 */
@Serializable

data class AvailableArtistRelationships (

    @SerialName(value = "similarArtists")
    val similarArtists: ResourceRelationship? = null,

    @SerialName(value = "albums")
    val albums: ResourceRelationship? = null,

    @SerialName(value = "videos")
    val videos: ResourceRelationship? = null,

    @SerialName(value = "tracks")
    val tracks: ResourceRelationship? = null

)

