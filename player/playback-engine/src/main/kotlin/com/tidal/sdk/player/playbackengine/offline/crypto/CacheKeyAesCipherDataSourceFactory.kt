package com.tidal.sdk.player.playbackengine.offline.crypto

import androidx.media3.datasource.AesCipherDataSource
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.CacheKeyFactory

/**
 * This is an implementation of [DataSource.Factory] whose main goal is to create a [DataSource]
 * which should be used for playback of content that is cached or offlined with aes encryption and a
 * cache key, using a [CacheKeyFactory].
 *
 * This wraps an [AesCipherDataSource] into our own implementation [CacheKeyAesCipherDataSource]
 * with a [CacheKeyFactory].
 *
 * The reason for this class is twofold:
 * 1. Standard implementation of [AesCipherDataSource] does not have a factory class, so this will
 *    keep it aligned with our other implementations like this.
 * 2. Standard implementation of [AesCipherDataSource] does not support adding a [CacheKeyFactory],
 *    so this adds that functionality to it.
 *
 * @param[cacheKeyFactory] An instance of [CacheKeyFactory]. Must be the same as the one used for
 *   caching or offlining this piece of content.
 * @param[secretKey] A secretKey of [ByteArray]. Must be the same value for the same content when
 *   reading from a [DataSource] and writing to a[androidx.media3.datasource.DataSink].
 * @param[upstream] An instance of [DataSource.Factory]. Should normally be any type of
 *   [androidx.media3.datasource.cache.CacheDataSource.Factory], depending on if your content has
 *   been cached or offlined.
 */
class CacheKeyAesCipherDataSourceFactory(
    private val cacheKeyFactory: CacheKeyFactory,
    private val secretKey: ByteArray,
    private val upstream: DataSource.Factory,
) : DataSource.Factory {

    override fun createDataSource(): DataSource {
        val aesCipherDataSource = AesCipherDataSource(secretKey, upstream.createDataSource())
        return CacheKeyAesCipherDataSource(cacheKeyFactory, aesCipherDataSource)
    }
}
