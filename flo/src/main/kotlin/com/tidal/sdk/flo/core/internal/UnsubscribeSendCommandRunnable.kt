package com.tidal.sdk.flo.core.internal

import com.squareup.moshi.Moshi

internal class UnsubscribeSendCommandRunnable(
    connectionMutableState: SubscriptionManager.ConnectionMutableState,
    moshi: Moshi,
    topic: String,
) : SendCommandRunnable(connectionMutableState, moshi) {

    override val command = Command.Unsubscribe(topic)
}
