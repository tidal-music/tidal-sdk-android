package com.tidal.sdk.tidalapi.generated.models

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * `format: uri`, `format: uuid`, and free-form object fields must decode without a contextual
 * serializer registered for them.
 */
class FormatMappingDecodingTest {

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

    @Test
    fun `uuid decodes as a string`() {
        val attributes =
            json.decodeFromString<DynamicPagesAttributes>(
                """{"pageType":"HOME_STATIC","reportingId":"83c92ac0-b8e3-4e28-9e27-07909dcde1e5"}"""
            )

        assertEquals("83c92ac0-b8e3-4e28-9e27-07909dcde1e5", attributes.reportingId)
    }

    @Test
    fun `free-form object decodes as a json object`() {
        val attributes =
            json.decodeFromString<ViewerContextsAttributes>("""{"hasInCollection":{}}""")

        assertEquals(JsonObject(emptyMap()), attributes.hasInCollection)
    }

    @Test
    fun `free-form map values decode as json elements`() {
        val document =
            json.decodeFromString<MutationResponseDocument>(
                """{"links":{"self":"/x"},"meta":{"count":2}}"""
            )

        assertEquals(mapOf("count" to JsonPrimitive(2)), document.meta)
    }
}
