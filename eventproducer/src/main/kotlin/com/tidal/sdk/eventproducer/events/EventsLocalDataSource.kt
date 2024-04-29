package com.tidal.sdk.eventproducer.events

import com.tidal.sdk.eventproducer.model.Event

interface EventsLocalDataSource {
    fun insertEvent(event: Event)
    fun deleteEvents(ids: List<String>)
    fun getAllEvents(): List<Event>
}
