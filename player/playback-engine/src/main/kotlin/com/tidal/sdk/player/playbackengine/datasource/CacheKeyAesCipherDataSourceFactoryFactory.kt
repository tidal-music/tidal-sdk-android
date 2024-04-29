package com.tidal.sdk.player.playbackengine.datasource

import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.CacheKeyFactory
import com.tidal.sdk.player.playbackengine.Encryption
import com.tidal.sdk.player.playbackengine.offline.crypto.CacheKeyAesCipherDataSourceFactory

internal class CacheKeyAesCipherDataSourceFactoryFactory(
    private val cacheDataSourceFactory: CacheDataSource.Factory,
    private val cacheKeyFactory: CacheKeyFactory,
    private val encryption: Encryption?,
) {

    fun create(cache: Cache) = CacheKeyAesCipherDataSourceFactory(
        cacheKeyFactory,
        encryption!!.secretKey,
        cacheDataSourceFactory.setCache(cache),
    )
}
