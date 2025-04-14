package com.tidal.sdk.flo.core.internal

import android.net.ConnectivityManager
import android.os.Handler
import com.squareup.moshi.Moshi
import com.tidal.sdk.flo.core.FloException
import com.tidal.sdk.flo.core.SubscriptionHandle
import okhttp3.OkHttpClient
import okhttp3.Response

internal class SubscriptionManager(
    private val url: String,
    private val tokenProvider: () -> String?,
    private val retryUponAuthorizationError: (Response) -> Boolean,
    private val connectivityManager: ConnectivityManager,
    private val operationHandler: Handler,
    private val callbackHandler: Handler,
) {

    private val mutableState = MutableState()
    private val okHttpClient = OkHttpClient()
    private val moshi = Moshi.Builder()
        .add(Event.JsonAdapterFactory())
        .add(Event.Message.JsonAdapterFactory())
        .add(Command.JsonAdapterFactory())
        .add(Command.Subscribe.Data.JsonAdapterFactory())
        .build()
    private val terminalErrorManager = TerminalErrorManager()
    private val defaultBackoffPolicy = DefaultBackoffPolicy()
    private val connectionRestorationNetworkConnectivityCallback =
        ConnectionRestorationNetworkConnectivityCallback(
            operationHandler,
            mutableState,
            url,
            okHttpClient,
            tokenProvider,
            retryUponAuthorizationError,
            moshi,
            terminalErrorManager,
            defaultBackoffPolicy,
        )

    fun add(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (FloException) -> Unit,
        tail: Int,
    ): SubscriptionHandle {
        val subscriptionHandle = SubscriptionHandle(SubscriptionHandleImpl(this, topic))
        operationHandler.post(
            DispatchSubscribeRunnable(
                url,
                mutableState,
                operationHandler,
                callbackHandler,
                connectivityManager,
                connectionRestorationNetworkConnectivityCallback,
                okHttpClient,
                tokenProvider,
                retryUponAuthorizationError,
                moshi,
                terminalErrorManager,
                defaultBackoffPolicy,
                topic,
                onMessage,
                {
                    subscriptionHandle.delegate = null
                    onError(it)
                },
                tail,
            ),
        )
        return subscriptionHandle
    }

    fun remove(topic: String) {
        operationHandler.post(
            DispatchUnsubscribeRunnable(
                mutableState,
                connectivityManager,
                connectionRestorationNetworkConnectivityCallback,
                moshi,
                topic,
            ),
        )
    }

    class MutableState {

        var connectionMutableState: ConnectionMutableState? = null
        var isNetworkConnectivityCallbackCurrentlyRegistered = false
    }

    class ConnectionMutableState {

        var desiredSubscriptions = mutableMapOf<SubscriptionIdentifier, SubscriptionDescriptor>()
        var socketConnectionState: SocketConnectionState = SocketConnectionState.NotConnected
        val isConnectionRequired: Boolean
            get() = desiredSubscriptions.isNotEmpty()
    }
}
