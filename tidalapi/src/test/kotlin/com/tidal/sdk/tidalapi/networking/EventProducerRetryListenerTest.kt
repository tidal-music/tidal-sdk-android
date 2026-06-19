package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.eventproducer.model.ConsentCategory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EventProducerRetryListenerTest {

    private val eventSender = FakeEventSender()
    private val listener = EventProducerRetryListener(eventSender)

    @Test
    fun `onRetry emits a single tidalapi_retry event with the retry payload`() {
        listener.onRetry(ErrorCategory.HTTP_STATUS, 1, 2000L)

        val event = eventSender.sentEvents.single()
        assertEquals("tidalapi_retry", event.eventName)
        assertEquals(ConsentCategory.NECESSARY, event.consentCategory)
        assertTrue(event.headers.isEmpty())

        val payload = Json.parseToJsonElement(event.payload).jsonObject
        assertEquals("HTTP_STATUS", payload.getValue("category").jsonPrimitive.content)
        assertEquals(1, payload.getValue("attempt").jsonPrimitive.int)
        assertEquals(2000L, payload.getValue("delayMillis").jsonPrimitive.long)
    }

    @Test
    fun `onRetriesExhausted emits a single tidalapi_retries_exhausted event with the exhaustion payload`() {
        listener.onRetriesExhausted(ErrorCategory.NETWORK, 10)

        val event = eventSender.sentEvents.single()
        assertEquals("tidalapi_retries_exhausted", event.eventName)
        assertEquals(ConsentCategory.NECESSARY, event.consentCategory)
        assertTrue(event.headers.isEmpty())

        val payload = Json.parseToJsonElement(event.payload).jsonObject
        assertEquals("NETWORK", payload.getValue("category").jsonPrimitive.content)
        assertEquals(10, payload.getValue("attempts").jsonPrimitive.int)
    }
}
