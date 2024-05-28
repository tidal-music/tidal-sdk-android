package com.tidal.sdk.player.events.util

import android.net.ConnectivityManager
import android.net.NetworkInfo
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class ActiveMobileNetworkTypeTest {

    private val connectivityManager = mock<ConnectivityManager>()
    private val activeMobileNetworkType = ActiveMobileNetworkType(connectivityManager)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(connectivityManager)

    @Test
    fun hasNoActiveNetwork() {
        whenever(connectivityManager.activeNetworkInfo) doReturn null

        val actual = activeMobileNetworkType.value

        verify(connectivityManager).activeNetworkInfo
        assertThat(actual).isEmpty()
    }

    @Test
    fun hasAnActiveNetwork() {
        val subtypeName = "subtypeName"
        val activeNetworkInfo = mock<NetworkInfo> {
            on { it.subtypeName } doReturn subtypeName
        }
        whenever(connectivityManager.activeNetworkInfo) doReturn activeNetworkInfo

        val actual = activeMobileNetworkType.value

        verify(connectivityManager).activeNetworkInfo
        verify(activeNetworkInfo).subtypeName
        assertThat(actual).isEqualTo(subtypeName)
    }
}
