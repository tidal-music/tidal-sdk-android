package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.model.ConsentCategory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * A [TidalApiRetryListener] that emits one TIDAL event per retry and per budget-exhaustion through
 * [EventSender]. Telemetry only — it never affects retry behaviour. Stateless and thread-safe; the
 * interceptor may invoke it from any HTTP worker thread.
 */
internal class EventProducerRetryListener(private val eventSender: EventSender) :
    TidalApiRetryListener {

    override fun onRetry(category: ErrorCategory, attempt: Int, delayMillis: Long) {
        val payload =
            JsonObject(
                mapOf(
                    "category" to JsonPrimitive(category.name),
                    "attempt" to JsonPrimitive(attempt),
                    "delayMillis" to JsonPrimitive(delayMillis),
                )
            )
        eventSender.sendEvent(
            EVENT_RETRY,
            ConsentCategory.NECESSARY,
            Json.encodeToString(payload),
            emptyMap(),
        )
    }

    override fun onRetriesExhausted(category: ErrorCategory, attempts: Int) {
        val payload =
            JsonObject(
                mapOf(
                    "category" to JsonPrimitive(category.name),
                    "attempts" to JsonPrimitive(attempts),
                )
            )
        eventSender.sendEvent(
            EVENT_RETRIES_EXHAUSTED,
            ConsentCategory.NECESSARY,
            Json.encodeToString(payload),
            emptyMap(),
        )
    }

    private companion object {
        const val EVENT_RETRY = "tidalapi_retry"
        const val EVENT_RETRIES_EXHAUSTED = "tidalapi_retries_exhausted"
    }
}
