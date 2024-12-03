package com.tidal.sdk.flo.demo

import com.tidal.sdk.flo.core.FloClient
import com.tidal.sdk.flo.core.FloException
import com.tidal.sdk.flo.extensions.kotlincoroutines.topicAsFlow
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal typealias SelectedApiSubscriptionManager = KotlinCoroutineApiSubscriptionManager

internal class KotlinCoroutineApiSubscriptionManager(
    floClient: FloClient,
    name: String,
) : SubscriptionManager(floClient, name) {

    private val topicToJob = mutableMapOf<String, Job>()

    @Suppress("TooGenericExceptionCaught") // We don't want singled-out behavior
    override fun subscribeInternal(
        topic: String,
        onMessage: (String) -> Unit,
        onError: (Throwable) -> Unit,
        tail: Int,
    ) {
        topicToJob[topic] = GlobalScope.launch {
            floClient.topicAsFlow(topic, tail).collect {
                try {
                    onMessage(it)
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is FloException -> onError(throwable)
                        else -> throw throwable
                    }
                }
            }
        }
    }

    override fun unsubscribeInternal(topic: String) {
        topicToJob[topic]?.cancel(RequestedCancellationException())
    }
}

private class RequestedCancellationException : CancellationException()
