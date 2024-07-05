package com.tidal.sdk.player.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import com.tidal.networktime.NTPServer
import com.tidal.networktime.SNTPClient
import com.tidal.networktime.singletons.singleton
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.Base64Codec
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal object PlayerModule {

    @Provides
    @Reusable
    fun connectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java)

    @Provides
    @Reusable
    fun gson() = GsonBuilder().disableHtmlEscaping().create()

    @Provides
    @Reusable
    fun uuidWrapper() = UUIDWrapper()

    @Provides
    @Reusable
    fun sntpClient() = SNTPClient(NTPServer("time.google.com")).singleton

    @Provides
    @Reusable
    fun base64Codec() = Base64Codec()
}
