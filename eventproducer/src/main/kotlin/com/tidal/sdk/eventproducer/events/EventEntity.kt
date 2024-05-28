package com.tidal.sdk.eventproducer.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tidal.sdk.eventproducer.model.Event

@Entity(tableName = "events")
internal data class EventEntity(
    @PrimaryKey val id: String,
    val name: String,
    val headers: Map<String, String>,
    val payload: String,
)

internal fun EventEntity.toEvent(): Event = Event(id, name, headers, payload)

internal fun Event.toEventEntity(): EventEntity = EventEntity(id, name, headers, payload)
