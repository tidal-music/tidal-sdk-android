package com.tidal.sdk.player.playbackengine.player.di

import android.content.Context
import androidx.media3.common.C
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.cache.CacheDataSink
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheKeyFactory
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.manifest.DashManifest
import androidx.media3.exoplayer.dash.manifest.DashManifestParser
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.upstream.DefaultLoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.ParsingLoadable
import androidx.media3.extractor.ExtractorsFactory
import com.google.gson.Gson
import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.Base64Codec
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.playbackengine.Encryption
import com.tidal.sdk.player.playbackengine.PlayerLoadErrorHandlingPolicy
import com.tidal.sdk.player.playbackengine.StreamingApiRepository
import com.tidal.sdk.player.playbackengine.TidalExtractorsFactory
import com.tidal.sdk.player.playbackengine.audiomode.AudioModeRepository
import com.tidal.sdk.player.playbackengine.bts.DefaultBtsManifestFactory
import com.tidal.sdk.player.playbackengine.cache.DefaultCacheKeyFactory
import com.tidal.sdk.player.playbackengine.dash.DashManifestFactory
import com.tidal.sdk.player.playbackengine.datasource.CacheKeyAesCipherDataSourceFactoryFactory
import com.tidal.sdk.player.playbackengine.datasource.DecryptedHeaderFileDataSourceFactoryFactory
import com.tidal.sdk.player.playbackengine.di.ExoPlayerPlaybackEngineComponent
import com.tidal.sdk.player.playbackengine.drm.DrmSessionManagerFactory
import com.tidal.sdk.player.playbackengine.drm.DrmSessionManagerProviderFactory
import com.tidal.sdk.player.playbackengine.drm.MediaDrmCallbackExceptionFactory
import com.tidal.sdk.player.playbackengine.drm.TidalMediaDrmCallbackFactory
import com.tidal.sdk.player.playbackengine.emu.EmuManifestFactory
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.mediasource.DashMediaSourceFactoryFactory
import com.tidal.sdk.player.playbackengine.mediasource.MediaSourcerer
import com.tidal.sdk.player.playbackengine.mediasource.PlaybackInfoMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerAuthHlsMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerDashMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerDashOfflineMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerDecryptedHeaderProgressiveOfflineMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerHlsMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerProgressiveMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.PlayerProgressiveOfflineMediaSourceFactory
import com.tidal.sdk.player.playbackengine.mediasource.ProgressiveMediaSourceFactoryFactory
import com.tidal.sdk.player.playbackengine.mediasource.TidalMediaSourceCreator
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableFactory
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableLoaderCallbackFactory
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.playbackengine.model.AssetTimeoutConfig
import com.tidal.sdk.player.playbackengine.offline.OfflineDrmHelper
import com.tidal.sdk.player.playbackengine.offline.OfflinePlayDataSourceFactoryHelper
import com.tidal.sdk.player.playbackengine.offline.OfflinePlayDrmDataSourceFactoryHelper
import com.tidal.sdk.player.playbackengine.offline.OfflineStorageProvider
import com.tidal.sdk.player.playbackengine.offline.StorageDataSource
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider
import com.tidal.sdk.player.playbackengine.playbackprivilege.PlaybackPrivilegeProvider
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerState
import com.tidal.sdk.player.playbackengine.player.PlayerCache
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.quality.VideoQualityRepository
import com.tidal.sdk.player.streamingapi.StreamingApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.io.File
import javax.inject.Named
import kotlin.time.toJavaDuration
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient

private const val CACHE_DIR = "exoplayer-cache"
private const val MINIMUM_LOADABLE_RETRY_COUNT = 10

