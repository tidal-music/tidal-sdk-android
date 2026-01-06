package com.tidal.sdk.player.playbackengine.exclusivemode

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.exoplayer.audio.AudioCapabilities
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.audio.DefaultAudioOffloadSupportProvider
import androidx.media3.exoplayer.audio.DefaultAudioSink
import androidx.media3.exoplayer.audio.DefaultAudioTrackBufferSizeProvider

internal class ExclusiveModeAudioSinkProvider(
    private val context: Context,
    private val defaultAudioTrackBufferSizeProvider: DefaultAudioTrackBufferSizeProvider,
) {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun createForExclusiveMode(): DefaultAudioSink {
        val audioSink = DefaultAudioSink.Builder(context)
            .setAudioCapabilities(AudioCapabilities.DEFAULT_AUDIO_CAPABILITIES)
            .setAudioProcessors(emptyArray())
            .setAudioTrackBufferSizeProvider(defaultAudioTrackBufferSizeProvider)
            .setAudioOffloadSupportProvider(DefaultAudioOffloadSupportProvider(context))
            .build()
        audioSink.setOffloadMode(AudioSink.OFFLOAD_MODE_ENABLED_GAPLESS_REQUIRED)
        return audioSink
    }

    fun createDefault(audioCapabilities: AudioCapabilities): DefaultAudioSink {
        return DefaultAudioSink.Builder(context)
            .setAudioCapabilities(audioCapabilities)
            .setAudioProcessors(emptyArray())
            .setAudioTrackBufferSizeProvider(defaultAudioTrackBufferSizeProvider)
            .build()
    }
}
