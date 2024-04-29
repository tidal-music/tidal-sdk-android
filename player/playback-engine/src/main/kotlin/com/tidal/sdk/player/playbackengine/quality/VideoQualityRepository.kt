package com.tidal.sdk.player.playbackengine.quality

import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/**
 * Repository for getting the [VideoQuality] to be used in a specific request for [PlaybackInfo].
 */
internal class VideoQualityRepository {

    val streamingQuality = VideoQuality.HIGH
}
