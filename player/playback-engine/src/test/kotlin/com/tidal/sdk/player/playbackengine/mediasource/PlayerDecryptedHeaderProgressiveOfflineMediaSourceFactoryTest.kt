package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.Encryption
import com.tidal.sdk.player.playbackengine.bts.BtsManifest
import com.tidal.sdk.player.playbackengine.bts.BtsManifestFactory
import com.tidal.sdk.player.playbackengine.datasource.DecryptedHeaderFileDataSourceFactory
import com.tidal.sdk.player.playbackengine.datasource.DecryptedHeaderFileDataSourceFactoryFactory
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

internal class PlayerDecryptedHeaderProgressiveOfflineMediaSourceFactoryTest {

    private val progressiveMediaSourceFactoryFactory = mock<ProgressiveMediaSourceFactoryFactory>()
    private val decryptedHeaderFileDataSourceFactoryFactory =
        mock<DecryptedHeaderFileDataSourceFactoryFactory>()
    private val encryption = mock<Encryption>()
    private val btsManifestFactory = mock<BtsManifestFactory>()
    private val playerDecryptedHeaderProgressiveOfflineMediaSourceFactory =
        PlayerDecryptedHeaderProgressiveOfflineMediaSourceFactory(
            progressiveMediaSourceFactoryFactory,
            decryptedHeaderFileDataSourceFactoryFactory,
            encryption,
            btsManifestFactory,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            progressiveMediaSourceFactoryFactory,
            decryptedHeaderFileDataSourceFactoryFactory,
            encryption,
            btsManifestFactory,
        )

    @ParameterizedTest
    @MethodSource("btsManifests")
    fun create(btsManifest: BtsManifest) {
        val mediaItem = mock<MediaItem>()
        val productId = "123"
        val builtMediaItem = mock<MediaItem>()
        val expectedProgressiveMediaSource = mock<ProgressiveMediaSource>()
        val progressiveMediaSourceFactory =
            mock<ProgressiveMediaSource.Factory> {
                on { createMediaSource(builtMediaItem) } doReturn expectedProgressiveMediaSource
            }
        val decryptedHeaderFileDataSourceFactory = mock<DecryptedHeaderFileDataSourceFactory>()
        val decryptedHeader = "decryptedHeader".toByteArray()
        whenever(btsManifestFactory.create(ENCODED_MANIFEST)).thenReturn(btsManifest)
        val mediaItemBuilder = mock<MediaItem.Builder>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(mediaItem.buildUpon()) doReturn mediaItemBuilder
        whenever(mediaItemBuilder.build()) doReturn builtMediaItem
        whenever(encryption.getDecryptedHeader(productId)).thenReturn(decryptedHeader)
        whenever(decryptedHeaderFileDataSourceFactoryFactory.create(decryptedHeader))
            .thenReturn(decryptedHeaderFileDataSourceFactory)
        whenever(progressiveMediaSourceFactoryFactory.create(decryptedHeaderFileDataSourceFactory))
            .thenReturn(progressiveMediaSourceFactory)

        val actualProgressiveMediaSource =
            playerDecryptedHeaderProgressiveOfflineMediaSourceFactory.create(
                mediaItem,
                ENCODED_MANIFEST,
                productId,
            )

        assertThat(actualProgressiveMediaSource).isSameAs(expectedProgressiveMediaSource)
        verify(btsManifestFactory).create(ENCODED_MANIFEST)
        verify(mediaItem).buildUpon()
        verify(mediaItemBuilder).setUri(btsManifest.urls.firstOrNull())
        verify(mediaItemBuilder).build()
        verify(encryption).getDecryptedHeader(productId)
        verify(decryptedHeaderFileDataSourceFactoryFactory).create(decryptedHeader)
        verify(progressiveMediaSourceFactoryFactory).create(decryptedHeaderFileDataSourceFactory)
        verify(progressiveMediaSourceFactory).createMediaSource(builtMediaItem)
        verifyNoMoreInteractions(
            mediaItem,
            mediaItemBuilder,
            builtMediaItem,
            expectedProgressiveMediaSource,
            progressiveMediaSourceFactory,
            decryptedHeaderFileDataSourceFactory,
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
