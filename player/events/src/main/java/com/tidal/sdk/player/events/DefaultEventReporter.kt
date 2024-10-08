package com.tidal.sdk.player.events

import com.google.gson.Gson
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.player.common.model.BaseMediaProduct.Extras
import com.tidal.sdk.player.events.converter.EventFactory
import com.tidal.sdk.player.events.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Default [EventReporter] implementation.
 *
 * @param eventFactories A map of factories used to create [Event] instances from the corresponding
 * payloads indexed by payload class.
 * @param eventSender An [EventSender] from the EventProducer SDK.
 * @param gson A [Gson] instance used to get the content to encode.
 * @param coroutineScope A [CoroutineScope] instance used to dispatch event assembly.
 */
internal class DefaultEventReporter(
    private val eventFactories: Map<Class<out Event.Payload>, EventFactory<out Event.Payload>>,
    private val eventSender: EventSender,
    private val gson: Gson,
    private val coroutineScope: CoroutineScope,
) : EventReporter {

    /**
     * Can be used to report events inheriting from [Event.Payload]. Extra event information not
     * defined in the payload type will be filled in automatically.
     */
    override fun <T : Event.Payload> report(payload: T, extras: Extras?) {
        @Suppress("UNCHECKED_CAST")
        val eventFactory = eventFactories[payload::class.java]!! as EventFactory<T>
        coroutineScope.launch {
            val event = eventFactory(payload, extras)
            eventSender.sendEvent(
                event.name,
                event.consentCategory,
                gson.toJson(event),
                emptyMap(),
            )
        }
    }
}
