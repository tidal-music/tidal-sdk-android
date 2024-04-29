package com.tidal.sdk.player.playbackengine.player.renderer

import android.os.Handler
import androidx.media3.decoder.flac.LibflacAudioRenderer
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.video.MediaCodecVideoRenderer
import androidx.media3.exoplayer.video.VideoRendererEventListener
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.playbackengine.player.renderer.audio.fallback.FallbackAudioRendererFactory
import com.tidal.sdk.player.playbackengine.player.renderer.audio.flac.LibflacAudioRendererFactory
import com.tidal.sdk.player.playbackengine.player.renderer.video.MediaCodecVideoRendererFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class PlayerRenderersFactoryTest {

    private val mediaCodecVideoRendererFactory = mock<MediaCodecVideoRendererFactory>()
    private val libflacAudioRendererFactory = mock<LibflacAudioRendererFactory>()
    private val fallbackAudioRendererFactory = mock<FallbackAudioRendererFactory>()
    private val playerRenderersFactory = PlayerRenderersFactory(
        mediaCodecVideoRendererFactory,
        libflacAudioRendererFactory,
        fallbackAudioRendererFactory,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        mediaCodecVideoRendererFactory,
        libflacAudioRendererFactory,
        fallbackAudioRendererFactory,
    )

    @Test
    fun createRenderersIncludesExpectedRenderers() =
        testForRenderers(mock(), mock(), mock())

    @Test
    fun createRenderersIncludesExpectedRenderersWhenLibflacAudioRendererFactoryReturnsNull() =
        testForRenderers(mock(), null, mock())

    @Suppress("LongMethod")
    private fun testForRenderers(
        mediaCodecVideoRenderer: MediaCodecVideoRenderer,
        libflacAudioRenderer: LibflacAudioRenderer?,
        fallbackAudioRenderer: MediaCodecAudioRenderer,
    ) {
        val eventHandler = mock<Handler>()
        val videoRendererEventListener = mock<VideoRendererEventListener>()
        val audioRendererEventListener = mock<AudioRendererEventListener>()
        whenever(mediaCodecVideoRendererFactory.create(eventHandler, videoRendererEventListener))
            .thenReturn(mediaCodecVideoRenderer)
        whenever(libflacAudioRendererFactory.create(eventHandler, audioRendererEventListener))
            .thenReturn(libflacAudioRenderer)
        whenever(fallbackAudioRendererFactory.create(eventHandler, audioRendererEventListener))
            .thenReturn(fallbackAudioRenderer)

        val actual = playerRenderersFactory.createRenderers(
            eventHandler,
            videoRendererEventListener,
            audioRendererEventListener,
            mock(),
            mock(),
        )

        assertThat(actual).isEqualTo(
            arrayOf(
                mediaCodecVideoRenderer,
                libflacAudioRenderer,
                fallbackAudioRenderer,
            ).filterNotNull()
                .toTypedArray(),
        )
        verify(mediaCodecVideoRendererFactory).create(eventHandler, videoRendererEventListener)
        verify(libflacAudioRendererFactory).create(eventHandler, audioRendererEventListener)
        verify(fallbackAudioRendererFactory).create(eventHandler, audioRendererEventListener)
        verifyNoInteractions(
            eventHandler,
            videoRendererEventListener,
            audioRendererEventListener,
            mediaCodecVideoRenderer,
            fallbackAudioRenderer,
        )
        libflacAudioRenderer?.also { verifyNoInteractions(it) }
    }
}
