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
 * attributes object representing some of the resource's data
 *
 * @param name Playlist name
 * @param bounded Indicates if the playlist has a duration and set number of tracks
 * @param externalLinks Sharing links to the playlist
 * @param createdAt Datetime of playlist creation (ISO 8601)
 * @param lastModifiedAt Datetime of last modification of the playlist (ISO 8601)
 * @param privacy Privacy setting of the playlist
 * @param playlistType The type of the playlist
 * @param imageLinks Images associated with the playlist
 * @param description Playlist description
 * @param duration Duration of the playlist expressed in accordance with ISO 8601
 * @param numberOfItems Number of items in the playlist
 */

@Serializable
data class PlaylistsAttributes(

    // Playlist name

    @SerialName(value = "name")
    val name: kotlin.String,
    // Indicates if the playlist has a duration and set number of tracks

    @SerialName(value = "bounded")
    val bounded: kotlin.Boolean,
    // Sharing links to the playlist

    @SerialName(value = "externalLinks")
    val externalLinks: kotlin.collections.List<PlaylistsExternalLink>,
    // Datetime of playlist creation (ISO 8601)

    @SerialName(value = "createdAt")
    val createdAt: kotlin.String,
    // Datetime of last modification of the playlist (ISO 8601)

    @SerialName(value = "lastModifiedAt")
    val lastModifiedAt: kotlin.String,
    // Privacy setting of the playlist

    @SerialName(value = "privacy")
    val privacy: kotlin.String,
    // The type of the playlist

    @SerialName(value = "playlistType")
    val playlistType: kotlin.String,
    // Images associated with the playlist

    @SerialName(value = "imageLinks")
    val imageLinks: kotlin.collections.List<PlaylistsImageLink>,
    // Playlist description

    @SerialName(value = "description")
    val description: kotlin.String? = null,
    // Duration of the playlist expressed in accordance with ISO 8601

    @SerialName(value = "duration")
    val duration: kotlin.String? = null,
    // Number of items in the playlist

    @SerialName(value = "numberOfItems")
    val numberOfItems: kotlin.Int? = null,
)