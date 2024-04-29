package com.tidal.sdk.player.playbackengine

import android.os.Handler
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackSession
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.PlaybackStatistics
import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StartedStall
import com.tidal.sdk.player.playbackengine.model.PlaybackContext
import com.tidal.sdk.player.playbackengine.model.PlaybackState
import com.tidal.sdk.player.playbackengine.player.ExtendedExoPlayer
import com.tidal.sdk.player.playbackengine.util.SynchronousSurfaceHolder
import com.tidal.sdk.player.playbackengine.view.AspectRatioAdjustingSurfaceView
import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetDelegatedInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty

internal fun ExoPlayerPlaybackEngine.reflectionSetPlaybackState(newPlaybackState: PlaybackState) =
    reflectionSetInstanceMemberProperty("playbackState", newPlaybackState)

internal var ExoPlayerPlaybackEngine.reflectionExtendedExoPlayer: ExtendedExoPlayer
    get() = reflectionGetInstanceMemberProperty("extendedExoPlayer")!!
    set(value) = reflectionSetInstanceMemberProperty("extendedExoPlayer", value)

internal var ExoPlayerPlaybackEngine.reflectionPlaybackContext: PlaybackContext?
    get() = reflectionGetInstanceMemberProperty("playbackContext")
    set(value) = reflectionSetInstanceMemberProperty("playbackContext", value)

internal var ExoPlayerPlaybackEngine.reflectionNextPlaybackContext: PlaybackContext?
    get() = reflectionGetInstanceMemberProperty("nextPlaybackContext")
    set(value) = reflectionSetInstanceMemberProperty("nextPlaybackContext", value)

internal val ExoPlayerPlaybackEngine.reflectionInternalHandler: Handler
    get() = reflectionGetInstanceMemberProperty("internalHandler")!!

internal var ExoPlayerPlaybackEngine.reflectionVideoSurfaceViewAndSurfaceHolder:
    Pair<AspectRatioAdjustingSurfaceView, SynchronousSurfaceHolder>?
    get() = reflectionGetInstanceMemberProperty("videoSurfaceViewAndSurfaceHolder")
    set(value) = reflectionSetInstanceMemberProperty("videoSurfaceViewAndSurfaceHolder", value)

internal var ExoPlayerPlaybackEngine.reflectionCurrentPlaybackStatistics: PlaybackStatistics?
    get() = reflectionGetInstanceMemberProperty("currentPlaybackStatistics")
    set(value) = reflectionSetInstanceMemberProperty("currentPlaybackStatistics", value)

internal var ExoPlayerPlaybackEngine.reflectionNextPlaybackStatistics:
    PlaybackStatistics.Undetermined?
    get() = reflectionGetInstanceMemberProperty("nextPlaybackStatistics")
    set(value) = reflectionSetInstanceMemberProperty("nextPlaybackStatistics", value)

internal var ExoPlayerPlaybackEngine.reflectionCurrentStall: StartedStall?
    get() = reflectionGetInstanceMemberProperty("currentStall")
    set(value) = reflectionSetInstanceMemberProperty("currentStall", value)

internal fun ExoPlayerPlaybackEngine.reflectionSetDelegatedCurrentStall(newValue: StartedStall?) =
    reflectionSetDelegatedInstanceMemberProperty("currentStall", newValue)

internal var ExoPlayerPlaybackEngine.reflectionCurrentPlaybackSession: PlaybackSession?
    get() = reflectionGetInstanceMemberProperty("currentPlaybackSession")
    set(value) = reflectionSetInstanceMemberProperty("currentPlaybackSession", value)

internal var ExoPlayerPlaybackEngine.reflectionNextPlaybackSession: PlaybackSession?
    get() = reflectionGetInstanceMemberProperty("nextPlaybackSession")
    set(value) = reflectionSetInstanceMemberProperty("nextPlaybackSession", value)
