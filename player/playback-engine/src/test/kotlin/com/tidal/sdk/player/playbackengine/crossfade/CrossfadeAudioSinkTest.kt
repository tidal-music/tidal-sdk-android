package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor.AudioFormat
import androidx.media3.exoplayer.audio.AudioSink
import assertk.assertThat
import assertk.assertions.containsExactly
import java.nio.ByteBuffer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

private const val FADE_START_US = 12_000L

internal class CrossfadeAudioSinkTest {

    private val processor = CrossfadeGainProcessor()
    private val sink = CrossfadeAudioSink(mock<AudioSink>(), processor)

    @BeforeEach
    fun beforeEach() {
        processor.configure(AudioFormat(SAMPLE_RATE, 1, C.ENCODING_PCM_16BIT))
        processor.flush()
        processor.fadeOut(startMs = FADE_START_US / 1_000, durationMs = 4L)
    }

    @Test
    fun anchorsTheFirstBufferAtItsStreamTime() {
        sink.setOutputStreamOffsetUs(1_000_000L)

        queueAt(1_000_000L + FADE_START_US)

        assertThat(processor.process(ShortArray(2) { 1_000 })).containsExactly(1_000, 844)
    }

    @Test
    fun reanchorsAfterAFlush() {
        queueAt(FADE_START_US)
        processor.process(ShortArray(4) { 1_000 })

        sink.flush()
        queueAt(0L)

        assertThat(processor.process(shortArrayOf(1_000))).containsExactly(1_000)
    }

    @Test
    fun reanchorsWhenTheNextItemStartsOnTheSamePlayer() {
        queueAt(FADE_START_US)
        processor.process(ShortArray(4) { 1_000 })

        sink.setOutputStreamOffsetUs(FADE_START_US + 4_000L)
        queueAt(FADE_START_US + 4_000L)

        assertThat(processor.process(shortArrayOf(1_000))).containsExactly(1_000)
    }

    private fun queueAt(presentationTimeUs: Long) {
        sink.handleBuffer(ByteBuffer.allocate(0), presentationTimeUs, 1)
    }
}
