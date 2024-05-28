package com.tidal.sdk.player.playbackengine.offline.crypto

import androidx.media3.datasource.AesCipherDataSource
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.cache.CacheKeyFactory

/**
 * This is an implementation of [DataSource] that takes in an [AesCipherDataSource] and wraps it
 * with the provided [CacheKeyFactory].
 *
 * This is only used so it can recognize the cached or offlined files based on a cache key,
 * which the [AesCipherDataSource] does not support out of the box.
 *
 * @param[cacheKeyFactory] An instance of [CacheKeyFactory]. Must be the same as the one used
 * for caching or offlining this piece of content.
 * @param[aesCipherDataSource] An instance of [AesCipherDataSource].
 */
internal class CacheKeyAesCipherDataSource(
    private val cacheKeyFactory: CacheKeyFactory,
    private val aesCipherDataSource: AesCipherDataSource,
) : DataSource by aesCipherDataSource {

    override fun open(dataSpec: DataSpec): Long {
        val dataSpecWithCacheKey =
            dataSpec.buildUpon().setKey(cacheKeyFactory.buildCacheKey(dataSpec)).build()
        return aesCipherDataSource.open(dataSpecWithCacheKey)
    }
}
