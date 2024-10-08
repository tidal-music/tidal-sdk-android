package com.tidal.sdk.player.playbackengine.di

import android.content.Context
import android.media.AudioManager
import android.net.ConnectivityManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.drm.ExoMediaDrm
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.playbackengine.ExoPlayerPlaybackEngine
import com.tidal.sdk.player.playbackengine.PlaybackContextFactory
import com.tidal.sdk.player.playbackengine.PlaybackEngine
import com.tidal.sdk.player.playbackengine.SingleHandlerPlaybackEngine
import com.tidal.sdk.player.playbackengine.audiomode.AudioModeRepository
import com.tidal.sdk.player.playbackengine.dj.DateParser
import com.tidal.sdk.player.playbackengine.dj.DjSessionManager
import com.tidal.sdk.player.playbackengine.dj.HlsTagsParser
import com.tidal.sdk.player.playbackengine.error.ErrorCodeFactory
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.UndeterminedPlaybackSessionResolver
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.VersionedCdm
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.network.NetworkTransportHelper
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDeviceManager
import com.tidal.sdk.player.playbackengine.player.CacheProvider
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerFactory
import com.tidal.sdk.player.playbackengine.player.PlayerCache
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.util.SynchronousSurfaceHolder
import com.tidal.sdk.player.playbackengine.volume.LoudnessNormalizer
import com.tidal.sdk.player.playbackengine.volume.VolumeHelper
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

@Module
@Suppress("TooManyFunctions")
internal object ExoPlayerPlaybackEngineModule {

    @Provides
    @Singleton
    fun looper(): Looper = HandlerThread("ExoPlayerPlaybackEngineThread").let {
        it.start()
        it.looper
    }

    @Provides
    @Singleton
    fun handler(looper: Looper) = Handler(looper)

    @Provides
    @Reusable
    fun audioManager(context: Context) = context.getSystemService(Context.AUDIO_SERVICE)
        as AudioManager

    @Provides
    @Reusable
    @Named("outputDeviceHandler")
    fun outputDeviceHandler(looper: Looper) = Handler(looper)

    @Provides
    @Singleton
    fun outputDeviceManager(
        audioManager: AudioManager,
        @Named("outputDeviceHandler") handler: Handler,
    ) = OutputDeviceManager(audioManager, handler)

    @Provides
    @Singleton
    @Named("internalHandler")
    fun internalHandler(looper: Looper) = Handler(looper)

    @Provides
    @Reusable
    fun playbackContextFactory() = PlaybackContextFactory()

    @Provides
    @Reusable
    fun networkTransportHelper(connectivityManager: ConnectivityManager) =
        NetworkTransportHelper(connectivityManager)

    @Provides
    @Singleton
    fun audioQualityRepository(networkTransportHelper: NetworkTransportHelper) =
        AudioQualityRepository(networkTransportHelper)

    @Provides
    @Reusable
    fun loudnessNormalizer() = LoudnessNormalizer()

    @Provides
    @Singleton
    fun volumeHelper(loudnessNormalizer: LoudnessNormalizer) = VolumeHelper(loudnessNormalizer)

    @Provides
    @Reusable
    fun errorCodeFactory() = ErrorCodeFactory()

    @Provides
    @Reusable
    fun errorHandler(errorCodeFactory: ErrorCodeFactory) = ErrorHandler(errorCodeFactory)

    @Provides
    @Reusable
    fun hlsTagsParser() = HlsTagsParser()

    @Provides
    @Reusable
    fun dateParser() = DateParser()

    @Provides
    @Reusable
    @Named("djSessionHandler")
    fun djSessionHandler(looper: Looper) = Handler(looper)

    @Provides
    @Singleton
    fun djSessionManager(
        hlsTagsParser: HlsTagsParser,
        dateParser: DateParser,
        @Named("djSessionHandler") handler: Handler,
    ) = DjSessionManager(hlsTagsParser, dateParser, handler)

    @Provides
    @Reusable
    @Suppress("UnsafeOptInUsageError")
    fun exoMediaDrmProvider() = FrameworkMediaDrm.DEFAULT_PROVIDER

    @Provides
    @Reusable
    @Suppress("UnsafeOptInUsageError")
    fun versionedCdmCalculator(exoMediaDrmProvider: ExoMediaDrm.Provider) =
        VersionedCdm.Calculator(exoMediaDrmProvider)

    @Provides
    @Reusable
    fun undeterminedPlaybackSessionResolver(versionedCdmCalculator: VersionedCdm.Calculator) =
        UndeterminedPlaybackSessionResolver(versionedCdmCalculator)

    @Provides
    @Singleton
    fun audioModeRepository() = AudioModeRepository()

    @Provides
    @Singleton
    @Suppress("UnsafeOptInUsageError")
    fun provideDatabaseProvider(context: Context): DatabaseProvider =
        StandaloneDatabaseProvider(context)

    @Provides
    @Singleton
    @Suppress("UnsafeOptInUsageError")
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
    @Singleton
    fun exoPlayerPlaybackEngine(
        coroutineScope: CoroutineScope,
        extendedExoPlayerFactory: ExtendedExoPlayerFactory,
        @Named("internalHandler") internalHandler: Handler,
        events: MutableSharedFlow<Event>,
        synchronousSurfaceHolderFactory: SynchronousSurfaceHolder.Factory,
        streamingPrivileges: StreamingPrivileges,
        playbackContextFactory: PlaybackContextFactory,
        audioQualityRepository: AudioQualityRepository,
        audioModeRepository: AudioModeRepository,
        volumeHelper: VolumeHelper,
        trueTimeWrapper: TrueTimeWrapper,
        eventReporter: EventReporter,
        errorHandler: ErrorHandler,
        djSessionManager: DjSessionManager,
        undeterminedPlaybackSessionResolver: UndeterminedPlaybackSessionResolver,
        outputDeviceManager: OutputDeviceManager,
        playerCache: PlayerCache,
    ) = ExoPlayerPlaybackEngine(
        coroutineScope,
        extendedExoPlayerFactory,
        internalHandler,
        events,
        synchronousSurfaceHolderFactory,
        streamingPrivileges,
        playbackContextFactory,
        audioQualityRepository,
        audioModeRepository,
        volumeHelper,
        trueTimeWrapper,
        eventReporter,
        errorHandler,
        djSessionManager,
        undeterminedPlaybackSessionResolver,
        outputDeviceManager,
        playerCache,
    )

    @Provides
    @Reusable
    fun singleThreadExoPlayerPlaybackEngine(
        handler: Handler,
        delegate: ExoPlayerPlaybackEngine,
    ): PlaybackEngine = SingleHandlerPlaybackEngine(handler, delegate)
}

private const val CACHE_DIR = "exoplayer-cache"
