package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.Cache
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider

/**
 * Abstract class that helps create and store DataSourceFactories. The building blocks are here, but
 * implementations are needed to create the actual and specific instance of [T] (which has to be an
 * instance of [DataSource.Factory]), based on the given cache.
 *
 * @param[offlineCacheProvider] An instance of [OfflineCacheProvider]. Used to get the correct
 *   [Cache] instance.
 */
internal abstract class OfflineDataSourceFactoryHelper<T : DataSource.Factory>(
    private val offlineCacheProvider: OfflineCacheProvider?
) {

    private val externalDataSourceFactories = mutableMapOf<String, T>()
    private val internalDataSourceFactories = mutableMapOf<String, T>()

    fun getExternal(path: String): T =
        externalDataSourceFactories.getOrPut(path) {
            create(checkNotNull(offlineCacheProvider).getExternal(path))
        }

    fun getInternal(path: String): T =
        internalDataSourceFactories.getOrPut(path) {
            create(checkNotNull(offlineCacheProvider).getInternal(path))
        }

    abstract fun create(cache: Cache): T
}
