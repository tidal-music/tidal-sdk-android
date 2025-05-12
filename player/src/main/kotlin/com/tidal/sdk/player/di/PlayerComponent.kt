package com.tidal.sdk.player.di

import android.content.Context
import com.tidal.sdk.auth.CredentialsProvider
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.offlineplay.OfflinePlayProvider
import com.tidal.sdk.player.playbackengine.PlaybackEngine
import com.tidal.sdk.player.playbackengine.model.AssetTimeoutConfig
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.streamingapi.StreamingApiTimeoutConfig
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Component(
    modules =
        [
            PlayerModule::class,
            EventReporterModule::class,
            NetworkModule::class,
            PlaybackEngineModule::class,
            StreamingApiModule::class,
            StreamingPrivilegesModule::class,
        ]
)
@Singleton
internal interface PlayerComponent {

    val configuration: Configuration
    val playbackEngine: PlaybackEngine
    val streamingPrivileges: StreamingPrivileges

    @Component.Factory
    interface Factory {

        @Suppress("LongParameterList")
        fun create(
            @BindsInstance context: Context,
            @BindsInstance credentialsProvider: CredentialsProvider,
            @BindsInstance eventSender: EventSender,
            @BindsInstance @Named("useLibflacAudioRenderer") useLibflacAudioRenderer: Boolean,
            @BindsInstance userClientIdSupplier: (() -> Int)?,
            @BindsInstance version: String,
            @BindsInstance bufferConfiguration: BufferConfiguration,
            @BindsInstance assetTimeoutConfig: AssetTimeoutConfig,
            @BindsInstance streamingApiTimeoutConfig: StreamingApiTimeoutConfig,
            @BindsInstance cacheProvider: CacheProvider,
            @BindsInstance @Named("isOfflineMode") isOfflineMode: Boolean,
            @BindsInstance okHttpClient: OkHttpClient,
            @BindsInstance @Named("isDebuggable") isDebuggable: Boolean,
            @BindsInstance playbackPrivilegeProvider: PlaybackPrivilegeProvider,
            @BindsInstance offlinePlayProvider: OfflinePlayProvider?,
        ): PlayerComponent
    }
}
