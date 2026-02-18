package com.tidal.sdk.player.di

import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.offlineplay.OfflinePlayProvider
import com.tidal.sdk.player.streamingapi.StreamingApiModuleRoot
import com.tidal.sdk.player.streamingapi.StreamingApiTimeoutConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient

private const val TIDAL_API_CACHE_DIR = "playerTidalApi"

@Module
internal object StreamingApiModule {

    @Provides @Reusable fun apiErrorFactory(gson: Gson) = ApiError.Factory(gson)

    @Provides
    @Reusable
    @Named("tidalApiCacheDir")
    fun tidalApiCacheDir(@Named("appSpecificCacheDir") appSpecificCacheDir: File) =
        File(appSpecificCacheDir, TIDAL_API_CACHE_DIR)

    @Provides
    @Singleton
    fun streamingApi(
        @LocalWithCacheAndAuth okHttpClient: OkHttpClient,
        streamingApiTimeoutConfig: StreamingApiTimeoutConfig,
        gson: Gson,
        apiErrorFactory: ApiError.Factory,
        offlinePlayProvider: OfflinePlayProvider?,
        credentialsProvider: CredentialsProvider,
        @Named("tidalApiCacheDir") tidalApiCacheDir: File,
    ) =
        StreamingApiModuleRoot(
                okHttpClient,
                streamingApiTimeoutConfig,
                gson,
                apiErrorFactory,
                offlinePlayProvider?.offlinePlaybackInfoProvider,
                credentialsProvider,
                tidalApiCacheDir,
            )
            .streamingApi
}
