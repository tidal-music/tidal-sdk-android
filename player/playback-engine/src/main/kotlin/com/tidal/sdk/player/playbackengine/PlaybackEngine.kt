package com.tidal.sdk.player.playbackengine

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.LoudnessNormalizationMode
import com.tidal.sdk.player.common.model.MediaProduct
import com.tidal.sdk.player.common.model.UsbDacExclusiveMode
import com.tidal.sdk.player.playbackengine.model.Event
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDevice
import com.tidal.sdk.player.playbackengine.outputdevice.UsbAudioDevice
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView
import kotlinx.coroutines.flow.Flow

/** This should be implemented in order to play media. */
interface PlaybackEngine : Action, Query, Configuration

/**
 * This is the Action api where we can perform various actions on the playback engine in order to
 * manipulate playback.
 */
interface Action {

    /**
     * Resets playback and immediately transition to set selected [MediaProduct]. Playback state
     * will immediately change to [PlaybackState.NOT_PLAYING]. When this call returns, the requested
     * media product is considered the active one.
     */
    fun load(mediaProduct: MediaProduct)

    /**
     * Playback makes an implicit transition to the selected [MediaProduct] once the currently
     * active finishes playing.
     *
     * This must be called everytime the next in queue changes in order to continue playback, and in
     * order to continue playback of the correct [MediaProduct].
     *
     * This action does not explicitly affect [PlaybackState].
     *
     * Should not be called when current state is [PlaybackState.IDLE].
     */
    fun setNext(mediaProduct: MediaProduct?)

    /**
     * Starts playback of active [MediaProduct].
     *
     * The target playback state for this action is [PlaybackState.PLAYING].
     *
     * Should not be called when current state is [PlaybackState.IDLE].
     */
    fun play()

    /**
     * Pauses playback of active [MediaProduct].
     *
     * The target playback state for this action is [PlaybackState.NOT_PLAYING].
     *
     * Should not be called when current state is [PlaybackState.IDLE].
     */
    fun pause()

    /**
     * Changes asset position in the active media product.
     *
     * This action does not explicitly affect [PlaybackState].
     *
     * Should not be called when current state is [PlaybackState.IDLE].
     */
    fun seek(time: Float)

    /**
     * Changes the currently loaded media product to the one set previously via [setNext],
     * implicitly continuing playback if it was ongoing.
     *
     * This action does not explicitly affect [PlaybackState].
     *
     * Should not be called when current state is [PlaybackState.IDLE].
     */
    fun skipToNext()

    /**
     * Sets or clears an [AspectRatioAdjustingSurfaceView] for the player to use for video playback.
     *
     * @see AspectRatioAdjustingSurfaceView
     */
    var videoSurfaceView: AspectRatioAdjustingSurfaceView?

    /**
     * Sets repeat one mode on or off. Repeat all is handled by the host.
     *
     * Should be called when bootstrapping Playback engine or whenever repeat mode one is enabled.
     */
    fun setRepeatOne(enable: Boolean)

    /**
     * Resets the playback engine to its initial state, effectively a stop action.
     *
     * This removes all media products that are set on the playback engine.
     *
     * Playback state will immediately change to [PlaybackState.IDLE].
     */
    fun reset()

    /**
     * Releases all resources required by this instance exclusively.
     *
     * Can not be used after this method is called, but creating a new instance is safe.
     */
    fun release()
}

/** This is the Query api where we can query various information related to the playback engine. */
interface Query {

    /**
     * Gets the currently active [MediaProduct]. May be null, most likely if current state is
     * [PlaybackState.IDLE].
     */
    val mediaProduct: MediaProduct?

    /**
     * Gets the currently active [PlaybackContext]. May be null, most likely if current state is
     * [PlaybackState.IDLE].
     */
    val playbackContext: PlaybackContext?

    /** Gets the currently active [PlaybackState]. */
    val playbackState: PlaybackState

    /** Gets the current asset position, in seconds, from the active [MediaProduct], as [Float]. */
    val assetPosition: Float

    /** Gets the current output device, as [OutputDevice]. */
    val outputDevice: OutputDevice

    /** Gets the currently connected USB audio device, if any. Only available on API 34+. */
    val connectedUsbDevice: UsbAudioDevice?

    /** Returns true if exclusive mode is currently active for playback. */
    val isExclusiveModeActive: Boolean

    /**
     * Gets the stream of events describing happenings that consumers may want to react to.
     * Backpressure not handled.
     */
    val events: Flow<Event>
}

/**
 * This is the Configuration api where we can configure and query various information related to the
 * playback engine.
 */
interface Configuration {

    /** Get or set [AudioQuality] to use when streaming over WiFi. */
    var streamingWifiAudioQuality: AudioQuality

    /** Get or set [AudioQuality] to use when streaming over cellular. */
    var streamingCellularAudioQuality: AudioQuality

    /** Get or set [LoudnessNormalizationMode] to use when playing. */
    var loudnessNormalizationMode: LoudnessNormalizationMode

    /** Get or set [loudnessNormalizationPreAmp] as [Int] to use when playing. */
    var loudnessNormalizationPreAmp: Int

    /** Get or set [immersiveAudio] as [Boolean] to use when playing. */
    var immersiveAudio: Boolean

    /**
     * Get or set [UsbDacExclusiveMode] to use when playing.
     * When set to [UsbDacExclusiveMode.AUTO], exclusive mode will be enabled automatically
     * when a USB DAC is connected and playing lossless content on API 34+.
     * For bit-perfect playback, also set [loudnessNormalizationMode] to
     * [LoudnessNormalizationMode.NONE].
     */
    var usbDacExclusiveMode: UsbDacExclusiveMode
}
