package com.tidal.sdk.player.playbackengine.mediasource

import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider
import androidx.media3.exoplayer.hls.HlsMediaSource
import assertk.assertThat
import assertk.assertions.isSameInstanceAs
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

private const val MIME_TYPE = MimeTypes.APPLICATION_M3U8

internal class PlayerAuthHlsMediaSourceFactoryTest {

    private val hlsMediaSourceFactory = mock<HlsMediaSource.Factory>()
    private val playerAuthHlsMediaSourceFactory =
        PlayerAuthHlsMediaSourceFactory(hlsMediaSourceFactory)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(hlsMediaSourceFactory)

    @Test
    fun create() {
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

        val actualHlsMediaSource =
            playerAuthHlsMediaSourceFactory.create(mediaItem, url, drmSessionManagerProvider)

        assertThat(actualHlsMediaSource).isSameInstanceAs(expectedHlsMediaSource)
        verify(mediaItem).buildUpon()
        verify(mediaItemBuilder).setUri(url)
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
}
