package com.tidal.sdk.eventproducer.scheduler

import com.tidal.sdk.eventproducer.repository.EventsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay

@Singleton
internal class MonitoringScheduler @Inject constructor(private val repository: EventsRepository) {

    suspend fun scheduleSendMonitoringInfo() {
        while (true) {
            delay(MONITORING_SLEEP_DURATION_MS)
            sendMonitoringEvent()
        }
    }

    private suspend fun sendMonitoringEvent() {
        repository.sendMonitoringEvent()
        repository.clearMonitoringInfo()
    }

    companion object {

        const val MONITORING_SLEEP_DURATION_MS = 60000L
    }
}
