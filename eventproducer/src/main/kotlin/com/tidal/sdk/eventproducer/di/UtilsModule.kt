package com.tidal.sdk.eventproducer.di

import com.tidal.sdk.eventproducer.auth.AuthProvider
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.utils.HeadersUtils
import dagger.Module
import dagger.Provides

@Module
internal class UtilsModule {

    @Provides
    fun provideHeadersUtils(configProvider: EventsConfigProvider, authProvider: AuthProvider) =
        HeadersUtils(configProvider.config.appVersion, authProvider)
}
