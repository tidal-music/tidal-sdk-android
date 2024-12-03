package com.tidal.sdk.flo.core.internal

import com.squareup.moshi.Moshi

internal abstract class SendCommandRunnable(
    private val connectionMutableState: SubscriptionManager.ConnectionMutableState,
    private val moshi: Moshi,
) : Runnable {

    abstract val command: Command

    override fun run() {
        val connectionState = connectionMutableState.socketConnectionState
        if (connectionState is SocketConnectionState.Connected) {
            connectionState.webSocket.send(moshi.adapter(Command::class.java).toJson(command))
        }
    }
}
