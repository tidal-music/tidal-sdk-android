package com.tidal.sdk.player.streamingprivileges.connection.websocketevents

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState
import com.tidal.sdk.player.streamingprivileges.connection.IfRelevantOrCloseRunnable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * Converts [WebSocket] callbacks into [Runnable] instances that are posted to
 * [networkInteractionsHandler].
 */
@Suppress("LongParameterList")
internal class DumpCallbacksToHandlerWebSocketListener @AssistedInject constructor(
    private val networkInteractionsHandler: Handler,
    private val onWebSocketFailure: OnWebSocketFailure,
    private val onWebSocketMessage: OnWebSocketMessage,
    private val onWebSocketOpen: OnWebSocketOpen,
    private val ifRelevantOrCloseRunnableFactory: IfRelevantOrCloseRunnable.Factory,
    @Assisted
    private val connectionMutableState: ConnectionMutableState,
) : WebSocketListener() {

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) =
        ifRelevantOrClose {
            onWebSocketFailure(connectionMutableState)
        }

    override fun onMessage(webSocket: WebSocket, text: String) = ifRelevantOrClose {
        onWebSocketMessage(webSocket, text, connectionMutableState)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) = ifRelevantOrClose {
        onWebSocketOpen(webSocket, connectionMutableState)
    }

    private fun ifRelevantOrClose(block: () -> Unit) {
        networkInteractionsHandler
            .post(ifRelevantOrCloseRunnableFactory.create(connectionMutableState, block))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            connectionMutableState: ConnectionMutableState,
        ): DumpCallbacksToHandlerWebSocketListener
    }
}
