package com.tidal.sdk.player.streamingprivileges.connection

import android.net.ConnectivityManager
import com.tidal.sdk.player.streamingprivileges.MutableState
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesNetworkCallback

internal class DisconnectRunnable(
    private val mutableState: MutableState,
    private val connectivityManager: ConnectivityManager,
    private val streamingPrivilegesNetworkCallback: StreamingPrivilegesNetworkCallback,
) : Runnable {

    override fun run() {
        if (mutableState.keepAlive) {
            return
        }
        if (mutableState.isNetworkConnectivityCallbackCurrentlyRegistered) {
            connectivityManager.unregisterNetworkCallback(streamingPrivilegesNetworkCallback)
            mutableState.isNetworkConnectivityCallbackCurrentlyRegistered = false
        }
        val connectionMutableState = mutableState.connectionMutableState ?: return
        connectionMutableState.streamingPrivilegesListener = null
        connectionMutableState.isConnectionRelevant = false
        val connectionState = connectionMutableState.socketConnectionState
        if (connectionState is SocketConnectionState.Connected) {
            connectionState.webSocket.close(CloseReason.REASON_REQUESTED_BY_SELF)
            connectionMutableState.socketConnectionState = SocketConnectionState.NotConnected
        }
        mutableState.connectionMutableState = null
    }
}
