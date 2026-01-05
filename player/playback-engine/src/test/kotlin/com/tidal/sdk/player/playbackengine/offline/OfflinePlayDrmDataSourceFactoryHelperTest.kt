package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class OfflinePlayDrmDataSourceFactoryHelperTest {

    private val cacheDataSourceFactory = mock<CacheDataSource.Factory>()
    private val offlineCacheProvider = mock<OfflineCacheProvider>()
    private val offlinePlayDrmDataSourceFactoryHelper =
        OfflinePlayDrmDataSourceFactoryHelper(cacheDataSourceFactory, offlineCacheProvider)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(cacheDataSourceFactory, offlineCacheProvider)

    @Test
    fun create() {
        val cache = mock<Cache>()
        whenever(cacheDataSourceFactory.setCache(cache)).thenReturn(cacheDataSourceFactory)
        whenever(cacheDataSourceFactory.setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR))
            .thenReturn(cacheDataSourceFactory)

        val actualDataSourceFactory = offlinePlayDrmDataSourceFactoryHelper.create(cache)

        assertThat(actualDataSourceFactory).isSameInstanceAs(cacheDataSourceFactory)
        verify(cacheDataSourceFactory).setCache(cache)
        verify(cacheDataSourceFactory).setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        verifyNoMoreInteractions(cache, cacheDataSourceFactory)
    }
}
