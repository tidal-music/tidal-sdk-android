package com.tidal.sdk.player.playbackengine

import android.os.Handler
import android.os.HandlerThread
import assertk.assertThat
import assertk.assertions.isNotSameAs
import assertk.assertions.isSameAs
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
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Test

internal class SingleHandlerPlaybackEngineThreadingTest {
    private val lock = ReentrantLock()
    private val delegateWaitCondition = lock.newCondition()
    private lateinit var executionThread: Thread
    private val handlerThread = HandlerThread("TestThread").apply { start() }
    private val handler = Handler(handlerThread.looper)
    private val delegate = object : PlaybackEngine {
        override val mediaProduct = MediaProduct(ProductType.TRACK, "123")
        override val playbackContext = PlaybackContext.Track(
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
            set(_) = assignThreadAndReleaseLock()
        override var streamingCellularAudioQuality = AudioQuality.LOW
            set(_) = assignThreadAndReleaseLock()
        override var loudnessNormalizationMode = LoudnessNormalizationMode.ALBUM
            set(_) = assignThreadAndReleaseLock()
        override var loudnessNormalizationPreAmp = LOUDNESS_NORMALIZATION_PRE_AMP_DEFAULT
            set(_) = assignThreadAndReleaseLock()
        override var videoSurfaceView: AspectRatioAdjustingSurfaceView? = null
            set(_) = assignThreadAndReleaseLock()

        override fun load(mediaProduct: MediaProduct) {
            assignThreadAndReleaseLock()
        }

        override fun setNext(mediaProduct: MediaProduct?) {
            assignThreadAndReleaseLock()
        }

        override fun play() {
            assignThreadAndReleaseLock()
        }

        override fun pause() {
            assignThreadAndReleaseLock()
        }

        override fun seek(time: Float) {
            assignThreadAndReleaseLock()
        }

        override fun skipToNext() {
            assignThreadAndReleaseLock()
        }

        override fun setRepeatOne(enable: Boolean) {
            assignThreadAndReleaseLock()
        }

        override fun reset() {
            assignThreadAndReleaseLock()
        }

        override fun release() {
            assignThreadAndReleaseLock()
        }

        private fun assignThreadAndReleaseLock() {
            executionThread = Thread.currentThread()
            lock.withLock { delegateWaitCondition.signal() }
        }
    }
    private val singleHandlerPlaybackEngine = SingleHandlerPlaybackEngine(handler, delegate)

    @Test
    fun loadSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.load(MediaProduct(ProductType.TRACK, "123"))
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun nextSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.setNext(MediaProduct(ProductType.TRACK, "123"))
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun playSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.play()
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun pauseSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.pause()
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun seekSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.seek(78F)
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun setRepeatOneSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.setRepeatOne(true)
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    fun resetSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.reset()
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun releaseSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.release()
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun setVideoViewSwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.videoSurfaceView = null
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun setStreamingWifiAudioQualitySwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.streamingWifiAudioQuality = AudioQuality.LOW
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }

    @Test
    fun setStreamingCellularAudioQualitySwitchesThread() {
        lock.withLock {
            singleHandlerPlaybackEngine.streamingCellularAudioQuality = AudioQuality.LOW
            while (!this::executionThread.isInitialized) {
                delegateWaitCondition.await()
            }
        }
        assertThat(executionThread).isNotSameAs(Thread.currentThread())
        assertThat(executionThread).isSameAs(handlerThread)
    }
}
