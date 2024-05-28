package com.tidal.sdk.player.streamingprivileges

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable

/**
 * When we find an available Internet connection, if we are not connected or trying to connect,
 * request a connection attempt.
 */
internal class StreamingPrivilegesNetworkCallback(
    private val networkInteractionsHandler: Handler,
    private val connectRunnable: ConnectRunnable,
) : ConnectivityManager.NetworkCallback() {

    // TODO Remove this after minSdk >= 26 -> https://developer.android.com/reference/android/net/ConnectivityManager.NetworkCallback#onAvailable(android.net.Network):~:text=Starting%20with%20Build.VERSION_CODES.O%2C%20this%20will%20always%20immediately%20be%20followed%20by%20a%20call%20to%20onCapabilitiesChanged(android.net.Network%2C%20android.net.NetworkCapabilities)
    override fun onAvailable(network: Network) {
        dispatchConnect()
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            dispatchConnect()
        }
    }

    private fun dispatchConnect() = networkInteractionsHandler.post(connectRunnable)
}
