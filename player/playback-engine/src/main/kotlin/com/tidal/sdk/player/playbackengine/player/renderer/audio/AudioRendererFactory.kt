package com.tidal.sdk.player.playbackengine.player.renderer.audio

import android.os.Handler
import androidx.media3.exoplayer.BaseRenderer
import androidx.media3.exoplayer.audio.AudioRendererEventListener

interface AudioRendererFactory {

    @Suppress("UnsafeOptInUsageError")
    fun create(
        eventHandler: Handler,
        audioRendererEventListener: AudioRendererEventListener,
    ): BaseRenderer?
}
