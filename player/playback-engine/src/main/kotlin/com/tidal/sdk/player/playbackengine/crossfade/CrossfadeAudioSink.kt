package com.tidal.sdk.player.playbackengine.crossfade

import androidx.media3.common.Format
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.audio.ForwardingAudioSink
import java.nio.ByteBuffer

/** Tells [gainProcessor] the stream time of the samples reaching it after each discontinuity. */
internal class CrossfadeAudioSink(
    sink: AudioSink,
    private val gainProcessor: CrossfadeGainProcessor,
) : ForwardingAudioSink(sink) {

    private var outputStreamOffsetUs = 0L
    private var needsAnchor = true

    override fun configure(
        inputFormat: Format,
        specifiedBufferSize: Int,
        outputChannels: IntArray?,
    ) {
        needsAnchor = true
        super.configure(inputFormat, specifiedBufferSize, outputChannels)
    }

    override fun setOutputStreamOffsetUs(outputStreamOffsetUs: Long) {
        this.outputStreamOffsetUs = outputStreamOffsetUs
        needsAnchor = true
        super.setOutputStreamOffsetUs(outputStreamOffsetUs)
    }

    override fun handleBuffer(
        buffer: ByteBuffer,
        presentationTimeUs: Long,
        encodedAccessUnitCount: Int,
    ): Boolean {
        if (needsAnchor) {
            gainProcessor.anchor(presentationTimeUs - outputStreamOffsetUs)
            needsAnchor = false
        }
        return super.handleBuffer(buffer, presentationTimeUs, encodedAccessUnitCount)
    }

    override fun flush() {
        needsAnchor = true
        super.flush()
    }
}
