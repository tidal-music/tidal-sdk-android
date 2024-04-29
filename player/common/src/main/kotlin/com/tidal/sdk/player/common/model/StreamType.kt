package com.tidal.sdk.player.common.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.StreamType.LIVE
import com.tidal.sdk.player.common.model.StreamType.ON_DEMAND

/**
 * Stream type of a given video.
 *
 * [ON_DEMAND] Indicating the video is an on-demand stream.
 * [LIVE] Indicating the video is a live stream.
 */
@Keep
enum class StreamType {
    ON_DEMAND,
    LIVE,
}
