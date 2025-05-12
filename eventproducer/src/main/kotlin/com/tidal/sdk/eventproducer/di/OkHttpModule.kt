package com.tidal.sdk.eventproducer.di

import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.BuildConfig
import com.tidal.sdk.eventproducer.auth.DefaultAuthenticator
import com.tidal.sdk.eventproducer.network.HeadersInterceptor
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
internal class OkHttpModule {

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    @Named("headerInterceptor")
    fun provideHeadersInterceptor(credentialsProvider: CredentialsProvider): Interceptor =
        HeadersInterceptor(credentialsProvider)

    @Provides
    @Singleton
    fun provideBaseOkHttpClient(
        @Named("loggingInterceptor") loggingInterceptor: HttpLoggingInterceptor,
        @Named("headerInterceptor") headerInterceptor: Interceptor,
        defaultAuthenticator: DefaultAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                addInterceptor(headerInterceptor)
                authenticator(defaultAuthenticator)
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }
            }
            .build()
    }
}
