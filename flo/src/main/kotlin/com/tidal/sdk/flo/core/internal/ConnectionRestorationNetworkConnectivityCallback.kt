package com.tidal.sdk.flo.core.internal

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Handler
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Response

internal class ConnectionRestorationNetworkConnectivityCallback
// No DI framework to avoid classpath bloat so need to pipe dependencies explicitly
@Suppress("LongParameterList")
constructor(
    private val operationHandler: Handler,
    private val mutableState: SubscriptionManager.MutableState,
    private val url: String,
    private val okHttpClient: OkHttpClient,
    private val tokenProvider: () -> String?,
    private val retryUponAuthorizationError: (Response) -> Boolean,
    private val moshi: Moshi,
    private val terminalErrorManager: TerminalErrorManager,
    private val backoffPolicy: BackoffPolicy,
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        maybeDispatchConnect()
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            maybeDispatchConnect()
        }
    }

    private fun maybeDispatchConnect() = mutableState.connectionMutableState?.let {
        operationHandler.post(
            ConnectRunnable(
                url,
                it,
                operationHandler,
                okHttpClient,
                tokenProvider,
                retryUponAuthorizationError,
                moshi,
                terminalErrorManager,
                backoffPolicy,
            ),
        )
    }
}
