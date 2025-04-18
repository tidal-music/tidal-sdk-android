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


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Transient

/**
 * metadata about an image
 *
 * @param width image width (in pixels)
 * @param height image height (in pixels)
 */

@Serializable

data class ImageLinkMeta (

    /* image width (in pixels) */
    
    @SerialName(value = "width")
    val width: kotlin.Int,
    /* image height (in pixels) */
    
    @SerialName(value = "height")
    val height: kotlin.Int
) {


}

