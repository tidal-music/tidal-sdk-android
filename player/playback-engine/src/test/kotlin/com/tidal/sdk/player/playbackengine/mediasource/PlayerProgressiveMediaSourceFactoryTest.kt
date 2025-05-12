package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.bts.BtsManifest
import com.tidal.sdk.player.playbackengine.bts.BtsManifestFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Answers
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

private const val ENCODED_MANIFEST = "encodedManifest"

internal class PlayerProgressiveMediaSourceFactoryTest {

    private val progressiveMediaSourceFactoryFactory = mock<ProgressiveMediaSourceFactoryFactory>()
    private val cacheDataSourceFactory = mock<CacheDataSource.Factory>()
    private val btsManifestFactory = mock<BtsManifestFactory>()
    private val playerProgressiveMediaSourceFactory =
        PlayerProgressiveMediaSourceFactory(
            progressiveMediaSourceFactoryFactory,
            cacheDataSourceFactory,
            btsManifestFactory,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            progressiveMediaSourceFactoryFactory,
            cacheDataSourceFactory,
            btsManifestFactory,
        )

    @ParameterizedTest
    @MethodSource("btsManifests")
    fun create(btsManifest: BtsManifest) {
        val mediaItem = mock<MediaItem>()
        val builtMediaItem = mock<MediaItem>()
        val expectedProgressiveMediaSource = mock<ProgressiveMediaSource>()
        val progressiveMediaSourceFactory =
            mock<ProgressiveMediaSource.Factory> {
                on { createMediaSource(builtMediaItem) } doReturn expectedProgressiveMediaSource
            }
        whenever(btsManifestFactory.create(ENCODED_MANIFEST)).thenReturn(btsManifest)
        val mediaItemBuilder = mock<MediaItem.Builder>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(mediaItem.buildUpon()) doReturn mediaItemBuilder
        whenever(mediaItemBuilder.build()) doReturn builtMediaItem
        whenever(progressiveMediaSourceFactoryFactory.create(cacheDataSourceFactory))
            .thenReturn(progressiveMediaSourceFactory)

        val actualProgressiveMediaSource =
            playerProgressiveMediaSourceFactory.create(mediaItem, ENCODED_MANIFEST)

        assertThat(actualProgressiveMediaSource).isSameAs(expectedProgressiveMediaSource)
        verify(btsManifestFactory).create(ENCODED_MANIFEST)
        verify(mediaItem).buildUpon()
        verify(mediaItemBuilder).setUri(btsManifest.urls.firstOrNull())
        verify(mediaItemBuilder).build()
        verify(progressiveMediaSourceFactoryFactory).create(cacheDataSourceFactory)
        verify(progressiveMediaSourceFactory).createMediaSource(builtMediaItem)
        verifyNoMoreInteractions(
            mediaItem,
            builtMediaItem,
            mediaItemBuilder,
            expectedProgressiveMediaSource,
            progressiveMediaSourceFactory,
        )
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun btsManifests() =
            setOf(
                Arguments.of(BtsManifest("", listOf())),
                Arguments.of(BtsManifest("", listOf("url"))),
                Arguments.of(BtsManifest("", listOf("url", "url2"))),
                Arguments.of(BtsManifest("codecs", listOf())),
                Arguments.of(BtsManifest("codecs", listOf("url"))),
                Arguments.of(BtsManifest("codecs", listOf("url", "url2"))),
                Arguments.of(BtsManifest("codecs,codecs", listOf())),
                Arguments.of(BtsManifest("codecs,codecs", listOf("url"))),
                Arguments.of(BtsManifest("codecs,codecs", listOf("url", "url2"))),
            )
    }
}
