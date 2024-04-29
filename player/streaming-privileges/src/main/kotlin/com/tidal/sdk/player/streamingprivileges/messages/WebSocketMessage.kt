package com.tidal.sdk.player.streamingprivileges.messages

import androidx.annotation.Keep
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

internal sealed interface WebSocketMessage {

    sealed interface Incoming : WebSocketMessage {

        object Reconnect : Incoming

        data class StreamingPrivilegesRevoked(val privilegedClientDisplayName: String) : Incoming

        enum class Type(val string: String) {

            RECONNECT("RECONNECT"),
            STREAMING_PRIVILEGES_REVOKED("PRIVILEGED_SESSION_NOTIFICATION"),
        }
    }

    sealed interface Outgoing : WebSocketMessage {

        @Keep
        @Suppress("UnusedPrivateMember")
        data class Acquire constructor(@Transient val startedAtMillis: Long) : Outgoing {

            private val type = Type.ACQUIRE
            private val payload = JsonObject().apply { addProperty("startedAt", startedAtMillis) }
        }

        private enum class Type {

            @SerializedName("USER_ACTION")
            ACQUIRE,
        }
    }
}
