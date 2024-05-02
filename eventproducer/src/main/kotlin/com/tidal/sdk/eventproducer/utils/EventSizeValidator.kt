package com.tidal.sdk.eventproducer.utils

import com.squareup.moshi.Moshi
import com.tidal.sdk.eventproducer.model.Event
import dagger.Reusable
import javax.inject.Inject

@Reusable
internal class EventSizeValidator @Inject constructor(private val moshi: Moshi) {

    fun isEventSizeValid(event: Event): Boolean {
        val adapter = moshi.adapter(Event::class.java)
        val eventSize = adapter.toJson(event).toByteArray(CHARSET).size
        return eventSize <= EVENT_SIZE_BYTES_LIMIT
    }

    companion object {
        private val CHARSET = Charsets.UTF_8
        private const val EVENT_SIZE_BYTES_LIMIT = 20480
    }
}
