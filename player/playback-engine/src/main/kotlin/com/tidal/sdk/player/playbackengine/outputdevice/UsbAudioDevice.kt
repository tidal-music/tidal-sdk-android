package com.tidal.sdk.player.playbackengine.outputdevice

import android.media.AudioDeviceInfo

data class UsbAudioDevice(
    val deviceInfo: AudioDeviceInfo,
    val supportedSampleRates: IntArray,
    val supportedEncodings: IntArray,
    val productName: CharSequence?,
) {
    fun supportsSampleRate(sampleRate: Int): Boolean =
        supportedSampleRates.isEmpty() || sampleRate in supportedSampleRates

    fun supportsEncoding(encoding: Int): Boolean =
        supportedEncodings.isEmpty() || encoding in supportedEncodings

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UsbAudioDevice

        if (deviceInfo.id != other.deviceInfo.id) return false
        if (!supportedSampleRates.contentEquals(other.supportedSampleRates)) return false
        if (!supportedEncodings.contentEquals(other.supportedEncodings)) return false
        if (productName != other.productName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deviceInfo.id.hashCode()
        result = 31 * result + supportedSampleRates.contentHashCode()
        result = 31 * result + supportedEncodings.contentHashCode()
        result = 31 * result + (productName?.hashCode() ?: 0)
        return result
    }
}
