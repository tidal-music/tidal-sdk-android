package androidx.media3.exoplayer.mediacodec

import androidx.media3.common.Format

internal fun MediaCodecRenderer.realSupportsFormat(
    mediaCodecSelector: MediaCodecSelector,
    format: Format,
): Int {
    return supportsFormat(mediaCodecSelector, format)
}
