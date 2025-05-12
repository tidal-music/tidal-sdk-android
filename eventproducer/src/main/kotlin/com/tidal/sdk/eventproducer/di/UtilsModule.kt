package com.tidal.sdk.eventproducer.di

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.utils.HeadersUtils
import com.tidal.sdk.eventproducer.utils.TrueTimeWrapper
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal class UtilsModule {

    @Provides
    fun provideHeadersUtils(
        configProvider: EventsConfigProvider,
        credentialsProvider: CredentialsProvider,
        trueTimeWrapper: TrueTimeWrapper,
    ) = HeadersUtils(configProvider.config.appVersion, credentialsProvider, trueTimeWrapper)

    @Provides @Reusable fun provideTrueTimeWrapper() = TrueTimeWrapper()
}
