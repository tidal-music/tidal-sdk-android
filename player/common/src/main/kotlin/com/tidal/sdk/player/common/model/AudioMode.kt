package com.tidal.sdk.player.common.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.AudioMode.DOLBY_ATMOS
import com.tidal.sdk.player.common.model.AudioMode.STEREO

/**
 * Audio mode of a given product.
 *
 * [DOLBY_ATMOS] is the dolby atmos version of the track, only supported by a subset of devices that
 * has an ac4 decoder. [STEREO] is the default stereo version of the track. All devices support
 * this.
 */
@Keep
enum class AudioMode {
    DOLBY_ATMOS,
    STEREO,
}
