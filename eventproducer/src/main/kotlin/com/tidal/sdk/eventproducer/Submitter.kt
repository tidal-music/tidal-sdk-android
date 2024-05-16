package com.tidal.sdk.eventproducer

import com.tidal.sdk.eventproducer.model.ConsentCategory
import com.tidal.sdk.eventproducer.model.Event
import com.tidal.sdk.eventproducer.model.EventsConfigProvider
import com.tidal.sdk.eventproducer.model.MonitoringEvent
import com.tidal.sdk.eventproducer.model.MonitoringEventType
import com.tidal.sdk.eventproducer.repository.EventsRepository
import com.tidal.sdk.eventproducer.utils.EventSizeValidator
import com.tidal.sdk.eventproducer.utils.HeadersUtils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class Submitter @Inject constructor(
    private val configProvider: EventsConfigProvider,
    private val eventSizeValidator: EventSizeValidator,
    private val repository: EventsRepository,
    private val headersUtils: HeadersUtils,
) {

    fun sendEvent(
        eventName: String,
        consentCategory: ConsentCategory,
        payload: String,
        suppliedHeaders: Map<String, String>,
    ) {
        if (configProvider.config.blacklistedConsentCategories.contains(consentCategory)) {
            storeNewMonitoringEvent(eventName, MonitoringEventType.EventConsentFiltered)
        } else {
            val event = createEvent(eventName, consentCategory, payload, suppliedHeaders)
            if (eventSizeValidator.isEventSizeValid(event)) {
                repository.insertEvent(event)
            } else {
                storeNewMonitoringEvent(eventName, MonitoringEventType.EventValidationFailed)
            }
        }
    }

    private fun storeNewMonitoringEvent(
        eventName: String,
        monitoringEventType: MonitoringEventType,
    ) {
        repository.storeNewMonitoringEvent(
            MonitoringEvent(
                monitoringEventType,
                eventName,
            ),
        )
    }

    private fun createEvent(
        eventName: String,
        consentCategory: ConsentCategory,
        payload: String,
        suppliedHeaders: Map<String, String>,
    ): Event {
        val defaultHeaders = headersUtils.getDefaultHeaders(consentCategory, false)
        val headers = headersUtils.getEventHeaders(defaultHeaders, suppliedHeaders)
        return Event(UUID.randomUUID().toString(), eventName, headers, payload)
    }
}
