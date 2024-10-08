package com.tidal.sdk.player.playbackengine.player.renderer

import android.os.Handler
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.metadata.MetadataOutput
import androidx.media3.exoplayer.text.TextOutput
import androidx.media3.exoplayer.video.VideoRendererEventListener
import com.tidal.sdk.player.playbackengine.player.renderer.audio.fallback.FallbackAudioRendererFactory
import com.tidal.sdk.player.playbackengine.player.renderer.audio.flac.LibflacAudioRendererFactory
import com.tidal.sdk.player.playbackengine.player.renderer.video.MediaCodecVideoRendererFactory

@Suppress("UnsafeOptInUsageError")
internal class PlayerRenderersFactory(
    private val mediaCodecVideoRendererFactory: MediaCodecVideoRendererFactory,
    private val libflacAudioRendererFactory: LibflacAudioRendererFactory?,
    private val fallbackAudioRendererFactory: FallbackAudioRendererFactory,
) : RenderersFactory {

    @Suppress("SpreadOperator")
    override fun createRenderers(
        eventHandler: Handler,
        videoRendererEventListener: VideoRendererEventListener,
        audioRendererEventListener: AudioRendererEventListener,
        textRendererOutput: TextOutput,
        metadataRendererOutput: MetadataOutput,
    ) = arrayOf(
        mediaCodecVideoRendererFactory.create(eventHandler, videoRendererEventListener),
        libflacAudioRendererFactory?.create(eventHandler, audioRendererEventListener),
        fallbackAudioRendererFactory.create(eventHandler, audioRendererEventListener),
    ).filterNotNull()
        .toTypedArray()
}
