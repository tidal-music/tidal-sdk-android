package com.tidal.sdk.player.streamingapi.di

import com.tidal.sdk.player.streamingapi.StreamingApiTimeoutConfig
import com.tidal.sdk.player.streamingapi.drm.api.DrmLicenseService
import com.tidal.sdk.player.streamingapi.playbackinfo.api.PlaybackInfoService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named
import javax.inject.Singleton
import kotlin.time.toJavaDuration
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
internal object RetrofitModule {

    @Provides
    @Reusable
    @StreamingApiComponent.Local
    fun okHttpClient(
        okHttpClient: OkHttpClient,
        streamingApiTimeoutConfig: StreamingApiTimeoutConfig,
    ) =
        okHttpClient
            .newBuilder()
            .connectTimeout(streamingApiTimeoutConfig.connectTimeout.toJavaDuration())
            .readTimeout(streamingApiTimeoutConfig.readTimeout.toJavaDuration())
            .writeTimeout(streamingApiTimeoutConfig.writeTimeout.toJavaDuration())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @StreamingApiComponent.Local okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        @Named("apiEndpoint") apiEndpoint: String,
    ) =
        Retrofit.Builder()
            .baseUrl(apiEndpoint)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun providePlaybackInfoService(retrofit: Retrofit): PlaybackInfoService {
        return retrofit.create(PlaybackInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideDrmLicenseService(retrofit: Retrofit): DrmLicenseService {
        return retrofit.create(DrmLicenseService::class.java)
    }
}
