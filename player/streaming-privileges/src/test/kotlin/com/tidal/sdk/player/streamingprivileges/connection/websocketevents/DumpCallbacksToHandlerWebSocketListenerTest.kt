package com.tidal.sdk.player.streamingprivileges.connection.websocketevents

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.IfRelevantOrCloseRunnable
import okhttp3.Response
import okhttp3.WebSocket
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class DumpCallbacksToHandlerWebSocketListenerTest {

    private val networkInteractionsHandler = mock<Handler>()
    private val onWebSocketFailure = mock<OnWebSocketFailure>()
    private val onWebSocketMessage = mock<OnWebSocketMessage>()
    private val onWebSocketOpen = mock<OnWebSocketOpen>()
    private val ifRelevantOrCloseRunnableFactory = mock<IfRelevantOrCloseRunnable.Factory>()
    private val connectionMutableState = mock<ConnectionMutableState>()
    private val dumpCallbacksToHandlerWebSocketListener =
        DumpCallbacksToHandlerWebSocketListener(
            networkInteractionsHandler,
            onWebSocketFailure,
            onWebSocketMessage,
            onWebSocketOpen,
            ifRelevantOrCloseRunnableFactory,
            connectionMutableState,
        )

    @AfterEach
    fun afterEach() =
        verifyNoMoreInteractions(
            networkInteractionsHandler,
            onWebSocketFailure,
            onWebSocketMessage,
            onWebSocketOpen,
            ifRelevantOrCloseRunnableFactory,
            connectionMutableState,
        )

    @Test
    fun onFailurePostsRightRunnable() {
        val webSocket = mock<WebSocket>()
        val throwable = mock<Throwable>()
        val response = mock<Response>()
        val blockCaptor = argumentCaptor<() -> Unit>()
        val ifRelevantOrCloseRunnable = mock<IfRelevantOrCloseRunnable>()
        whenever(
            ifRelevantOrCloseRunnableFactory.create(eq(connectionMutableState), any<() -> Unit>())
        ) doReturn ifRelevantOrCloseRunnable

        dumpCallbacksToHandlerWebSocketListener.onFailure(webSocket, throwable, response)

        verify(ifRelevantOrCloseRunnableFactory)
            .create(eq(connectionMutableState), blockCaptor.capture())
        verify(networkInteractionsHandler).post(ifRelevantOrCloseRunnable)
        verifyNoInteractions(
            onWebSocketFailure,
            webSocket,
            throwable,
            response,
            ifRelevantOrCloseRunnable,
        )

        blockCaptor.firstValue()

        verify(onWebSocketFailure)(connectionMutableState)
        verifyNoInteractions(webSocket, throwable, response, ifRelevantOrCloseRunnable)
    }

    @Test
    fun onMessagePostsRightRunnable() {
        val webSocket = mock<WebSocket>()
        val message = "message"
        val blockCaptor = argumentCaptor<() -> Unit>()
        val ifRelevantOrCloseRunnable = mock<IfRelevantOrCloseRunnable>()
        whenever(
            ifRelevantOrCloseRunnableFactory.create(eq(connectionMutableState), any<() -> Unit>())
        ) doReturn ifRelevantOrCloseRunnable

        dumpCallbacksToHandlerWebSocketListener.onMessage(webSocket, message)

        verify(ifRelevantOrCloseRunnableFactory)
            .create(eq(connectionMutableState), blockCaptor.capture())
        verify(networkInteractionsHandler).post(ifRelevantOrCloseRunnable)
        verifyNoInteractions(onWebSocketMessage, webSocket, ifRelevantOrCloseRunnable)

        blockCaptor.firstValue()

        verify(onWebSocketMessage)(webSocket, message, connectionMutableState)
        verifyNoInteractions(webSocket, ifRelevantOrCloseRunnable)
    }

    @Test
    fun onOpenPostsRightRunnable() {
        val webSocket = mock<WebSocket>()
        val response = mock<Response>()
        val blockCaptor = argumentCaptor<() -> Unit>()
        val ifRelevantOrCloseRunnable = mock<IfRelevantOrCloseRunnable>()
        whenever(
            ifRelevantOrCloseRunnableFactory.create(eq(connectionMutableState), any<() -> Unit>())
        ) doReturn ifRelevantOrCloseRunnable

        dumpCallbacksToHandlerWebSocketListener.onOpen(webSocket, response)

        verify(ifRelevantOrCloseRunnableFactory)
            .create(eq(connectionMutableState), blockCaptor.capture())
        verify(networkInteractionsHandler).post(ifRelevantOrCloseRunnable)
        verifyNoInteractions(onWebSocketOpen, webSocket, response, ifRelevantOrCloseRunnable)

        blockCaptor.firstValue()

        verify(onWebSocketOpen)(webSocket, connectionMutableState)
        verifyNoInteractions(webSocket, response, ifRelevantOrCloseRunnable)
    }
}
