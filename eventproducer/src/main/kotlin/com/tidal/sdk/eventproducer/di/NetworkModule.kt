package com.tidal.sdk.eventproducer.di

import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.tidal.sdk.eventproducer.network.service.SqsService
import dagger.Module
import dagger.Provides
import java.net.URL
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideSqsService(
        tlConsumerUrl: URL,
        okhttpClient: OkHttpClient,
        xmlConverter: TikXmlConverterFactory,
    ): SqsService {
        val retrofit = Retrofit.Builder().baseUrl(tlConsumerUrl).client(okhttpClient)
            .addConverterFactory(xmlConverter).build()
        return retrofit.create(SqsService::class.java)
    }
}
