package com.tidal.sdk.player.streamingprivileges.connection

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class IfRelevantOrCloseRunnable @AssistedInject constructor(
    @Assisted
    private val connectionMutableState: ConnectionMutableState,
    @Assisted
    private val block: () -> Unit,
    private val disconnectRunnable: DisconnectRunnable,
) : Runnable {

    override fun run() {
        if (connectionMutableState.isConnectionRelevant) {
            block()
        } else {
            disconnectRunnable.run()
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            connectionMutableState: ConnectionMutableState,
            block: () -> Unit,
        ): IfRelevantOrCloseRunnable
    }
}
