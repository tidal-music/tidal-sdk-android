/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Artwork source file
 *
 * @param md5Hash MD5 hash of file to be uploaded
 * @param propertySize File size of the artwork in bytes
 * @param status
 * @param uploadLink
 */
@Serializable
data class ArtworkSourceFile(

    /* MD5 hash of file to be uploaded */

    @SerialName(value = "md5Hash") val md5Hash: kotlin.String,
    /* File size of the artwork in bytes */

    @SerialName(value = "size") val propertySize: kotlin.Long,
    @SerialName(value = "status") val status: FileStatus,
    @SerialName(value = "uploadLink") val uploadLink: FileUploadLink,
) {}
