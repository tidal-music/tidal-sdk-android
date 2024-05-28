package com.tidal.sdk.player.events.util

import android.net.ConnectivityManager

internal class ActiveMobileNetworkType(private val connectivityManager: ConnectivityManager) {
    val value: String
        // FIXME See deprecation info on
        // https://developer.android.com/reference/android/net/NetworkInfo#getSubtypeName()
        get() = connectivityManager.activeNetworkInfo?.subtypeName ?: ""
}
