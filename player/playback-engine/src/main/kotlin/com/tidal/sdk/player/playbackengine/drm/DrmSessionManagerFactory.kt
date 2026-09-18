package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManager
import com.tidal.sdk.player.common.model.Extras
import com.tidal.sdk.player.streamingapi.playbackinfo.model.PlaybackInfo

internal class DrmSessionManagerFactory(
    private val defaultDrmSessionManagerBuilder: DefaultDrmSessionManager.Builder,
    private val tidalMediaDrmCallbackFactory: TidalMediaDrmCallbackFactory,
    private val offlineLicenseManager: OfflineLicenseManager,
) {

    fun createDrmSessionManagerForOnlinePlay(
        playbackInfo: PlaybackInfo,
        extras: Extras?,
    ): DrmSessionManager {
        val licenseUrl = playbackInfo.licenseUrl
        if (licenseUrl.isNullOrEmpty()) {
            return DrmSessionManager.DRM_UNSUPPORTED
        }
        val drmSessionManager =
            createDefaultDrmSessionManager(playbackInfo, licenseUrl = licenseUrl, extras = extras)

        val cachedKeySetId = offlineLicenseManager.getValidKeySetId(playbackInfo)
        if (cachedKeySetId != null) {
            // Reuse the persisted offline license; the CDM decrypts from it with no network
            // request.
            drmSessionManager.setMode(DefaultDrmSessionManager.MODE_PLAYBACK, cachedKeySetId)
        } else {
            // Stream as usual now, but lazily persist an offline license for the next play.
            offlineLicenseManager.acquireAndStoreAsync(playbackInfo, extras)
        }
        return drmSessionManager
    }

    fun createDrmSessionManagerForOfflinePlay(
        playbackInfo: PlaybackInfo.Offline,
        extras: Extras?,
    ): DrmSessionManager {
        if (playbackInfo.offlineLicense!!.isEmpty()) {
            return DrmSessionManager.DRM_UNSUPPORTED
        }
        return createDefaultDrmSessionManager(
            playbackInfo.delegate,
            licenseUrl = "",
            extras = extras,
        )
    }

    private fun createDefaultDrmSessionManager(
        playbackInfo: PlaybackInfo,
        licenseUrl: String,
        drmMode: DrmMode = DrmMode.Streaming,
        extras: Extras?,
    ): DefaultDrmSessionManager {
        return defaultDrmSessionManagerBuilder.build(
            tidalMediaDrmCallbackFactory.create(
                licenseUrl,
                playbackInfo.streamingSessionId,
                drmMode,
                extras,
            )
        )
    }

    private val PlaybackInfo.Offline.delegate: PlaybackInfo
        get() =
            when (this) {
                is PlaybackInfo.Offline.Track -> track
                is PlaybackInfo.Offline.Video -> video
                else ->
                    throw IllegalArgumentException("Not a valid delegate for PlaybackInfo.Offline")
            }
}
