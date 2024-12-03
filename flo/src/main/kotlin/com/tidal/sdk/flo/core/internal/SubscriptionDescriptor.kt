package com.tidal.sdk.flo.core.internal

import com.tidal.sdk.flo.core.FloException

internal class SubscriptionDescriptor(
    val onMessage: (String) -> Unit,
    val onError: (FloException) -> Unit,
    val replayStrategy: ReplayStrategy?,
) {

    constructor(subscriptionDescriptor: SubscriptionDescriptor, replayStrategy: ReplayStrategy?) :
        this(
            subscriptionDescriptor.onMessage,
            subscriptionDescriptor.onError,
            replayStrategy,
        )

    sealed interface ReplayStrategy {

        class Tail private constructor(val tail: Int) : ReplayStrategy {

            companion object {

                fun ifMeaningful(tail: Int) = if (tail > 0) {
                    Tail(tail)
                } else {
                    null
                }
            }
        }

        class LastId(val lastId: String) : ReplayStrategy
    }
}
