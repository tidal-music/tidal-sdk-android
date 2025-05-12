package com.tidal.sdk.player.streamingprivileges.acquire

import com.google.gson.Gson
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState
import com.tidal.sdk.player.streamingprivileges.messages.WebSocketMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class AcquireRunnable
@AssistedInject
constructor(
    @Assisted private val connectionMutableState: ConnectionMutableState,
    private val gson: Gson,
    @Assisted private val startedAtMillis: Long,
) : Runnable {

    override fun run() {
        if (!connectionMutableState.isConnectionRelevant) {
            return
        }
        when (val connectionState = connectionMutableState.socketConnectionState) {
            is SocketConnectionState.Connected ->
                connectionState.webSocket.send(
                    gson.toJson(WebSocketMessage.Outgoing.Acquire(startedAtMillis))
                )

            else -> Unit
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            startedAtMillis: Long,
            connectionMutableState: ConnectionMutableState,
        ): AcquireRunnable
    }
}
