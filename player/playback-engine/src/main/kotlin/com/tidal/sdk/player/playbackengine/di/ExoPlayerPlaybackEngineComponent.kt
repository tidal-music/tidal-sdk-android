package com.tidal.sdk.player.playbackengine.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.playbackengine.Encryption
import com.tidal.sdk.player.playbackengine.PlaybackEngine
import com.tidal.sdk.player.playbackengine.model.AssetTimeoutConfig
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.playbackengine.player.renderer.audio.AudioDecodingMode
import com.tidal.sdk.player.streamingapi.StreamingApi
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import dagger.BindsInstance
import dagger.Component
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient

@Component(modules = [ExoPlayerPlaybackEngineModule::class, ExtendedExoPlayerFactoryModule::class])
@Singleton
interface ExoPlayerPlaybackEngineComponent {

    val playbackEngine: PlaybackEngine

    @Component.Factory
    interface Factory {
        @Suppress("LongParameterList")
        fun create(
            @BindsInstance context: Context,
            @BindsInstance connectivityManager: ConnectivityManager,
            @BindsInstance events: MutableSharedFlow<Event>,
            @BindsInstance bufferConfiguration: BufferConfiguration,
            @BindsInstance assetTimeoutConfig: AssetTimeoutConfig,
            @BindsInstance cacheProvider: CacheProvider,
            @BindsInstance configuration: Configuration,
            @BindsInstance audioDecodingMode: AudioDecodingMode,
            @BindsInstance @Named("useLibflacAudioRenderer") useLibflacAudioRenderer: Boolean,
            @BindsInstance appSpecificCacheDir: File,
            @BindsInstance streamingApi: StreamingApi,
            @BindsInstance okHttpClient: OkHttpClient,
            @BindsInstance gson: Gson,
            @BindsInstance eventReporter: EventReporter,
            @BindsInstance streamingPrivileges: StreamingPrivileges,
            @BindsInstance uuidWrapper: UUIDWrapper,
            @BindsInstance trueTimeWrapper: TrueTimeWrapper,
            @BindsInstance playbackPrivilegeProvider: PlaybackPrivilegeProvider,
            @BindsInstance offlineCacheProvider: OfflineCacheProvider?,
            @BindsInstance encryption: Encryption?,
            @BindsInstance base64Codec: Base64Codec,
            @BindsInstance coroutineDispatcher: CoroutineDispatcher,
            @BindsInstance coroutineScope: CoroutineScope,
        ): ExoPlayerPlaybackEngineComponent
    }
}
