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
@SerialName(value = "providers")
data class ProvidersResource(

    // resource unique identifier

    @SerialName(value = "id")
    val id: kotlin.String,
    // resource unique type
    @Transient
    @SerialName(value = "type")
    val type: kotlin.String = "",

    @SerialName(value = "attributes")
    val attributes: ProvidersAttributes? = null,
    // relationships object describing relationships between the resource and other resources

    @Contextual @SerialName(value = "relationships")
    val relationships: kotlin.Any? = null,

    @SerialName(value = "links")
    val links: Links? = null,
) : AlbumsMultiDataDocumentIncludedInner, TracksRelationshipsDocumentIncludedInner, AlbumsRelationshipDocumentIncludedInner, PlaylistsMultiDataDocumentIncludedInner, ProvidersRelationshipDocumentIncludedInner, ArtistsRelationshipDocumentIncludedInner, AlbumsItemsRelationshipDocumentIncludedInner, ArtistsMultiDataDocumentIncludedInner, VideosRelationshipsDocumentIncludedInner, UserPublicProfilePicksMultiDataDocumentIncludedInner, PlaylistsOwnersRelationshipDocumentIncludedInner, VideosMultiDataDocumentIncludedInner, UserPublicProfilePicksItemRelationshipDocumentIncludedInner, TracksMultiDataDocumentIncludedInner