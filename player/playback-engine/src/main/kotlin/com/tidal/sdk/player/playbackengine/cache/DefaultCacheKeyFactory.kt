package com.tidal.sdk.player.playbackengine.cache

import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.cache.CacheKeyFactory

internal class DefaultCacheKeyFactory : CacheKeyFactory {
    override fun buildCacheKey(dataSpec: DataSpec) = dataSpec.uri.path.toString()
}
