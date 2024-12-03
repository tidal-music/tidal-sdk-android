package com.tidal.sdk.flo.core.internal

import android.net.ConnectivityManager
import android.os.Handler
import com.squareup.moshi.Moshi
import com.tidal.sdk.flo.core.FloException
import okhttp3.OkHttpClient
import okhttp3.Response

internal class DispatchSubscribeRunnable
// LongParameterList -> No DI framework to avoid classpath bloat so need to pipe deps explicitly
@Suppress("LongParameterList", "MaxLineLength")
constructor(
    private val url: String,
    private val mutableState: SubscriptionManager.MutableState,
    private val operationHandler: Handler,
    private val callbackHandler: Handler,
    private val connectivityManager: ConnectivityManager,
    private val connectionRestorationNetworkConnectivityCallback: ConnectionRestorationNetworkConnectivityCallback, // ktlint-disable max-line-length parameter-wrapping
    private val okHttpClient: OkHttpClient,
    private val tokenProvider: () -> String?,
    private val retryUponAuthorizationError: (Response) -> Boolean,
    private val moshi: Moshi,
    private val terminalErrorManager: TerminalErrorManager,
    private val backoffPolicy: BackoffPolicy,
    private val topic: String,
    private val onMessage: (String) -> Unit,
    private val onError: (FloException) -> Unit,
    private val tail: Int,
) : Runnable {

    override fun run() {
        val connectionMutableState =
            mutableState.connectionMutableState ?: SubscriptionManager.ConnectionMutableState()
        mutableState.connectionMutableState = connectionMutableState
        val key = SubscriptionIdentifier(topic)
        check(connectionMutableState.desiredSubscriptions.containsKey(key)) {
            "Concurrent topic subscriptions on the same url are not supported - topic=$topic"
        }
        val subscriptionDescriptor = SubscriptionDescriptor(
            PostRunnableToHandlerFunction(
                callbackHandler,
                InvokeFunction1Runnable.Factory(onMessage),
            ),
            PostRunnableToHandlerFunction(
                callbackHandler,
                InvokeFunction1Runnable.Factory(onError),
            ),
            SubscriptionDescriptor.ReplayStrategy.Tail.ifMeaningful(tail),
        )
        connectionMutableState.desiredSubscriptions[key] = subscriptionDescriptor
        if (!mutableState.isNetworkConnectivityCallbackCurrentlyRegistered) {
            RegisterDefaultNetworkCallbackRunnable(
                mutableState,
                operationHandler,
                connectivityManager,
                connectionRestorationNetworkConnectivityCallback,
                url,
                0,
            ).run()
        }
        if (connectionMutableState.socketConnectionState is SocketConnectionState.NotConnected) {
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
        SubscribeSendCommandRunnable(
            connectionMutableState,
            moshi,
            topic,
            subscriptionDescriptor.replayStrategy,
        ).run()
    }
}
