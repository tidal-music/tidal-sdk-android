package com.tidal.sdk.player.playbackengine.quality

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.playbackengine.network.NetworkTransportHelper
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

/**
 * Repository for getting the [AudioQuality] to be used in a specific request for [PlaybackInfo].
 */
internal class AudioQualityRepository(
    private val networkTransportHelper: NetworkTransportHelper,
    var streamingWifiAudioQuality: AudioQuality = AudioQuality.LOW,
    var streamingCellularAudioQuality: AudioQuality = AudioQuality.LOW,
) {

    val streamingQuality: AudioQuality
        get() = if (networkTransportHelper.isWifiOrEthernet()) {
            streamingWifiAudioQuality
        } else {
            streamingCellularAudioQuality
        }
}
