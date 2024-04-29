package com.tidal.sdk.player.di

import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.offlineplay.OfflinePlayProvider
import com.tidal.sdk.player.streamingapi.StreamingApiModuleRoot
import com.tidal.sdk.player.streamingapi.StreamingApiTimeoutConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Module
internal object StreamingApiModule {

    @Provides
    @Reusable
    fun apiErrorFactory(gson: Gson) = ApiError.Factory(gson)

    @Provides
    @Singleton
    fun streamingApi(
        @LocalWithCacheAndAuth
        okHttpClient: OkHttpClient,
        streamingApiTimeoutConfig: StreamingApiTimeoutConfig,
        gson: Gson,
        apiErrorFactory: ApiError.Factory,
        offlinePlayProvider: OfflinePlayProvider?,
    ) = StreamingApiModuleRoot(
        okHttpClient,
        streamingApiTimeoutConfig,
        gson,
        apiErrorFactory,
        offlinePlayProvider?.offlinePlaybackInfoProvider,
    ).streamingApi
}
