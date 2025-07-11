/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * object containing references to the primary source of the error
 *
 * @param header string indicating the name of a single request header which caused the error
 * @param parameter string indicating which URI query parameter caused the error.
 * @param pointer a JSON Pointer [RFC6901] to the value in the request document that caused the
 *   error
 */
@Serializable
data class ErrorObjectSource(

    /* string indicating the name of a single request header which caused the error */

    @SerialName(value = "header") val header: kotlin.String? = null,
    /* string indicating which URI query parameter caused the error. */

    @SerialName(value = "parameter") val parameter: kotlin.String? = null,
    /* a JSON Pointer [RFC6901] to the value in the request document that caused the error */

    @SerialName(value = "pointer") val pointer: kotlin.String? = null,
) {}
