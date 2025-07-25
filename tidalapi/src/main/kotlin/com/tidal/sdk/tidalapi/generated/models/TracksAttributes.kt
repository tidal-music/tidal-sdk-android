/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param duration Duration (ISO 8601)
 * @param explicit Explicit content
 * @param isrc International Standard Recording Code (ISRC)
 * @param key Key
 * @param keyScale The scale of the key
 * @param mediaTags
 * @param popularity Popularity (0.0 - 1.0)
 * @param title Track title
 * @param accessType Access type
 * @param availability Available usage for this track
 * @param bpm Beats per minute
 * @param copyright Copyright
 * @param createdAt Datetime of track creation (ISO 8601)
 * @param externalLinks Track links external to TIDAL API
 * @param genreTags
 * @param spotlighted Is the track spotlighted?
 * @param toneTags
 * @param version Track version, complements title
 */
@Serializable
data class TracksAttributes(

    /* Duration (ISO 8601) */

    @SerialName(value = "duration") val duration: kotlin.String,
    /* Explicit content */

    @SerialName(value = "explicit") val explicit: kotlin.Boolean,
    /* International Standard Recording Code (ISRC) */

    @SerialName(value = "isrc") val isrc: kotlin.String,
    /* Key */

    @SerialName(value = "key") val key: TracksAttributes.Key,
    /* The scale of the key */

    @SerialName(value = "keyScale") val keyScale: TracksAttributes.KeyScale,
    @SerialName(value = "mediaTags") val mediaTags: kotlin.collections.List<kotlin.String>,
    /* Popularity (0.0 - 1.0) */

    @SerialName(value = "popularity") val popularity: kotlin.Double,
    /* Track title */

    @SerialName(value = "title") val title: kotlin.String,
    /* Access type */

    @SerialName(value = "accessType") val accessType: TracksAttributes.AccessType? = null,
    /* Available usage for this track */

    @SerialName(value = "availability")
    val availability: kotlin.collections.List<TracksAttributes.Availability>? = null,
    /* Beats per minute */

    @SerialName(value = "bpm") val bpm: kotlin.Float? = null,
    /* Copyright */

    @SerialName(value = "copyright") val copyright: kotlin.String? = null,
    /* Datetime of track creation (ISO 8601) */

    @SerialName(value = "createdAt") val createdAt: kotlin.String? = null,
    /* Track links external to TIDAL API */

    @SerialName(value = "externalLinks")
    val externalLinks: kotlin.collections.List<ExternalLink>? = null,
    @SerialName(value = "genreTags") val genreTags: kotlin.collections.List<kotlin.String>? = null,
    /* Is the track spotlighted? */

    @SerialName(value = "spotlighted") val spotlighted: kotlin.Boolean? = null,
    @SerialName(value = "toneTags") val toneTags: kotlin.collections.List<kotlin.String>? = null,
    /* Track version, complements title */

    @SerialName(value = "version") val version: kotlin.String? = null,
) {

    /**
     * Key
     *
     * Values: UNKNOWN,C,CSharp,D,Eb,E,F,FSharp,G,Ab,A,Bb,B
     */
    @Serializable
    enum class Key(val value: kotlin.String) {
        @SerialName(value = "UNKNOWN") UNKNOWN("UNKNOWN"),
        @SerialName(value = "C") C("C"),
        @SerialName(value = "CSharp") CSharp("CSharp"),
        @SerialName(value = "D") D("D"),
        @SerialName(value = "Eb") Eb("Eb"),
        @SerialName(value = "E") E("E"),
        @SerialName(value = "F") F("F"),
        @SerialName(value = "FSharp") FSharp("FSharp"),
        @SerialName(value = "G") G("G"),
        @SerialName(value = "Ab") Ab("Ab"),
        @SerialName(value = "A") A("A"),
        @SerialName(value = "Bb") Bb("Bb"),
        @SerialName(value = "B") B("B"),
    }

    /**
     * The scale of the key
     *
     * Values:
     * UNKNOWN,MAJOR,MINOR,AEOLIAN,BLUES,DORIAN,HARMONIC_MINOR,LOCRIAN,LYDIAN,MIXOLYDIAN,PENTATONIC_MAJOR,PHRYGIAN,MELODIC_MINOR,PENTATONIC_MINOR
     */
    @Serializable
    enum class KeyScale(val value: kotlin.String) {
        @SerialName(value = "UNKNOWN") UNKNOWN("UNKNOWN"),
        @SerialName(value = "MAJOR") MAJOR("MAJOR"),
        @SerialName(value = "MINOR") MINOR("MINOR"),
        @SerialName(value = "AEOLIAN") AEOLIAN("AEOLIAN"),
        @SerialName(value = "BLUES") BLUES("BLUES"),
        @SerialName(value = "DORIAN") DORIAN("DORIAN"),
        @SerialName(value = "HARMONIC_MINOR") HARMONIC_MINOR("HARMONIC_MINOR"),
        @SerialName(value = "LOCRIAN") LOCRIAN("LOCRIAN"),
        @SerialName(value = "LYDIAN") LYDIAN("LYDIAN"),
        @SerialName(value = "MIXOLYDIAN") MIXOLYDIAN("MIXOLYDIAN"),
        @SerialName(value = "PENTATONIC_MAJOR") PENTATONIC_MAJOR("PENTATONIC_MAJOR"),
        @SerialName(value = "PHRYGIAN") PHRYGIAN("PHRYGIAN"),
        @SerialName(value = "MELODIC_MINOR") MELODIC_MINOR("MELODIC_MINOR"),
        @SerialName(value = "PENTATONIC_MINOR") PENTATONIC_MINOR("PENTATONIC_MINOR"),
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
