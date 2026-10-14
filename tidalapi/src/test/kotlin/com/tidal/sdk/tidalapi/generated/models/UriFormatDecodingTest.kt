package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** `format: uri` strings must decode without a contextual serializer registered for them. */
class UriFormatDecodingTest {

    private val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
        serializersModule = getOneOfSerializer()
    }

    @Test
    fun `artwork file href decodes as a string`() {
        val file =
            json.decodeFromString<ArtworkFile>(
                """{"href":"https://resources.tidal.com/a.jpg","meta":{"width":640,"height":640}}"""
            )

        assertEquals("https://resources.tidal.com/a.jpg", file.href)
    }

    @Test
    fun `external link href decodes as a string`() {
        val link =
            json.decodeFromString<ExternalLink>(
                """{"href":"https://example.com","meta":{"type":"OFFICIAL_HOMEPAGE"}}"""
            )

        assertEquals("https://example.com", link.href)
    }
}
