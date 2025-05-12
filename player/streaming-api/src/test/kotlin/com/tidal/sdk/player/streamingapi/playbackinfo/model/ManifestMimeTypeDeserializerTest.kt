package com.tidal.sdk.player.streamingapi.playbackinfo.model

import assertk.assertThat
import assertk.assertions.isSameAs
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.mock

internal class ManifestMimeTypeDeserializerTest {

    private val deserializer = ManifestMimeType.Converter.Deserializer()

    @ParameterizedTest
    @EnumSource(ManifestMimeType::class)
    fun deserializeValidValues(manifestMimeType: ManifestMimeType) {
        val jsonElement = JsonPrimitive(manifestMimeType.mimeType)

        val actual = deserializer.deserialize(jsonElement, mock(), mock())

        assertThat(actual).isSameAs(manifestMimeType)
    }

    @Test
    fun deserializeNullJsonElement() {
        assertThrows<JsonParseException> { deserializer.deserialize(null, mock(), mock()) }
    }

    @Test
    fun deserializeInvalidJsonElement() {
        assertThrows<JsonParseException> {
            deserializer.deserialize(JsonPrimitive(""), mock(), mock())
        }
    }
}
