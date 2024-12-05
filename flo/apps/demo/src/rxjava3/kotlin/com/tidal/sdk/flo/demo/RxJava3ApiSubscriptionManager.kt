package com.tidal.sdk.flo.demo

import com.tidal.sdk.flo.core.FloClient
import com.tidal.sdk.flo.extensions.rxjava3.topicAsFlowable
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.Disposable

internal typealias SelectedApiSubscriptionManager = RxJava3ApiSubscriptionManager

internal class RxJava3ApiSubscriptionManager(
    floClient: FloClient,
    name: String,
) : SubscriptionManager(floClient, name) {

    private val topicToDisposable = mutableMapOf<String, Disposable>()

    override fun subscribeInternal(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (Throwable) -> Unit,
        tail: Int,
    ) {
        topicToDisposable[topic] =
            floClient.topicAsFlowable(topic, tail, BackpressureStrategy.LATEST)
                .subscribe(
                    onMessage,
                    onError,
                )
    }

    override fun unsubscribeInternal(topic: String) {
        topicToDisposable[topic]?.dispose()
    }
}
