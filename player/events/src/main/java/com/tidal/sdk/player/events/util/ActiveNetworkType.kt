package com.tidal.sdk.player.events.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.tidal.sdk.player.events.model.StreamingSessionStart

internal class ActiveNetworkType(private val connectivityManager: ConnectivityManager) {
    val value: StreamingSessionStart.NetworkType
        get() = connectivityManager.run {
            activeNetwork?.let { getNetworkCapabilities(it) }?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                        StreamingSessionStart.NetworkType.ETHERNET

                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                        StreamingSessionStart.NetworkType.WIFI

                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                        StreamingSessionStart.NetworkType.MOBILE

                    else -> StreamingSessionStart.NetworkType.OTHER
                }
            } ?: StreamingSessionStart.NetworkType.NONE
        }
}
