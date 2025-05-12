package com.tidal.sdk.player.playbackengine

import android.os.Handler
import android.os.HandlerThread
import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.ProductType
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDevice
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView
import com.tidal.sdk.player.playbackengine.volume.LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Assert.fail
import org.junit.Test

internal class SingleHandlerPlaybackEngineHandlerPostOrThrowTest {
    private val delegate =
        object : PlaybackEngine {
            override val mediaProduct = MediaProduct(ProductType.TRACK, "123", referenceId = "fdsa")
            override val playbackContext =
                PlaybackContext.Track(
                    AudioMode.STEREO,
                    AudioQuality.HIGH,
                    320_000,
                    16,
                    "aac",
                    44100,
                    "123",
                    AssetPresentation.FULL,
                    100F,
                    AssetSource.ONLINE,
                    "playbackSessionId",
                    "uuid",
                )
            override val playbackState = PlaybackState.IDLE
            override val assetPosition = 0f
            override val outputDevice = OutputDevice.TYPE_BUILTIN_SPEAKER
            override val events = MutableSharedFlow<Event>()
            override var streamingWifiAudioQuality = AudioQuality.HIGH
            override var streamingCellularAudioQuality = AudioQuality.LOW
            override var immersiveAudio = true
            override var loudnessNormalizationMode = LoudnessNormalizationMode.ALBUM
            override var loudnessNormalizationPreAmp = LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT
            override var videoSurfaceView: AspectRatioAdjustingSurfaceView? = null

            override fun load(mediaProduct: MediaProduct) = Unit

            override fun setNext(mediaProduct: MediaProduct?) = Unit

            override fun play() = Unit

            override fun pause() = Unit

            override fun seek(time: Float) = Unit

            override fun skipToNext() = Unit

            override fun setRepeatOne(enable: Boolean) = Unit

            override fun reset() = Unit

            override fun release() = Unit
        }
    private val handlerThread = HandlerThread("TestThread").apply { start() }
    private val handler = Handler(handlerThread.looper)
    private val singleHandlerPlaybackEngine = SingleHandlerPlaybackEngine(handler, delegate)

    @Test(expected = IllegalStateException::class)
    fun loadThrowsIfReleased() = runAfterRelease {
        singleHandlerPlaybackEngine.load(MediaProduct(ProductType.TRACK, "123"))
    }

    @Test(expected = IllegalStateException::class)
    fun nextThrowsIfReleased() = runAfterRelease { setNext(MediaProduct(ProductType.TRACK, "123")) }

    @Test(expected = IllegalStateException::class)
    fun playThrowsIfReleased() = runAfterRelease { play() }

    @Test(expected = IllegalStateException::class)
    fun pauseThrowsIfReleased() = runAfterRelease { pause() }

    @Test(expected = IllegalStateException::class)
    fun seekThrowsIfReleased() = runAfterRelease { seek(-7F) }

    @Test(expected = IllegalStateException::class)
    fun resetThrowsIfReleased() = runAfterRelease { reset() }

    @Test(expected = IllegalStateException::class)
    fun releaseThrowsIfReleased() = runAfterRelease { release() }

    @Test(expected = IllegalStateException::class)
    fun setVideoSurfaceViewThrowsIfReleased() = runAfterRelease { videoSurfaceView = null }

    @Test(expected = IllegalStateException::class)
    fun setStreamingWifiAudioQualityThrowsIfReleased() = runAfterRelease {
        streamingWifiAudioQuality = AudioQuality.HIGH
    }

    @Test(expected = IllegalStateException::class)
    fun setStreamingCellularAudioQualityThrowsIfReleased() = runAfterRelease {
        streamingCellularAudioQuality = AudioQuality.LOW
    }

    private fun runAfterRelease(onRelease: SingleHandlerPlaybackEngine.() -> Unit) {
        singleHandlerPlaybackEngine.release()
        var attemptsPerformed = 0
        while (handlerThread.isAlive) {
            if (++attemptsPerformed > 3) {
                fail("Failed to wait for HandlerThread to die")
            }
            Thread.sleep(1_000)
        }
        singleHandlerPlaybackEngine.onRelease()
    }
}
