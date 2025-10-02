package com.tidal.sdk.eventproducer.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Converter
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

@Module
internal class ConvertersModule {

    @Provides
    @Singleton
    fun provideXmlConverter(): Converter.Factory {
        return SimpleXmlConverterFactory.createNonStrict()
    }

    @Provides @Singleton fun provideMoshi(): Moshi = Moshi.Builder().build()
}
