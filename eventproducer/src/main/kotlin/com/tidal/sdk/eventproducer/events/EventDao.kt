package com.tidal.sdk.eventproducer.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tidal.sdk.eventproducer.model.Event

@Dao
internal abstract class EventDao : EventsLocalDataSource {

    override fun insertEvent(event: Event) {
        insert(event.toEventEntity())
    }

    override fun getAllEvents(): List<Event> {
        return getAll().map { it.toEvent() }
    }

    override fun deleteEvents(ids: List<String>) {
        delete(ids)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insert(event: EventEntity): Long

    @Query("DELETE FROM events WHERE id IN (:ids)")
    protected abstract fun delete(ids: List<String>): Int

    @Query("SELECT * FROM events")
    protected abstract fun getAll(): List<EventEntity>
}
