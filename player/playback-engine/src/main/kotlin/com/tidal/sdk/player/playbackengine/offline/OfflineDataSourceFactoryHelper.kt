package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.Cache
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider

/**
 * Abstract class that helps create and store DataSourceFactories.
 * The building blocks are here, but implementations are needed to create the actual and specific
 * instance of [T] (which has to be an instance of [DataSource.Factory]), based on the given cache.
 *
 * @param[offlineCacheProvider] An instance of [OfflineCacheProvider]. Used to get the correct
 * [Cache] instance.
 */
internal abstract class OfflineDataSourceFactoryHelper<T : DataSource.Factory>(
    private val offlineCacheProvider: OfflineCacheProvider?,
) {

    private var externalDataSourceFactory: T? = null
    private var internalDataSourceFactory: T? = null

    fun getExternal(path: String) =
        externalDataSourceFactory ?: create(offlineCacheProvider!!.getExternal(path))
            .also { externalDataSourceFactory = it }

    fun getInternal(path: String) =
        internalDataSourceFactory ?: create(offlineCacheProvider!!.getInternal(path))
            .also { internalDataSourceFactory = it }

    @Suppress("UnsafeOptInUsageError")
    abstract fun create(cache: Cache): T
}
