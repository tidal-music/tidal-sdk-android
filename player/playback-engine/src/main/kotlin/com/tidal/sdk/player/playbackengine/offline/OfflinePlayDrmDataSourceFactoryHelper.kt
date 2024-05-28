package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider

/**
 * This creates and stores DataSourceFactories used for drm protected offline playback.
 *
 * @param[offlineCacheProvider] An instance of [OfflineCacheProvider]. Used to get the correct
 * [Cache] instance.
 */
internal class OfflinePlayDrmDataSourceFactoryHelper(
    private val cacheDataSourceFactory: CacheDataSource.Factory,
    offlineCacheProvider: OfflineCacheProvider?,
) : OfflineDataSourceFactoryHelper<DataSource.Factory>(offlineCacheProvider) {

    override fun create(cache: Cache) = cacheDataSourceFactory.setCache(cache)
}
