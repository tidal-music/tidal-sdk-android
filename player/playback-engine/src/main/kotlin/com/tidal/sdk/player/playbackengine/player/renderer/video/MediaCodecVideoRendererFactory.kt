package com.tidal.sdk.player.playbackengine.player.renderer.video

import android.content.Context
import android.os.Handler
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.video.MediaCodecVideoRenderer
import androidx.media3.exoplayer.video.VideoRendererEventListener

internal class MediaCodecVideoRendererFactory(private val context: Context) {

    @Suppress("UnsafeOptInUsageError")
    fun create(
        eventHandler: Handler,
        videoRendererEventListener: VideoRendererEventListener,
    ) = MediaCodecVideoRenderer(
        context,
        MediaCodecSelector.DEFAULT,
        DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS,
        false,
        eventHandler,
        videoRendererEventListener,
        DefaultRenderersFactory.MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY,
    )
}
