package com.tidal.sdk.flo.core.internal

import okhttp3.WebSocket

internal class IfRequiredOrCloseRunnable(
    private val connectionMutableState: SubscriptionManager.ConnectionMutableState,
    private val webSocket: WebSocket,
    private val block: () -> Unit,
) : Runnable {

    override fun run() {
        if (connectionMutableState.isConnectionRequired) {
            block()
        } else {
            webSocket.close(CLOSURE_STATUS_GOING_AWAY, null)
            connectionMutableState.socketConnectionState = SocketConnectionState.NotConnected
        }
    }

    companion object {
        private const val CLOSURE_STATUS_GOING_AWAY = 1001
    }
}
