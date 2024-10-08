package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.Cache
import com.tidal.sdk.player.playbackengine.datasource.CacheKeyAesCipherDataSourceFactoryFactory
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider

/**
 * This creates and stores DataSourceFactories used for offline playback.
 *
 * @param[offlineCacheProvider] An instance of [OfflineCacheProvider]. Used to get the correct
 * [Cache] instance.
 */
internal class OfflinePlayDataSourceFactoryHelper(
    @Suppress("MaxLineLength") private val cacheKeyAesCipherDataSourceFactoryFactory: CacheKeyAesCipherDataSourceFactoryFactory, // ktlint-disable max-line-length parameter-wrapping
    offlineCacheProvider: OfflineCacheProvider?,
) : OfflineDataSourceFactoryHelper<DataSource.Factory>(offlineCacheProvider) {

    @Suppress("UnsafeOptInUsageError")
    override fun create(cache: Cache) = cacheKeyAesCipherDataSourceFactoryFactory.create(cache)
}
