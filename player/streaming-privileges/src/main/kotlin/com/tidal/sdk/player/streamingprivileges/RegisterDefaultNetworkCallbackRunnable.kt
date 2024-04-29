package com.tidal.sdk.player.streamingprivileges

import android.net.ConnectivityManager
import android.os.Handler
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Suppress("LongParameterList", "TooGenericExceptionCaught", "SwallowedException")
internal class RegisterDefaultNetworkCallbackRunnable @AssistedInject constructor(
    private val mutableState: MutableState,
    private val networkInteractionsHandler: Handler,
    private val connectivityManager: ConnectivityManager,
    private val factory: Factory,
    private val streamingPrivilegesNetworkCallback: StreamingPrivilegesNetworkCallback,
    @Assisted
    private val failureCount: Int,
) : Runnable {

    override fun run() {
        if (mutableState.isNetworkConnectivityCallbackCurrentlyRegistered) {
            return
        }
        try {
            connectivityManager.registerDefaultNetworkCallback(streamingPrivilegesNetworkCallback)
            mutableState.isNetworkConnectivityCallbackCurrentlyRegistered = true
        } catch (tooManyRequestsException: RuntimeException) {
            val failuresIncludingSelf = failureCount + 1
            networkInteractionsHandler.postDelayed(
                factory.create(failuresIncludingSelf),
                ONE_THOUSAND * failuresIncludingSelf,
            )
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(failureCount: Int = 0): RegisterDefaultNetworkCallbackRunnable
    }

    companion object {
        private const val ONE_THOUSAND = 1_000L
    }
}
