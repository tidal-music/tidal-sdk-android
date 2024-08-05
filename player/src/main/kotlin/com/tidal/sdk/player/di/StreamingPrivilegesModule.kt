package com.tidal.sdk.player.di

import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesModuleRoot
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
internal object StreamingPrivilegesModule {

    @Provides
    @Singleton
    fun streamingPrivileges(
        connectivityManager: ConnectivityManager,
        gson: Gson,
        @LocalWithCacheAndAuth
        okHttpClient: OkHttpClient,
        sntpClient: SNTPClient,
    ) = StreamingPrivilegesModuleRoot(
        connectivityManager,
        okHttpClient,
        gson,
        sntpClient,
    ).streamingPrivileges
}
