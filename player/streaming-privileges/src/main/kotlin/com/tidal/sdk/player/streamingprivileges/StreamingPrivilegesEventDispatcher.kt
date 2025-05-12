package com.tidal.sdk.player.streamingprivileges

import android.os.Handler
import com.tidal.sdk.player.streamingprivileges.connection.ConnectionMutableState

/**
 * Dispatches StreamingPrivileges events to listeners.
 *
 * @param networkInteractionsHandler The [Handler] to dispatch events on.
 * @param connectionMutableState The [ConnectionMutableState] for this StreamingPrivileges.
 */
internal class StreamingPrivilegesEventDispatcher(private val networkInteractionsHandler: Handler) {

    fun dispatchConnectionEstablished(connectionMutableState: ConnectionMutableState) {
        networkInteractionsHandler.post {
            connectionMutableState.streamingPrivilegesListener?.onConnectionEstablished()
        }
    }

    fun dispatchStreamingPrivilegeRevoked(
        connectionMutableState: ConnectionMutableState,
        privilegedClientDisplayName: String?,
    ) {
        networkInteractionsHandler.post {
            connectionMutableState.streamingPrivilegesListener?.onStreamingPrivilegesRevoked(
                privilegedClientDisplayName
            )
        }
    }
}
