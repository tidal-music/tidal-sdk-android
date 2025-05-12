package com.tidal.sdk.player.events.di

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Build
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.ClientSupplier
import com.tidal.sdk.player.events.UserSupplier
import com.tidal.sdk.player.events.converter.AudioDownloadStatisticsEventFactory
import com.tidal.sdk.player.events.converter.AudioPlaybackSessionEventFactory
import com.tidal.sdk.player.events.converter.AudioPlaybackStatisticsEventFactory
import com.tidal.sdk.player.events.converter.BroadcastPlaybackSessionEventFactory
import com.tidal.sdk.player.events.converter.BroadcastPlaybackStatisticsEventFactory
import com.tidal.sdk.player.events.converter.DrmLicenseFetchEventFactory
import com.tidal.sdk.player.events.converter.EventFactory
import com.tidal.sdk.player.events.converter.NotStartedPlaybackStatisticsEventFactory
import com.tidal.sdk.player.events.converter.PlaybackInfoFetchEventFactory
import com.tidal.sdk.player.events.converter.StreamingSessionEndEventFactory
import com.tidal.sdk.player.events.converter.StreamingSessionStartEventFactory
import com.tidal.sdk.player.events.converter.StreamingSessionStartPayloadDecorator
import com.tidal.sdk.player.events.converter.UCPlaybackSessionEventFactory
import com.tidal.sdk.player.events.converter.UCPlaybackStatisticsEventFactory
import com.tidal.sdk.player.events.converter.VideoDownloadStatisticsEventFactory
import com.tidal.sdk.player.events.converter.VideoPlaybackSessionEventFactory
import com.tidal.sdk.player.events.converter.VideoPlaybackStatisticsEventFactory
import com.tidal.sdk.player.events.model.AudioDownloadStatistics
import com.tidal.sdk.player.events.model.AudioPlaybackSession
import com.tidal.sdk.player.events.model.AudioPlaybackStatistics
import com.tidal.sdk.player.events.model.BroadcastPlaybackSession
import com.tidal.sdk.player.events.model.BroadcastPlaybackStatistics
import com.tidal.sdk.player.events.model.DrmLicenseFetch
import com.tidal.sdk.player.events.model.Event
import com.tidal.sdk.player.events.model.NotStartedPlaybackStatistics
import com.tidal.sdk.player.events.model.PlaybackInfoFetch
import com.tidal.sdk.player.events.model.StreamingSessionEnd
import com.tidal.sdk.player.events.model.StreamingSessionStart
import com.tidal.sdk.player.events.model.UCPlaybackSession
import com.tidal.sdk.player.events.model.UCPlaybackStatistics
import com.tidal.sdk.player.events.model.VideoDownloadStatistics
import com.tidal.sdk.player.events.model.VideoPlaybackSession
import com.tidal.sdk.player.events.model.VideoPlaybackStatistics
import com.tidal.sdk.player.events.util.ActiveMobileNetworkType
import com.tidal.sdk.player.events.util.ActiveNetworkType
import com.tidal.sdk.player.events.util.HardwarePlatform
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
internal object EventFactoryModule {

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(AudioDownloadStatistics.Payload::class)
    fun audioDownloadStatisticsEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        audioDownloadStatisticsFactory: AudioDownloadStatistics.Factory,
    ): EventFactory<out Event.Payload> =
        AudioDownloadStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            audioDownloadStatisticsFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(VideoDownloadStatistics.Payload::class)
    fun videoDownloadStatisticsEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        videoDownloadStatisticsFactory: VideoDownloadStatistics.Factory,
    ): EventFactory<out Event.Payload> =
        VideoDownloadStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            videoDownloadStatisticsFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(DrmLicenseFetch.Payload::class)
    fun drmLicenseFetchEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        drmLicenseFetchFactory: DrmLicenseFetch.Factory,
    ): EventFactory<out Event.Payload> =
        DrmLicenseFetchEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            drmLicenseFetchFactory,
        )

    @Provides @Reusable fun hardwarePlatform() = HardwarePlatform(Build.MODEL, Build.MANUFACTURER)

    @Provides
    @Reusable
    fun activeNetworkType(connectivityManager: ConnectivityManager) =
        ActiveNetworkType(connectivityManager)

    @Provides
    @Reusable
    fun activeMobileNetworkType(connectivityManager: ConnectivityManager) =
        ActiveMobileNetworkType(connectivityManager)

    @Provides @Reusable fun resources(context: Context) = context.resources

    @Provides
    @Reusable
    fun streamingSessionStartPayloadDecorator(
        streamingSessionStartDecoratedPayloadFactory:
            StreamingSessionStart.DecoratedPayload.Factory, // ktlint-disable max-line-length
        // parameter-wrapping
        hardwarePlatform: HardwarePlatform,
        resources: Resources,
        activeNetworkType: ActiveNetworkType,
        activeMobileNetworkType: ActiveMobileNetworkType,
    ) =
        StreamingSessionStartPayloadDecorator(
            streamingSessionStartDecoratedPayloadFactory,
            hardwarePlatform,
            Build.VERSION.RELEASE,
            resources,
            activeNetworkType,
            activeMobileNetworkType,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(StreamingSessionStart.Payload::class)
    fun streamingSessionStartEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        streamingSessionStartPayloadDecorator: StreamingSessionStartPayloadDecorator,
        streamingSessionStartFactory: StreamingSessionStart.Factory,
    ): EventFactory<out Event.Payload> =
        StreamingSessionStartEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            streamingSessionStartPayloadDecorator,
            streamingSessionStartFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(StreamingSessionEnd.Payload::class)
    fun streamingSessionEndEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        streamingSessionEndFactory: StreamingSessionEnd.Factory,
    ): EventFactory<out Event.Payload> =
        StreamingSessionEndEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            streamingSessionEndFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(PlaybackInfoFetch.Payload::class)
    fun playbackInfoFetchEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        playbackInfoFetchFactory: PlaybackInfoFetch.Factory,
    ): EventFactory<out Event.Payload> =
        PlaybackInfoFetchEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            playbackInfoFetchFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(UCPlaybackSession.Payload::class)
    fun ucPlaybackSessionEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        ucPlaybackSessionFactory: UCPlaybackSession.Factory,
    ): EventFactory<out Event.Payload> =
        UCPlaybackSessionEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            ucPlaybackSessionFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(AudioPlaybackSession.Payload::class)
    fun audioPlaybackSessionEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        audioPlaybackSessionFactory: AudioPlaybackSession.Factory,
    ): EventFactory<out Event.Payload> =
        AudioPlaybackSessionEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            audioPlaybackSessionFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(BroadcastPlaybackSession.Payload::class)
    fun broadcastPlaybackSessionEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        broadcastPlaybackSessionFactory: BroadcastPlaybackSession.Factory,
    ): EventFactory<out Event.Payload> =
        BroadcastPlaybackSessionEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            broadcastPlaybackSessionFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(UCPlaybackStatistics.Payload::class)
    fun ucPlaybackStatisticsEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        ucPlaybackStatisticsFactory: UCPlaybackStatistics.Factory,
    ): EventFactory<out Event.Payload> =
        UCPlaybackStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            ucPlaybackStatisticsFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(VideoPlaybackSession.Payload::class)
    fun videoPlaybackSessionEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        videoPlaybackSessionFactory: VideoPlaybackSession.Factory,
    ): EventFactory<out Event.Payload> =
        VideoPlaybackSessionEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            videoPlaybackSessionFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(AudioPlaybackStatistics.Payload::class)
    fun audioPlaybackStatisticsEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        audioPlaybackStatisticsFactory: AudioPlaybackStatistics.Factory,
    ): EventFactory<out Event.Payload> =
        AudioPlaybackStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            audioPlaybackStatisticsFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(BroadcastPlaybackStatistics.Payload::class)
    fun broadcastPlaybackStatisticsEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        broadcastPlaybackStatisticsFactory: BroadcastPlaybackStatistics.Factory,
    ): EventFactory<out Event.Payload> =
        BroadcastPlaybackStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            broadcastPlaybackStatisticsFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(VideoPlaybackStatistics.Payload::class)
    fun videoPlaybackStatisticsEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        videoPlaybackStatisticsFactory: VideoPlaybackStatistics.Factory,
    ): EventFactory<out Event.Payload> =
        VideoPlaybackStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            videoPlaybackStatisticsFactory,
        )

    @Provides
    @Reusable
    @IntoMap
    @EventFactoryKey(NotStartedPlaybackStatistics.Payload::class)
    fun errorPlaybackStatisticsEventFactory(
        trueTimeWrapper: TrueTimeWrapper,
        uuidWrapper: UUIDWrapper,
        userSupplier: UserSupplier,
        clientSupplier: ClientSupplier,
        notStartedPlaybackStatisticsFactory: NotStartedPlaybackStatistics.Factory,
    ): EventFactory<out Event.Payload> =
        NotStartedPlaybackStatisticsEventFactory(
            trueTimeWrapper,
            uuidWrapper,
            userSupplier,
            clientSupplier,
            notStartedPlaybackStatisticsFactory,
        )

    @MapKey annotation class EventFactoryKey(val value: KClass<out Event.Payload>)
}
