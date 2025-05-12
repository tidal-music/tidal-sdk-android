package com.tidal.sdk.player.playbackengine.player.renderer.audio.fallback

import android.content.Context
import android.os.Handler
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.DefaultAudioSink
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import com.tidal.sdk.player.playbackengine.player.renderer.audio.AudioRendererFactory

internal class FallbackAudioRendererFactory(
    private val context: Context,
    private val defaultAudioSink: DefaultAudioSink,
) : AudioRendererFactory {

    override fun create(
        eventHandler: Handler,
        audioRendererEventListener: AudioRendererEventListener,
    ) =
        MediaCodecAudioRenderer(
            context,
            MediaCodecSelector.DEFAULT,
            false,
            eventHandler,
            audioRendererEventListener,
            defaultAudioSink,
        )
}
