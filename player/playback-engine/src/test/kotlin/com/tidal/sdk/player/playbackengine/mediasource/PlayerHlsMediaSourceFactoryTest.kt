package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import androidx.media3.exoplayer.hls.HlsMediaSource
import assertk.assertThat
import assertk.assertions.isSameAs
import com.tidal.sdk.player.playbackengine.emu.EmuManifest
import com.tidal.sdk.player.playbackengine.emu.EmuManifestFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
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
private const val MIME_TYPE = MimeTypes.APPLICATION_M3U8

internal class PlayerHlsMediaSourceFactoryTest {

    private val hlsMediaSourceFactory = mock<HlsMediaSource.Factory>()
    private val emuManifestFactory = mock<EmuManifestFactory>()
    private val playerHlsMediaSourceFactory = PlayerHlsMediaSourceFactory(
        hlsMediaSourceFactory,
        emuManifestFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        hlsMediaSourceFactory,
        emuManifestFactory,
    )

    @ParameterizedTest
    @MethodSource("emuManifests")
    fun create(emuManifest: EmuManifest) {
        val mediaItem = mock<MediaItem>()
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        val builtMediaItem = mock<MediaItem>()
        val expectedHlsMediaSource = mock<HlsMediaSource>()
        whenever(emuManifestFactory.create(ENCODED_MANIFEST)).thenReturn(emuManifest)
        val mediaItemBuilder = mock<MediaItem.Builder>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(mediaItem.buildUpon()) doReturn mediaItemBuilder
        whenever(mediaItemBuilder.build()) doReturn builtMediaItem
        whenever(hlsMediaSourceFactory.setDrmSessionManagerProvider(drmSessionManagerProvider))
            .thenReturn(hlsMediaSourceFactory)
        whenever(hlsMediaSourceFactory.createMediaSource(builtMediaItem))
            .thenReturn(expectedHlsMediaSource)

        val actualHlsMediaSource = playerHlsMediaSourceFactory.create(
            mediaItem,
            ENCODED_MANIFEST,
            drmSessionManagerProvider,
        )

        assertThat(actualHlsMediaSource).isSameAs(expectedHlsMediaSource)
        verify(emuManifestFactory).create(ENCODED_MANIFEST)
        verify(mediaItem).buildUpon()
        verify(mediaItemBuilder).setUri(emuManifest.urls.firstOrNull())
        verify(mediaItemBuilder).setMimeType(MIME_TYPE)
        verify(mediaItemBuilder).build()
        verify(hlsMediaSourceFactory).setDrmSessionManagerProvider(drmSessionManagerProvider)
        verify(hlsMediaSourceFactory).createMediaSource(builtMediaItem)
        verifyNoMoreInteractions(
            mediaItem,
            builtMediaItem,
            mediaItemBuilder,
            expectedHlsMediaSource,
            drmSessionManagerProvider,
        )
    }

    @Test
    fun createWithUrl() {
        val mediaItem = mock<MediaItem>()
        val url = ""
        val drmSessionManagerProvider = mock<DrmSessionManagerProvider>()
        val builtMediaItem = mock<MediaItem>()
        val expectedHlsMediaSource = mock<HlsMediaSource>()
        val mediaItemBuilder = mock<MediaItem.Builder>(defaultAnswer = Answers.RETURNS_SELF)
        whenever(mediaItem.buildUpon()) doReturn mediaItemBuilder
        whenever(mediaItemBuilder.build()) doReturn builtMediaItem
        whenever(hlsMediaSourceFactory.setDrmSessionManagerProvider(drmSessionManagerProvider))
            .thenReturn(hlsMediaSourceFactory)
        whenever(hlsMediaSourceFactory.createMediaSource(builtMediaItem))
            .thenReturn(expectedHlsMediaSource)

        val actualHlsMediaSource = playerHlsMediaSourceFactory.createWithUrl(
            mediaItem,
            url,
            drmSessionManagerProvider,
        )

        assertThat(actualHlsMediaSource).isSameAs(expectedHlsMediaSource)
        verify(mediaItem).buildUpon()
        verify(mediaItemBuilder).setUri(url)
        verify(mediaItemBuilder).setMimeType(MIME_TYPE)
        verify(mediaItemBuilder).build()
        verify(hlsMediaSourceFactory).setDrmSessionManagerProvider(drmSessionManagerProvider)
        verify(hlsMediaSourceFactory).createMediaSource(builtMediaItem)
        verifyNoMoreInteractions(
            emuManifestFactory,
            mediaItem,
            builtMediaItem,
            mediaItemBuilder,
            expectedHlsMediaSource,
            drmSessionManagerProvider,
        )
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember")
        private fun emuManifests() = setOf(
            Arguments.of(EmuManifest(listOf())),
            Arguments.of(EmuManifest(listOf("url"))),
            Arguments.of(EmuManifest(listOf("url", "url2"))),
        )
    }
}
