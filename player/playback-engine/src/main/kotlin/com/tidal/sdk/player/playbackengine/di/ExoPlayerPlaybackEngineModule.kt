package com.tidal.sdk.player.playbackengine.di

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.net.ConnectivityManager
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.media3.exoplayer.drm.ExoMediaDrm
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import com.google.gson.Gson
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.playbackengine.ExoPlayerPlaybackEngine
import com.tidal.sdk.player.playbackengine.PlaybackContextFactory
import com.tidal.sdk.player.playbackengine.PlaybackEngine
import com.tidal.sdk.player.playbackengine.SingleHandlerPlaybackEngine
import com.tidal.sdk.player.playbackengine.dj.DateParser
import com.tidal.sdk.player.playbackengine.dj.DjSessionManager
import com.tidal.sdk.player.playbackengine.dj.HlsTagsParser
import com.tidal.sdk.player.playbackengine.error.ErrorCodeFactory
import com.tidal.sdk.player.playbackengine.error.ErrorHandler
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackReport
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.UndeterminedPlaybackSessionResolver
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.VersionedCdm
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.network.NetworkTransportHelper
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDeviceManager
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerFactory
import com.tidal.sdk.player.playbackengine.quality.AudioQualityRepository
import com.tidal.sdk.player.playbackengine.util.SynchronousSurfaceHolder
import com.tidal.sdk.player.playbackengine.volume.LoudnessNormalizer
import com.tidal.sdk.player.playbackengine.volume.VolumeHelper
import com.tidal.sdk.player.streamingprivileges.StreamingPrivileges
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow

@Module
@Suppress("TooManyFunctions")
internal object ExoPlayerPlaybackEngineModule {

    @Provides
    @Reusable
    fun coroutineDispatcher() = Dispatchers.Default

    @Provides
    @Singleton
    fun coroutineScope(coroutineDispatcher: CoroutineDispatcher) =
        CoroutineScope(coroutineDispatcher)

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
    fun sharedPreferences(context: Context) = context.getSharedPreferences(
        context.packageName + "_preferences_com.tidal.sdk.player",
        Context.MODE_PRIVATE,
    )

    @Provides
    @Singleton
    fun playbackReportHandler(
        gson: Gson,
        sharedPreferences: SharedPreferences,
        eventReporter: EventReporter,
    ) = PlaybackReport.Handler(gson, sharedPreferences, eventReporter)

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
    fun exoMediaDrmProvider() = FrameworkMediaDrm.DEFAULT_PROVIDER

    @Provides
    @Reusable
    fun versionedCdmCalculator(exoMediaDrmProvider: ExoMediaDrm.Provider) =
        VersionedCdm.Calculator(exoMediaDrmProvider)

    @Provides
    @Reusable
    fun undeterminedPlaybackSessionResolver(versionedCdmCalculator: VersionedCdm.Calculator) =
        UndeterminedPlaybackSessionResolver(versionedCdmCalculator)

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
        volumeHelper: VolumeHelper,
        trueTimeWrapper: TrueTimeWrapper,
        playbackReportHandler: PlaybackReport.Handler,
        eventReporter: EventReporter,
        errorHandler: ErrorHandler,
        djSessionManager: DjSessionManager,
        undeterminedPlaybackSessionResolver: UndeterminedPlaybackSessionResolver,
        outputDeviceManager: OutputDeviceManager,
    ) = ExoPlayerPlaybackEngine(
        coroutineScope,
        extendedExoPlayerFactory,
        internalHandler,
        events,
        synchronousSurfaceHolderFactory,
        streamingPrivileges,
        playbackContextFactory,
        audioQualityRepository,
        volumeHelper,
        trueTimeWrapper,
        playbackReportHandler,
        eventReporter,
        errorHandler,
        djSessionManager,
        undeterminedPlaybackSessionResolver,
        outputDeviceManager,
    )

    @Provides
    @Reusable
    fun singleThreadExoPlayerPlaybackEngine(
        handler: Handler,
        delegate: ExoPlayerPlaybackEngine,
    ): PlaybackEngine = SingleHandlerPlaybackEngine(handler, delegate)
}
