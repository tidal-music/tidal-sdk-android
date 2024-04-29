package com.tidal.sdk.player.streamingprivileges.connection.websocketevents

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesEventDispatcher
import com.tidal.sdk.player.streamingprivileges.connection.CloseReason
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState
import com.tidal.sdk.player.streamingprivileges.connection.close
import com.tidal.sdk.player.streamingprivileges.messages.WebSocketMessage
import com.tidal.sdk.player.streamingprivileges.messages.incoming.IncomingWebSocketMessageParser
import okhttp3.WebSocket

internal class OnWebSocketMessage(
    private val networkInteractionsHandler: Handler,
    private val connectRunnable: ConnectRunnable,
    private val incomingWebSocketMessageParser: IncomingWebSocketMessageParser,
    private val streamingPrivilegesEventDispatcher: StreamingPrivilegesEventDispatcher,
) {

    operator fun invoke(
        webSocket: WebSocket,
        text: String,
        connectionMutableState: ConnectionMutableState,
    ) {
        when (val message = incomingWebSocketMessageParser.parse(text)) {
            is WebSocketMessage.Incoming.Reconnect -> {
                webSocket.close(CloseReason.REASON_REQUESTED_BY_PEER)
                connectionMutableState.socketConnectionState = SocketConnectionState.NotConnected
                networkInteractionsHandler.post(connectRunnable)
            }

            is WebSocketMessage.Incoming.StreamingPrivilegesRevoked -> {
                streamingPrivilegesEventDispatcher.dispatchStreamingPrivilegeRevoked(
                    connectionMutableState,
                    message.privilegedClientDisplayName,
                )
            }
        }
    }
}
