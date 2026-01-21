package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.streamingapi.offline.Storage
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

@Suppress("MagicNumber")
object TrackPlaybackInfoFactory {

    val DEFAULT =
        PlaybackInfo.Track(
            ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT.toInt(),
            AudioQuality.LOW,
            AssetPresentation.FULL,
            AudioMode.STEREO,
            ApiConstants.MANIFEST_HASH,
            ApiConstants.STREAMING_SESSION_ID,
            ManifestMimeType.DASH,
            ApiConstants.MANIFEST,
            null,
            -9.8F,
            0.999923F,
            -9.8F,
            0.999923F,
            0,
            0,
        )

    val OFFLINE_PLAY = PlaybackInfo.Offline.Track(DEFAULT, "", Storage(false, ""), false)
}
