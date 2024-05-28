package com.tidal.sdk.player.playbackengine.player.di

import android.content.Context
import androidx.media3.common.audio.AudioProcessor
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.audio.AudioCapabilities
import androidx.media3.exoplayer.audio.DefaultAudioSink
import androidx.media3.exoplayer.audio.DefaultAudioTrackBufferSizeProvider
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.player.renderer.PlayerRenderersFactory
import com.tidal.sdk.player.playbackengine.player.renderer.audio.fallback.FallbackAudioRendererFactory
import com.tidal.sdk.player.playbackengine.player.renderer.audio.flac.LibflacAudioRendererFactory
import com.tidal.sdk.player.playbackengine.player.renderer.video.MediaCodecVideoRendererFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlin.time.DurationUnit

@Module
@Suppress("TooManyFunctions")
internal object RendererModule {

    @Provides
    @Reusable
    fun mediaCodecVideoRendererFactory(context: Context) = MediaCodecVideoRendererFactory(context)

    @Provides
    @Reusable
    fun libflacAudioRendererFactory() = LibflacAudioRendererFactory()

    @Provides
    @Reusable
    fun audioCapabilities(context: Context) = AudioCapabilities.getCapabilities(context)

    @Provides
    @Reusable
    fun defaultAudioTrackBufferSizeProvider(
        bufferConfiguration: BufferConfiguration,
    ) = DefaultAudioTrackBufferSizeProvider.Builder()
        .setMinPcmBufferDurationUs(
            bufferConfiguration.audioTrackBuffer.toInt(DurationUnit.MICROSECONDS),
        )
        .setMaxPcmBufferDurationUs(
            bufferConfiguration.audioTrackBuffer.toInt(DurationUnit.MICROSECONDS),
        )
        .build()

    @Provides
    @Reusable
    fun audioProcessors() = emptyArray<AudioProcessor>()

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun defaultAudioSink(
        audioCapabilities: AudioCapabilities,
        audioProcessors: Array<AudioProcessor>,
        defaultAudioTrackBufferSizeProvider: DefaultAudioTrackBufferSizeProvider,
    ) = DefaultAudioSink.Builder()
        .setAudioCapabilities(audioCapabilities)
        .setAudioProcessors(audioProcessors)
        .setAudioTrackBufferSizeProvider(defaultAudioTrackBufferSizeProvider)
        .build()

    @Provides
    @Reusable
    fun fallbackAudioRendererFactory(context: Context, defaultAudioSink: DefaultAudioSink) =
        FallbackAudioRendererFactory(context, defaultAudioSink)

    @Provides
    @Reusable
    @Suppress("SpreadOperator")
    fun renderersFactory(
        mediaCodecVideoRendererFactory: MediaCodecVideoRendererFactory,
        libflacAudioRendererFactory: LibflacAudioRendererFactory,
        fallbackAudioRendererFactory: FallbackAudioRendererFactory,
    ): RenderersFactory = PlayerRenderersFactory(
        mediaCodecVideoRendererFactory,
        libflacAudioRendererFactory,
        fallbackAudioRendererFactory,
    )
}
