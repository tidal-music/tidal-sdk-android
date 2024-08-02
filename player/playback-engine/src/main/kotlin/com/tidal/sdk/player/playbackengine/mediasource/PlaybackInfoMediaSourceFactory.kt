package com.tidal.sdk.player.playbackengine.mediasource

import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSpec
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaLoadData
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy
import androidx.media3.exoplayer.upstream.Loader
import com.tidal.sdk.player.common.ForwardingMediaProduct
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableFactory
import com.tidal.sdk.player.playbackengine.mediasource.loadable.PlaybackInfoLoadableLoaderCallbackFactory
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession

private const val DATA_TYPE = C.DATA_TYPE_MANIFEST
private const val LOADER_THREAD_NAME_SUFFIX = "PlaybackInfoMediaSource"

internal class PlaybackInfoMediaSourceFactory(
    private val loadErrorHandlingPolicy: LoadErrorHandlingPolicy,
    private val playbackInfoLoadableFactory: PlaybackInfoLoadableFactory,
    @Suppress("MaxLineLength") private val playbackInfoLoadableLoaderCallbackFactory: PlaybackInfoLoadableLoaderCallbackFactory, // ktlint-disable max-line-length parameter-wrapping
) {

    fun create(
        streamingSession: StreamingSession,
        forwardingMediaProduct: ForwardingMediaProduct<MediaProduct>,
    ) =
        PlaybackInfoMediaSource(
            forwardingMediaProduct,
            loadErrorHandlingPolicy,
            playbackInfoLoadableFactory.create(streamingSession, forwardingMediaProduct),
            MediaItem.Builder()
                .setMediaId(forwardingMediaProduct.hashCode().toString())
                .setUri(Uri.EMPTY)
                .build(),
            DATA_TYPE,
            { elapsedRealtimeMs, loadDurationMs ->
                LoadEventInfo(
                    LoadEventInfo.getNewId(),
                    DataSpec(Uri.EMPTY),
                    Uri.EMPTY,
                    emptyMap(),
                    elapsedRealtimeMs,
                    loadDurationMs,
                    0,
                )
            },
            { loadEventInfo, error, errorCount ->
                LoadErrorHandlingPolicy.LoadErrorInfo(
                    loadEventInfo,
                    MediaLoadData(DATA_TYPE),
                    error,
                    errorCount,
                )
            },
            Loader(LOADER_THREAD_NAME_SUFFIX),
            playbackInfoLoadableLoaderCallbackFactory,
            forwardingMediaProduct.extras,
        )
}
