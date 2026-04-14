package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.offline.Storage
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

object VideoPlaybackInfoFactory {

    private const val HLS_DATA_URL =
        "data:application/vnd.apple.mpegurl;base64,${ApiConstants.MANIFEST}"

    val DEFAULT =
        PlaybackInfo.Video(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT.toInt(),
            VideoQuality.LOW,
            AssetPresentation.FULL,
            StreamType.ON_DEMAND,
            "",
            "streamingSessionId",
            ManifestMimeType.HLS,
            HLS_DATA_URL,
            null,
            0f,
            0f,
            0f,
            0f,
            -1,
            -1,
        )

    val OFFLINE_PLAY = PlaybackInfo.Offline.Video(DEFAULT, "", Storage(false, ""), false)
}
