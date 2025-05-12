package com.tidal.sdk.player.events.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.tidal.sdk.player.events.model.StreamingSessionStart
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class ActiveNetworkTypeTest {

    private val connectivityManager = mock<ConnectivityManager>()
    private val activeNetworkType = ActiveNetworkType(connectivityManager)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(connectivityManager)

    @Test
    fun hasNoActiveNetwork() {
        whenever(connectivityManager.activeNetwork) doReturn null

        val actual = activeNetworkType.value

        verify(connectivityManager).activeNetwork
        assertThat(actual).isEqualTo(StreamingSessionStart.NetworkType.NONE)
    }

    @ParameterizedTest
    @ValueSource(
        ints =
            [
                NetworkCapabilities.TRANSPORT_ETHERNET,
                NetworkCapabilities.TRANSPORT_WIFI,
                NetworkCapabilities.TRANSPORT_CELLULAR,
                -7,
            ]
    )
    fun hasAnActiveNetwork(transport: Int) {
        val networkCapabilities =
            mock<NetworkCapabilities> { on { hasTransport(transport) } doReturn true }
        val activeNetwork = mock<Network>()
        whenever(connectivityManager.activeNetwork) doReturn activeNetwork
        whenever(connectivityManager.getNetworkCapabilities(activeNetwork))
            .thenReturn(networkCapabilities)
        val expected =
            when (transport) {
                NetworkCapabilities.TRANSPORT_ETHERNET -> StreamingSessionStart.NetworkType.ETHERNET
                NetworkCapabilities.TRANSPORT_WIFI -> StreamingSessionStart.NetworkType.WIFI
                NetworkCapabilities.TRANSPORT_CELLULAR -> StreamingSessionStart.NetworkType.MOBILE
                else -> StreamingSessionStart.NetworkType.OTHER
            }

        val actual = activeNetworkType.value

        verify(connectivityManager).activeNetwork
        verify(connectivityManager).getNetworkCapabilities(activeNetwork)
        if (
            transport in
                arrayOf(
                    NetworkCapabilities.TRANSPORT_ETHERNET,
                    NetworkCapabilities.TRANSPORT_WIFI,
                    NetworkCapabilities.TRANSPORT_CELLULAR,
                )
        ) {
            verify(networkCapabilities).hasTransport(transport)
        }
        assertThat(actual).isEqualTo(expected)
    }
}
