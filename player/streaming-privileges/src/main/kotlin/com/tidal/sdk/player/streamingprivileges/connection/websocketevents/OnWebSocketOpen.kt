package com.tidal.sdk.player.streamingprivileges.connection.websocketevents

import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesEventDispatcher
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState
import okhttp3.WebSocket

internal class OnWebSocketOpen(
    private val connectedFactory: SocketConnectionState.Connected.Factory,
    private val streamingPrivilegesEventDispatcher: StreamingPrivilegesEventDispatcher,
) {

    operator fun invoke(webSocket: WebSocket, connectionMutableState: ConnectionMutableState) {
        connectionMutableState.socketConnectionState = connectedFactory.create(webSocket)
        streamingPrivilegesEventDispatcher.dispatchConnectionEstablished(connectionMutableState)
    }
}
