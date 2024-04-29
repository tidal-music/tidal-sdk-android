package com.tidal.sdk.player.playbackengine.cache

import android.net.Uri
import androidx.media3.datasource.DataSpec
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions

internal class DefaultCacheKeyFactoryTest {

    private val cacheKeyFactory = DefaultCacheKeyFactory()

    @Test
    fun buildCacheKeyFromDataSpecKey() {
        val key = "123abc"
        val path = "/path/to/uri"
        val uri = mock<Uri> {
            on { this.path } doReturn path
        }
        val dataSpec = spy(DataSpec.Builder().setKey(key).setUri(uri).build())

        val actual = cacheKeyFactory.buildCacheKey(dataSpec)

        verify(uri).path
        verifyNoMoreInteractions(uri)
        verifyNoInteractions(dataSpec)
        assertThat(actual).isEqualTo(path)
    }

    @Test
    fun buildCacheKeyFromUriPath() {
        val path = "/path/to/uri"
        val uri = mock<Uri> {
            on { this.path } doReturn path
        }
        val dataSpec = spy(DataSpec.Builder().setUri(uri).build())

        val actual = cacheKeyFactory.buildCacheKey(dataSpec)

        verify(uri).path
        verifyNoMoreInteractions(uri)
        verifyNoInteractions(dataSpec)
        assertThat(actual).isEqualTo(path)
    }

    @Test
    fun buildCacheKeyFromUriPathWithNullPath() {
        val uri = mock<Uri> {
            on { this.path } doReturn null
        }
        val dataSpec = spy(DataSpec.Builder().setUri(uri).build())

        val actual = cacheKeyFactory.buildCacheKey(dataSpec)

        verify(uri).path
        verifyNoMoreInteractions(uri)
        verifyNoInteractions(dataSpec)
        assertThat(actual).isEqualTo("null")
    }
}
