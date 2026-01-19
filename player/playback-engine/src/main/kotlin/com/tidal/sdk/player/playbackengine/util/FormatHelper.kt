package com.tidal.sdk.player.playbackengine.util

import androidx.media3.common.Format
import com.tidal.sdk.player.common.model.AudioFormat
import com.tidal.sdk.player.common.model.AudioMode
import com.tidal.sdk.player.common.model.AudioQuality

/** Helper object for extracting audio format information from Format. */
object FormatHelper {

    data class AudioFormatInfo(
        val audioQuality: AudioQuality,
        val audioMode: AudioMode,
        val bitRate: Int?,
        val codec: String?,
        val sampleRate: Int? = null,
        val bitDepth: Int? = null,
    )

    private const val CODEC_AAC = "aac"
    private const val CODEC_FLAC = "flac"
    private const val CODEC_EAC3_JOC = "eac3_joc"

    @Suppress("MagicNumber")
    fun extractAudioFormatInfo(format: Format): AudioFormatInfo? {
        val parts = format.id?.split(",") ?: return null
        val audioFormat = getAudioFormat(parts[0])
        val quality = audioFormat.getAudioQuality()
        val bitRate = format.bitrate.takeIf { it != Format.NO_VALUE }
        val audioMode = getAudioMode(audioFormat)
        val codec = getCodec(audioMode, quality)

        if (quality == AudioQuality.HI_RES_LOSSLESS || quality == AudioQuality.LOSSLESS) {
            val sampleRate = parts.getOrNull(1)?.toIntOrNull()
            val bitDepth = parts.getOrNull(2)?.toIntOrNull()
            return AudioFormatInfo(
                audioQuality = quality,
                audioMode = audioMode,
                bitRate = bitRate,
                codec = codec,
                sampleRate = sampleRate,
                bitDepth = bitDepth,
            )
        }

        return AudioFormatInfo(
            audioQuality = quality,
            audioMode = audioMode,
            bitRate = bitRate,
            codec = codec,
        )
    }

    private fun getAudioFormat(format: String): AudioFormat {
        return when (format) {
            AudioFormat.FLAC_HIRES.name -> AudioFormat.FLAC_HIRES
            AudioFormat.FLAC.name -> AudioFormat.FLAC
            AudioFormat.AACLC.name -> AudioFormat.AACLC
            AudioFormat.EAC3_JOC.name -> AudioFormat.EAC3_JOC
            else -> AudioFormat.HEAACV1
        }
    }

    private fun AudioFormat.getAudioQuality(): AudioQuality {
        return when (this) {
            AudioFormat.FLAC_HIRES -> AudioQuality.HI_RES_LOSSLESS
            AudioFormat.FLAC -> AudioQuality.LOSSLESS
            AudioFormat.AACLC -> AudioQuality.HIGH
            AudioFormat.HEAACV1,
            AudioFormat.EAC3_JOC -> AudioQuality.LOW
        }
    }

    private fun getCodec(audioMode: AudioMode, audioQuality: AudioQuality): String? {
        if (audioMode == AudioMode.DOLBY_ATMOS) return CODEC_EAC3_JOC
        return when (audioQuality) {
            AudioQuality.LOW,
            AudioQuality.HIGH -> CODEC_AAC
            AudioQuality.LOSSLESS,
            AudioQuality.HI_RES_LOSSLESS -> CODEC_FLAC
        }
    }

    private fun getAudioMode(audioFormat: AudioFormat): AudioMode {
        if (audioFormat == AudioFormat.EAC3_JOC) {
            return AudioMode.DOLBY_ATMOS
        }

        return AudioMode.STEREO
    }
}
