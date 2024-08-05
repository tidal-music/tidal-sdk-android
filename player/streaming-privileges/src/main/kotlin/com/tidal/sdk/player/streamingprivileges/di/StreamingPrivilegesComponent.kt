package com.tidal.sdk.player.streamingprivileges.di

import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.networktime.SNTPClient
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Component(modules = [StreamingPrivilegesModule::class])
@Singleton
interface StreamingPrivilegesComponent {

    val streamingPrivileges: StreamingPrivileges

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance connectivityManager: ConnectivityManager,
            @BindsInstance okHttpClient: OkHttpClient,
            @BindsInstance gson: Gson,
            @BindsInstance sntpClient: SNTPClient,
        ): StreamingPrivilegesComponent
    }
}
