package com.tidal.sdk.player.repeatableflakytest

/**
 * Denotes that a test method may be retried until it succeeds. Must be used alongside
 * [RepeatableFlakyTestRule].
 *
 * @param maxRuns The maximum number of runs to perform before giving up.
 * @see RepeatableFlakyTestRule
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class RepeatableFlakyTest(val maxRuns: Int = 5)
