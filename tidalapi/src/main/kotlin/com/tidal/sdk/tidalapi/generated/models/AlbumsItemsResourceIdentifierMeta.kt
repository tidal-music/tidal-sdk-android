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
 *
 *
 * @param volumeNumber volume number
 * @param trackNumber track number
 */

@Serializable
data class AlbumsItemsResourceIdentifierMeta(

    // volume number

    @SerialName(value = "volumeNumber")
    val volumeNumber: kotlin.Int,
    // track number

    @SerialName(value = "trackNumber")
    val trackNumber: kotlin.Int,
)