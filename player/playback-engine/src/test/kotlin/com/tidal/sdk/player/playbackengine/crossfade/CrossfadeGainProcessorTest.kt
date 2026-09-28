package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor.AudioFormat
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isFalse
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.junit.jupiter.api.Test

/** One frame per millisecond, so each sample sits at its index in ms. */
private const val SAMPLE_RATE = 1_000

internal class CrossfadeGainProcessorTest {

    private val processor = CrossfadeGainProcessor()

    @Test
    fun passesSamplesThroughWithoutAFade() {
        configure(C.ENCODING_PCM_16BIT)

        assertThat(process(shortArrayOf(1_000, -1_000, 32_767)))
            .containsExactly(1_000, -1_000, 32_767)
    }

    @Test
    fun fadesInWithAnSCurveFromTheStartOfTheStream() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(0L)
        processor.fadeIn(4L)

        assertThat(process(ShortArray(6) { 1_000 })).containsExactly(0, 156, 500, 844, 1_000, 1_000)
    }

    @Test
    fun fadesOutWithAnSCurveFromTheAnchoredStreamTime() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(10_000L)
        processor.fadeOut(startMs = 12L, durationMs = 4L)

        assertThat(process(ShortArray(7) { -1_000 }))
            .containsExactly(-1_000, -1_000, -1_000, -844, -500, -156, 0)
    }

    @Test
    fun keepsCountingStreamTimeAcrossBuffers() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(0L)
        processor.fadeIn(4L)
        process(ShortArray(2) { 1_000 })

        assertThat(process(ShortArray(2) { 1_000 })).containsExactly(500, 844)
    }

    @Test
    fun appliesTheGainToFloatSamples() {
        configure(C.ENCODING_PCM_FLOAT)
        processor.anchor(2_000L)
        processor.fadeIn(4L)
        val input = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder())
        input.asFloatBuffer().put(floatArrayOf(0.5f, -0.5f))

        val output = processed(input).asFloatBuffer()

        assertThat(FloatArray(output.remaining()) { output.get(it) }.toList())
            .containsExactly(0.25f, -0.421875f)
    }

    @Test
    fun clearStopsTheFade() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(0L)
        processor.fadeIn(4L)
        processor.clear()

        assertThat(process(shortArrayOf(1_000))).containsExactly(1_000)
    }

    @Test
    fun isInactiveForEncodedAudio() {
        processor.configure(AudioFormat(SAMPLE_RATE, 2, C.ENCODING_AC3))

        assertThat(processor.isActive).isFalse()
    }

    private fun configure(encoding: Int) {
        processor.configure(AudioFormat(SAMPLE_RATE, 1, encoding))
        processor.flush()
    }

    private fun process(samples: ShortArray): List<Int> {
        val input = ByteBuffer.allocateDirect(samples.size * 2).order(ByteOrder.nativeOrder())
        input.asShortBuffer().put(samples)
        val output = processed(input).asShortBuffer()
        return List(output.remaining()) { output.get(it).toInt() }
    }

    private fun processed(input: ByteBuffer): ByteBuffer {
        processor.queueInput(input)
        return processor.output.order(ByteOrder.nativeOrder())
    }
}
