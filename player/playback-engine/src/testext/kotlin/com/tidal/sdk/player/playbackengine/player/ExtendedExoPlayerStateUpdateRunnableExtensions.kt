package com.tidal.sdk.player.playbackengine.player

import com.tidal.sdk.player.reflectionGetInstanceMemberProperty

internal val ExtendedExoPlayerStateUpdateRunnable.Companion.reflectionDELAY_MILLIS: Long
    get() = reflectionGetInstanceMemberProperty("DELAY_MILLIS")!!
