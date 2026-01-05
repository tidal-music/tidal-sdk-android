package com.tidal.sdk.player.playbackengine

import android.os.Handler
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.UsbDacExclusiveMode
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView

/**
 * A [PlaybackEngine] that wraps another one ([delegate]), swapping calls that might mutate or
 * depend on its state onto a different, dedicated thread.
 */
internal class SingleHandlerPlaybackEngine(
    private val handler: Handler,
    private val delegate: PlaybackEngine,
) : PlaybackEngine {

    override val mediaProduct by delegate::mediaProduct

    override val playbackContext by delegate::playbackContext

    override val playbackState by delegate::playbackState

    override val assetPosition by delegate::assetPosition

    override val outputDevice by delegate::outputDevice

    override val events by delegate::events

    override var streamingWifiAudioQuality: AudioQuality
        get() = delegate.streamingWifiAudioQuality
        set(value) = postOrThrow { delegate.streamingWifiAudioQuality = value }

    override var streamingCellularAudioQuality: AudioQuality
        get() = delegate.streamingCellularAudioQuality
        set(value) = postOrThrow { delegate.streamingCellularAudioQuality = value }

    override var loudnessNormalizationMode: LoudnessNormalizationMode
        get() = delegate.loudnessNormalizationMode
        set(value) = postOrThrow { delegate.loudnessNormalizationMode = value }

    override var loudnessNormalizationPreAmp: Int
        get() = delegate.loudnessNormalizationPreAmp
        set(value) = postOrThrow { delegate.loudnessNormalizationPreAmp = value }

    override var immersiveAudio: Boolean
        get() = delegate.immersiveAudio
        set(value) = postOrThrow { delegate.immersiveAudio = value }

    override var usbDacExclusiveMode: UsbDacExclusiveMode
        get() = delegate.usbDacExclusiveMode
        set(value) = postOrThrow { delegate.usbDacExclusiveMode = value }

    override val connectedUsbDevice by delegate::connectedUsbDevice

    override val isExclusiveModeActive by delegate::isExclusiveModeActive

    override var videoSurfaceView: AspectRatioAdjustingSurfaceView?
        set(value) = postOrThrow { delegate.videoSurfaceView = value }
        get() = delegate.videoSurfaceView

    override fun load(mediaProduct: MediaProduct) = postOrThrow { delegate.load(mediaProduct) }

    override fun setNext(mediaProduct: MediaProduct?) = postOrThrow {
        delegate.setNext(mediaProduct)
    }

    override fun play() = postOrThrow { delegate.play() }

    override fun pause() = postOrThrow { delegate.pause() }

    override fun seek(time: Float) = postOrThrow { delegate.seek(time) }

    override fun skipToNext() = postOrThrow { delegate.skipToNext() }

    override fun setRepeatOne(enable: Boolean) = postOrThrow { delegate.setRepeatOne(enable) }

    override fun reset() = postOrThrow { delegate.reset() }

    override fun release() = postOrThrow {
        delegate.release()
        handler.looper.quit()
    }

    private fun postOrThrow(runnable: Runnable) {
        if (!handler.post(runnable)) {
            val className = SingleHandlerPlaybackEngine::class.simpleName
            error("Attempt to use a released instance of $className")
        }
    }
}
