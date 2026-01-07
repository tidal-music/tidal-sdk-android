package com.tidal.sdk.eventproducer.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi
import nl.adaptivity.xmlutil.serialization.UnknownChildHandler
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

@Module
internal class ConvertersModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideXmlConverter(): Converter.Factory {
        val xml = XML {
            autoPolymorphic = false
            // Skip unknown elements during deserialization
            unknownChildHandler = UnknownChildHandler { _, _, _, _, _ -> emptyList() }
        }
        return xml.asConverterFactory("application/xml".toMediaType())
    }

    @Provides @Singleton fun provideMoshi(): Moshi = Moshi.Builder().build()
}
