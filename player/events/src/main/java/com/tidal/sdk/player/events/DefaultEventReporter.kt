package com.tidal.sdk.player.events

import com.google.gson.Gson
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.events.converter.EventFactory
import com.tidal.sdk.player.events.model.Event

/**
 * Default [EventReporter] implementation.
 *
 * @param eventFactories A map of factories used to create [Event] instances from the corresponding
 * payloads indexed by payload class.
 * @param eventSender An [EventSender] from the EventProducer SDK.
 * @param gson A [Gson] instance used to get the content to encode.
 */
internal class DefaultEventReporter(
    private val eventFactories: Map<Class<out Event.Payload>, EventFactory<out Event.Payload>>,
    private val eventSender: EventSender,
    private val gson: Gson,
) : EventReporter {

    /**
     * Can be used to report events inheriting from [Event.Payload]. Extra event information not
     * defined in the payload type will be filled in automatically.
     */
    override fun <T : Event.Payload> report(payload: T) {
        @Suppress("UNCHECKED_CAST")
        val eventFactory = eventFactories[payload::class.java]!! as EventFactory<T>
        val event = eventFactory(payload)
        eventSender.sendEvent(
            event.name,
            event.consentCategory,
            gson.toJson(event),
            emptyMap(),
        )
    }
}
