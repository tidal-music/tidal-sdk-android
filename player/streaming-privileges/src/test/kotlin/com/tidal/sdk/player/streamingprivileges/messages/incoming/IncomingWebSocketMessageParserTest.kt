package com.tidal.sdk.player.streamingprivileges.messages.incoming

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameInstanceAs
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.tidal.sdk.player.streamingprivileges.messages.WebSocketMessage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class IncomingWebSocketMessageParserTest {

    private val gson = mock<Gson>()
    private val incomingWebSocketMessageParser = IncomingWebSocketMessageParser(gson)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(gson)

    @Test
    fun parseTypeReconnectReturnsReconnect() {
        val msgString = "msg string"
        val typeElement =
            mock<JsonElement> {
                on { asString } doReturn WebSocketMessage.Incoming.Type.RECONNECT.string
            }
        val jsonObject = mock<JsonObject> { on { get("type") } doReturn typeElement }
        whenever(gson.fromJson(msgString, JsonObject::class.java)) doReturn jsonObject

        val actual = incomingWebSocketMessageParser.parse(msgString)

        verify(gson).fromJson(msgString, JsonObject::class.java)
        verify(jsonObject)["type"]
        verify(typeElement).asString
        assertThat(actual).isSameInstanceAs(WebSocketMessage.Incoming.Reconnect)
        verifyNoMoreInteractions(typeElement, jsonObject)
    }

    @Test
    fun parseTypeUnknownThrowsIllegalArgumentException() {
        val msgString = "msg string"
        val typeElement = mock<JsonElement> { on { asString } doReturn "unknown type" }
        val jsonObject = mock<JsonObject> { on { get("type") } doReturn typeElement }
        whenever(gson.fromJson(msgString, JsonObject::class.java)) doReturn jsonObject

        assertThrows<IllegalArgumentException> { incomingWebSocketMessageParser.parse(msgString) }

        verify(gson).fromJson(msgString, JsonObject::class.java)
        verify(jsonObject)["type"]
        verify(typeElement).asString
        verifyNoMoreInteractions(typeElement, jsonObject)
    }

    @Test
    fun parseTypeStreamingPrivilegesRevokedReturnsStreamingPrivilegesRevokedWithClientName() {
        val privilegedClientDisplayName = "privilegedClientDisplayName"
        val clientDisplayNameElement =
            mock<JsonElement> { on { asString } doReturn privilegedClientDisplayName }
        val msgString = "msg string"
        val typeElement =
            mock<JsonElement> {
                on { asString }
                    .thenReturn(WebSocketMessage.Incoming.Type.STREAMING_PRIVILEGES_REVOKED.string)
            }
        val payloadObject =
            mock<JsonObject> { on { get("clientDisplayName") } doReturn clientDisplayNameElement }
        val payloadElement = mock<JsonElement> { on { asJsonObject } doReturn payloadObject }
        val jsonObject =
            mock<JsonObject> {
                on { get("type") } doReturn typeElement
                on { get("payload") } doReturn payloadElement
            }
        whenever(gson.fromJson(msgString, JsonObject::class.java)) doReturn jsonObject

        val actual = incomingWebSocketMessageParser.parse(msgString)

        verify(gson).fromJson(msgString, JsonObject::class.java)
        verify(jsonObject)["type"]
        verify(typeElement).asString
        verify(jsonObject)["payload"]
        verify(payloadElement).asJsonObject
        verify(payloadObject)["clientDisplayName"]
        verify(clientDisplayNameElement).asString
        assertThat(actual)
            .isEqualTo(
                WebSocketMessage.Incoming.StreamingPrivilegesRevoked(privilegedClientDisplayName)
            )
        verifyNoMoreInteractions(clientDisplayNameElement, typeElement, payloadElement, jsonObject)
    }
}
