package com.tidal.sdk.player.common.model

import androidx.annotation.Keep
import com.tidal.sdk.player.common.model.AudioQuality.HIGH
import com.tidal.sdk.player.common.model.AudioQuality.HI_RES_LOSSLESS
import com.tidal.sdk.player.common.model.AudioQuality.LOSSLESS
import com.tidal.sdk.player.common.model.AudioQuality.LOW

/**
 * Audio quality of a given track.
 *
 * [LOW] is our normal quality, 96 kbps AAC.
 * [HIGH] is our high quality, 320 kbps AAC.
 * [LOSSLESS] is our lossless quality, up to 1411 kbps FLAC.
 * [HI_RES_LOSSLESS] is our HiRes FLAC option.
 */
@Keep
enum class AudioQuality : ProductQuality {
    LOW,
    HIGH,
    LOSSLESS,
    HI_RES_LOSSLESS,
}
