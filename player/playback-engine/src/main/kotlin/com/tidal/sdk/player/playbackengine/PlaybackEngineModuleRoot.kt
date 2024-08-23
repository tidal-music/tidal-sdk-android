package com.tidal.sdk.player.playbackengine

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.playbackengine.di.DaggerExoPlayerPlaybackEngineComponent
import com.tidal.sdk.player.playbackengine.model.AssetTimeoutConfig
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.streamingapi.StreamingApi
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import java.io.File
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient

@Suppress("LongParameterList")
class PlaybackEngineModuleRoot(
    context: Context,
    connectivityManager: ConnectivityManager,
    useLibflacAudioRenderer: Boolean,
    events: MutableSharedFlow<Event>,
    bufferConfiguration: BufferConfiguration,
    assetTimeoutConfig: AssetTimeoutConfig,
    cacheProvider: CacheProvider,
    configuration: Configuration,
    appSpecificCacheDir: File,
    streamingApi: StreamingApi,
    okHttpClient: OkHttpClient,
    okHttpClientWithAuth: OkHttpClient,
    gson: Gson,
    eventReporter: EventReporter,
    streamingPrivileges: StreamingPrivileges,
    uuidWrapper: UUIDWrapper,
    trueTimeWrapper: TrueTimeWrapper,
    playbackPrivilegeProvider: PlaybackPrivilegeProvider,
    offlineCacheProvider: OfflineCacheProvider?,
    encryption: Encryption?,
    base64Codec: Base64Codec,
    coroutineDispatcher: CoroutineDispatcher,
    coroutineScope: CoroutineScope,
) {

    private val component = componentFactoryF()
        .create(
            context,
            connectivityManager,
            events,
            bufferConfiguration,
            assetTimeoutConfig,
            cacheProvider,
            configuration,
            useLibflacAudioRenderer,
            appSpecificCacheDir,
            streamingApi,
            okHttpClient,
            okHttpClientWithAuth,
            gson,
            eventReporter,
            streamingPrivileges,
            uuidWrapper,
            trueTimeWrapper,
            playbackPrivilegeProvider,
            offlineCacheProvider,
            encryption,
            base64Codec,
            coroutineDispatcher,
            coroutineScope,
        )
    val playbackEngine = component.playbackEngine

    companion object {

        private var componentFactoryF = { DaggerExoPlayerPlaybackEngineComponent.factory() }
    }
}
