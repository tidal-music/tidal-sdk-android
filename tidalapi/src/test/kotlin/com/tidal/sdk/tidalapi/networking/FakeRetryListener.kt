package com.tidal.sdk.tidalapi.networking

/** A hand-written [TidalApiRetryListener] that records every callback for assertion in tests. */
class FakeRetryListener : TidalApiRetryListener {

    data class Retry(val category: ErrorCategory, val attempt: Int, val delayMillis: Long)

    data class Exhausted(val category: ErrorCategory, val attempts: Int)

    private val _retries = mutableListOf<Retry>()
    private val _exhaustions = mutableListOf<Exhausted>()

    val retries: List<Retry>
        get() = _retries

    val exhaustions: List<Exhausted>
        get() = _exhaustions

    override fun onRetry(category: ErrorCategory, attempt: Int, delayMillis: Long) {
        _retries += Retry(category, attempt, delayMillis)
    }

    override fun onRetriesExhausted(category: ErrorCategory, attempts: Int) {
        _exhaustions += Exhausted(category, attempts)
    }
}
