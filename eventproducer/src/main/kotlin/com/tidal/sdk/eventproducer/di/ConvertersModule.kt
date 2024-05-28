package com.tidal.sdk.eventproducer.di

import com.squareup.moshi.Moshi
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class ConvertersModule {

    @Provides
    @Singleton
    fun provideXmlConverter(): TikXmlConverterFactory {
        return TikXmlConverterFactory.create(
            TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .build(),
        )
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}
