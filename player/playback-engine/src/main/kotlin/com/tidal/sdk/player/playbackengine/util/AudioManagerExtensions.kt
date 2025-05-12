package com.tidal.sdk.player.playbackengine.util

import android.media.AudioManager

val AudioManager.scaledVolume: Float
    get() =
        getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() /
            getStreamMaxVolume(AudioManager.STREAM_MUSIC)
