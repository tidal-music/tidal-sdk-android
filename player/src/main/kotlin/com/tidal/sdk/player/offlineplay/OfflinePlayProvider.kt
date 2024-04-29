package com.tidal.sdk.player.offlineplay

import com.tidal.sdk.player.playbackengine.Encryption
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider
import com.tidal.sdk.player.streamingapi.playbackinfo.offline.OfflinePlaybackInfoProvider

/**
 * Collection of classes needed for offline playback.
 */
class OfflinePlayProvider(
    val offlinePlaybackInfoProvider: OfflinePlaybackInfoProvider? = null,
    val offlineCacheProvider: OfflineCacheProvider? = null,
    val encryption: Encryption? = null,
)
