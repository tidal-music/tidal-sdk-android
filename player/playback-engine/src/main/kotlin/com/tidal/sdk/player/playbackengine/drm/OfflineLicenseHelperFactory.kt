package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionEventListener
import androidx.media3.exoplayer.drm.OfflineLicenseHelper

internal class OfflineLicenseHelperFactory {
    fun create(drmSessionManager: DefaultDrmSessionManager) =
        OfflineLicenseHelper(drmSessionManager, DrmSessionEventListener.EventDispatcher())
}
