package com.tidal.sdk.player.playbackengine.mediasource

import com.tidal.sdk.player.playbackengine.mediasource.streamingsession.StreamingSession
import com.tidal.sdk.player.reflectionGetInstanceMemberProperty
import com.tidal.sdk.player.reflectionSetInstanceMemberProperty

internal var MediaSourcerer.reflectionCurrentStreamingSession: StreamingSession?
    get() = reflectionGetInstanceMemberProperty("currentStreamingSession")
    set(value) = reflectionSetInstanceMemberProperty("currentStreamingSession", value)

internal var MediaSourcerer.reflectionNextStreamingSession: StreamingSession?
    get() = reflectionGetInstanceMemberProperty("nextStreamingSession")
    set(value) = reflectionSetInstanceMemberProperty("nextStreamingSession", value)
