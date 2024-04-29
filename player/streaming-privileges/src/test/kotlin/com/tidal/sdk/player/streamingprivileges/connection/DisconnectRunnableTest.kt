package com.tidal.sdk.player.streamingprivileges.connection

import android.net.ConnectivityManager
import com.tidal.sdk.player.streamingprivileges.MutableState
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesNetworkCallback
import okhttp3.WebSocket
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class DisconnectRunnableTest {

    private val mutableState = mock<MutableState>()
    private val networkConnectivityManager = mock<ConnectivityManager>()
    private val streamingPrivilegesNetworkCallback = mock<StreamingPrivilegesNetworkCallback>()
    private val disconnectRunnable = DisconnectRunnable(
        mutableState,
        networkConnectivityManager,
        streamingPrivilegesNetworkCallback,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        mutableState,
        networkConnectivityManager,
        streamingPrivilegesNetworkCallback,
    )

    @Test
    fun runWhenKeepAliveTrue() {
        whenever(mutableState.keepAlive) doReturn true

        disconnectRunnable.run()

        verify(mutableState).keepAlive
    }

    @ParameterizedTest
    @MethodSource("combinationsForRunWhenKeepAliveFalse")
    fun runWhenKeepAliveFalse(
        isNetworkConnectivityCallbackCurrentlyRegistered: Boolean,
        isConnectionMutableStateNull: Boolean,
        isSocketConnectionStateConnected: Boolean,
    ) {
        whenever(mutableState.isNetworkConnectivityCallbackCurrentlyRegistered)
            .thenReturn(isNetworkConnectivityCallbackCurrentlyRegistered)
        val webSocket = mock<WebSocket>()
        val socketConnectionStateConnected = mock<SocketConnectionState.Connected> {
            on { it.webSocket } doReturn webSocket
        }
        val socketConnectionStateNotConnected = mock<SocketConnectionState.NotConnected>()
        val connectionMutableState = mock<ConnectionMutableState> {
            on { it.socketConnectionState } doReturn if (isSocketConnectionStateConnected) {
                socketConnectionStateConnected
            } else {
                socketConnectionStateNotConnected
            }
        }
        if (!isConnectionMutableStateNull) {
            whenever(mutableState.connectionMutableState) doReturn connectionMutableState
        }

        disconnectRunnable.run()

        verify(mutableState).keepAlive
        verify(mutableState).isNetworkConnectivityCallbackCurrentlyRegistered
        if (isNetworkConnectivityCallbackCurrentlyRegistered) {
            verify(networkConnectivityManager)
                .unregisterNetworkCallback(streamingPrivilegesNetworkCallback)
            verify(mutableState).isNetworkConnectivityCallbackCurrentlyRegistered = false
        }
        verify(mutableState).connectionMutableState
        if (!isConnectionMutableStateNull) {
            verify(connectionMutableState).streamingPrivilegesListener = null
            verify(connectionMutableState).isConnectionRelevant = false
            verify(connectionMutableState).socketConnectionState
            if (isSocketConnectionStateConnected) {
                verify(socketConnectionStateConnected).webSocket
                verify(webSocket).close(
                    CloseReason.REASON_REQUESTED_BY_SELF.code,
                    CloseReason.REASON_REQUESTED_BY_SELF.description,
                )
                verify(connectionMutableState).socketConnectionState =
                    SocketConnectionState.NotConnected
            }
            verify(mutableState).connectionMutableState = null
        }
        verifyNoMoreInteractions(
            webSocket,
            socketConnectionStateConnected,
            socketConnectionStateNotConnected,
            connectionMutableState,
        )
    }

    companion object {

        @JvmStatic
        @Suppress("UnusedPrivateMember", "NestedBlockDepth")
        private fun combinationsForRunWhenKeepAliveFalse(): Iterable<Arguments> {
            val argumentSet = mutableSetOf<Arguments>()
            repeat(8) { // 2 pow 3
                argumentSet.add(Arguments.of(it shr 0 == 1, it shr 1 == 1, it shr 2 == 1))
            }
            return argumentSet
        }
    }
}
