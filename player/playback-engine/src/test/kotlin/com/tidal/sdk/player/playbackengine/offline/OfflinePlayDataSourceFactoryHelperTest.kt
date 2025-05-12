package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.cache.Cache
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.datasource.CacheKeyAesCipherDataSourceFactoryFactory
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider
import com.tidal.sdk.player.playbackengine.offline.crypto.CacheKeyAesCipherDataSourceFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class OfflinePlayDataSourceFactoryHelperTest {

    private val cacheKeyAesCipherDataSourceFactoryFactory =
        mock<CacheKeyAesCipherDataSourceFactoryFactory>()
    private val offlineCacheProvider = mock<OfflineCacheProvider>()
    private val offlinePlayDataSourceFactoryHelper =
        OfflinePlayDataSourceFactoryHelper(
            cacheKeyAesCipherDataSourceFactoryFactory,
            offlineCacheProvider,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(cacheKeyAesCipherDataSourceFactoryFactory, offlineCacheProvider)

    @Test
    fun create() {
        val cache = mock<Cache>()
        val cacheKeyAesCipherDataSourceFactory = mock<CacheKeyAesCipherDataSourceFactory>()
        whenever(cacheKeyAesCipherDataSourceFactoryFactory.create(cache))
            .thenReturn(cacheKeyAesCipherDataSourceFactory)

        val actualDataSourceFactory = offlinePlayDataSourceFactoryHelper.create(cache)

        assertThat(actualDataSourceFactory).isSameAs(cacheKeyAesCipherDataSourceFactory)
        verify(cacheKeyAesCipherDataSourceFactoryFactory).create(cache)
        verifyNoMoreInteractions(cache, cacheKeyAesCipherDataSourceFactory)
    }
}
