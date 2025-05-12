package com.tidal.sdk.eventproducer.scheduler

import com.tidal.sdk.eventproducer.model.Event
import com.tidal.sdk.eventproducer.repository.EventsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay

@Singleton
internal class SendEventBatchScheduler
@Inject
constructor(private val repository: EventsRepository) {

    suspend fun scheduleBatchAndSend() {
        while (true) {
            delay(SLEEP_DURATION_MS)
            val allEvents = repository.getAll()
            if (allEvents.isNotEmpty()) {
                allEvents.chunked(MAX_BATCH_SIZE).forEach { eventBatch ->
                    sendEventBatch(eventBatch)
                }
            }
        }
    }

    private suspend fun sendEventBatch(eventBatch: List<Event>) {
        val response = repository.sendEventsToSqs(eventBatch)
        val successfullySentEventIds =
            response.successData?.result?.successfullySentEntries?.map { it.id } ?: emptyList()
        successfullySentEventIds.let { repository.deleteEvents(it) }
    }

    companion object {
        const val MAX_BATCH_SIZE = 10
        const val SLEEP_DURATION_MS = 30000L
    }
}
