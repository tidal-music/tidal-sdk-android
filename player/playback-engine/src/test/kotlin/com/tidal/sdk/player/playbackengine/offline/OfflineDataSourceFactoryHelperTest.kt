package com.tidal.sdk.player.playbackengine.offline

import androidx.media3.datasource.DataSource
import androidx.media3.datasource.cache.Cache
import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import com.tidal.sdk.player.playbackengine.offline.cache.OfflineCacheProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class OfflineDataSourceFactoryHelperTest {

    private val offlineCacheProvider = mock<OfflineCacheProvider>()
    private val helper = TestOfflineDataSourceFactoryHelper(offlineCacheProvider)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(offlineCacheProvider)

    @Test
    fun getExternalMemoizesPerPath() {
        val cacheA = mock<Cache>()
        val cacheB = mock<Cache>()
        whenever(offlineCacheProvider.getExternal("a")).thenReturn(cacheA)
        whenever(offlineCacheProvider.getExternal("b")).thenReturn(cacheB)

        val firstA = helper.getExternal("a")
        val secondA = helper.getExternal("a")
        val firstB = helper.getExternal("b")
        val secondB = helper.getExternal("b")

        assertThat(secondA).isSameInstanceAs(firstA)
        assertThat(secondB).isSameInstanceAs(firstB)
        assertThat(firstA.cache).isSameInstanceAs(cacheA)
        assertThat(firstB.cache).isSameInstanceAs(cacheB)
        verify(offlineCacheProvider).getExternal("a")
        verify(offlineCacheProvider).getExternal("b")
    }

    @Test
    fun getInternalMemoizesPerPath() {
        val cacheA = mock<Cache>()
        val cacheB = mock<Cache>()
        whenever(offlineCacheProvider.getInternal("a")).thenReturn(cacheA)
        whenever(offlineCacheProvider.getInternal("b")).thenReturn(cacheB)

        val firstA = helper.getInternal("a")
        val secondA = helper.getInternal("a")
        val firstB = helper.getInternal("b")
        val secondB = helper.getInternal("b")

        assertThat(secondA).isSameInstanceAs(firstA)
        assertThat(secondB).isSameInstanceAs(firstB)
        assertThat(firstA.cache).isSameInstanceAs(cacheA)
        assertThat(firstB.cache).isSameInstanceAs(cacheB)
        verify(offlineCacheProvider).getInternal("a")
        verify(offlineCacheProvider).getInternal("b")
    }

    @Test
    fun internalAndExternalMapsAreIndependent() {
        val externalCache = mock<Cache>()
        val internalCache = mock<Cache>()
        whenever(offlineCacheProvider.getExternal("p")).thenReturn(externalCache)
        whenever(offlineCacheProvider.getInternal("p")).thenReturn(internalCache)

        val external = helper.getExternal("p")
        val internal = helper.getInternal("p")

        assertThat(external.cache).isSameInstanceAs(externalCache)
        assertThat(internal.cache).isSameInstanceAs(internalCache)
        verify(offlineCacheProvider).getExternal("p")
        verify(offlineCacheProvider).getInternal("p")
    }

    @Test
    fun getExternalThrowsWhenProviderIsNull() {
        val helperWithoutProvider = TestOfflineDataSourceFactoryHelper(null)

        assertThrows<IllegalStateException> { helperWithoutProvider.getExternal("a") }
    }

    @Test
    fun getInternalThrowsWhenProviderIsNull() {
        val helperWithoutProvider = TestOfflineDataSourceFactoryHelper(null)

        assertThrows<IllegalStateException> { helperWithoutProvider.getInternal("a") }
    }

    private class TestFactory(val cache: Cache) : DataSource.Factory {
        override fun createDataSource(): DataSource = mock()
    }

    private class TestOfflineDataSourceFactoryHelper(offlineCacheProvider: OfflineCacheProvider?) :
        OfflineDataSourceFactoryHelper<TestFactory>(offlineCacheProvider) {
        override fun create(cache: Cache): TestFactory = TestFactory(cache)
    }
}
