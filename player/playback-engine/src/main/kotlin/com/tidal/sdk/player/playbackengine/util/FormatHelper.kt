package com.tidal.sdk.player.playbackengine.util

import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.MimeTypes

/** Helper object for extracting audio format information from Media3 Format objects. */
object FormatHelper {

    /** Audio format information extracted from Format and initializationData. */
    data class AudioFormatInfo(val sampleRate: Int?, val bitDepth: Int?, val bitRate: Int?)

    /**
     * Extracts sample rate, bit depth, and bit rate from the Format's initializationData. Falls
     * back to format.sampleRate and format.pcmEncoding if initializationData parsing fails. Bit
     * rate is taken from format.bitrate as it's not stored in initializationData.
     */
    @Suppress("MagicNumber")
    fun extractAudioFormatInfo(format: Format): AudioFormatInfo {
        val initData = format.initializationData
        val mimeType = format.sampleMimeType
        val bitRate = format.bitrate.takeIf { it != Format.NO_VALUE }

        // Try to parse from initializationData based on codec type
        if (initData.isNotEmpty()) {
            when (mimeType) {
                MimeTypes.AUDIO_FLAC -> {
                    val parsed = parseFlacStreamInfo(initData[0])
                    if (parsed != null) {
                        return AudioFormatInfo(parsed.first, parsed.second, bitRate)
                    }
                }
                MimeTypes.AUDIO_AAC -> {
                    val parsed = parseAacAudioSpecificConfig(initData[0])
                    if (parsed != null) {
                        return AudioFormatInfo(parsed.first, parsed.second, bitRate)
                    }
                }
            }
        }

        // Fallback to Format fields
        val sampleRate = format.sampleRate.takeIf { it != Format.NO_VALUE }
        val bitDepth =
            when (format.pcmEncoding) {
                C.ENCODING_PCM_8BIT -> 8
                C.ENCODING_PCM_16BIT -> 16
                C.ENCODING_PCM_24BIT -> 24
                C.ENCODING_PCM_32BIT -> 32
                else -> null
            }

        return AudioFormatInfo(sampleRate, bitDepth, bitRate)
    }

    /**
     * Parses FLAC STREAMINFO metadata block to extract sample rate and bits per sample. STREAMINFO
     * layout (bytes 10-13 contain sample rate, channels, and bits per sample):
     * - Bytes 0-1: minimum block size
     * - Bytes 2-3: maximum block size
     * - Bytes 4-6: minimum frame size
     * - Bytes 7-9: maximum frame size
     * - Bytes 10-12 (20 bits): sample rate in Hz
     * - Byte 12 bits 3-1 (3 bits): number of channels - 1
     * - Byte 12 bit 0 + byte 13 bits 7-4 (5 bits): bits per sample - 1
     * - Bytes 13-17 (36 bits): total samples
     */
    @Suppress("MagicNumber")
    private fun parseFlacStreamInfo(data: ByteArray): Pair<Int, Int>? {
        // STREAMINFO block is 34 bytes; initializationData may have "fLaC" header (4 bytes)
        // plus metadata block header (4 bytes) before STREAMINFO
        val offset =
            when {
                data.size >= 42 &&
                    data[0] == 'f'.code.toByte() &&
                    data[1] == 'L'.code.toByte() &&
                    data[2] == 'a'.code.toByte() &&
                    data[3] == 'C'.code.toByte() -> 8 // Skip "fLaC" + block header
                data.size >= 34 -> 0 // Raw STREAMINFO
                else -> return null
            }

        if (data.size < offset + 18) return null

        // Sample rate: 20 bits starting at byte 10 of STREAMINFO
        val byte10 = data[offset + 10].toInt() and 0xFF
        val byte11 = data[offset + 11].toInt() and 0xFF
        val byte12 = data[offset + 12].toInt() and 0xFF
        val byte13 = data[offset + 13].toInt() and 0xFF

        val sampleRate = (byte10 shl 12) or (byte11 shl 4) or ((byte12 and 0xF0) shr 4)

        // Bits per sample: 5 bits (bit 0 of byte 12 + bits 7-4 of byte 13), value is stored as (bps
        // - 1)
        val bitsPerSample = (((byte12 and 0x01) shl 4) or ((byte13 and 0xF0) shr 4)) + 1

        return if (sampleRate > 0) Pair(sampleRate, bitsPerSample) else null
    }

    /**
     * Parses AAC AudioSpecificConfig to extract sample rate. Supports AAC-LC (audioObjectType=2)
     * and HE-AAC v1 (audioObjectType=5). AudioSpecificConfig layout:
     * - 5 bits: audioObjectType
     * - 4 bits: samplingFrequencyIndex (if 0xF, next 24 bits are explicit frequency)
     * - 4 bits: channelConfiguration
     * - For HE-AAC (SBR): additional extension fields
     *
     * AAC is lossy, so bit depth is the decoded PCM output (typically 16-bit).
     */
    @Suppress("MagicNumber")
    private fun parseAacAudioSpecificConfig(data: ByteArray): Pair<Int, Int>? {
        if (data.size < 2) return null

        // AAC sample rate lookup table (index 0-12, 13-14 reserved, 15 = explicit)
        val aacSampleRates =
            intArrayOf(
                96000,
                88200,
                64000,
                48000,
                44100,
                32000,
                24000,
                22050,
                16000,
                12000,
                11025,
                8000,
                7350,
            )

        var bitPosition = 0

        fun readBits(numBits: Int): Int {
            var result = 0
            repeat(numBits) {
                val byteIndex = bitPosition / 8
                val bitIndex = 7 - (bitPosition % 8)
                if (byteIndex < data.size) {
                    val bit = (data[byteIndex].toInt() shr bitIndex) and 1
                    result = (result shl 1) or bit
                }
                bitPosition++
            }
            return result
        }

        // Read audioObjectType (5 bits)
        var audioObjectType = readBits(5)
        // Extended audioObjectType
        if (audioObjectType == 31) {
            audioObjectType = 32 + readBits(6)
        }

        // Read samplingFrequencyIndex (4 bits)
        val samplingFrequencyIndex = readBits(4)
        val sampleRate =
            if (samplingFrequencyIndex == 0x0F) {
                // Explicit 24-bit sample rate
                readBits(24)
            } else if (samplingFrequencyIndex < aacSampleRates.size) {
                aacSampleRates[samplingFrequencyIndex]
            } else {
                return null // Invalid index
            }

        // Read channelConfiguration (4 bits) - not used but need to advance
        readBits(4)

        // For HE-AAC v1 (SBR), audioObjectType == 5, the extension sample rate may differ
        // but the base sample rate from above is usually what we want for playback
        // HE-AAC v2 has audioObjectType == 29

        // AAC decoded output is typically 16-bit PCM
        val bitDepth = 16

        return if (sampleRate > 0) Pair(sampleRate, bitDepth) else null
    }
}
