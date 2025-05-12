package com.tidal.sdk.player.streamingprivileges.acquire

import com.google.gson.Gson
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.SocketConnectionState
import com.tidal.sdk.player.streamingprivileges.messages.WebSocketMessage
import okhttp3.WebSocket
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class AcquireRunnableTest {

    private val connectionMutableState = mock<ConnectionMutableState>()
    private val gson = mock<Gson>()
    private val startedAtMillis = -1L
    private val acquireRunnable = AcquireRunnable(connectionMutableState, gson, startedAtMillis)

    @AfterEach fun afterEach() = verifyNoMoreInteractions(connectionMutableState, gson)

    @Test
    fun runWhenWithNoRelevantConnection() {
        acquireRunnable.run()

        verify(connectionMutableState).isConnectionRelevant
    }

    @Test
    fun runWhenConnectedToSocketWithRelevantConnection() {
        whenever(connectionMutableState.isConnectionRelevant) doReturn true
        val webSocket = mock<WebSocket>()
        val socketConnectionState =
            mock<SocketConnectionState.Connected> { on { it.webSocket } doReturn webSocket }
        whenever(connectionMutableState.socketConnectionState) doReturn socketConnectionState
        val msgText = "msgText"
        whenever(gson.toJson(WebSocketMessage.Outgoing.Acquire(startedAtMillis))) doReturn msgText

        acquireRunnable.run()

        verify(connectionMutableState).isConnectionRelevant
        verify(connectionMutableState).socketConnectionState
        verify(socketConnectionState).webSocket
        verify(gson).toJson(WebSocketMessage.Outgoing.Acquire(startedAtMillis))
        verify(webSocket).send(msgText)
        verifyNoMoreInteractions(webSocket, socketConnectionState)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun runWhenNotConnectedToSocket(isConnectionRelevant: Boolean) =
        testNothingToDo(isConnectionRelevant, SocketConnectionState.NotConnected)

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun runWhenConnectingToSocket(isConnectionRelevant: Boolean) {
        val socketConnectionState = mock<SocketConnectionState.Connecting.ForReal>()

        testNothingToDo(isConnectionRelevant, socketConnectionState)

        verifyNoInteractions(socketConnectionState)
    }

    private fun testNothingToDo(
        isConnectionRelevant: Boolean,
        socketConnectionState: SocketConnectionState,
    ) {
        whenever(connectionMutableState.isConnectionRelevant) doReturn isConnectionRelevant
        if (isConnectionRelevant) {
            whenever(connectionMutableState.socketConnectionState) doReturn socketConnectionState
        }

        acquireRunnable.run()

        verify(connectionMutableState).isConnectionRelevant
        if (isConnectionRelevant) {
            verify(connectionMutableState).socketConnectionState
        }
    }
}
