/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param title Track title
 * @param isrc International Standard Recording Code (ISRC)
 * @param duration Duration (ISO 8601)
 * @param explicit Explicit content
 * @param popularity Popularity (0.0 - 1.0)
 * @param mediaTags
 * @param version Track version, complements title
 * @param copyright Copyright
 * @param key Key
 * @param keyScale The scale of the key
 * @param bpm Beats per minute
 * @param accessType Access type
 * @param availability Available usage for this track
 * @param toneTags Tone tags
 * @param genreTags Genre tags
 * @param externalLinks Track links external to TIDAL API
 */
@Serializable
data class TracksAttributes(

    /* Track title */

    @SerialName(value = "title") val title: kotlin.String,
    /* International Standard Recording Code (ISRC) */

    @SerialName(value = "isrc") val isrc: kotlin.String,
    /* Duration (ISO 8601) */

    @SerialName(value = "duration") val duration: kotlin.String,
    /* Explicit content */

    @SerialName(value = "explicit") val explicit: kotlin.Boolean,
    /* Popularity (0.0 - 1.0) */

    @SerialName(value = "popularity") val popularity: kotlin.Double,
    @SerialName(value = "mediaTags") val mediaTags: kotlin.collections.List<kotlin.String>,
    /* Track version, complements title */

    @SerialName(value = "version") val version: kotlin.String? = null,
    /* Copyright */

    @SerialName(value = "copyright") val copyright: kotlin.String? = null,
    /* Key */

    @SerialName(value = "key") val key: TracksAttributes.Key? = null,
    /* The scale of the key */

    @SerialName(value = "keyScale") val keyScale: TracksAttributes.KeyScale? = null,
    /* Beats per minute */

    @SerialName(value = "bpm") val bpm: kotlin.Float? = null,
    /* Access type */

    @SerialName(value = "accessType") val accessType: TracksAttributes.AccessType? = null,
    /* Available usage for this track */

    @SerialName(value = "availability")
    val availability: kotlin.collections.List<TracksAttributes.Availability>? = null,
    /* Tone tags */

    @SerialName(value = "toneTags") val toneTags: kotlin.collections.List<kotlin.String>? = null,
    /* Genre tags */

    @SerialName(value = "genreTags") val genreTags: kotlin.collections.List<kotlin.String>? = null,
    /* Track links external to TIDAL API */

    @SerialName(value = "externalLinks")
    val externalLinks: kotlin.collections.List<ExternalLink>? = null,
) {

    /**
     * Key
     *
     * Values: A,Ab,B,Bb,C,CSharp,D,E,Eb,F,FSharp,G
     */
    @Serializable
    enum class Key(val value: kotlin.String) {
        @SerialName(value = "A") A("A"),
        @SerialName(value = "Ab") Ab("Ab"),
        @SerialName(value = "B") B("B"),
        @SerialName(value = "Bb") Bb("Bb"),
        @SerialName(value = "C") C("C"),
        @SerialName(value = "CSharp") CSharp("CSharp"),
        @SerialName(value = "D") D("D"),
        @SerialName(value = "E") E("E"),
        @SerialName(value = "Eb") Eb("Eb"),
        @SerialName(value = "F") F("F"),
        @SerialName(value = "FSharp") FSharp("FSharp"),
        @SerialName(value = "G") G("G"),
    }

    /**
     * The scale of the key
     *
     * Values: MAJOR,MINOR
     */
    @Serializable
    enum class KeyScale(val value: kotlin.String) {
        @SerialName(value = "MAJOR") MAJOR("MAJOR"),
        @SerialName(value = "MINOR") MINOR("MINOR"),
    }

    /**
     * Access type
     *
     * Values: PUBLIC,UNLISTED,PRIVATE
     */
    @Serializable
    enum class AccessType(val value: kotlin.String) {
        @SerialName(value = "PUBLIC") PUBLIC("PUBLIC"),
        @SerialName(value = "UNLISTED") UNLISTED("UNLISTED"),
        @SerialName(value = "PRIVATE") PRIVATE("PRIVATE"),
    }

    /**
     * Available usage for this track
     *
     * Values: STREAM,DJ,STEM
     */
    @Serializable
    enum class Availability(val value: kotlin.String) {
        @SerialName(value = "STREAM") STREAM("STREAM"),
        @SerialName(value = "DJ") DJ("DJ"),
        @SerialName(value = "STEM") STEM("STEM"),
    }
}
