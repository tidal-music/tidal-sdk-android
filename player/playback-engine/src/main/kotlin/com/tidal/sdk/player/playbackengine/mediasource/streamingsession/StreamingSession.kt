package com.tidal.sdk.player.playbackengine.mediasource.streamingsession

import com.tidal.sdk.player.common.Configuration
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.UUIDWrapper
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.commonandroid.TrueTimeWrapper
import com.tidal.sdk.player.events.EventReporter
import com.tidal.sdk.player.events.model.StreamingSessionStart
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession.Explicit
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession.Implicit
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import java.util.UUID

internal sealed class StreamingSession private constructor(
    val id: UUID,
    val versionedCdmCalculator: VersionedCdm.Calculator,
    val configuration: Configuration,
) {

    fun createUndeterminedPlaybackStatistics(
        idealStartTimestampMs: PlaybackStatistics.IdealStartTimestampMs,
    ) = PlaybackStatistics.Undetermined(id, idealStartTimestampMs, emptyList())

    fun createPlaybackSession(
        playbackInfo: PlaybackInfo,
        requestedMediaProduct: ForwardingMediaProduct<*>,
    ) = when (playbackInfo) {
        is PlaybackInfo.Track -> PlaybackSession.Audio(
            id,
            playbackInfo.trackId.toString(),
            requestedMediaProduct.productId,
            playbackInfo.assetPresentation,
            playbackInfo.audioMode,
            playbackInfo.audioQuality,
            requestedMediaProduct.sourceType,
            requestedMediaProduct.sourceId,
        )

        is PlaybackInfo.Video -> PlaybackSession.Video(
            id,
            playbackInfo.videoId.toString(),
            requestedMediaProduct.productId,
            playbackInfo.assetPresentation,
            playbackInfo.videoQuality,
            requestedMediaProduct.sourceType,
            requestedMediaProduct.sourceId,
        )

        is PlaybackInfo.Broadcast -> PlaybackSession.Broadcast(
            id,
            playbackInfo.id,
            requestedMediaProduct.productId,
            playbackInfo.audioQuality,
            requestedMediaProduct.sourceType,
            requestedMediaProduct.sourceId,
        )

        is PlaybackInfo.Offline.Track -> PlaybackSession.Audio(
            id,
            playbackInfo.track.trackId.toString(),
            requestedMediaProduct.productId,
            playbackInfo.track.assetPresentation,
            playbackInfo.track.audioMode,
            playbackInfo.track.audioQuality,
            requestedMediaProduct.sourceType,
            requestedMediaProduct.sourceId,
        )

        is PlaybackInfo.Offline.Video -> PlaybackSession.Video(
            id,
            playbackInfo.video.videoId.toString(),
            requestedMediaProduct.productId,
            playbackInfo.video.assetPresentation,
            playbackInfo.video.videoQuality,
            requestedMediaProduct.sourceType,
            requestedMediaProduct.sourceId,
        )
    }

    class Explicit(
        id: UUID,
        versionedCdmCalculator: VersionedCdm.Calculator,
        configuration: Configuration,
    ) :
        StreamingSession(id, versionedCdmCalculator, configuration)

    class Implicit(
        id: UUID,
        versionedCdmCalculator: VersionedCdm.Calculator,
        configuration: Configuration,
    ) :
        StreamingSession(id, versionedCdmCalculator, configuration)

    sealed class Creator<T : Factory> private constructor(
        private val factory: T,
        private val trueTimeWrapper: TrueTimeWrapper,
        private val eventReporter: EventReporter,
    ) {

        abstract val startReason: StreamingSessionStart.StartReason

        fun createAndReportStart(
            sessionProductType: ProductType,
            sessionProductId: String,
        ) = factory.create().also {
            eventReporter.report(
                StreamingSessionStart.Payload(
                    it.id.toString(),
                    trueTimeWrapper.currentTimeMillis,
                    startReason,
                    it.configuration.isOfflineMode,
                    sessionProductType,
                    sessionProductId,
                ),
            )
        }

        class Explicit(
            factory: Factory.Explicit,
            trueTimeWrapper: TrueTimeWrapper,
            eventReporter: EventReporter,
        ) : Creator<Factory.Explicit>(factory, trueTimeWrapper, eventReporter) {

            override val startReason = StreamingSessionStart.StartReason.EXPLICIT
        }

        class Implicit(
            factory: Factory.Implicit,
            trueTimeWrapper: TrueTimeWrapper,
            eventReporter: EventReporter,
        ) : Creator<Factory.Implicit>(factory, trueTimeWrapper, eventReporter) {

            override val startReason = StreamingSessionStart.StartReason.IMPLICIT
        }
    }

    sealed class Factory private constructor(
        protected val uuidWrapper: UUIDWrapper,
        protected val versionedCdmCalculator: VersionedCdm.Calculator,
        protected val configuration: Configuration,
    ) {

        abstract fun create(): StreamingSession

        class Explicit(
            uuidWrapper: UUIDWrapper,
            versionedCdmCalculator: VersionedCdm.Calculator,
            configuration: Configuration,
        ) :
            Factory(uuidWrapper, versionedCdmCalculator, configuration) {

            override fun create(): StreamingSession = Explicit(
                uuidWrapper.randomUUID,
                versionedCdmCalculator,
                configuration,
            )
        }

        class Implicit(
            uuidWrapper: UUIDWrapper,
            versionedCdmCalculator: VersionedCdm.Calculator,
            configuration: Configuration,
        ) :
            Factory(uuidWrapper, versionedCdmCalculator, configuration) {

            override fun create(): StreamingSession = Implicit(
                uuidWrapper.randomUUID,
                versionedCdmCalculator,
                configuration,
            )
        }
    }
}
