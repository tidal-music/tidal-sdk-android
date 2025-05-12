package com.tidal.sdk.player.streamingprivileges

import android.net.Network
import android.net.NetworkCapabilities
import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

internal class StreamingPrivilegesNetworkCallbackTest {

    private val networkInteractionsHandler = mock<Handler>()
    private val connectRunnable = mock<ConnectRunnable>()
    private val streamingPrivilegesNetworkCallback =
        StreamingPrivilegesNetworkCallback(networkInteractionsHandler, connectRunnable)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(networkInteractionsHandler, connectRunnable)

    @Test
    fun onAvailableWhenNoConnectionMutableState() {
        val network = mock<Network>()

        streamingPrivilegesNetworkCallback.onAvailable(network)

        assertDispatchConnect(network)
    }

    @Test
    fun onCapabilitiesChangedWhenNetworkHasInternet() {
        val network = mock<Network>()
        val networkCapabilities =
            mock<NetworkCapabilities> {
                on { hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } doReturn true
            }

        streamingPrivilegesNetworkCallback.onCapabilitiesChanged(network, networkCapabilities)

        assertDispatchConnect(network)
        verify(networkCapabilities).hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        verifyNoMoreInteractions(networkCapabilities)
    }

    @Test
    fun onCapabilitiesChangedWhenNetworkHasNoInternet() {
        val network = mock<Network>()
        val networkCapabilities = mock<NetworkCapabilities>()

        streamingPrivilegesNetworkCallback.onCapabilitiesChanged(network, networkCapabilities)

        verify(networkCapabilities).hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        verifyNoMoreInteractions(network, networkCapabilities)
    }

    private fun assertDispatchConnect(network: Network) {
        verify(networkInteractionsHandler).post(connectRunnable)

        verifyNoMoreInteractions(network)
    }
}
