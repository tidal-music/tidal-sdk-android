package com.tidal.sdk.flo.demo

import com.tidal.sdk.flo.core.FloClient
import com.tidal.sdk.flo.extensions.rxjava.topicAsObservable
import rx.Emitter
import rx.Subscription

internal typealias SelectedApiSubscriptionManager = RxJavaApiSubscriptionManager

internal class RxJavaApiSubscriptionManager(
    floClient: FloClient,
    name: String,
) : SubscriptionManager(floClient, name) {

    private val topicToSubscription = mutableMapOf<String, Subscription>()

    override fun subscribeInternal(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (Throwable) -> Unit,
        tail: Int,
    ) {
        topicToSubscription[topic] = floClient.topicAsObservable(
            topic,
            tail,
            Emitter.BackpressureMode.LATEST,
        )
            .subscribe(
                onMessage,
                onError,
            )
    }

    override fun unsubscribeInternal(topic: String) {
        topicToSubscription[topic]?.unsubscribe()
    }
}
