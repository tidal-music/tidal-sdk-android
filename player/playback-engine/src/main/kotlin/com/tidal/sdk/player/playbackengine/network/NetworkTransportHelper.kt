package com.tidal.sdk.player.playbackengine.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

internal class NetworkTransportHelper(private val connectivityManager: ConnectivityManager) {

    fun isWifiOrEthernet(): Boolean {
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}
