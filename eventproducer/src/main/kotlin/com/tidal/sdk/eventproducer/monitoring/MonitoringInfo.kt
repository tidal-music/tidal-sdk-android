package com.tidal.sdk.eventproducer.monitoring

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MonitoringInfo(
    var consentFilteredEvents: Map<String, Int> = mapOf(),
    var validationFailedEvents: Map<String, Int> = mapOf(),
    var storingFailedEvents: Map<String, Int> = mapOf(),
)

internal fun MonitoringEntity.toMonitoringInfo(): MonitoringInfo {
    return MonitoringInfo(
        consentFilteredEvents = consentFilteredEvents,
        validationFailedEvents = validationFailedEvents,
        storingFailedEvents = storingFailedEvents,
    )
}

internal fun MonitoringInfo.toMonitoringEntity(): MonitoringEntity {
    return MonitoringEntity(
        consentFilteredEvents = consentFilteredEvents,
        validationFailedEvents = validationFailedEvents,
        storingFailedEvents = storingFailedEvents,
    )
}

internal fun MonitoringInfo.isNotEmpty(): Boolean {
    return consentFilteredEvents.isNotEmpty() ||
        validationFailedEvents.isNotEmpty() ||
        storingFailedEvents.isNotEmpty()
}
