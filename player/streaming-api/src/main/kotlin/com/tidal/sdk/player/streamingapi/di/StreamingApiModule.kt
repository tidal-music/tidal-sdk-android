package com.tidal.sdk.player.streamingapi.di

import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.streamingapi.StreamingApi
import com.tidal.sdk.player.streamingapi.StreamingApiDefault
import com.tidal.sdk.player.streamingapi.drm.repository.DrmLicenseRepository
import com.tidal.sdk.player.streamingapi.playbackinfo.mapper.ApiErrorMapper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.repository.PlaybackInfoRepository
import com.tidal.sdk.tidalapi.generated.TidalApiClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal object StreamingApiModule {

    @Provides
    @Reusable
    @StreamingApiComponent.Local
    fun provideGson(gson: Gson): Gson {
        return gson
            .newBuilder()
            .registerTypeAdapter(
                ManifestMimeType::class.java,
                ManifestMimeType.Converter.Deserializer(),
            )
            .create()
    }

    @Provides
    @Reusable
    fun provideConverterFactory(@StreamingApiComponent.Local gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Reusable
    fun provideApiErrorMapper(apiErrorFactory: ApiError.Factory) = ApiErrorMapper(apiErrorFactory)

    @Provides
    @Reusable
    fun provideTidalApiClient(credentialsProvider: CredentialsProvider): TidalApiClient =
        TidalApiClient(credentialsProvider)

    @Provides
    @Reusable
    fun streamingApiDefault(
        playbackInfoRepository: PlaybackInfoRepository,
        drmLicenseRepository: DrmLicenseRepository,
    ): StreamingApi = StreamingApiDefault(playbackInfoRepository, drmLicenseRepository)
}
