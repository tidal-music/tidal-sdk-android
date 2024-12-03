package com.tidal.sdk.flo.extensions.kotlincoroutines

import com.tidal.sdk.flo.core.FloClient
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

/**
 * Subscribes to a topic using a [Flow]-based API. Operations on the returned [Flow], such as
 * buffering management via [Flow.buffer], are supported.
 *
 * @return A cold stream that can be used to control the subscription.
 * @see [FloClient.subscribe]
 */
@JvmOverloads
fun FloClient.topicAsFlow(topic: String, tail: Int = 0) = callbackFlow {
    val subscriptionHandle = subscribe(
        topic,
        { trySendBlocking(it) },
        { cancel("There has been a terminal error.", it) },
        tail,
    )
    awaitClose { subscriptionHandle.unsubscribe() }
}
