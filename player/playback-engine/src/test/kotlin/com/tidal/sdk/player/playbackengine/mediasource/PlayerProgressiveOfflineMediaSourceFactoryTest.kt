package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.bts.BtsManifest
import com.tidal.sdk.player.playbackengine.bts.BtsManifestFactory
import com.tidal.sdk.player.playbackengine.offline.OfflineStorageProvider
import com.tidal.sdk.player.streamingapi.offline.Storage
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

internal class PlayerProgressiveOfflineMediaSourceFactoryTest {

    private val progressiveMediaSourceFactoryFactory = mock<ProgressiveMediaSourceFactoryFactory>()
    private val btsManifestFactory = mock<BtsManifestFactory>()
    private val offlineStorageProvider = mock<OfflineStorageProvider>()
    private val playerProgressiveOfflineMediaSourceFactory =
        PlayerProgressiveOfflineMediaSourceFactory(
            progressiveMediaSourceFactoryFactory,
            btsManifestFactory,
            offlineStorageProvider,
        )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        progressiveMediaSourceFactoryFactory,
        btsManifestFactory,
        offlineStorageProvider,
    )

    @ParameterizedTest
    @MethodSource("btsManifests")
    fun create(btsManifest: BtsManifest) {
        val mediaItem = mock<MediaItem>()
        val storage = mock<Storage>()
        val builtMediaItem = mock<MediaItem>()
        val expectedProgressiveMediaSource = mock<ProgressiveMediaSource>()
        val progressiveMediaSourceFactory = mock<ProgressiveMediaSource.Factory> {
            on { createMediaSource(builtMediaItem) } doReturn expectedProgressiveMediaSource
        }
        val dataSourceFactoryForOfflinePlay = mock<DataSource.Factory>()
        whenever(btsManifestFactory.create(ENCODED_MANIFEST)).thenReturn(btsManifest)
        val mediaItemBuilder = mock<MediaItem.Builder>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(mediaItem.buildUpon()) doReturn mediaItemBuilder
        whenever(mediaItemBuilder.build()) doReturn builtMediaItem
        whenever(offlineStorageProvider.getDataSourceFactoryForOfflinePlay(storage))
            .thenReturn(dataSourceFactoryForOfflinePlay)
        whenever(progressiveMediaSourceFactoryFactory.create(dataSourceFactoryForOfflinePlay))
            .thenReturn(progressiveMediaSourceFactory)

        val actualProgressiveMediaSource = playerProgressiveOfflineMediaSourceFactory.create(
            mediaItem,
            ENCODED_MANIFEST,
            storage,
        )

        assertThat(actualProgressiveMediaSource).isSameAs(expectedProgressiveMediaSource)
        verify(btsManifestFactory).create(ENCODED_MANIFEST)
        verify(mediaItem).buildUpon()
        verify(mediaItemBuilder).setUri(btsManifest.urls.firstOrNull())
        verify(mediaItemBuilder).build()
        verify(offlineStorageProvider).getDataSourceFactoryForOfflinePlay(storage)
        verify(progressiveMediaSourceFactoryFactory).create(dataSourceFactoryForOfflinePlay)
        verify(progressiveMediaSourceFactory).createMediaSource(builtMediaItem)
        verifyNoMoreInteractions(
            mediaItem,
            builtMediaItem,
            mediaItemBuilder,
            expectedProgressiveMediaSource,
            progressiveMediaSourceFactory,
            dataSourceFactoryForOfflinePlay,
        )
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun btsManifests() = setOf(
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
