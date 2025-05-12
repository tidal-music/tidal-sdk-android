package com.tidal.sdk.player.streamingprivileges.connection

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import okhttp3.WebSocket

internal sealed interface SocketConnectionState {

    val failedAttemptsOrZero: Int
        get() = if (this is Connecting) failedAttempts else 0

    object NotConnected : SocketConnectionState

    sealed interface Connecting : SocketConnectionState {

        val failedAttempts: Int

        data class AwaitingBackOffExpiry
        @AssistedInject
        constructor(@Assisted val retryAtMillis: Long, @Assisted override val failedAttempts: Int) :
            Connecting {

            @AssistedFactory
            interface Factory {

                fun create(retryAtMillis: Long, failedAttempts: Int): AwaitingBackOffExpiry
            }
        }

        data class ForReal @AssistedInject constructor(@Assisted override val failedAttempts: Int) :
            Connecting {

            @AssistedFactory
            interface Factory {

                fun create(failedAttempts: Int): ForReal
            }
        }
    }

    class Connected @AssistedInject constructor(@Assisted val webSocket: WebSocket) :
        SocketConnectionState {

        @AssistedFactory
        interface Factory {

            fun create(webSocket: WebSocket): Connected
        }
    }
}
