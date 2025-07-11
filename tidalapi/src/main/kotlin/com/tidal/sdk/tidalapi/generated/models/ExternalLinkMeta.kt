/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * metadata about an external link
 *
 * @param type
 */
@Serializable
data class ExternalLinkMeta(@SerialName(value = "type") val type: ExternalLinkMeta.Type) {

    /**
     * Values:
     * TIDAL_SHARING,TIDAL_AUTOPLAY_ANDROID,TIDAL_AUTOPLAY_IOS,TIDAL_AUTOPLAY_WEB,TWITTER,FACEBOOK,INSTAGRAM,TIKTOK,SNAPCHAT,HOMEPAGE,CASHAPP_CONTRIBUTIONS
     */
    @Serializable
    enum class Type(val value: kotlin.String) {
        @SerialName(value = "TIDAL_SHARING") TIDAL_SHARING("TIDAL_SHARING"),
        @SerialName(value = "TIDAL_AUTOPLAY_ANDROID")
        TIDAL_AUTOPLAY_ANDROID("TIDAL_AUTOPLAY_ANDROID"),
        @SerialName(value = "TIDAL_AUTOPLAY_IOS") TIDAL_AUTOPLAY_IOS("TIDAL_AUTOPLAY_IOS"),
        @SerialName(value = "TIDAL_AUTOPLAY_WEB") TIDAL_AUTOPLAY_WEB("TIDAL_AUTOPLAY_WEB"),
        @SerialName(value = "TWITTER") TWITTER("TWITTER"),
        @SerialName(value = "FACEBOOK") FACEBOOK("FACEBOOK"),
        @SerialName(value = "INSTAGRAM") INSTAGRAM("INSTAGRAM"),
        @SerialName(value = "TIKTOK") TIKTOK("TIKTOK"),
        @SerialName(value = "SNAPCHAT") SNAPCHAT("SNAPCHAT"),
        @SerialName(value = "HOMEPAGE") HOMEPAGE("HOMEPAGE"),
        @SerialName(value = "CASHAPP_CONTRIBUTIONS") CASHAPP_CONTRIBUTIONS("CASHAPP_CONTRIBUTIONS"),
    }
}