@Module
@Suppress("TooManyFunctions")
internal object MediaSourcererModule {

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun provideDatabaseProvider(context: Context): DatabaseProvider =
        StandaloneDatabaseProvider(context)

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun cache(
        appSpecificCacheDir: File,
        databaseProvider: DatabaseProvider,
        cacheProvider: CacheProvider,
    ): PlayerCache {
        return when (cacheProvider) {
            is CacheProvider.External -> PlayerCache.Provided(cacheProvider.cache)
            is CacheProvider.Internal -> {
                val cacheDir = File(appSpecificCacheDir, CACHE_DIR)
                val cacheEvictor = LeastRecentlyUsedCacheEvictor(cacheProvider.cacheSizeBytes.value)
                PlayerCache.Internal(SimpleCache(cacheDir, cacheEvictor, databaseProvider))
            }
        }
    }

    @Provides
    @Reusable
    @ExtendedExoPlayerComponent.Local
    fun okHttpClient(
        @ExoPlayerPlaybackEngineComponent.Local
        okHttpClient: OkHttpClient,
        assetTimeoutConfig: AssetTimeoutConfig,
    ) = okHttpClient.newBuilder()
        .connectTimeout(assetTimeoutConfig.connectTimeout.toJavaDuration())
        .readTimeout(assetTimeoutConfig.readTimeout.toJavaDuration())
        .writeTimeout(assetTimeoutConfig.writeTimeout.toJavaDuration())
        .build()

    @Provides
    @Reusable
    @ExtendedExoPlayerComponent.LocalWithAuth
    fun okHttpClientWithAuth(
        @ExoPlayerPlaybackEngineComponent.LocalWithAuth
        okHttpClient: OkHttpClient,
        assetTimeoutConfig: AssetTimeoutConfig,
    ) = okHttpClient.newBuilder()
        .connectTimeout(assetTimeoutConfig.connectTimeout.toJavaDuration())
        .readTimeout(assetTimeoutConfig.readTimeout.toJavaDuration())
        .writeTimeout(assetTimeoutConfig.writeTimeout.toJavaDuration())
        .build()

    @Provides
    @ExtendedExoPlayerComponent.Local
    fun okHttpDataSourceFactory(
        @ExtendedExoPlayerComponent.Local okHttpClient: OkHttpClient,
    ): OkHttpDataSource.Factory {
        return OkHttpDataSource.Factory(okHttpClient)
    }

    @Provides
    @ExtendedExoPlayerComponent.LocalWithAuth
    fun okHttpDataSourceFactoryWithAuth(
        @ExtendedExoPlayerComponent.LocalWithAuth okHttpClient: OkHttpClient,
    ): OkHttpDataSource.Factory {
        return OkHttpDataSource.Factory(okHttpClient)
    }

    @Provides
    @Reusable
    fun fileDataSourceFactory() = FileDataSource.Factory()

    @Provides
    @Reusable
    fun cacheKeyFactory(): CacheKeyFactory = DefaultCacheKeyFactory()

    @Provides
    @Reusable
    fun cacheDataSinkFactory(playerCache: PlayerCache) =
        CacheDataSink.Factory().setCache(playerCache.cache)

