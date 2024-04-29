package com.tidal.sdk.player.playbackengine.outputdevice

import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import kotlin.properties.Delegates

internal class OutputDeviceManager constructor(
    private val audioManager: AudioManager,
    private val handler: Handler,
) {

    @RequiresApi(Build.VERSION_CODES.S)
    private val bluetoothTypesApi31 = intArrayOf(
        AudioDeviceInfo.TYPE_BLUETOOTH_A2DP,
        AudioDeviceInfo.TYPE_BLE_HEADSET,
        AudioDeviceInfo.TYPE_BLE_SPEAKER,
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val bluetoothTypesApi33 = intArrayOf(
        AudioDeviceInfo.TYPE_BLUETOOTH_A2DP,
        AudioDeviceInfo.TYPE_BLE_HEADSET,
        AudioDeviceInfo.TYPE_BLE_SPEAKER,
        AudioDeviceInfo.TYPE_BLE_BROADCAST,
    )

    private val bluetoothTypes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        bluetoothTypesApi33
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        bluetoothTypesApi31
    } else {
        intArrayOf(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)
    }

    var outputDevice: OutputDevice by Delegates.observable(
        OutputDevice.TYPE_BUILTIN_SPEAKER,
    ) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            callback?.invoke(newValue)
        }
    }
        private set

    private var callback: ((OutputDevice) -> Unit)? = null

    fun start(callback: (OutputDevice) -> Unit) {
        this.callback = callback
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
        audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
            .firstOrNull { it.type in bluetoothTypes }
            ?.let {
                outputDevice = OutputDevice.TYPE_BLUETOOTH
            } ?: run {
            outputDevice = OutputDevice.TYPE_BUILTIN_SPEAKER
        }
    }
}

enum class OutputDevice {
    TYPE_BUILTIN_SPEAKER,
    TYPE_BLUETOOTH,
}
