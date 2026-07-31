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

internal class PlayerRenderersFactory(
    private val mediaCodecVideoRendererFactory: MediaCodecVideoRendererFactory,
    private val libflacAudioRendererFactory: LibflacAudioRendererFactory?,
    private val fallbackAudioRendererFactory: FallbackAudioRendererFactory,
) : RenderersFactory {

    /**
     * The order of audio renderers matters: ExoPlayer's MappingTrackSelector assigns an entire
     * track group to a single renderer and breaks format-support ties by array order. Mixed-format
     * adaptive groups (e.g. E-AC-3 JOC + FLAC + AAC) must map to the MediaCodec audio renderer, so
     * it is registered before the libflac renderer. FLAC-only groups on devices whose platform FLAC
     * decoder is missing still fall through to libflac, as MediaCodec reports them unsupported
     * there.
     */
    override fun createRenderers(
        eventHandler: Handler,
        videoRendererEventListener: VideoRendererEventListener,
        audioRendererEventListener: AudioRendererEventListener,
        textRendererOutput: TextOutput,
        metadataRendererOutput: MetadataOutput,
    ) =
        arrayOf(
                mediaCodecVideoRendererFactory.create(eventHandler, videoRendererEventListener),
                fallbackAudioRendererFactory.create(eventHandler, audioRendererEventListener),
                libflacAudioRendererFactory?.create(eventHandler, audioRendererEventListener),
            )
            .filterNotNull()
            .toTypedArray()
}
