package com.tidal.sdk.flo.core.internal

import com.tidal.sdk.flo.core.FloException

internal class TerminalErrorManager {

    fun dispatchErrorAndTerminateConnection(
        connectionMutableState: SubscriptionManager.ConnectionMutableState,
        floException: FloException,
    ) {
        if (connectionMutableState.isConnectionRequired) {
            connectionMutableState.desiredSubscriptions.apply {
                forEach { it.value.onError(floException) }
                clear()
            }
        }
        connectionMutableState.socketConnectionState = SocketConnectionState.NotConnected
    }
}
