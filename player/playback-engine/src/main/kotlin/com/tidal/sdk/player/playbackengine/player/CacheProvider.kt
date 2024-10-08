package com.tidal.sdk.player.playbackengine.player

import androidx.media3.datasource.cache.Cache
import com.tidal.sdk.player.playbackengine.model.ByteAmount
import com.tidal.sdk.player.playbackengine.model.ByteAmount.Companion.gigabytes

/**
 * Decide whether or not we want Player to create and use a [Cache] instance or if we want to
 * provide it externally.
 */
sealed class CacheProvider private constructor() {

    /**
     * Let Player create and use its own [Cache] instance with custom parameters.
     */
    data class Internal(val cacheSizeBytes: ByteAmount = 2.gigabytes) : CacheProvider()

    /**
     * Use an external [Cache] instead of Player creating one of its own.
     * Reason for using this is to reuse a cache instance that is already in use.
     */
    @Suppress("UnsafeOptInUsageError")
    data class External(val cache: Cache) : CacheProvider()
}
