package com.tidal.sdk.flo.core.internal

import android.os.Handler
import com.squareup.moshi.Moshi
import com.tidal.sdk.flo.core.FloException
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

internal class ConnectionWebSocketListener
// No DI framework to avoid classpath bloat so need to pipe dependencies explicitly
@Suppress("LongParameterList")
constructor(
    private val url: String,
    private val connectionMutableState: SubscriptionManager.ConnectionMutableState,
    private val operationHandler: Handler,
    private val okHttpClient: OkHttpClient,
    private val tokenProvider: () -> String?,
    private val retryUponAuthorizationError: (Response) -> Boolean,
    private val moshi: Moshi,
    private val terminalErrorManager: TerminalErrorManager,
    private val backoffPolicy: BackoffPolicy,
) : WebSocketListener() {

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        ifRequiredOrClose(webSocket) {
            if (response?.code == HTTP_CODE_401 && !retryUponAuthorizationError(response)) {
                terminalErrorManager.dispatchErrorAndTerminateConnection(
                    connectionMutableState,
                    FloException.NotAuthorized(t),
                )
                return@ifRequiredOrClose
            }
            connectionMutableState.socketConnectionState = SocketConnectionState.NotConnected
            ConnectRunnable(
                url,
                connectionMutableState,
                operationHandler,
                okHttpClient,
                tokenProvider,
                retryUponAuthorizationError,
                moshi,
                terminalErrorManager,
                backoffPolicy,
            ).run()
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        ifRequiredOrClose(webSocket) {
            val event = moshi.adapter(Event::class.java).fromJson(text)
            if (event == null || event is Event.UnsubscribeSuccess) {
                return@ifRequiredOrClose
            }
            val subscriptionIdentifier = SubscriptionIdentifier(event.topic)
            val subscriptionDescriptor =
                connectionMutableState.desiredSubscriptions[subscriptionIdentifier]
            if (subscriptionDescriptor == null) {
                UnsubscribeSendCommandRunnable(connectionMutableState, moshi, event.topic).run()
                return@ifRequiredOrClose
            }
            if (event is Event.SubscribeSuccess) {
                connectionMutableState.desiredSubscriptions[subscriptionIdentifier] =
                    SubscriptionDescriptor(subscriptionDescriptor, null)
            } else if (event is Event.Message) {
                if (event.data.id != null) {
                    connectionMutableState.desiredSubscriptions[subscriptionIdentifier] =
                        SubscriptionDescriptor(
                            subscriptionDescriptor,
                            SubscriptionDescriptor.ReplayStrategy.LastId(event.data.id),
                        )
                }
                subscriptionDescriptor.onMessage(event.data.payload)
            }
        }
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        ifRequiredOrClose(webSocket) {
            val connected = SocketConnectionState.Connected(webSocket)
            connectionMutableState.socketConnectionState = connected
            connectionMutableState.desiredSubscriptions.forEach {
                SubscribeSendCommandRunnable(
                    connectionMutableState,
                    moshi,
                    it.key.topic,
                    it.value.replayStrategy,
                )
                    .run()
            }
        }
    }

    private fun ifRequiredOrClose(webSocket: WebSocket, block: () -> Unit) =
        operationHandler.post(IfRequiredOrCloseRunnable(connectionMutableState, webSocket, block))
}

private const val HTTP_CODE_401 = 401
