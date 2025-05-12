package com.tidal.sdk.player.playbackengine.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class NetworkTransportHelperTest {

    private val connectivityManager = mock<ConnectivityManager>()
    private val networkTransportHelper = NetworkTransportHelper(connectivityManager)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(connectivityManager)

    @ParameterizedTest
    @ValueSource(
        ints = [NetworkCapabilities.TRANSPORT_WIFI, NetworkCapabilities.TRANSPORT_ETHERNET]
    )
    fun isWifiOrEthernetReturnsTrueForWifiOrEthernetNetworks(networkCapability: Int) {
        hasTransportForNetworkCapability(networkCapability, true)
    }

    @ParameterizedTest
    @ValueSource(
        ints =
            [
                NetworkCapabilities.TRANSPORT_BLUETOOTH,
                NetworkCapabilities.TRANSPORT_CELLULAR,
                NetworkCapabilities.TRANSPORT_LOWPAN,
                NetworkCapabilities.TRANSPORT_USB,
                NetworkCapabilities.TRANSPORT_VPN,
                NetworkCapabilities.TRANSPORT_WIFI_AWARE,
            ]
    )
    fun isWifiOrEthernetReturnsFalseForOtherNetworks(networkCapability: Int) {
        hasTransportForNetworkCapability(networkCapability, false)
    }

    private fun hasTransportForNetworkCapability(networkCapability: Int, hasTransport: Boolean) {
        val activeNetwork = mock<Network>()
        val networkCapabilities =
            mock<NetworkCapabilities> {
                on { it.hasTransport(networkCapability) } doReturn hasTransport
            }
        whenever(connectivityManager.activeNetwork).thenReturn(activeNetwork)
        whenever(connectivityManager.getNetworkCapabilities(activeNetwork))
            .thenReturn(networkCapabilities)

        val actual = networkTransportHelper.isWifiOrEthernet()

        assertThat(actual).isEqualTo(hasTransport)
        verify(connectivityManager).activeNetwork
        verify(connectivityManager).getNetworkCapabilities(activeNetwork)
        if (networkCapability == NetworkCapabilities.TRANSPORT_WIFI) {
            verify(networkCapabilities).hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            verify(networkCapabilities).hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            verify(networkCapabilities).hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        verifyNoMoreInteractions(activeNetwork, networkCapabilities)
    }

    @Test
    fun isWifiOrEthernetReturnsFalseForMissingNetworkCapabilities() {
        val activeNetwork = mock<Network>()
        whenever(connectivityManager.activeNetwork).thenReturn(activeNetwork)
        whenever(connectivityManager.getNetworkCapabilities(mock())).thenReturn(null)

        val actual = networkTransportHelper.isWifiOrEthernet()

        assertThat(actual).isEqualTo(false)
        verify(connectivityManager).activeNetwork
        verify(connectivityManager).getNetworkCapabilities(activeNetwork)
        verifyNoMoreInteractions(activeNetwork)
    }
}
