package com.tidal.sdk.player.common.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.VideoQuality.AUDIO_ONLY
import com.tidal.sdk.player.common.model.VideoQuality.HIGH
import com.tidal.sdk.player.common.model.VideoQuality.LOW
import com.tidal.sdk.player.common.model.VideoQuality.MEDIUM

/**
 * Video quality of a given video.
 *
 * [AUDIO_ONLY] is used when we only want the audio of the video.
 * [LOW] is our normal video quality.
 * [MEDIUM] is our medium video quality.
 * [HIGH] is our highest video quality.
 */
@Keep
enum class VideoQuality : ProductQuality {
    AUDIO_ONLY,
    LOW,
    MEDIUM,
    HIGH,
}
