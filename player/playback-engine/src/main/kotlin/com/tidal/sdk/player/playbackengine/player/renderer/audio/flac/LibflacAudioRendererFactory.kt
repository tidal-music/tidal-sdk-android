package com.tidal.sdk.player.playbackengine.player.renderer.audio.flac

import android.os.Handler
import androidx.media3.decoder.flac.LibflacAudioRenderer
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import com.tidal.sdk.player.playbackengine.player.renderer.audio.AudioRendererFactory

internal class LibflacAudioRendererFactory : AudioRendererFactory {

    override fun create(
        eventHandler: Handler,
        audioRendererEventListener: AudioRendererEventListener,
    ) = LibflacAudioRenderer(eventHandler, audioRendererEventListener)
}
