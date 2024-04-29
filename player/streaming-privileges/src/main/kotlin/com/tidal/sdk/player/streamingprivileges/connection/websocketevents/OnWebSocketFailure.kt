package com.tidal.sdk.player.streamingprivileges.connection.websocketevents

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState

internal class OnWebSocketFailure(
    private val networkInteractionsHandler: Handler,
    private val connectRunnable: ConnectRunnable,
) {

    operator fun invoke(connectionMutableState: ConnectionMutableState) {
        connectionMutableState.socketConnectionState = SocketConnectionState.NotConnected
        networkInteractionsHandler.post(connectRunnable)
    }
}
