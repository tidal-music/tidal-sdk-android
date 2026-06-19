package com.tidal.sdk.player.streamingapi

import com.google.gson.Gson
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.model.ApiError
import com.tidal.sdk.player.streamingapi.di.DaggerStreamingApiComponent
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider
import java.io.File
import okhttp3.OkHttpClient

class StreamingApiModuleRoot(
    okHttpClient: OkHttpClient,
    streamingApiTimeoutConfig: StreamingApiTimeoutConfig,
    gson: Gson,
    apiErrorFactory: ApiError.Factory,
    offlinePlaybackInfoProvider: OfflinePlaybackInfoProvider?,
    credentialsProvider: CredentialsProvider,
    tidalApiCacheDir: File?,
    apiEndpoint: String,
    openApiEndpoint: String,
    eventSender: EventSender?,
) {

    val streamingApi =
        componentFactoryF()
            .create(
                okHttpClient,
                streamingApiTimeoutConfig,
                gson,
                apiErrorFactory,
                offlinePlaybackInfoProvider,
                credentialsProvider,
                tidalApiCacheDir,
                apiEndpoint,
                openApiEndpoint,
                eventSender,
            )
            .streamingApi

    companion object {

        private var componentFactoryF = { DaggerStreamingApiComponent.factory() }
    }
}
