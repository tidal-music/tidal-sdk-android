package com.tidal.sdk.player.streamingprivileges.messages.incoming

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.tidal.sdk.player.streamingprivileges.messages.WebSocketMessage

internal class IncomingWebSocketMessageParser(private val gson: Gson) {

    fun parse(messageText: String): WebSocketMessage.Incoming {
        val parsedMessage = gson.fromJson(messageText, JsonObject::class.java)
        return parsedMessage["type"].asString.let {
            when (it) {
                WebSocketMessage.Incoming.Type.RECONNECT.string ->
                    WebSocketMessage.Incoming.Reconnect

                WebSocketMessage.Incoming.Type.STREAMING_PRIVILEGES_REVOKED.string -> {
                    val payload = parsedMessage["payload"]?.asJsonObject
                    WebSocketMessage.Incoming.StreamingPrivilegesRevoked(
                        payload?.get("clientDisplayName")?.asString
                    )
                }

                else -> throw IllegalArgumentException("Unexpected type $it")
            }
        }
    }
}
