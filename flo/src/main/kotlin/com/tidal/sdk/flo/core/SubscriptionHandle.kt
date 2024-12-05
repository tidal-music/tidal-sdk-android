package com.tidal.sdk.flo.core

import com.tidal.sdk.flo.core.internal.SubscriptionHandleImpl

/**
 * A handle for subscription termination.
 */
class SubscriptionHandle internal constructor(delegate: SubscriptionHandleImpl?) {
    internal var delegate = delegate
        set(value) {
            requireNotNull(value)
            field = null
        }

    /**
     * Requests the associated subscription to be terminated. This method is safe to call at any
     * point.
     */
    fun unsubscribe() = delegate?.unsubscribe()
}
