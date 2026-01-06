package com.tidal.sdk.player.playbackengine.outputdevice

import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import kotlin.properties.Delegates

internal class OutputDeviceManager
constructor(private val audioManager: AudioManager, private val handler: Handler) {

    @RequiresApi(Build.VERSION_CODES.S)
    private val bluetoothTypesApi31 =
        intArrayOf(
            AudioDeviceInfo.TYPE_BLUETOOTH_A2DP,
            AudioDeviceInfo.TYPE_BLE_HEADSET,
            AudioDeviceInfo.TYPE_BLE_SPEAKER,
        )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val bluetoothTypesApi33 =
        intArrayOf(
            AudioDeviceInfo.TYPE_BLUETOOTH_A2DP,
            AudioDeviceInfo.TYPE_BLE_HEADSET,
            AudioDeviceInfo.TYPE_BLE_SPEAKER,
            AudioDeviceInfo.TYPE_BLE_BROADCAST,
        )

    private val bluetoothTypes =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bluetoothTypesApi33
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            bluetoothTypesApi31
        } else {
            intArrayOf(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)
        }

    private val usbDeviceTypes =
        intArrayOf(
            AudioDeviceInfo.TYPE_USB_DEVICE,
            AudioDeviceInfo.TYPE_USB_HEADSET,
        )

    var outputDevice: OutputDevice by
        Delegates.observable(OutputDevice.TYPE_BUILTIN_SPEAKER) { _, oldValue, newValue ->
            if (oldValue != newValue) {
                callback?.invoke(newValue)
            }
        }
        private set

    var connectedUsbDevice: UsbAudioDevice? by
        Delegates.observable(null) { _, oldValue, newValue ->
            if (oldValue != newValue) {
                usbDeviceCallback?.invoke(newValue)
            }
        }
        private set

    private var callback: ((OutputDevice) -> Unit)? = null
    private var usbDeviceCallback: ((UsbAudioDevice?) -> Unit)? = null

    fun start(
        callback: (OutputDevice) -> Unit,
        usbDeviceCallback: ((UsbAudioDevice?) -> Unit)? = null,
    ) {
        this.callback = callback
        this.usbDeviceCallback = usbDeviceCallback
        audioManager.registerAudioDeviceCallback(
            object : AudioDeviceCallback() {
                override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>?) {
                    update()
                }

                override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>?) {
                    update()
                }
            },
            handler,
        )
    }

    private fun update() {
        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            devices.firstOrNull { it.type in usbDeviceTypes }?.let { usbDevice ->
                outputDevice = OutputDevice.TYPE_USB_DAC
                connectedUsbDevice = UsbAudioDevice(
                    deviceInfo = usbDevice,
                    supportedSampleRates = usbDevice.sampleRates,
                    supportedEncodings = usbDevice.encodings,
                    productName = usbDevice.productName,
                )
                return
            }
        }

        connectedUsbDevice = null

        devices.firstOrNull { it.type in bluetoothTypes }
            ?.let { outputDevice = OutputDevice.TYPE_BLUETOOTH }
            ?: run { outputDevice = OutputDevice.TYPE_BUILTIN_SPEAKER }
    }
}

enum class OutputDevice {
    TYPE_BUILTIN_SPEAKER,
    TYPE_BLUETOOTH,
    TYPE_USB_DAC,
}
