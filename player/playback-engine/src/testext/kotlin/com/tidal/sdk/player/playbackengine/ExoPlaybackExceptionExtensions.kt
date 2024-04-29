package com.tidal.sdk.player.playbackengine

import androidx.media3.exoplayer.ExoPlaybackException
import com.tidal.sdk.player.reflectionSetInstanceFinalField

internal fun ExoPlaybackException.reflectionSetErrorCode(newErrorCode: Int) =
    reflectionSetInstanceFinalField("errorCode", newErrorCode)

internal fun ExoPlaybackException.reflectionSetType(newType: Int) =
    reflectionSetInstanceFinalField("type", newType)
