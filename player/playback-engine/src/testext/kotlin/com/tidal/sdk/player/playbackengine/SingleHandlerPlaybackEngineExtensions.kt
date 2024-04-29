package com.tidal.sdk.player.playbackengine

import android.os.Handler
import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val SingleHandlerPlaybackEngine.reflectionDelegate: PlaybackEngine
    get() = reflectionGetInstanceMemberProperty<PlaybackEngine>("delegate")!!

internal val SingleHandlerPlaybackEngine.reflectionHandler: Handler
    get() = reflectionGetInstanceMemberProperty("handler")!!
