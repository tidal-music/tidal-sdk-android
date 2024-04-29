package com.tidal.sdk.player.streamingprivileges

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class SetStreamingPrivilegesListenerRunnable @AssistedInject constructor(
    private val mutableState: MutableState,
    @Assisted
    private val streamingPrivilegesListener: StreamingPrivilegesListener?,
) : Runnable {

    override fun run() {
        mutableState.streamingPrivilegesListener = streamingPrivilegesListener
        mutableState.connectionMutableState
            ?.streamingPrivilegesListener = streamingPrivilegesListener
    }

    @AssistedFactory
    interface Factory {

        fun create(
            streamingPrivilegesListener: StreamingPrivilegesListener?,
        ): SetStreamingPrivilegesListenerRunnable
    }
}
