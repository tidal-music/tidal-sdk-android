package com.tidal.sdk.player.playbackengine.player.renderer.audio

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * The possible decoding modes to use for audio.
 */
enum class AudioDecodingMode {
    /**
     * Use on-device decoding capabilities. This might cause playback to use different sample
     * frequencies than the original ones that the tracks have depending on your device, but may be
     * the most reliable option for cases in which other values cause issues.
     */
    NATIVE,

    /**
     * Use bit-perfect audio decoding. This provides a theoretically pure playback experience, but
     * requires a connected digital-to-analog converter (DAC) + a custom renderer that handles usb
     * routing and playback of wanted format and may not work reliably on all devices.
     *
     * Using this below the annotated API level is equivalent to using [NATIVE].
     *
     * Using this without an appropriate connected DAC is functionally equivalent to using [NATIVE],
     * but connecting an appropriate DAC will automatically cause decoding to switch to bit-perfect
     * until it is disconnected.
     */
    @RequiresApi(Build.VERSION_CODES.P)
    BIT_PERFECT,
}
