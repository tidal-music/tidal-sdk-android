package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

object BroadcastPlaybackInfoFactory {

    val DEFAULT =
        PlaybackInfo.Broadcast(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT.toString(),
            AudioQuality.LOW,
            "streamingSessionId",
            ManifestMimeType.EMU,
            ApiConstants.MANIFEST_DJ_SESSION,
            null,
            0F,
            0F,
            0F,
            0F,
            0,
            0,
        )

    val DEFAULT_MISSING_STREAMING_SESSION_ID = DEFAULT.copy(streamingSessionId = "")

    val REPLACEMENT_ID = DEFAULT.copy(ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT_2.toString())

    val REPLACEMENT_AUDIO_QUALITY = DEFAULT.copy(audioQuality = AudioQuality.HIGH)
}