    @Provides
    @Reusable
    fun provideCacheDataSourceFactoryForOnline(
        playerCache: PlayerCache,
        @ExtendedExoPlayerComponent.Local
        okHttpDataSourceFactory: OkHttpDataSource.Factory,
        fileDataSourceFactory: FileDataSource.Factory,
        cacheKeyFactory: CacheKeyFactory,
        cacheDataSinkFactory: CacheDataSink.Factory,
    ): CacheDataSource.Factory {
        return CacheDataSource.Factory()
            .setCache(playerCache.cache)
            .setCacheKeyFactory(cacheKeyFactory)
            .setUpstreamDataSourceFactory(okHttpDataSourceFactory)
            .setCacheReadDataSourceFactory(fileDataSourceFactory)
            .setCacheWriteDataSinkFactory(cacheDataSinkFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    @Provides
    @Reusable
    fun extractorsFactory(): ExtractorsFactory = TidalExtractorsFactory()

    @Provides
    @Reusable
    fun progressiveMediaSourceFactoryFactory(
        extractorsFactory: ExtractorsFactory,
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ) = ProgressiveMediaSourceFactoryFactory(extractorsFactory, loadErrorHandlingPolicy)

    @Provides
    @Reusable
    @Named("defaultLoadErrorHandlingPolicy")
    fun loadErrorHandlingPolicy(): LoadErrorHandlingPolicy =
        DefaultLoadErrorHandlingPolicy(MINIMUM_LOADABLE_RETRY_COUNT)

    @Provides
    @Reusable
    internal fun providePlayerLoadErrorHandlingPolicy(
        @Named("defaultLoadErrorHandlingPolicy")
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ): LoadErrorHandlingPolicy = PlayerLoadErrorHandlingPolicy(loadErrorHandlingPolicy)

    @Provides
    @Reusable
    fun dashMediaSourceFactory(
        cacheDataSourceFactoryForOnline: CacheDataSource.Factory,
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ): DashMediaSource.Factory {
        return DashMediaSource.Factory(cacheDataSourceFactoryForOnline)
            .setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
    }

    @Provides
    @Reusable
    @ExtendedExoPlayerComponent.Local
    fun hlsMediaSourceFactory(
        @ExtendedExoPlayerComponent.Local
        okHttpDataSourceFactory: OkHttpDataSource.Factory,
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ): HlsMediaSource.Factory {
        return HlsMediaSource.Factory(okHttpDataSourceFactory)
            .setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
    }

    @Provides
    @Reusable
    @ExtendedExoPlayerComponent.LocalWithAuth
    fun authHlsMediaSourceFactory(
        @ExtendedExoPlayerComponent.LocalWithAuth
        okHttpDataSourceFactory: OkHttpDataSource.Factory,
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ): HlsMediaSource.Factory {
        return HlsMediaSource.Factory(okHttpDataSourceFactory)
            .setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
    }

    @Provides
    @Reusable
    fun btsManifestFactory(
        gson: Gson,
        base64Codec: Base64Codec,
    ) = DefaultBtsManifestFactory(gson, base64Codec)

    @Provides
    @Reusable
    fun storageDataSource() = StorageDataSource()

    @Provides
    @Reusable
    fun storageDataSourceFactory(
        storageDataSource: StorageDataSource,
    ) = StorageDataSource.Factory(storageDataSource)

    @Provides
    @Reusable
    @Named("cacheDataSourceFactoryForOfflinePlay")
    fun cacheDataSourceFactory(
        storageDataSourceFactory: StorageDataSource.Factory,
        cacheKeyFactory: CacheKeyFactory,
    ) = CacheDataSource.Factory()
        .setCacheKeyFactory(cacheKeyFactory)
        .setUpstreamDataSourceFactory(storageDataSourceFactory)
        .setCacheWriteDataSinkFactory(null)

    @Provides
    @Reusable
    fun cacheKeyAesCipherDataSourceFactoryFactory(
        @Named("cacheDataSourceFactoryForOfflinePlay")
        cacheDataSourceFactory: CacheDataSource.Factory,
        cacheKeyFactory: CacheKeyFactory,
        encryption: Encryption?,
    ) = CacheKeyAesCipherDataSourceFactoryFactory(
        cacheDataSourceFactory,
        cacheKeyFactory,
        encryption,
    )

    @Provides
    @Reusable
    fun offlinePlayDataSourceFactoryHelper(
        cacheKeyAesCipherDataSourceFactoryFactory: CacheKeyAesCipherDataSourceFactoryFactory,
        offlineCacheProvider: OfflineCacheProvider?,
    ) = OfflinePlayDataSourceFactoryHelper(
        cacheKeyAesCipherDataSourceFactoryFactory,
        offlineCacheProvider,
    )

    @Provides
    @Reusable
    fun offlinePlayDrmDataSourceFactoryHelper(
        @Named("cacheDataSourceFactoryForOfflinePlay")
        cacheDataSourceFactory: CacheDataSource.Factory,
        offlineCacheProvider: OfflineCacheProvider?,
    ) = OfflinePlayDrmDataSourceFactoryHelper(
        cacheDataSourceFactory,
        offlineCacheProvider,
    )

    @Provides
    @Reusable
    fun offlineStorageProvider(
        offlinePlayDataSourceFactoryHelper: OfflinePlayDataSourceFactoryHelper,
        offlinePlayDrmDataSourceFactoryHelper: OfflinePlayDrmDataSourceFactoryHelper,
    ) = OfflineStorageProvider(
        offlinePlayDataSourceFactoryHelper,
        offlinePlayDrmDataSourceFactoryHelper,
    )

    @Provides
    @Reusable
    fun playerProgressiveOfflineMediaSourceFactory(
        progressiveMediaSourceFactoryFactory: ProgressiveMediaSourceFactoryFactory,
        btsManifestFactory: DefaultBtsManifestFactory,
        offlineStorageProvider: OfflineStorageProvider,
    ) = PlayerProgressiveOfflineMediaSourceFactory(
        progressiveMediaSourceFactoryFactory,
        btsManifestFactory,
        offlineStorageProvider,
    )

    @Provides
    @Reusable
    fun decryptedHeaderFileDataSourceFactoryFactory(
        upstream: FileDataSource.Factory,
    ) = DecryptedHeaderFileDataSourceFactoryFactory(upstream)

    @Provides
    @Reusable
    fun playerDecryptedHeaderProgressiveOfflineMediaSourceFactory(
        progressiveMediaSourceFactoryFactory: ProgressiveMediaSourceFactoryFactory,
        decryptedHeaderFileDataSourceFactoryFactory: DecryptedHeaderFileDataSourceFactoryFactory,
        encryption: Encryption?,
        btsManifestFactory: DefaultBtsManifestFactory,
    ) = PlayerDecryptedHeaderProgressiveOfflineMediaSourceFactory(
        progressiveMediaSourceFactoryFactory,
        decryptedHeaderFileDataSourceFactoryFactory,
        encryption,
        btsManifestFactory,
    )

    @Provides
    @Reusable
    fun dashManifestParser(): ParsingLoadable.Parser<DashManifest> = DashManifestParser()

    @Provides
    @Reusable
    fun dashManifestMapper(
        base64Codec: Base64Codec,
        dashManifestParser: ParsingLoadable.Parser<DashManifest>,
    ) = DashManifestFactory(base64Codec, dashManifestParser)

    @Provides
    @Reusable
    fun playerDashMediaSourceFactory(
        dashMediaSourceFactory: DashMediaSource.Factory,
        dashManifestFactory: DashManifestFactory,
    ) = PlayerDashMediaSourceFactory(dashMediaSourceFactory, dashManifestFactory)

    @Provides
    @Reusable
    fun dashMediaSourceFactoryFactory(
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ) = DashMediaSourceFactoryFactory(loadErrorHandlingPolicy)

    @Provides
    @Reusable
    fun offlineDrmHelper(base64Codec: Base64Codec) = OfflineDrmHelper(base64Codec)

    @Provides
    @Reusable
    fun playerDashOfflineMediaSourceFactory(
        dashMediaSourceFactoryFactory: DashMediaSourceFactoryFactory,
        dashManifestFactory: DashManifestFactory,
        offlineStorageProvider: OfflineStorageProvider,
        offlineDrmHelper: OfflineDrmHelper,
    ) = PlayerDashOfflineMediaSourceFactory(
        dashMediaSourceFactoryFactory,
        dashManifestFactory,
        offlineStorageProvider,
        offlineDrmHelper,
    )

    @Provides
    @Reusable
    fun emuManifestFactory(
        gson: Gson,
        base64Codec: Base64Codec,
    ) = EmuManifestFactory(gson, base64Codec)

    @Provides
    @Reusable
    fun playerHlsMediaSourceFactory(
        @ExtendedExoPlayerComponent.Local
        hlsMediaSourceFactory: HlsMediaSource.Factory,
        emuManifestFactory: EmuManifestFactory,
    ) = PlayerHlsMediaSourceFactory(hlsMediaSourceFactory, emuManifestFactory)

    @Provides
    @Reusable
    fun playerAuthHlsMediaSourceFactory(
        @ExtendedExoPlayerComponent.LocalWithAuth
        hlsMediaSourceFactory: HlsMediaSource.Factory,
    ) = PlayerAuthHlsMediaSourceFactory(hlsMediaSourceFactory)

    @Provides
    @Reusable
    fun defaultDrmSessionManagerBuilder(
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ) = DefaultDrmSessionManager.Builder()
        .setLoadErrorHandlingPolicy(loadErrorHandlingPolicy)
        .setUseDrmSessionsForClearContent(C.TRACK_TYPE_AUDIO, C.TRACK_TYPE_VIDEO)
        .setPlayClearSamplesWithoutKeys(true)
        .setMultiSession(true)
        .setForceWidevineL3(true)
        .setUuidAndExoMediaDrmProvider(C.WIDEVINE_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER)

    @Provides
    @Reusable
    fun videoQualityRepository() = VideoQualityRepository()

    @Provides
    @Reusable
    fun mediaDrmCallbackExceptionFactory() = MediaDrmCallbackExceptionFactory()

    @Provides
    @Reusable
    fun streamingApiRepository(
        streamingApi: StreamingApi,
        audioQualityRepository: AudioQualityRepository,
        videoQualityRepository: VideoQualityRepository,
        audioModeRepository: AudioModeRepository,
        trueTimeWrapper: TrueTimeWrapper,
        mediaDrmCallbackExceptionFactory: MediaDrmCallbackExceptionFactory,
        eventReporter: EventReporter,
        errorHandler: ErrorHandler,
    ) = StreamingApiRepository(
        streamingApi,
        audioQualityRepository,
        videoQualityRepository,
        audioModeRepository,
        trueTimeWrapper,
        mediaDrmCallbackExceptionFactory,
        eventReporter,
        errorHandler,
    )

    @Provides
    @Reusable
    fun tidalMediaDrmCallbackFactory(
        streamingApiRepository: StreamingApiRepository,
        base64Codec: Base64Codec,
        @ExtendedExoPlayerComponent.Local
        okHttpClient: OkHttpClient,
    ) = TidalMediaDrmCallbackFactory(
        streamingApiRepository,
        base64Codec,
        okHttpClient,
    )

    @Provides
    @Reusable
    fun drmSessionManagerFactory(
        defaultDrmSessionManagerBuilder: DefaultDrmSessionManager.Builder,
        tidalMediaDrmCallbackFactory: TidalMediaDrmCallbackFactory,
    ) = DrmSessionManagerFactory(
        defaultDrmSessionManagerBuilder,
        tidalMediaDrmCallbackFactory,
    )

    @Provides
    @Reusable
    fun drmSessionManagerProviderFactory() = DrmSessionManagerProviderFactory()

    @Provides
    @Reusable
    fun tidalMediaSourceCreator(
        playerProgressiveMediaSourceFactory: PlayerProgressiveMediaSourceFactory,
        playerDashMediaSourceFactory: PlayerDashMediaSourceFactory,
        playerHlsMediaSourceFactory: PlayerHlsMediaSourceFactory,
        playerAuthHlsMediaSourceFactory: PlayerAuthHlsMediaSourceFactory,
        @Suppress("MaxLineLength") playerDecryptedHeaderProgressiveOfflineMediaSourceFactory: PlayerDecryptedHeaderProgressiveOfflineMediaSourceFactory, // ktlint-disable max-line-length parameter-wrapping
        playerProgressiveOfflineMediaSourceFactory: PlayerProgressiveOfflineMediaSourceFactory,
        playerDashOfflineMediaSourceFactory: PlayerDashOfflineMediaSourceFactory,
        drmSessionManagerFactory: DrmSessionManagerFactory,
        drmSessionManagerProviderFactory: DrmSessionManagerProviderFactory,
    ) = TidalMediaSourceCreator(
        playerProgressiveMediaSourceFactory,
        playerDashMediaSourceFactory,
        playerHlsMediaSourceFactory,
        playerAuthHlsMediaSourceFactory,
        playerDecryptedHeaderProgressiveOfflineMediaSourceFactory,
        playerProgressiveOfflineMediaSourceFactory,
        playerDashOfflineMediaSourceFactory,
        drmSessionManagerFactory,
        drmSessionManagerProviderFactory,
    )

    @Provides
    @Reusable
    fun playbackInfoLoadableFactory(
        streamingApiRepository: StreamingApiRepository,
        coroutineDispatcher: CoroutineDispatcher,
        extendedExoPlayerState: ExtendedExoPlayerState,
        playbackPrivilegeProvider: PlaybackPrivilegeProvider,
    ) = PlaybackInfoLoadableFactory(
        streamingApiRepository,
        coroutineDispatcher,
        extendedExoPlayerState,
        playbackPrivilegeProvider,
    )

    @Provides
    @Reusable
    fun playbackInfoLoadableLoaderCallbackFactory(
        tidalMediaSourceCreator: TidalMediaSourceCreator,
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    ) = PlaybackInfoLoadableLoaderCallbackFactory(tidalMediaSourceCreator, loadErrorHandlingPolicy)

    @Provides
    @Reusable
    fun playbackInfoMediaSourceFactory(
        loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
        playbackInfoLoadableFactory: PlaybackInfoLoadableFactory,
        playbackInfoLoadableLoaderCallbackFactory: PlaybackInfoLoadableLoaderCallbackFactory,
    ) = PlaybackInfoMediaSourceFactory(
        loadErrorHandlingPolicy,
        playbackInfoLoadableFactory,
        playbackInfoLoadableLoaderCallbackFactory,
    )

    @Provides
    @Reusable
    fun explicitStreamingSessionFactory(uuidWrapper: UUIDWrapper, configuration: Configuration) =
        StreamingSession.Factory.Explicit(uuidWrapper, configuration)

    @Provides
    @Reusable
    fun explicitStreamingSessionCreator(
        streamingSessionFactoryExplicit: StreamingSession.Factory.Explicit,
        trueTimeWrapper: TrueTimeWrapper,
        eventReporter: EventReporter,
    ) = StreamingSession.Creator.Explicit(
        streamingSessionFactoryExplicit,
        trueTimeWrapper,
        eventReporter,
    )

    @Provides
    @Reusable
    fun implicitStreamingSessionFactory(uuidWrapper: UUIDWrapper, configuration: Configuration) =
        StreamingSession.Factory.Implicit(uuidWrapper, configuration)

    @Provides
    @Reusable
    fun implicitStreamingSessionCreator(
        streamingSessionFactoryImplicit: StreamingSession.Factory.Implicit,
        trueTimeWrapper: TrueTimeWrapper,
        eventReporter: EventReporter,
    ) = StreamingSession.Creator.Implicit(
        streamingSessionFactoryImplicit,
        trueTimeWrapper,
        eventReporter,
    )

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun mediaSourcerer(
        exoPlayer: ExoPlayer,
        playbackInfoMediaSourceFactory: PlaybackInfoMediaSourceFactory,
        explicitStreamingSessionFactory: StreamingSession.Creator.Explicit,
        implicitStreamingSessionFactory: StreamingSession.Creator.Implicit,
        eventReporter: EventReporter,
        trueTimeWrapper: TrueTimeWrapper,
    ) = MediaSourcerer(
        exoPlayer,
        playbackInfoMediaSourceFactory,
        explicitStreamingSessionFactory,
        implicitStreamingSessionFactory,
        eventReporter,
        trueTimeWrapper,
    )
}
