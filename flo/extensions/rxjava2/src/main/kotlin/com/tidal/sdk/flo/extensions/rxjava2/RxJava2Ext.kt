package com.tidal.sdk.flo.extensions.rxjava2

import com.tidal.sdk.flo.core.FloClient
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Subscribes to a topic using a [Flowable]-based API. Operations on the returned [Flowable] are
 * supported.
 *
 * @param backpressureStrategy Specifies a [BackpressureStrategy] to apply if incoming messages
 * outpace consumer capabilities.
 * @return A cold stream that can be used to control the subscription.
 * @see [FloClient.subscribe]
 */
@JvmOverloads
fun FloClient.topicAsFlowable(
    topic: String,
    tail: Int = 0,
    backpressureStrategy: BackpressureStrategy,
) = Flowable.create(
    { emitter ->
        val subscriptionHandle = subscribe(
            topic,
            { emitter.onNext(it) },
            {
                emitter.apply {
                    onError(it)
                    onComplete()
                }
            },
            tail,
        )
        emitter.setCancellable { subscriptionHandle.unsubscribe() }
    },
    backpressureStrategy,
)
