package com.tidal.sdk.flo.core

import android.net.ConnectivityManager
import android.os.Handler
import com.tidal.sdk.flo.core.internal.SubscriptionManager
import okhttp3.Response

/**
 * A client that allows subscribing to topics on a given connection.
 *
 * @param connectivityManager A handle for Android-specific network operations.
 * @param tokenProvider A provider of tokens for authorization.
 * @param retryUponAuthorizationError Criteria to determine whether to retry or not upon receiving
 * an authorization error. If a retry should happen, we will query for a token again.
 * @param url The location for the socket connection.
 * @param operationHandler A [Handler] to run internal operations on.
 * @param callbackHandler A [Handler] to invoke callbacks on.
 */
class FloClient(
    connectivityManager: ConnectivityManager,
    tokenProvider: () -> String?,
    retryUponAuthorizationError: (Response) -> Boolean,
    url: String,
    operationHandler: Handler,
    callbackHandler: Handler,
) {

    private val subscriptionManager = SubscriptionManager(
        url,
        tokenProvider,
        retryUponAuthorizationError,
        connectivityManager,
        operationHandler,
        callbackHandler,
    )

    /**
     * Subscribe to a given topic.
     *
     * @param topic The target topic.
     * @param onMessage Message callback, invoked on the main thread.
     * @param onError Error callback, invoked on the main thread. This callback being invoked
     * implies termination of this subscription (equivalent to calling
     * [SubscriptionHandle.unsubscribe] on the return of this function), meaning you are encouraged
     * to release any references to the returned [SubscriptionHandle] when this happens.
     * @param tail Indicates the amount of messages to be replayed on subscription.
     * @return A handle to terminate the subscription.
     */
    @JvmOverloads
    fun subscribe(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (FloException) -> Unit,
        tail: Int = 0,
    ) = subscriptionManager.add(topic, onMessage, onError, tail)
}
