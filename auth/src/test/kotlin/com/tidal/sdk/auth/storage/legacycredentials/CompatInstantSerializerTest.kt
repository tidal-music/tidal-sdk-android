package com.tidal.sdk.auth.storage.legacycredentials

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlin.test.Test
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

@OptIn(ExperimentalTime::class)
class CompatInstantSerializerTest {

    @Test
    fun `deserialize ISO 8601 string to Instant`() {
        val json = Json
        val result = json.decodeFromString(CompatInstantSerializer, "\"2024-03-15T10:30:00Z\"")
        assertEquals(Instant.parse("2024-03-15T10:30:00Z"), result)
    }

    @Test
    fun `serialize Instant to ISO 8601 string`() {
        val json = Json
        val instant = Instant.parse("2024-03-15T10:30:00Z")
        val result = json.encodeToString(CompatInstantSerializer, instant)
        assertEquals("\"2024-03-15T10:30:00Z\"", result)
    }

    @Test
    fun `round-trip serialization preserves value`() {
        val json = Json
        val original = Instant.fromEpochSeconds(0)
        val serialized = json.encodeToString(CompatInstantSerializer, original)
        val deserialized = json.decodeFromString(CompatInstantSerializer, serialized)
        assertEquals(original, deserialized)
    }

    @Test
    fun `deserialize LegacyCredentialsV2 from hardcoded JSON with Instant field`() {
        // This JSON simulates what kotlinx-datetime 0.5.0's InstantIso8601Serializer
        // would have written to SharedPreferences. The test proves our custom serializer
        // can read it without depending on the removed serializer class.
        val storedJson =
            """
            {
                "clientId": "testClient",
                "requestedScopes": ["scope1"],
                "clientUniqueKey": "key123",
                "grantedScopes": ["scope1"],
                "userId": "user1",
                "expires": "1970-01-01T00:00:00Z",
                "token": "testToken"
            }
        """
                .trimIndent()

        val result = Json.decodeFromString<LegacyCredentialsV2>(storedJson)

        assertEquals("testClient", result.clientId)
        assertEquals(Instant.fromEpochSeconds(0), result.expires)
        assertEquals("testToken", result.token)
    }

    @Test
    fun `deserialize LegacyCredentialsV2 with null expires`() {
        val storedJson =
            """
            {
                "clientId": "testClient",
                "requestedScopes": ["scope1"],
                "clientUniqueKey": null,
                "grantedScopes": ["scope1"],
                "userId": null,
                "expires": null,
                "token": "testToken"
            }
        """
                .trimIndent()

        val result = Json.decodeFromString<LegacyCredentialsV2>(storedJson)

        assertNull(result.expires)
    }

    @Test
    fun `deserialize LegacyCredentialsV1 from hardcoded JSON with Instant field`() {
        val storedJson =
            """
            {
                "clientId": "testClient",
                "requestedScopes": {"scopes": ["scope1"]},
                "clientUniqueKey": "key123",
                "grantedScopes": {"scopes": ["scope1"]},
                "userId": "user1",
                "expires": "2024-12-31T23:59:59Z",
                "token": "testToken"
            }
        """
                .trimIndent()

        val result = Json.decodeFromString<LegacyCredentialsV1>(storedJson)

        assertEquals("testClient", result.clientId)
        assertEquals(Instant.parse("2024-12-31T23:59:59Z"), result.expires)
    }

    @Test
    fun `toCredentials converts Instant to epochSeconds`() {
        val credentials =
            LegacyCredentialsV2(
                clientId = "testClient",
                requestedScopes = setOf("scope1"),
                clientUniqueKey = null,
                grantedScopes = setOf("scope1"),
                userId = null,
                expires = Instant.fromEpochSeconds(1710499800),
                token = "testToken",
            )

        val converted = credentials.toCredentials()

        assertEquals(1710499800L, converted.expires)
    }
}
