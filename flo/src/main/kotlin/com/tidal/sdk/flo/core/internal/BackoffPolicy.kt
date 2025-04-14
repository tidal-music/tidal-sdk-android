package com.tidal.sdk.flo.core.internal

/**
 * Describes how to act when a connection attempt fails due to non-authorization-related issues.
 */
internal interface BackoffPolicy {

    /**
     * Implementations should return a delay to wait until the next attempt is performed, or null if
     * no further attempts should be made. If return a non-null value, implementations should make
     * sure that the value returned is strictly larger than 0. Doing otherwise may result in
     * unpredictable behavior and is not supported.
     *
     * @param accumulatedFailedAttempts How many attempts have been made during the history of this
     * backoff. Will never be less than 1.
     * @return An amount of time to wait until the next attempt is performed, in milliseconds.
     */
    fun onFailedAttempt(accumulatedFailedAttempts: Int): Long?
}
