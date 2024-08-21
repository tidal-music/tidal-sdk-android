package com.tidal.sdk.player.streamingapi

import com.tidal.sdk.player.common.model.AssetPresentation
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.streamingapi.offline.Storage
import com.tidal.sdk.player.streamingapi.playbackinfo.model.ManifestMimeType
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

@Suppress("MagicNumber")
object TrackPlaybackInfoFactory {

    val DEFAULT = PlaybackInfo.Track(
        ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT,
        AudioQuality.LOW,
        AssetPresentation.FULL,
        AudioMode.STEREO,
        16,
        44100,
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

    val EMPTY_STREAMING_SESSION_ID = DEFAULT.copy(streamingSessionId = "")

    val REPLACEMENT_TRACK_ID = DEFAULT.copy(ApiConstants.PLAYBACK_INFO_ID_FOR_DEFAULT_2)

    val REPLACEMENT_AUDIO_QUALITY = DEFAULT.copy(audioQuality = AudioQuality.HIGH)

    val PROTECTED = DEFAULT.copy(licenseSecurityToken = ApiConstants.LICENSE_SECURITY_TOKEN)

    val OFFLINE = DEFAULT.copy(
        offlineRevalidateAt = ApiConstants.OFFLINE_REVALIDATE_AT_SECONDS,
        offlineValidUntil = ApiConstants.OFFLINE_VALID_UNTIL_SECONDS,
    )

    val OFFLINE_PLAY = PlaybackInfo.Offline.Track(
        DEFAULT,
        "",
        Storage(false, ""),
    )
}
