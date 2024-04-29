package com.tidal.sdk.player.playbackengine

import com.tidal.sdk.player.playbackengine.di.ExoPlayerPlaybackEngineComponent
import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty

internal var PlaybackEngineModuleRoot.Companion.reflectionComponentFactoryF:
    () -> ExoPlayerPlaybackEngineComponent.Factory
    get() = reflectionGetInstanceMemberProperty("componentFactoryF")!!
    set(value) = reflectionSetInstanceMemberProperty("componentFactoryF", value)
