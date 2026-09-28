package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor.AudioFormat
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.junit.jupiter.api.Test

private const val RAMP_UP_MS = 250

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
    fun appliesTheSameGainToEveryChannelOfAFrame() {
        configure(C.ENCODING_PCM_16BIT, channelCount = 2)
        processor.anchor(0L)
        processor.fadeIn(4L)

        assertThat(process(shortArrayOf(1_000, -500, 1_000, -500, 1_000, -500, 1_000, -500)))
            .containsExactly(0, 0, 156, -78, 500, -250, 844, -422)
    }

    @Test
    fun clearBeforeTheFadeOutStartsStopsItAtOnce() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(0L)
        processor.fadeOut(startMs = 12L, durationMs = 4L)
        processor.clear()

        assertThat(process(ShortArray(16) { 1_000 }).last()).isEqualTo(1_000)
    }

    @Test
    fun clearDuringTheFadeRampsBackUpFromWhereItGotTo() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(0L)
        processor.fadeIn(4L)
        process(ShortArray(2) { 1_000 })

        processor.clear()

        val ramp = process(ShortArray(RAMP_UP_MS + 1) { 1_000 })
        assertThat(ramp.first()).isEqualTo(500)
        assertThat(ramp.zipWithNext().all { (previous, next) -> next >= previous }).isTrue()
        assertThat(ramp.last()).isEqualTo(1_000)
    }

    @Test
    fun aNewAnchorDropsARampUp() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(0L)
        processor.fadeIn(4L)
        process(ShortArray(2) { 1_000 })
        processor.clear()

        processor.anchor(0L)

        assertThat(process(shortArrayOf(1_000))).containsExactly(1_000)
    }

    @Test
    fun aFlushDropsARampUp() {
        configure(C.ENCODING_PCM_16BIT)
        processor.anchor(0L)
        processor.fadeIn(4L)
        process(ShortArray(2) { 1_000 })
        processor.clear()

        processor.flush()

        assertThat(process(shortArrayOf(1_000))).containsExactly(1_000)
    }

    @Test
    fun isInactiveForFloatAudio() {
        processor.configure(AudioFormat(SAMPLE_RATE, 2, C.ENCODING_PCM_FLOAT))

        assertThat(processor.isActive).isFalse()
    }

    @Test
    fun isInactiveForEncodedAudio() {
        processor.configure(AudioFormat(SAMPLE_RATE, 2, C.ENCODING_AC3))

        assertThat(processor.isActive).isFalse()
    }

    private fun configure(encoding: Int, channelCount: Int = 1) {
        processor.configure(AudioFormat(SAMPLE_RATE, channelCount, encoding))
        processor.flush()
    }

    private fun process(samples: ShortArray) = processor.process(samples)
}

/** One frame per millisecond, so each sample sits at its index in ms. */
internal const val SAMPLE_RATE = 1_000

/** Queues mono or interleaved 16-bit [samples] and returns what comes out. */
internal fun CrossfadeGainProcessor.process(samples: ShortArray): List<Int> {
    val input = ByteBuffer.allocateDirect(samples.size * 2).order(ByteOrder.nativeOrder())
    input.asShortBuffer().put(samples)
    queueInput(input)
    val output = output.order(ByteOrder.nativeOrder()).asShortBuffer()
    return List(output.remaining()) { output.get(it).toInt() }
}
