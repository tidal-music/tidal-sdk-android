package com.tidal.sdk.eventproducer.di

import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.tidal.sdk.eventproducer.network.service.SqsService
import dagger.Module
import dagger.Provides
import java.net.URI
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideSqsService(
        tlConsumerUri: URI,
        okhttpClient: OkHttpClient,
        xmlConverter: TikXmlConverterFactory,
    ): SqsService {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(tlConsumerUri.toURL())
                .client(okhttpClient)
                .addConverterFactory(xmlConverter)
                .build()
        return retrofit.create(SqsService::class.java)
    }
}
