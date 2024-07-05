package com.tidal.sdk.eventproducer.di

import com.tidal.networktime.NTPServer
import com.tidal.networktime.SNTPClient
import com.tidal.networktime.singletons.singleton
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.utils.HeadersUtils
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal class UtilsModule {

    @Provides
    fun provideHeadersUtils(
        configProvider: EventsConfigProvider,
        credentialsProvider: CredentialsProvider,
        sntpClient: SNTPClient,
    ) = HeadersUtils(configProvider.config.appVersion, credentialsProvider, sntpClient)

    @Provides
    @Reusable
    fun provideSNTPClient() = SNTPClient(NTPServer("time.google.com")).singleton
}
