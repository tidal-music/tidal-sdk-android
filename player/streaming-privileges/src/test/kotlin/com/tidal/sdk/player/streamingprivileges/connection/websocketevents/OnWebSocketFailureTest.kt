package com.tidal.sdk.player.streamingprivileges.connection.websocketevents

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

internal class OnWebSocketFailureTest {

    private val networkInteractionsHandler = mock<Handler>()
    private val connectRunnable = mock<ConnectRunnable>()
    private val onWebSocketFailure = OnWebSocketFailure(networkInteractionsHandler, connectRunnable)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(networkInteractionsHandler, connectRunnable)

    @Test
    fun invokeSetsConnectionStateAndPostsConnectRunnable() {
        val connectionMutableState = mock<ConnectionMutableState>()

        onWebSocketFailure(connectionMutableState)

        verify(connectionMutableState).socketConnectionState = SocketConnectionState.NotConnected
        verify(networkInteractionsHandler).post(connectRunnable)
        verifyNoMoreInteractions(connectionMutableState)
    }
}
