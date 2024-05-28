package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.common.Timeline
import androidx.media3.datasource.TransferListener
import androidx.media3.exoplayer.source.CompositeMediaSource
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaPeriod
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.upstream.Allocator
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy.LoadErrorInfo
import androidx.media3.exoplayer.upstream.Loader
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadable
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableLoaderCallbackFactory
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import java.io.IOException

/**
 * A [CompositeMediaSource] that fetches Playback info with the help of a [PlaybackInfoLoadable],
 * and then creates a [MediaSource] with the help of a [PlaybackInfoLoadableLoaderCallback].
 */
@Suppress("LongParameterList")
internal class PlaybackInfoMediaSource(
    val forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>,
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    private val loadable: PlaybackInfoLoadable,
    private val mediaItem: MediaItem,
    private val dataType: Int,
    private val loadEventInfoF: (Long, Long) -> LoadEventInfo,
    private val loadErrorInfoF: (LoadEventInfo, error: IOException, errorCount: Int) ->
    LoadErrorInfo,
    private val loader: Loader,
    private val loaderCallbackFactory: PlaybackInfoLoadableLoaderCallbackFactory,
) : CompositeMediaSource<Void>() {

    val playbackInfo: PlaybackInfo? by loadable::playbackInfo
    private val eventDispatcher by lazy { createEventDispatcherF() }
    private val loaderCallback by lazy {
        loaderCallbackFactory.create(
            mediaItem,
            dataType,
            eventDispatcher,
            loadEventInfoF,
            loadErrorInfoF,
            prepareChildSourceF,
        )
    }

    private var createEventDispatcherF = { createEventDispatcher(null) }
    private var prepareChildSourceF: (MediaSource) -> Unit = { prepareChildSource(null, it) }
    private var prepareSourceInternalF = {
        loader.startLoading(
            loadable,
            loaderCallback,
            loadErrorHandlingPolicy.getMinimumLoadableRetryCount(dataType),
        ).also { eventDispatcher.loadStarted(loadEventInfoF(it, 0), dataType) }
    }

    override fun getMediaItem() = mediaItem

    override fun prepareSourceInternal(mediaTransferListener: TransferListener?) {
        super.prepareSourceInternal(mediaTransferListener)
        prepareSourceInternalF()
    }

    override fun releaseSourceInternal() {
        super.releaseSourceInternal()
        loader.release()
    }

    override fun createPeriod(
        id: MediaSource.MediaPeriodId,
        allocator: Allocator,
        startPositionUs: Long,
    ) = loaderCallback.selectedMediaSource!!.createPeriod(id, allocator, startPositionUs)

    override fun releasePeriod(mediaPeriod: MediaPeriod) {
        loaderCallback.selectedMediaSource?.releasePeriod(mediaPeriod)
    }

    override fun maybeThrowSourceInfoRefreshError() {
        loader.maybeThrowError()
        super.maybeThrowSourceInfoRefreshError()
    }

    override fun onChildSourceInfoRefreshed(
        id: Void?,
        mediaSource: MediaSource,
        timeline: Timeline,
    ) = refreshSourceInfo(timeline)
}
