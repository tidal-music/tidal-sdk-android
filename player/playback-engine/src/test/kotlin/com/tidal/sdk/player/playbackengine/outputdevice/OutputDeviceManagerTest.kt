package com.tidal.sdk.player.playbackengine.outputdevice

import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Handler
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class OutputDeviceManagerTest {

    private val audioManager = mock<AudioManager>()
    private val handler = mock<Handler>()
    private val outputDeviceManager = OutputDeviceManager(audioManager, handler)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(audioManager, handler)

    @Test
    fun `start does not change outputDevice when no callbacks are invoked`() {
        val callback = mock<(OutputDevice) -> Unit>()

        outputDeviceManager.start(callback)

        assertThat(outputDeviceManager.outputDevice).isEqualTo(OutputDevice.TYPE_BUILTIN_SPEAKER)
        verify(audioManager).registerAudioDeviceCallback(any(), any())
        verifyNoInteractions(callback)
    }

    @Test
    fun `start sets output device when callback adds devices`() {
        val callback = mock<(OutputDevice) -> Unit>()
        val audioDeviceInfo = mock<AudioDeviceInfo> {
            on { it.type } doReturn AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
        }
        whenever(audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)).thenReturn(
            arrayOf(
                audioDeviceInfo,
            ),
        )
        whenever(audioManager.registerAudioDeviceCallback(any(), any())).thenAnswer {
            val callbackArg = it.arguments[0] as AudioDeviceCallback
            val handlerArg = it.arguments[1] as Handler
            callbackArg.onAudioDevicesAdded(arrayOf(audioDeviceInfo))
            assertThat(handlerArg).isSameAs(handler)
        }

        outputDeviceManager.start(callback)

        assertThat(outputDeviceManager.outputDevice).isEqualTo(OutputDevice.TYPE_BLUETOOTH)
        verify(audioManager).registerAudioDeviceCallback(any(), any())
        verify(audioManager).getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        verify(audioDeviceInfo).type
        verify(callback).invoke(OutputDevice.TYPE_BLUETOOTH)
        verifyNoMoreInteractions(callback, audioDeviceInfo)
    }

    @Test
    fun `start sets output device when callback removes devices`() {
        val callback = mock<(OutputDevice) -> Unit>()
        val audioDeviceInfo = mock<AudioDeviceInfo> {
            on { it.type } doReturn AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
        }
        whenever(audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)).thenReturn(
            arrayOf(
                audioDeviceInfo,
            ),
        )
        whenever(audioManager.registerAudioDeviceCallback(any(), any())).thenAnswer {
            val callbackArg = it.arguments[0] as AudioDeviceCallback
            val handlerArg = it.arguments[1] as Handler
            callbackArg.onAudioDevicesRemoved(arrayOf(audioDeviceInfo))
            assertThat(handlerArg).isSameAs(handler)
        }

        outputDeviceManager.start(callback)

        assertThat(outputDeviceManager.outputDevice).isEqualTo(OutputDevice.TYPE_BLUETOOTH)
        verify(audioManager).registerAudioDeviceCallback(any(), any())
        verify(audioManager).getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        verify(audioDeviceInfo).type
        verify(callback).invoke(OutputDevice.TYPE_BLUETOOTH)
        verifyNoMoreInteractions(callback, audioDeviceInfo)
    }
}
