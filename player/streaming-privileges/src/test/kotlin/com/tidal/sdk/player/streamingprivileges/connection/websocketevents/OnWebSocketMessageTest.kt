package com.tidal.sdk.player.streamingprivileges.connection.websocketevents

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.StreamingPrivilegesEventDispatcher
import com.tidal.sdk.player.streamingprivileges.connection.CloseReason
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState
import com.tidal.sdk.player.streamingprivileges.messages.WebSocketMessage
import com.tidal.sdk.player.streamingprivileges.messages.incoming.IncomingWebSocketMessageParser
import okhttp3.WebSocket
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class OnWebSocketMessageTest {

    private val networkInteractionsHandler = mock<Handler>()
    private val connectRunnable = mock<ConnectRunnable>()
    private val incomingWebSocketMessageParser = mock<IncomingWebSocketMessageParser>()
    private val streamingPrivilegesEventDispatcher = mock<StreamingPrivilegesEventDispatcher>()
    private val onWebSocketMessage = OnWebSocketMessage(
        networkInteractionsHandler,
        connectRunnable,
        incomingWebSocketMessageParser,
        streamingPrivilegesEventDispatcher,
    )

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(
        networkInteractionsHandler,
        connectRunnable,
        incomingWebSocketMessageParser,
        streamingPrivilegesEventDispatcher,
    )

    @Test
    fun invokeForReconnect() {
        val webSocket = mock<WebSocket>()
        val text = "text"
        val message = mock<WebSocketMessage.Incoming.Reconnect>()
        whenever(incomingWebSocketMessageParser.parse(text)) doReturn message
        val connectionMutableState = mock<ConnectionMutableState>()

        onWebSocketMessage(webSocket, text, connectionMutableState)

        verify(incomingWebSocketMessageParser).parse(text)
        verify(webSocket).close(
            CloseReason.REASON_REQUESTED_BY_PEER.code,
            CloseReason.REASON_REQUESTED_BY_PEER.description,
        )
        verify(connectionMutableState).socketConnectionState = SocketConnectionState.NotConnected
        verify(networkInteractionsHandler).post(connectRunnable)
        verifyNoMoreInteractions(webSocket, message, connectionMutableState)
    }

    @Test
    fun invokeForStreamingPrivilegesRevoked() {
        val webSocket = mock<WebSocket>()
        val connectionMutableState = mock<ConnectionMutableState>()
        val privilegedClientDisplayName = "privilegedClientDisplayName"
        val text = "text"
        val message = mock<WebSocketMessage.Incoming.StreamingPrivilegesRevoked> {
            on { it.privilegedClientDisplayName } doReturn privilegedClientDisplayName
        }
        whenever(incomingWebSocketMessageParser.parse(text)) doReturn message

        onWebSocketMessage(webSocket, text, connectionMutableState)

        verify(incomingWebSocketMessageParser).parse(text)
        verify(message).privilegedClientDisplayName
        verify(streamingPrivilegesEventDispatcher)
            .dispatchStreamingPrivilegeRevoked(connectionMutableState, privilegedClientDisplayName)
        verifyNoMoreInteractions(webSocket, connectionMutableState, message)
    }
}
