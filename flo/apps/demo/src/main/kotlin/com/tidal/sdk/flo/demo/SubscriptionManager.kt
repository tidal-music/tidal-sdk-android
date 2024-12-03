package com.tidal.sdk.flo.demo

import com.tidal.sdk.flo.core.FloClient

abstract class SubscriptionManager(
    protected val floClient: FloClient,
    val name: String,
) {

    var subscribedTopics = listOf<String>()
        private set

    fun subscribe(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (Throwable) -> Unit,
        tail: Int = 0,
    ) = synchronized(this) {
        subscribedTopics = subscribedTopics + topic
        subscribeInternal(topic, onMessage, onError, tail)
    }

    fun unsubscribe(topic: String) = synchronized(this) {
        subscribedTopics = subscribedTopics - topic
        unsubscribeInternal(topic)
    }

    protected abstract fun subscribeInternal(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (Throwable) -> Unit,
        tail: Int = 0,
    )

    protected abstract fun unsubscribeInternal(topic: String)
}
