package com.tidal.sdk.player.playbackengine.player.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.PriorityTaskManager
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.RenderersFactory
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.ExoTrackSelection
import androidx.media3.exoplayer.trackselection.TrackSelector
import com.tidal.sdk.player.playbackengine.mediasource.MediaSourcerer
import com.tidal.sdk.player.playbackengine.model.BufferConfiguration
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerState
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayerStateUpdateRunnable
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlin.time.DurationUnit

@Module
internal object ExtendedExoPlayerModule {

    @Provides
    @Reusable
    fun trackSelectionFactory(): ExoTrackSelection.Factory = AdaptiveTrackSelection.Factory()

    @Provides
    @Reusable
    fun trackSelector(
        context: Context,
        trackSelectionFactory: ExoTrackSelection.Factory,
    ): TrackSelector = DefaultTrackSelector(context, trackSelectionFactory)

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun loadControl(bufferConfiguration: BufferConfiguration): LoadControl =
        bufferConfiguration.run {
            DefaultLoadControl.Builder()
                .setBackBuffer(backBufferDuration.toInt(DurationUnit.MILLISECONDS), true)
                .setBufferDurationsMs(
                    minPlaybackBufferAudio.toInt(DurationUnit.MILLISECONDS),
                    maxPlaybackBufferAudio.toInt(DurationUnit.MILLISECONDS),
                    minPlaybackBufferVideo.toInt(DurationUnit.MILLISECONDS),
                    maxPlaybackBufferVideo.toInt(DurationUnit.MILLISECONDS),
                    bufferForPlayback.toInt(DurationUnit.MILLISECONDS),
                    bufferForPlaybackAfterRebuffer.toInt(DurationUnit.MILLISECONDS),
                )
                .setBufferSize(audioBufferSize.value.toInt(), videoBufferSize.value.toInt())
                .setPrioritizeTimeOverSizeThresholds(true)
                .build()
        }

    @Provides @ExtendedExoPlayerComponent.Scoped fun priorityTaskManager() = PriorityTaskManager()

    @Provides
    @Reusable
    fun audioAttributes() =
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun exoPlayer(
        context: Context,
        renderersFactory: RenderersFactory,
        trackSelector: TrackSelector,
        loadControl: LoadControl,
        looper: Looper,
        priorityTaskManager: PriorityTaskManager,
        audioAttributes: AudioAttributes,
    ) =
        ExoPlayer.Builder(context, renderersFactory)
            .setTrackSelector(trackSelector)
            .setLoadControl(loadControl)
            .setLooper(looper)
            .setPriorityTaskManager(priorityTaskManager)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_NETWORK)
            .setAudioAttributes(audioAttributes, true)
            .build()

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun extendedExoPlayerState() = ExtendedExoPlayerState()

    @Provides
    @ExtendedExoPlayerComponent.Scoped
    fun extendedExoPlayer(
        exoPlayer: ExoPlayer,
        loadControl: LoadControl,
        mediaSourcerer: MediaSourcerer,
        extendedExoPlayerState: ExtendedExoPlayerState,
    ) = ExtendedExoPlayer(exoPlayer, loadControl, mediaSourcerer, extendedExoPlayerState)

    @Provides
    @Reusable
    fun extendedExoPlayerStateUpdateRunnable(
        extendedExoPlayerState: ExtendedExoPlayerState,
        exoPlayer: ExoPlayer,
        handler: Handler,
    ) = ExtendedExoPlayerStateUpdateRunnable(extendedExoPlayerState, exoPlayer, handler)
}
