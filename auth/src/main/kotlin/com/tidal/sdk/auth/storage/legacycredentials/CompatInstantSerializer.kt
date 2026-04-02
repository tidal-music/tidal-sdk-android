package com.tidal.sdk.auth.storage.legacycredentials

import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A custom serializer for [Instant] that does not depend on
 * `kotlinx.datetime.serializers.InstantIso8601Serializer`, which was removed in kotlinx-datetime
 * 0.7.0. Uses the stable [Instant.parse] and [Instant.toString] APIs that are available across all
 * kotlinx-datetime versions.
 */
@OptIn(ExperimentalTime::class)
internal object CompatInstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}
