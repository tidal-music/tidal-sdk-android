package com.tidal.sdk.player.playbackengine.exclusivemode

import com.tidal.sdk.player.common.model.AudioQuality
import com.tidal.sdk.player.common.model.UsbDacExclusiveMode
import com.tidal.sdk.player.playbackengine.outputdevice.OutputDevice
import com.tidal.sdk.player.playbackengine.outputdevice.UsbAudioDevice

internal class UsbDacExclusiveModeRepository(
    var usbDacExclusiveMode: UsbDacExclusiveMode = UsbDacExclusiveMode.OFF,
) {
    fun shouldUseExclusiveMode(
        outputDevice: OutputDevice,
        usbDevice: UsbAudioDevice?,
        audioQuality: AudioQuality?,
    ): Boolean {
        if (usbDacExclusiveMode == UsbDacExclusiveMode.OFF) return false
        if (outputDevice != OutputDevice.TYPE_USB_DAC) return false
        if (usbDevice == null) return false
        if (audioQuality == null) return false
        if (audioQuality !in listOf(AudioQuality.LOSSLESS, AudioQuality.HI_RES_LOSSLESS)) {
            return false
        }
        return true
    }
}
