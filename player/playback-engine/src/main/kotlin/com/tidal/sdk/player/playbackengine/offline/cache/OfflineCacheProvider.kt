package com.tidal.sdk.player.playbackengine.offline.cache

import androidx.media3.datasource.cache.Cache

/**
 * Implement this to provide [Cache] instances for both internal and external storage used for
 * offline content.
 */
@Suppress("UnsafeOptInUsageError")
interface OfflineCacheProvider {
    fun getExternal(path: String): Cache

    fun getInternal(path: String): Cache
}
