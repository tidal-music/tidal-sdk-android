package com.tidal.sdk.flo.demo

import com.tidal.sdk.flo.core.FloClient
import com.tidal.sdk.flo.core.SubscriptionHandle

internal typealias SelectedApiSubscriptionManager = CoreApiSubscriptionManager

internal class CoreApiSubscriptionManager(
    floClient: FloClient,
    name: String,
) : SubscriptionManager(floClient, name) {

    private val topicToSubscriptionHandle = mutableMapOf<String, SubscriptionHandle>()

    override fun subscribeInternal(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (Throwable) -> Unit,
        tail: Int,
    ) {
        topicToSubscriptionHandle[topic] = floClient.subscribe(topic, onMessage, onError, tail)
    }

    override fun unsubscribeInternal(topic: String) {
        topicToSubscriptionHandle[topic]?.unsubscribe()
    }
}
