package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor.AudioFormat
import androidx.media3.common.audio.BaseAudioProcessor
import java.nio.ByteBuffer
import kotlin.math.roundToLong

/**
 * Applies the crossfade's S-curve to the decoded samples, so the fade is part of the audio itself
 * rather than the output volume. Gains follow the stream time of each sample, which keeps them in
 * step with what's heard however far ahead of playback the samples are processed.
 */
internal class CrossfadeGainProcessor : BaseAudioProcessor() {

    @Volatile private var fade: Fade? = null

    private var pendingAnchorUs: Long? = null
    private var anchorUs = 0L
    private var framesSinceAnchor = 0L

    /** Fades in over the first [durationMs] of the stream. */
    fun fadeIn(durationMs: Long) {
        fade = Fade(0L, durationMs * MICROS_PER_MS, isIn = true)
    }

    /** Fades out over the [durationMs] starting [startMs] into the stream. */
    fun fadeOut(startMs: Long, durationMs: Long) {
        fade = Fade(startMs * MICROS_PER_MS, durationMs * MICROS_PER_MS, isIn = false)
    }

    fun clear() {
        fade = null
    }

    /** Sets the stream time of the next samples queued. */
    fun anchor(timeUs: Long) {
        pendingAnchorUs = timeUs
    }

    override fun onConfigure(inputAudioFormat: AudioFormat): AudioFormat =
        if (inputAudioFormat.encoding in SUPPORTED_ENCODINGS) inputAudioFormat
        else AudioFormat.NOT_SET

    override fun queueInput(inputBuffer: ByteBuffer) {
        val size = inputBuffer.remaining()
        if (size == 0) return
        pendingAnchorUs?.let {
            anchorUs = it
            framesSinceAnchor = 0L
            pendingAnchorUs = null
        }
        val format = inputAudioFormat
        val output = replaceOutputBuffer(size)
        val fade = fade
        val frameCount = size / format.bytesPerFrame
        if (fade == null) {
            output.put(inputBuffer)
        } else {
            val startUs = timeUs()
            repeat(frameCount) { frame ->
                val gain = fade.gainAt(startUs + frame * C.MICROS_PER_SECOND / format.sampleRate)
                repeat(format.channelCount) { scale(inputBuffer, output, format.encoding, gain) }
            }
        }
        framesSinceAnchor += frameCount
        output.flip()
    }

    override fun onFlush() {
        anchorUs = timeUs()
        framesSinceAnchor = 0L
    }

    private fun timeUs(): Long {
        val sampleRate = inputAudioFormat.sampleRate.takeIf { it > 0 } ?: return anchorUs
        return anchorUs + framesSinceAnchor * C.MICROS_PER_SECOND / sampleRate
    }

    private data class Fade(val startUs: Long, val durationUs: Long, val isIn: Boolean) {

        fun gainAt(timeUs: Long): Float {
            val progress =
                ((timeUs - startUs).toFloat() / durationUs.coerceAtLeast(1L)).coerceIn(0f, 1f)
            val fadeIn = progress * progress * (3 - 2 * progress)
            return if (isIn) fadeIn else 1 - fadeIn
        }
    }

    private companion object {
        const val MICROS_PER_MS = 1_000L
        val SUPPORTED_ENCODINGS =
            setOf(
                C.ENCODING_PCM_16BIT,
                C.ENCODING_PCM_24BIT,
                C.ENCODING_PCM_32BIT,
                C.ENCODING_PCM_FLOAT,
            )

        /** Scales one little-endian sample from [input] into [output]. */
        fun scale(input: ByteBuffer, output: ByteBuffer, encoding: Int, gain: Float) {
            when (encoding) {
                C.ENCODING_PCM_16BIT -> {
                    val sample = (input.get().toInt() and 0xFF) or (input.get().toInt() shl 8)
                    output.putLittleEndian((sample * gain).roundToLong(), 2)
                }
                C.ENCODING_PCM_24BIT -> {
                    val sample =
                        (input.get().toInt() and 0xFF) or
                            ((input.get().toInt() and 0xFF) shl 8) or
                            (input.get().toInt() shl 16)
                    output.putLittleEndian((sample * gain).roundToLong(), 3)
                }
                C.ENCODING_PCM_32BIT -> {
                    output.putLittleEndian(
                        (input.getLittleEndianInt() * gain.toDouble()).roundToLong(),
                        4,
                    )
                }
                else -> {
                    val sample = java.lang.Float.intBitsToFloat(input.getLittleEndianInt())
                    output.putLittleEndian(
                        java.lang.Float.floatToRawIntBits(sample * gain).toLong(),
                        4,
                    )
                }
            }
        }

        fun ByteBuffer.getLittleEndianInt(): Int =
            (get().toInt() and 0xFF) or
                ((get().toInt() and 0xFF) shl 8) or
                ((get().toInt() and 0xFF) shl 16) or
                (get().toInt() shl 24)

        fun ByteBuffer.putLittleEndian(value: Long, bytes: Int) {
            repeat(bytes) { put((value shr (it * 8)).toByte()) }
        }
    }
}
