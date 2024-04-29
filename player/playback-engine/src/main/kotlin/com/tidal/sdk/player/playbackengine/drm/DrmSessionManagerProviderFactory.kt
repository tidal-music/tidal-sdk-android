package com.tidal.sdk.player.playbackengine.drm

import androidx.media3.exoplayer.drm.DrmSessionManager
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider

internal class DrmSessionManagerProviderFactory {

    fun create(drmSessionManager: DrmSessionManager) =
        DrmSessionManagerProvider { drmSessionManager }
}
