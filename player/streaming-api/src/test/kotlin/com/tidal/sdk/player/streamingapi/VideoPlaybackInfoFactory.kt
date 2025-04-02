package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.StreamType
import com.tidal.sdk.player.common.model.VideoQuality
import com.tidal.sdk.player.streamingapi.offline.Storage
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

object VideoPlaybackInfoFactory {

    val DEFAULT = PlaybackInfo.Video(
        ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT.toInt(),
        VideoQuality.LOW,
        AssetPresentation.FULL,
        StreamType.ON_DEMAND,
        ApiConstants.MANIFEST_HASH,
        ApiConstants.STREAMING_SESSION_ID,
        ManifestMimeType.DASH,
        ApiConstants.MANIFEST,
        null,
        0.0F,
        0.0F,
        0.0F,
        0.0F,
        0,
        0,
    )

    val EMPTY_STREAMING_SESSION_ID = DEFAULT.copy(streamingSessionId = "")

    val REPLACEMENT_VIDEO_ID = DEFAULT.copy(ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT_2.toInt())

    val REPLACEMENT_VIDEO_QUALITY = DEFAULT.copy(videoQuality = VideoQuality.HIGH)

    val PROTECTED = DEFAULT.copy(licenseSecurityToken = ApiConstants.LICENSE_SECURITY_TOKEN)

    val OFFLINE = DEFAULT.copy(
        offlineRevalidateAt = ApiConstants.OFFLINE_REVALIDATE_AT_SECONDS,
        offlineValidUntil = ApiConstants.OFFLINE_VALID_UNTIL_SECONDS,
    )

    val OFFLINE_PLAY = PlaybackInfo.Offline.Video(
        DEFAULT,
        "",
        Storage(false, ""),
        false,
    )
}
