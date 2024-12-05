package com.tidal.sdk.flo.core.internal

internal class SubscriptionHandleImpl(
    private val subscriptionManager: SubscriptionManager,
    private val topic: String,
) {

    fun unsubscribe() = subscriptionManager.remove(topic)
}
