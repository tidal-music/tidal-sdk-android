package com.tidal.sdk.flo.core.internal

import android.net.ConnectivityManager
import com.squareup.moshi.Moshi

internal class DispatchUnsubscribeRunnable
@Suppress("MaxLineLength")
constructor(
    private val mutableState: SubscriptionManager.MutableState,
    private val connectivityManager: ConnectivityManager,
    private val connectionRestorationNetworkConnectivityCallback: ConnectionRestorationNetworkConnectivityCallback, // ktlint-disable max-line-length parameter-wrapping
    private val moshi: Moshi,
    private val topic: String,
) : Runnable {

    override fun run() {
        val connectionMutableState = mutableState.connectionMutableState ?: return
        val key = SubscriptionIdentifier(topic)
        connectionMutableState.desiredSubscriptions.remove(key)
        UnsubscribeSendCommandRunnable(connectionMutableState, moshi, topic).run()
        if (!connectionMutableState.isConnectionRequired) {
            if (mutableState.isNetworkConnectivityCallbackCurrentlyRegistered) {
                connectivityManager.unregisterNetworkCallback(
                    connectionRestorationNetworkConnectivityCallback,
                )
                mutableState.isNetworkConnectivityCallbackCurrentlyRegistered = false
            }
            val connectionState = connectionMutableState.socketConnectionState
            if (connectionState is SocketConnectionState.Connected) {
                connectionState.webSocket.close(CLOSURE_STATUS_PURPOSE_FULFILLED, null)
                connectionMutableState.socketConnectionState = SocketConnectionState.NotConnected
            }
            mutableState.connectionMutableState = null
        }
    }

    companion object {
        private const val CLOSURE_STATUS_PURPOSE_FULFILLED = 1000
    }
}
