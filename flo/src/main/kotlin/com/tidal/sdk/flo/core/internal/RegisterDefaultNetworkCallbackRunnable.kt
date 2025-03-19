package com.tidal.sdk.flo.core.internal

import android.net.ConnectivityManager
import android.os.Handler
import android.os.SystemClock
import kotlin.time.Duration.Companion.seconds

internal class RegisterDefaultNetworkCallbackRunnable
@Suppress("MaxLineLength")
constructor(
    private val mutableState: SubscriptionManager.MutableState,
    private val operationHandler: Handler,
    private val connectivityManager: ConnectivityManager,
    private val connectionRestorationNetworkConnectivityCallback: ConnectionRestorationNetworkConnectivityCallback, // ktlint-disable max-line-length parameter-wrapping
    private val url: String,
    private val failureCount: Int,
) : Runnable {

    // SwallowedException -> It is useless
    // TooGenericExceptionCaught -> We don't want singled-out behavior
    @Suppress("SwallowedException", "TooGenericExceptionCaught")
    override fun run() {
        if (mutableState.isNetworkConnectivityCallbackCurrentlyRegistered) {
            return
        }
        try {
            connectivityManager.registerDefaultNetworkCallback(
                connectionRestorationNetworkConnectivityCallback,
            )
            mutableState.isNetworkConnectivityCallbackCurrentlyRegistered = true
        } catch (tooManyRequestsException: RuntimeException) {
            val failuresIncludingSelf = failureCount + 1
            operationHandler.postAtTime(
                RegisterDefaultNetworkCallbackRunnable(
                    mutableState,
                    operationHandler,
                    connectivityManager,
                    connectionRestorationNetworkConnectivityCallback,
                    url,
                    failuresIncludingSelf,
                ),
                url,
                SystemClock.uptimeMillis() + 1.seconds.inWholeMilliseconds * failuresIncludingSelf,
            )
        }
    }
}
