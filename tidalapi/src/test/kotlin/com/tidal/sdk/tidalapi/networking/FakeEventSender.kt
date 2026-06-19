package com.tidal.sdk.tidalapi.networking

import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.model.ConsentCategory

/** A hand-written [EventSender] that records every [sendEvent] call for assertion in tests. */
class FakeEventSender : EventSender {

    data class SentEvent(
        val eventName: String,
        val consentCategory: ConsentCategory,
        val payload: String,
        val headers: Map<String, String>,
    )

    private val _sentEvents = mutableListOf<SentEvent>()

    val sentEvents: List<SentEvent>
        get() = _sentEvents

    override fun sendEvent(
        eventName: String,
        consentCategory: ConsentCategory,
        payload: String,
        headers: Map<String, String>,
    ) {
        _sentEvents += SentEvent(eventName, consentCategory, payload, headers)
    }

    override fun setBlockedConsentCategories(blockedConsentCategories: Set<ConsentCategory>) = Unit
}
