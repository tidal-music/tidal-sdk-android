package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

object UCPlaybackInfoFactory {

    val DEFAULT = PlaybackInfo.UC(
        ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT.toString(),
        "https://fsu.fa.tidal.com/storage/${ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT}.m3u8",
        "streamingSessionId",
        ManifestMimeType.EMU,
        "",
        null,
        0F,
        0F,
        0F,
        0F,
        0,
        0,
    )
}
