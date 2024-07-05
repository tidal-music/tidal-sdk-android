package com.tidal.sdk.player.streamingprivileges

import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.streamingprivileges.di.DaggerStreamingPrivilegesComponent
import okhttp3.OkHttpClient

class StreamingPrivilegesModuleRoot(
    connectivityManager: ConnectivityManager,
    okHttpClient: OkHttpClient,
    gson: Gson,
    sntpClient: SNTPClient,
) {

    val streamingPrivileges = componentFactoryF()
        .create(
            connectivityManager,
            okHttpClient,
            gson,
            sntpClient,
        )
        .streamingPrivileges

    companion object {

        private var componentFactoryF = { DaggerStreamingPrivilegesComponent.factory() }
    }
}
