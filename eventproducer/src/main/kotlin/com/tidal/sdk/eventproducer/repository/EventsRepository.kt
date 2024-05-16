package com.tidal.sdk.eventproducer.repository

import android.database.sqlite.SQLiteException
import com.tidal.sdk.eventproducer.EventSender
import com.tidal.sdk.eventproducer.events.EventsLocalDataSource
import com.tidal.sdk.eventproducer.model.Event
import com.tidal.sdk.eventproducer.model.MonitoringEvent
import com.tidal.sdk.eventproducer.model.MonitoringEventType
import com.tidal.sdk.eventproducer.model.Result
import com.tidal.sdk.eventproducer.monitoring.MonitoringInfo
import com.tidal.sdk.eventproducer.monitoring.MonitoringLocalDataSource
import com.tidal.sdk.eventproducer.monitoring.isNotEmpty
import com.tidal.sdk.eventproducer.network.service.SendMessageBatchResponse
import com.tidal.sdk.eventproducer.network.service.SqsService
import com.tidal.sdk.eventproducer.utils.SqsRequestParametersConverter
import com.tidal.sdk.eventproducer.utils.safeRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("TooManyFunctions")
internal class EventsRepository @Inject constructor(
    private val eventsLocalDataSource: EventsLocalDataSource,
    private val monitoringLocalDataSource: MonitoringLocalDataSource,
    private val repositoryHelper: RepositoryHelper,
    private val sqsParametersConverter: SqsRequestParametersConverter,
    private val sqsService: SqsService,
) {

    /**
     * Adds event to local database only if database limit size hasn't been reached
     */
    fun insertEvent(event: Event) {
        if (repositoryHelper.isDatabaseLimitReached()) {
            registerEventStoringFailed(event.name)
        } else {
            insertEventToDatabase(event)
        }
    }

    private fun registerEventStoringFailed(eventName: String) {
        storeNewMonitoringEvent(
            MonitoringEvent(
                MonitoringEventType.EventStoringFailed,
                eventName,
            ),
        )
        EventSender.instance?.startOutage()
    }

    @Suppress("SwallowedException")
    private fun insertEventToDatabase(event: Event) {
        try {
            eventsLocalDataSource.insertEvent(event)
            EventSender.instance?.endOutage()
        } catch (e: SQLiteException) {
            registerEventStoringFailed(event.name)
        }
    }

    fun deleteEvents(ids: List<String>) = eventsLocalDataSource.deleteEvents(ids)

    fun getAll(): List<Event> = eventsLocalDataSource.getAllEvents()

    suspend fun sendEventsToSqs(events: List<Event>): Result<SendMessageBatchResponse> {
        return sendEventBatch(events)
    }

    private suspend fun sendEventBatch(events: List<Event>): Result<SendMessageBatchResponse> {
        val sqsParameters = sqsParametersConverter.getSendEventsParameters(events)
        return if (repositoryHelper.isTokenProvided()) {
            safeRequest { sqsService.sendEventsBatch(sqsParameters) }
        } else {
            safeRequest { sqsService.sendEventsBatchPublic(sqsParameters) }
        }
    }

    fun storeNewMonitoringEvent(event: MonitoringEvent) {
        with(monitoringLocalDataSource) {
            val updatedInfo = getMonitoringInfo().updateWithEvent(event)
            insert(updatedInfo)
        }
    }

    private fun MonitoringInfo.updateWithEvent(event: MonitoringEvent): MonitoringInfo {
        val updatedInfo = when (event.type) {
            MonitoringEventType.EventStoringFailed -> this.copy(
                consentFilteredEvents = this.consentFilteredEvents,
                validationFailedEvents = this.validationFailedEvents,
                storingFailedEvents = this.storingFailedEvents.bumpValueForKeyByOne(event.name),
            )

            MonitoringEventType.EventValidationFailed -> this.copy(
                consentFilteredEvents = this.consentFilteredEvents,
                validationFailedEvents = this.validationFailedEvents.bumpValueForKeyByOne(
                    event.name,
                ),
                storingFailedEvents = this.storingFailedEvents,
            )

            MonitoringEventType.EventConsentFiltered -> this.copy(
                consentFilteredEvents = this.consentFilteredEvents.bumpValueForKeyByOne(event.name),
                validationFailedEvents = this.validationFailedEvents,
                storingFailedEvents = this.storingFailedEvents,
            )
        }
        return updatedInfo
    }

    private fun Map<String, Int>.bumpValueForKeyByOne(name: String): Map<String, Int> {
        val map = this.toMutableMap()
        val value = this[name]?.let { it + 1 } ?: 1
        map[name] = value
        return map
    }

    suspend fun sendMonitoringEvent() {
        val monitoringInfo = monitoringLocalDataSource.getMonitoringInfo()
        if (monitoringInfo.isNotEmpty()) {
            val monitoringEvent = repositoryHelper.getMonitoringEvent(monitoringInfo)
            sendEventBatch(listOf(monitoringEvent))
        }
    }

    fun clearMonitoringInfo() = monitoringLocalDataSource.insert(MonitoringInfo())
}
