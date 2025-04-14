package com.tidal.sdk.flo.core.internal

import okhttp3.WebSocket

internal sealed interface SocketConnectionState {

    object NotConnected : SocketConnectionState

    sealed interface Connecting : SocketConnectionState {

        data class AwaitingBackoffExpiry(val retryAtMillis: Long) : Connecting

        object ForReal : Connecting
    }

    data class Connected(val webSocket: WebSocket) : SocketConnectionState
}
