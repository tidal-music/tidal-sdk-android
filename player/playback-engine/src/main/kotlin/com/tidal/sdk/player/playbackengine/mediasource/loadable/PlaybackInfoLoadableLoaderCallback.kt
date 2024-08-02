package com.tidal.sdk.player.playbackengine.mediasource.loadable

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MediaSourceEventListener
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy.LoadErrorInfo
import androidx.media3.exoplayer.upstream.Loader
import com.tidal.sdk.player.playbackengine.mediasource.TidalMediaSourceCreator
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo
import java.io.IOException

@Suppress("LongParameterList")
internal class PlaybackInfoLoadableLoaderCallback(
    private val mediaItem: MediaItem,
    private val tidalMediaSourceCreator: TidalMediaSourceCreator,
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    private val dataType: Int,
    private val eventDispatcher: MediaSourceEventListener.EventDispatcher,
    private val loadEventInfoF: (Long, Long) -> LoadEventInfo,
    private val loadErrorInfoF: (LoadEventInfo, error: IOException, errorCount: Int) ->
    LoadErrorInfo,
    private val prepareChildSourceF: (MediaSource) -> Unit,
    private val extras: Map<String, String?>?,
) : Loader.Callback<PlaybackInfoLoadable> {

    var selectedMediaSource: MediaSource? = null
        private set
    var playbackInfo: PlaybackInfo? = null
        private set

    override fun onLoadCompleted(
        loadable: PlaybackInfoLoadable,
        elapsedRealtimeMs: Long,
        loadDurationMs: Long,
    ) {
        playbackInfo = loadable.playbackInfo
        val loadEventInfo = loadEventInfoF(elapsedRealtimeMs, loadDurationMs)
        eventDispatcher.loadCompleted(loadEventInfo, dataType)

        loadable.also {
            with(tidalMediaSourceCreator(mediaItem, it.playbackInfo!!, extras)) {
                selectedMediaSource = this
                prepareChildSourceF(this)
            }
        }
    }

    override fun onLoadCanceled(
        loadable: PlaybackInfoLoadable,
        elapsedRealtimeMs: Long,
        loadDurationMs: Long,
        released: Boolean,
    ) {
        val loadEventInfo = loadEventInfoF(elapsedRealtimeMs, loadDurationMs)
        eventDispatcher.loadCanceled(loadEventInfo, dataType)
    }

    override fun onLoadError(
        loadable: PlaybackInfoLoadable,
        elapsedRealtimeMs: Long,
        loadDurationMs: Long,
        error: IOException,
        errorCount: Int,
    ): Loader.LoadErrorAction {
        val loadEventInfo = loadEventInfoF(elapsedRealtimeMs, loadDurationMs)
        val loadErrorInfo = loadErrorInfoF(loadEventInfo, error, errorCount)
        val retryDelayMs = loadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo)
        val isFatal = retryDelayMs == C.TIME_UNSET

        eventDispatcher.loadError(
            loadEventInfo,
            dataType,
            error,
            isFatal,
        )
        return if (isFatal) {
            Loader.DONT_RETRY_FATAL
        } else {
            Loader.createRetryAction(false, retryDelayMs)
        }
    }
}
