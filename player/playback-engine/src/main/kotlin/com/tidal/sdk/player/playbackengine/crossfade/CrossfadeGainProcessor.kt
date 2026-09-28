package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor.AudioFormat
import androidx.media3.common.audio.BaseAudioProcessor
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.roundToInt

/**
 * Applies the crossfade's S-curve to the decoded samples, so the fade is part of the audio itself
 * rather than the output volume. Gains follow the stream time of each sample, which keeps them in
 * step with what's heard however far ahead of playback the samples are processed.
 *
 * Media3 only hands custom processors 16-bit PCM, and skips them entirely with float output on.
 */
internal class CrossfadeGainProcessor : BaseAudioProcessor() {

    private val fade = AtomicReference<Fade?>(null)

    /** The stream time of the next sample to process. */
    @Volatile private var nextSampleUs = 0L

    private var pendingAnchorUs: Long? = null
    private var anchorUs = 0L
    private var framesSinceAnchor = 0L

    /** Fades in over the first [durationMs] of the stream. */
    fun fadeIn(durationMs: Long) {
        fade.set(Fade(0L, durationMs * MICROS_PER_MS, from = 0f, to = 1f))
    }

    /** Fades out over the [durationMs] starting [startMs] into the stream. */
    fun fadeOut(startMs: Long, durationMs: Long) {
        fade.set(Fade(startMs * MICROS_PER_MS, durationMs * MICROS_PER_MS, from = 1f, to = 0f))
    }

    /**
     * Returns to full gain. Samples already processed keep the fade, so this ramps up from where it
     * had got to rather than jumping.
     */
    fun clear() {
        val current = fade.get() ?: return
        val startUs = nextSampleUs
        val gain = current.gainAt(startUs)
        val ramp =
            if (gain < 1f) Fade(startUs, RAMP_UP_US, from = gain, to = 1f, isRampUp = true)
            else null
        fade.compareAndSet(current, ramp)
    }

    /** Sets the stream time of the next samples queued. */
    fun anchor(timeUs: Long) {
        pendingAnchorUs = timeUs
    }

    override fun onConfigure(inputAudioFormat: AudioFormat): AudioFormat =
        if (inputAudioFormat.encoding == C.ENCODING_PCM_16BIT) inputAudioFormat
        else AudioFormat.NOT_SET

    override fun queueInput(inputBuffer: ByteBuffer) {
        val size = inputBuffer.remaining()
        if (size == 0) return
        pendingAnchorUs?.let {
            anchorUs = it
            framesSinceAnchor = 0L
            pendingAnchorUs = null
            // Stream time restarts, and a ramp-up is only for the samples before it.
            dropRampUp()
        }
        val format = inputAudioFormat
        val output = replaceOutputBuffer(size)
        val fade = fade.get()
        val frameCount = size / format.bytesPerFrame
        if (fade == null) {
            output.put(inputBuffer)
        } else {
            val startUs = timeUs()
            repeat(frameCount) { frame ->
                val gain = fade.gainAt(startUs + frame * C.MICROS_PER_SECOND / format.sampleRate)
                repeat(format.channelCount) {
                    output.putShort((inputBuffer.getShort() * gain).roundToInt().toShort())
                }
            }
        }
        framesSinceAnchor += frameCount
        nextSampleUs = timeUs()
        output.flip()
    }

    override fun onFlush() {
        anchorUs = timeUs()
        framesSinceAnchor = 0L
        nextSampleUs = anchorUs
        // The faded samples a ramp-up recovers from are gone.
        dropRampUp()
    }

    private fun dropRampUp() {
        val current = fade.get() ?: return
        if (current.isRampUp) fade.compareAndSet(current, null)
    }

    private fun timeUs(): Long {
        val sampleRate = inputAudioFormat.sampleRate.takeIf { it > 0 } ?: return anchorUs
        return anchorUs + framesSinceAnchor * C.MICROS_PER_SECOND / sampleRate
    }

    private data class Fade(
        val startUs: Long,
        val durationUs: Long,
        val from: Float,
        val to: Float,
        val isRampUp: Boolean = false,
    ) {

        fun gainAt(timeUs: Long): Float {
            val progress =
                ((timeUs - startUs).toFloat() / durationUs.coerceAtLeast(1L)).coerceIn(0f, 1f)
            return from + (to - from) * progress * progress * (3 - 2 * progress)
        }
    }

    private companion object {
        const val MICROS_PER_MS = 1_000L
        const val RAMP_UP_US = 250_000L
    }
}
