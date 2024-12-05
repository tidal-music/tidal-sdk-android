package com.tidal.sdk.flo.core.internal

import com.squareup.moshi.Moshi

internal class SubscribeSendCommandRunnable(
    connectionMutableState: SubscriptionManager.ConnectionMutableState,
    moshi: Moshi,
    topic: String,
    replayStrategy: SubscriptionDescriptor.ReplayStrategy?,
) : SendCommandRunnable(connectionMutableState, moshi) {

    override val command = Command.Subscribe(
        topic,
        when (replayStrategy) {
            null -> null
            is SubscriptionDescriptor.ReplayStrategy.Tail ->
                Command.Subscribe.Data.Tail(replayStrategy.tail)

            is SubscriptionDescriptor.ReplayStrategy.LastId ->
                Command.Subscribe.Data.LastId(replayStrategy.lastId)
        },
    )
}
