package com.tidal.sdk.player.streamingapi.di

import com.google.gson.Gson
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.streamingapi.StreamingApi
import com.tidal.sdk.player.streamingapi.StreamingApiTimeoutConfig
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Singleton
@Component(
    modules = [
        StreamingApiModule::class,
        PlaybackInfoModule::class,
        DrmLicenseModule::class,
        RetrofitModule::class,
    ],
)
interface StreamingApiComponent {

    val streamingApi: StreamingApi

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance okHttpClient: OkHttpClient,
            @BindsInstance streamingApiTimeoutConfig: StreamingApiTimeoutConfig,
            @BindsInstance gson: Gson,
            @BindsInstance apiErrorFactory: ApiError.Factory,
            @BindsInstance offlinePlaybackInfoProvider: OfflinePlaybackInfoProvider?,
        ): StreamingApiComponent
    }

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Local
}
