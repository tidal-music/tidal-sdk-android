package com.tidal.sdk.flo.extensions.rxjava

import com.tidal.sdk.flo.core.FloClient
import rx.Emitter.BackpressureMode
import rx.Observable

/**
 * Subscribes to a topic using an [Observable]-based API. Operations on the returned [Observable]
 * are supported.
 *
 * @param backpressureMode Specifies a [BackpressureMode] to apply if incoming messages outpace
 * consumer capabilities.
 * @return A cold stream that can be used to control the subscription.
 * @see [FloClient.subscribe]
 */
@JvmOverloads
fun FloClient.topicAsObservable(topic: String, tail: Int = 0, backpressureMode: BackpressureMode) =
    Observable.create(
        { emitter ->
            val subscriptionHandle = subscribe(
                topic,
                { emitter.onNext(it) },
                {
                    emitter.apply {
                        onError(it)
                        onCompleted()
                    }
                },
                tail,
            )
            emitter.setCancellation { subscriptionHandle.unsubscribe() }
        },
        backpressureMode,
    )
