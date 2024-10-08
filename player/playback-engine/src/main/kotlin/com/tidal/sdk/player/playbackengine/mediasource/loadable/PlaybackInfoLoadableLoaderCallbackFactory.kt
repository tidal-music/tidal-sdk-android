package com.tidal.sdk.player.playbackengine.mediasource.loadable

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MediaSourceEventListener
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import com.tidal.sdk.player.common.model.BaseMediaProduct
import com.tidal.sdk.player.playbackengine.mediasource.TidalMediaSourceCreator
import java.io.IOException

internal class PlaybackInfoLoadableLoaderCallbackFactory(
    private val tidalMediaSourceCreator: TidalMediaSourceCreator,
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
) {

    @Suppress("LongParameterList")
    fun create(
        mediaItem: MediaItem,
        dataType: Int,
        eventDispatcher: MediaSourceEventListener.EventDispatcher,
        loadEventInfoF: (Long, Long) -> LoadEventInfo,
        loadErrorInfoF: (LoadEventInfo, error: IOException, errorCount: Int) ->
        LoadErrorHandlingPolicy.LoadErrorInfo,
        prepareChildSourceF: (MediaSource) -> Unit,
        extras: BaseMediaProduct.Extras?,
    ) = PlaybackInfoLoadableLoaderCallback(
        mediaItem,
        tidalMediaSourceCreator,
        loadErrorHandlingPolicy,
        dataType,
        eventDispatcher,
        loadEventInfoF,
        loadErrorInfoF,
        prepareChildSourceF,
        extras,
    )
}
