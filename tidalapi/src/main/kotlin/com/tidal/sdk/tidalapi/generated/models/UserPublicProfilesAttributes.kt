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
 * @param color
 * @param profileName Public Name of the user profile
 * @param picture
 * @param externalLinks ExternalLinks for the user's profile
 * @param numberOfFollowers Number of followers for the user
 * @param numberOfFollows Number of users the user follows
 */

@Serializable
data class UserPublicProfilesAttributes(

    @SerialName(value = "color")
    val color: kotlin.collections.List<kotlin.String>,
    // Public Name of the user profile

    @SerialName(value = "profileName")
    val profileName: kotlin.String? = null,

    @SerialName(value = "picture")
    val picture: UserPublicProfilesImageLink? = null,
    // ExternalLinks for the user's profile

    @SerialName(value = "externalLinks")
    val externalLinks: kotlin.collections.List<UserPublicProfilesExternalLink>? = null,
    // Number of followers for the user

    @SerialName(value = "numberOfFollowers")
    val numberOfFollowers: kotlin.Int? = null,
    // Number of users the user follows

    @SerialName(value = "numberOfFollows")
    val numberOfFollows: kotlin.Int? = null,
)