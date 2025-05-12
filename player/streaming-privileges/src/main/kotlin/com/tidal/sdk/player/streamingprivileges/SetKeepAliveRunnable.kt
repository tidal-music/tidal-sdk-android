package com.tidal.sdk.player.streamingprivileges

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectRunnable
import com.tidal.sdk.player.streamingprivileges.connection.DisconnectRunnable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class SetKeepAliveRunnable
@AssistedInject
constructor(
    private val mutableState: MutableState,
    private val networkInteractionsHandler: Handler,
    private val connectRunnable: ConnectRunnable,
    private val disconnectRunnable: DisconnectRunnable,
    @Assisted private val newValue: Boolean,
) : Runnable {

    override fun run() {
        mutableState.keepAlive = newValue
        when (newValue) {
            true -> networkInteractionsHandler.post(connectRunnable)
            false -> networkInteractionsHandler.post(disconnectRunnable)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(newValue: Boolean): SetKeepAliveRunnable
    }
}
