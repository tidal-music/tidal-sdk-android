package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManager
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

internal class DrmSessionManagerFactory(
    private val defaultDrmSessionManagerBuilder: DefaultDrmSessionManager.Builder,
    private val tidalMediaDrmCallbackFactory: TidalMediaDrmCallbackFactory,
) {

    fun createDrmSessionManagerForOnlinePlay(
        playbackInfo: PlaybackInfo,
        extras: Map<String, String?>?,
    ): DrmSessionManager {
        if (playbackInfo.licenseSecurityToken.isNullOrEmpty()) {
            return DrmSessionManager.DRM_UNSUPPORTED
        }
        return createDefaultDrmSessionManager(playbackInfo, extras = extras)
    }

    fun createDrmSessionManagerForOfflinePlay(
        playbackInfo: PlaybackInfo.Offline,
        extras: Map<String, String?>?,
    ): DrmSessionManager {
        if (playbackInfo.offlineLicense!!.isEmpty()) {
            return DrmSessionManager.DRM_UNSUPPORTED
        }
        return createDefaultDrmSessionManager(playbackInfo.delegate, extras = extras)
    }

    private fun createDefaultDrmSessionManager(
        playbackInfo: PlaybackInfo,
        drmMode: DrmMode = DrmMode.Streaming,
        extras: Map<String, String?>?,
    ): DefaultDrmSessionManager {
        return defaultDrmSessionManagerBuilder
            .build(tidalMediaDrmCallbackFactory.create(playbackInfo, drmMode, extras))
    }

    private val PlaybackInfo.Offline.delegate: PlaybackInfo
        get() = when (this) {
            is PlaybackInfo.Offline.Track -> track
            is PlaybackInfo.Offline.Video -> video
            else -> throw IllegalArgumentException("Not a valid delegate for PlaybackInfo.Offline")
        }
}
