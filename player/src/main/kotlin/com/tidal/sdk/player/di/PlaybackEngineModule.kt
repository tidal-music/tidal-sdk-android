package com.tidal.sdk.player.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.offlineplay.OfflinePlayProvider
import com.tidal.sdk.player.playbackengine.PlaybackEngineModuleRoot
import com.tidal.sdk.player.playbackengine.model.AssetTimeoutConfig
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.offline.OfflineLicenseProvider
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.streamingapi.StreamingApi
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient

@Module
internal object PlaybackEngineModule {

    @Provides @Singleton fun events() = MutableSharedFlow<Event>()

    @Provides
    @Singleton
    fun configuration(@Named("isOfflineMode") isOfflineMode: Boolean) = Configuration(isOfflineMode)

    @Provides
    @Singleton
    fun playbackEngineModuleRoot(
        context: Context,
        connectivityManager: ConnectivityManager,
        @Named("enableDecoderFallback") enableDecoderFallback: Boolean,
        events: MutableSharedFlow<Event>,
        bufferConfiguration: BufferConfiguration,
        assetTimeoutConfig: AssetTimeoutConfig,
        cacheProvider: CacheProvider,
        configuration: Configuration,
        @Named("appSpecificCacheDir") appSpecificCacheDir: File,
        streamingApi: StreamingApi,
        @Local okHttpClient: OkHttpClient,
        @LocalWithAuth okHttpClientWithAuth: OkHttpClient,
        gson: Gson,
        eventReporter: EventReporter,
        streamingPrivileges: StreamingPrivileges,
        uuidWrapper: UUIDWrapper,
        trueTimeWrapper: TrueTimeWrapper,
        playbackPrivilegeProvider: PlaybackPrivilegeProvider,
        offlinePlayProvider: OfflinePlayProvider?,
        base64Codec: Base64Codec,
        coroutineDispatcher: CoroutineDispatcher,
        coroutineScope: CoroutineScope,
    ): PlaybackEngineModuleRoot =
        PlaybackEngineModuleRoot(
            context,
            connectivityManager,
            enableDecoderFallback,
            events,
            bufferConfiguration,
            assetTimeoutConfig,
            cacheProvider,
            configuration,
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
            offlinePlayProvider?.offlineCacheProvider,
            offlinePlayProvider?.encryption,
            base64Codec,
            coroutineDispatcher,
            coroutineScope,
        )

    @Provides fun playbackEngine(root: PlaybackEngineModuleRoot) = root.playbackEngine

    @Provides
    fun offlineLicenseProvider(root: PlaybackEngineModuleRoot): OfflineLicenseProvider =
        root.offlineLicenseProvider
}
